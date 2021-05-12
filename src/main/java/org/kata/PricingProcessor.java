package org.kata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.kata.exception.DataException;
import org.kata.model.input.Tap;
import org.kata.model.input.Taps;
import org.kata.model.output.CustomerSummaries;
import org.kata.model.output.CustomerSummary;
import org.kata.model.output.Trip;
import org.kata.model.process.InvoicedJourney;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PricingProcessor {

    private static final Charset ENCODING = UTF_8;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File input;
    private final File output;
    private final JourneyEvaluator journeyEvaluator = new JourneyEvaluator();
    private static final Comparator<Tap> compareByTime = Comparator.comparing(Tap::getUnixTimestamp);

    public PricingProcessor(File input, File output) {
        this.input = input;
        this.output = output;
    }

    public void process() throws IOException {
        var taps = parseInputFile();

        CustomerSummaries result = groupTapsByCustomerId(taps);

        writeOutputFile(result);
    }

    private Taps parseInputFile() throws IOException {
        var content = FileUtils.readFileToString(input, ENCODING);
        return GSON.fromJson(content, Taps.class);
    }

    private void writeOutputFile(CustomerSummaries result) throws IOException {
        Writer writer = new FileWriterWithEncoding(output.getName(), ENCODING);
        GSON.toJson(result, writer);
        writer.close();
    }

    private CustomerSummaries groupTapsByCustomerId(Taps taps) {
        List<Tap> tapList = taps.getTaps();

        var customerSummaries = initCustomerSummaries(tapList);

        for (CustomerSummary customerSummary : customerSummaries.getCustomerSummaries()) {

            List<Tap> customerTaps = getTapsFromCustomer(tapList, customerSummary);

            for (var i = 0; i < customerTaps.size(); i = i + 2) {
                addTripToSummary(customerSummary, customerTaps, i);
            }
            customerSummary.setTotalCostInCents(sumTripsCost(customerSummary));
        }

        return customerSummaries;
    }

    private CustomerSummaries initCustomerSummaries(List<Tap> tapList) {
        List<Integer> customersId = tapList.stream()
                .collect(uniqueCustomerID());

        var customerSummaries = new CustomerSummaries();
        customerSummaries.setCustomerSummaries(new ArrayList<>());
        for (Integer id : customersId) {
            var customer = new CustomerSummary();
            customer.setCustomerId(id);
            customer.setTrips(new ArrayList<>());
            customerSummaries.getCustomerSummaries().add(customer);
        }
        return customerSummaries;
    }

    private List<Tap> getTapsFromCustomer(List<Tap> tapList, CustomerSummary customerSummary) {
        List<Tap> customerTap = tapList.stream()
                .filter(tap -> isTapFromCustomer(customerSummary, tap))
                .sorted(compareByTime)
                .collect(Collectors.toList());

        if (haveOddNumberOfTap(customerTap)) {
            throw new DataException(customerSummary.getCustomerId());
        }
        return customerTap;
    }

    private boolean isTapFromCustomer(CustomerSummary customerSummary, Tap tap) {
        return tap.getCustomerId() == customerSummary.getCustomerId();
    }

    private boolean haveOddNumberOfTap(List<Tap> customerTaps) {
        return customerTaps.size() % 2 != 0;
    }

    private void addTripToSummary(CustomerSummary customerSummary, List<Tap> customerTaps, int i) {
        var departure = customerTaps.get(i);
        var arrival = customerTaps.get(i + 1);
        var trip = buildTrip(departure, arrival);
        customerSummary.getTrips().add(trip);
    }

    private Trip buildTrip(Tap departure, Tap arrival) {
        var trip = new Trip();
        trip.setStationStart(departure.getStation());
        trip.setStationEnd(arrival.getStation());
        trip.setStartedJourneyAt(departure.getUnixTimestamp());
        InvoicedJourney journey = journeyEvaluator.calculatePrice(departure, arrival);
        trip.setCostInCents(journey.getPrice());
        trip.setZoneFrom(journey.getJourney().departZone().getZoneNumber());
        trip.setZoneTo(journey.getJourney().arrivalZone().getZoneNumber());
        return trip;
    }

    private int sumTripsCost(CustomerSummary customerSummary) {
        return customerSummary.getTrips().stream().mapToInt(Trip::getCostInCents).sum();
    }

    private static <T> Collector<T, Set<Integer>, List<Integer>> uniqueCustomerID() {
        return Collector.of(
                HashSet::new,
                (result, item) -> result.add(((Tap) item).getCustomerId()),
                (result1, result2) -> {
                    result1.addAll(result2);
                    return result1;
                },
                ArrayList::new,
                Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED);
    }
}
