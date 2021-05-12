package org.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JourneyEvaluatorConfigTest {

    @Test
    void the_configuration_initialisation_should_return_the_correct_amount_of_data() {
        JourneyEvaluatorConfig config = new JourneyEvaluatorConfig(
                "zonesStationsList_test.json",
                "zoneToZonePrice_test.csv");
        assertThat(config.getZones().size()).isEqualTo(2);
        assertThat(config.getZoneWithNumber(2).getStations().size()).isEqualTo(3);
        assertThat(config.getPricingBase().size()).isEqualTo(4);
    }
}