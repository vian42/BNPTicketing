package org.kata;

import org.kata.exception.DataException;
import org.kata.model.input.Tap;
import org.kata.model.input.Taps;
import org.kata.model.output.CustomerSummaries;
import org.kata.model.output.CustomerSummary;
import org.kata.model.output.Trip;
import org.kata.model.process.InvoicedJourney;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PricingProcessor {

    private final String input;
    private final String output;
    private final JourneyEvaluator journeyEvaluator = new JourneyEvaluator();
    private static final Comparator<Tap> compareByTime = Comparator.comparing(Tap::getUnixTimestamp);

    public PricingProcessor(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public void process() throws IOException {
        var utils = new FileManager();
        Taps taps = utils.getDataFromJsonFile(input, Taps.class);

        CustomerSummaries result = groupTapsByCustomerId(taps);

        utils.writeDataInJsonFile(output, result);
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

            var maxPriceChecker = new MaxPriceChecker();
            var fineChecker = new FineChecker();
            List<PriceModificator> list = Arrays.asList(maxPriceChecker,fineChecker);
            list.forEach(pc -> pc.applyChecking(customerSummary));
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
        return tapList.stream()
                .filter(tap -> isTapFromCustomer(customerSummary, tap))
                .sorted(compareByTime)
                .collect(Collectors.toList());
    }

    private boolean isTapFromCustomer(CustomerSummary customerSummary, Tap tap) {
        return tap.getCustomerId() == customerSummary.getCustomerId();
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
