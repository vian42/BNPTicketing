package org.kata;

import org.kata.model.output.CustomerSummary;

public class MaxPriceChecker implements PriceModificator{

    public static final int MAX_PRICE = 1000;
    public static final int CAP_VALUE_ZONE_1_2 = 800;
    public static final int CAP_VALUE_ZONE_3_4 = 600;

    @Override
    public void applyChecking(CustomerSummary customer) {
        if (areAllJourneyInZone1and2(customer)) {
            if (customer.getTotalCostInCents() > CAP_VALUE_ZONE_1_2) {
                customer.setTotalCostInCents(CAP_VALUE_ZONE_1_2);
            }
        }

        if (areAllJourneyInZone3and4(customer)) {
            if (customer.getTotalCostInCents() > CAP_VALUE_ZONE_3_4) {
                customer.setTotalCostInCents(CAP_VALUE_ZONE_3_4);
            }
        }

        if (customer.getTotalCostInCents() > MAX_PRICE) {
            customer.setTotalCostInCents(MAX_PRICE);
        }
    }

    private boolean areAllJourneyInZone3and4(CustomerSummary customer) {
        return customer.getTrips().stream().allMatch(
                trip -> (trip.getZoneFrom() == 3 || trip.getZoneFrom() == 4)
                        && (trip.getZoneTo() == 3 || trip.getZoneTo() == 4));

    }

    private boolean areAllJourneyInZone1and2(CustomerSummary customer) {
        return customer.getTrips().stream().allMatch(
                trip -> (trip.getZoneFrom() == 1 || trip.getZoneFrom() == 2)
                        && (trip.getZoneTo() == 1 || trip.getZoneTo() == 2));
    }
}
