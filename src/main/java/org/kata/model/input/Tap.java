package org.kata.model.input;

public class Tap {
    private int unixTimestamp;
    private int customerId;
    private String station;

    public int getUnixTimestamp() {
        return unixTimestamp;
    }

    public void setUnixTimestamp(int unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Tap{" +
                "unixTimestamp=" + unixTimestamp +
                ", customerId=" + customerId +
                ", station='" + station + '\'' +
                '}';
    }
}