package org.kata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JourneyEvaluatorConfigTest {

    @Test
    void the_configuration_initialisation_should_return_the_correct_amount_of_data() {
        JourneyEvaluatorConfig config = new JourneyEvaluatorConfig(
                "zonesStationsList_test.json",
                "zoneToZonePrice_test.csv");
        assertEquals(2, config.getZones().size());
        assertEquals(3,config.getZoneWithNumber(2).getStations().size());
        assertEquals(4,config.getPricingBase().size());
    }
}