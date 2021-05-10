package org.kata.model.output;

import java.util.List;

public class CustomerSummary {
    private int customerId;
    private int totalCostInCents;
    private List<Trip> trips;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int value) {
        this.customerId = value;
    }

    public long getTotalCostInCents() {
        return totalCostInCents;
    }

    public void setTotalCostInCents(int value) {
        this.totalCostInCents = value;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> value) {
        this.trips = value;
    }
}
