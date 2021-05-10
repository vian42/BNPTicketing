package org.kata.model.process;

import java.util.List;

public class Zone {
    private Integer zoneNumber;
    private List<String> stations;

    public Zone(Integer zoneNumber, List<String> stations) {
        this.zoneNumber = zoneNumber;
        this.stations = stations;
    }

    public List<String> getStations() {
        return stations;
    }

    public Integer getZoneNumber() {
        return zoneNumber;
    }

    @Override
    public String toString() {
        return zoneNumber.toString();
    }
}
