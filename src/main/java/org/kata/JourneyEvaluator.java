package org.kata;

import org.kata.model.process.FactJourney;
import org.kata.model.process.Journey;
import org.kata.model.process.Station;
import org.kata.model.process.Zone;
import org.kata.model.input.Tap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class JourneyEvaluator {

    private final JourneyEvaluatorConfig config;

    public JourneyEvaluator() {
        this.config = new JourneyEvaluatorConfig("zonesStationsList.json", "zoneToZonePrice.csv");
    }

    public FactJourney calculatePrice(Tap departure, Tap arrival) {
        List<Zone> possibleStartingZone = getZone(new Station(departure.getStation()));
        List<Zone> possibleArrivalZone = getZone(new Station(arrival.getStation()));
        List<Journey> applicableJourney = getApplicableJourney(possibleStartingZone,possibleArrivalZone);
        return getTheBest(applicableJourney);
    }

    private List<Zone> getZone(Station station) {
        return config.getZones().stream().filter(zone -> zone.getStations().contains(station.getCodeStation()))
                .collect(Collectors.toList());
    }

    private List<Journey> getApplicableJourney(List<Zone> dep, List<Zone> arv) {
        ArrayList<Journey> ret = new ArrayList<>();
        dep.forEach(zoneDepart ->
                arv.forEach(zoneArrive ->
                        ret.add(new Journey(zoneDepart, zoneArrive))));
        return ret;
    }

    private FactJourney getTheBest(List<Journey> applicableJourney) {
        return config.getPricingBase().stream().filter(data->applicableJourney.contains(data.getJourney()))
                .min(Comparator.naturalOrder())
                .orElseThrow();
    }
}
