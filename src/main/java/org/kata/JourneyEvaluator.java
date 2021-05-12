package org.kata;

import org.kata.model.input.Tap;
import org.kata.model.process.InvoicedJourney;
import org.kata.model.process.Journey;
import org.kata.model.process.Zone;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class JourneyEvaluator {

    private final JourneyEvaluatorConfig config;

    public JourneyEvaluator() {
        this.config = new JourneyEvaluatorConfig("zonesStationsList.json", "zoneToZonePrice.csv");
    }

    /**
     * For a given trip, return t
     * @param departure
     * @param arrival
     * @return
     */
    public InvoicedJourney calculatePrice(Tap departure, Tap arrival) {
        List<Zone> possibleStartingZones = getZone(departure.getStation());
        List<Zone> possibleArrivalZones = getZone(arrival.getStation());
        List<Journey> applicableJourneys = getApplicableJourneys(possibleStartingZones, possibleArrivalZones);
        return getTheCheapestJourney(applicableJourneys);
    }

    private List<Zone> getZone(String station) {
        return config.getZones().stream().filter(zone -> zone.getStations().contains(station))
                .collect(Collectors.toList());
    }

    private List<Journey> getApplicableJourneys(List<Zone> departures, List<Zone> arrivals) {
        ArrayList<Journey> ret = new ArrayList<>();
        departures.forEach(departureZone ->
                arrivals.forEach(arrivalZone ->
                        ret.add(new Journey(departureZone, arrivalZone))));
        return ret;
    }

    private InvoicedJourney getTheCheapestJourney(List<Journey> applicableJourneys) {
        return config.getPricingBase().stream()
                .filter(data -> applicableJourneys.contains(data.getJourney()))
                .min(Comparator.naturalOrder())
                .orElseThrow();
    }
}
