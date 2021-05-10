package org.kata.model.output;

public class Trip {

    private String stationStart;
    private String stationEnd;
    private int startedJourneyAt;
    private int costInCents;
    private int zoneFrom;
    private int zoneTo;

    public String getStationStart() {
        return stationStart;
    }

    public void setStationStart(String value) {
        this.stationStart = value;
    }

    public String getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(String value) {
        this.stationEnd = value;
    }

    public int getStartedJourneyAt() {
        return startedJourneyAt;
    }

    public void setStartedJourneyAt(int value) {
        this.startedJourneyAt = value;
    }

    public int getCostInCents() {
        return costInCents;
    }

    public void setCostInCents(int value) {
        this.costInCents = value;
    }

    public int getZoneFrom() {
        return zoneFrom;
    }

    public void setZoneFrom(int value) {
        this.zoneFrom = value;
    }

    public int getZoneTo() {
        return zoneTo;
    }

    public void setZoneTo(int value) {
        this.zoneTo = value;
    }

}
