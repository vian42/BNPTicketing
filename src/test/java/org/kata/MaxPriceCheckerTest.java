package org.kata;

import org.junit.jupiter.api.Test;
import org.kata.model.output.CustomerSummary;
import org.kata.model.output.Trip;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kata.MaxPriceChecker.*;

class MaxPriceCheckerTest {

    @Test
    void a_customer_with_a_total_price_inferior_of_ten_should_not_change_the_price() {
        CustomerSummary customer = new CustomerSummary();
        Trip trip = new Trip();
        trip.setZoneFrom(1);
        trip.setZoneTo(2);
        trip.setCostInCents(240);
        customer.setTrips(Collections.singletonList(trip));
        customer.setCustomerId(1);
        customer.setTotalCostInCents(240);

        MaxPriceChecker maxPriceChecker = new MaxPriceChecker();
        maxPriceChecker.applyChecking(customer);

        assertThat(customer.getTotalCostInCents()).isEqualTo(240);
    }

    @Test
    void a_customer_with_a_total_price_inferior_of_ten_should_not_change_the_price_2() {
        CustomerSummary customer = new CustomerSummary();
        Trip trip = new Trip();
        trip.setZoneFrom(3);
        trip.setZoneTo(4);
        trip.setCostInCents(200);
        customer.setTrips(Collections.singletonList(trip));
        customer.setCustomerId(1);
        customer.setTotalCostInCents(200);

        MaxPriceChecker maxPriceChecker = new MaxPriceChecker();
        maxPriceChecker.applyChecking(customer);

        assertThat(customer.getTotalCostInCents()).isEqualTo(200);
    }

    @Test
    void a_customer_with_a_total_price_inferior_of_ten_should_not_change_the_price_3() {
        CustomerSummary customer = new CustomerSummary();
        Trip trip = new Trip();
        trip.setZoneFrom(1);
        trip.setZoneTo(4);
        trip.setCostInCents(300);
        customer.setTrips(Collections.singletonList(trip));
        customer.setCustomerId(1);
        customer.setTotalCostInCents(300);

        MaxPriceChecker maxPriceChecker = new MaxPriceChecker();
        maxPriceChecker.applyChecking(customer);

        assertThat(customer.getTotalCostInCents()).isEqualTo(300);
    }

    @Test
    void a_customer_with_a_total_price_above_ten_should_set_the_price_at_ten() {
        CustomerSummary customer = new CustomerSummary();
        Trip trip = new Trip();
        trip.setZoneFrom(1);
        trip.setZoneTo(2);
        trip.setCostInCents(240);
        Trip tripB = new Trip();
        tripB.setZoneFrom(1);
        tripB.setZoneTo(4);
        tripB.setCostInCents(300);
        customer.setTrips(Arrays.asList(trip,trip,trip,trip,trip,trip,tripB));
        customer.setCustomerId(1);
        customer.setTotalCostInCents(240*6+300);

        MaxPriceChecker maxPriceChecker = new MaxPriceChecker();
        maxPriceChecker.applyChecking(customer);

        assertThat(customer.getTotalCostInCents()).isEqualTo(MAX_PRICE);
    }

    @Test
    void a_customer_with_journey_only_in_zone_1_and_2_is_cap_at_8() {
        CustomerSummary customer = new CustomerSummary();
        Trip trip = new Trip();
        trip.setZoneFrom(1);
        trip.setZoneTo(2);
        trip.setCostInCents(240);
        customer.setTrips(Arrays.asList(trip,trip,trip,trip,trip,trip));
        customer.setCustomerId(1);
        customer.setTotalCostInCents(240*6);

        MaxPriceChecker maxPriceChecker = new MaxPriceChecker();
        maxPriceChecker.applyChecking(customer);

        assertThat(customer.getTotalCostInCents()).isEqualTo(CAP_VALUE_ZONE_1_2);
    }

    @Test
    void a_customer_with_journey_only_in_zone_3_and_4_is_cap_at_6() {
        CustomerSummary customer = new CustomerSummary();
        Trip trip = new Trip();
        trip.setZoneFrom(3);
        trip.setZoneTo(4);
        trip.setCostInCents(200);
        customer.setTrips(Arrays.asList(trip,trip,trip,trip,trip,trip));
        customer.setCustomerId(1);
        customer.setTotalCostInCents(200*6);

        MaxPriceChecker maxPriceChecker = new MaxPriceChecker();
        maxPriceChecker.applyChecking(customer);

        assertThat(customer.getTotalCostInCents()).isEqualTo(CAP_VALUE_ZONE_3_4);
    }


}