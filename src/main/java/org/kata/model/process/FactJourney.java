package org.kata.model.process;

import java.util.Objects;

public class FactJourney implements Comparable<FactJourney> {
    private final Journey journey;
    private final Integer price;

    public FactJourney(Journey journey, Integer price) {
        this.journey = journey;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactJourney that = (FactJourney) o;
        return journey.equals(that.journey) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(journey, price);
    }

    @Override
    public int compareTo(FactJourney o) {
        return this.price.compareTo(o.price);
    }

    public Journey getJourney() {
        return journey;
    }

    public Integer getPrice() {
        return price;
    }
}
