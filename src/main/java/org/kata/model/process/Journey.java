package org.kata.model.process;

import java.util.Objects;

public class Journey {
    private final Zone departZone;
    private final Zone arrivalZone;

    public Journey(Zone departZone, Zone arrivalZone) {
        this.departZone = departZone;
        this.arrivalZone = arrivalZone;
    }

    public Zone getDepartZone() {
        return departZone;
    }

    public Zone getArrivalZone() {
        return arrivalZone;
    }

    @Override
    public String toString() {
        return "departZone=" + departZone + ", arrivalZone=" + arrivalZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journey journey = (Journey) o;
        return departZone.equals(journey.departZone) && arrivalZone.equals(journey.arrivalZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departZone, arrivalZone);
    }
}
