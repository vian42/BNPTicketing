package org.kata;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kata.model.input.Tap;
import org.kata.model.process.InvoicedJourney;

import static org.assertj.core.api.Assertions.assertThat;

class JourneyEvaluatorTest {
//    final static Logger LOGGER = LoggerFactory.getLogger(JourneyEvaluatorTest.class);

    private final JourneyEvaluator journeyEvaluator = new JourneyEvaluator();
    private SoftAssertions should;

    @BeforeEach
    void setup() {
        should = new SoftAssertions();
    }

    @AfterEach
    void afterTest() {
        should.assertAll();
    }

    @Test
    void a_journey_from_zone_1_to_zone_1_should_cost_240() {
        Tap departure = getDeparture("A");
        Tap arrival = getArrival("B");
        Integer expectedPrice = 240;
        Integer expectedDepartZone = 1;
        Integer expectedArrivalZone = 1;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    @Test
    void a_journey_from_zone_1_to_zone_2_should_cost_240() {
        Tap departure = getDeparture("A");
        Tap arrival = getArrival("D");
        Integer expectedPrice = 240;
        Integer expectedDepartZone = 1;
        Integer expectedArrivalZone = 2;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    @Test
    void a_journey_from_zone_4_to_zone_4_should_cost_200() {
        Tap departure = getDeparture("G");
        Tap arrival = getArrival("I");
        Integer expectedPrice = 200;
        Integer expectedDepartZone = 4;
        Integer expectedArrivalZone = 4;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    @Test
    void a_journey_from_zone_1_to_zone_2_and_3_should_cost_240() {
        Tap departure = getDeparture("B");
        Tap arrival = getArrival("C");
        Integer expectedPrice = 240;
        Integer expectedDepartZone = 1;
        Integer expectedArrivalZone = 2;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    @Test
    void a_journey_from_zone_2_and_3_to_zone_1_or_2_should_cost_240() {
        Tap departure = getDeparture("C");
        Tap arrival = getArrival("B");
        Integer expectedPrice = 240;
        Integer expectedDepartZone = 2;
        Integer expectedArrivalZone = 1;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    @Test
    void a_journey_from_zone_3_and_4_to_zone_1_or_2_should_cost_280() {
        Tap departure = getDeparture("F");
        Tap arrival = getArrival("D");
        Integer expectedPrice = 280;
        Integer expectedDepartZone = 3;
        Integer expectedArrivalZone = 2;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    @Test
    void a_journey_from_zone_2_and_3_to_zone_2_and_3_should_cost_200() {
        Tap departure = getDeparture("E");
        Tap arrival = getArrival("C");
        Integer expectedPrice = 200;
        Integer expectedDepartZone = 3;
        Integer expectedArrivalZone = 3;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    @Test
    void a_journey_from_zone_1_to_zone_4_should_cost_300() {
        Tap departure = getDeparture("B");
        Tap arrival = getArrival("I");
        Integer expectedPrice = 300;
        Integer expectedDepartZone = 1;
        Integer expectedArrivalZone = 4;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    @Test
    void a_journey_from_zone_1_to_zone_3_and_4_should_cost_280() {
        Tap departure = getDeparture("A");
        Tap arrival = getArrival("F");
        Integer expectedPrice = 280;
        Integer expectedDepartZone = 1;
        Integer expectedArrivalZone = 3;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }
    @Test
    void a_journey_from_zone_4_to_zone_3_and_4_should_cost_200() {
        Tap departure = getDeparture("G");
        Tap arrival = getArrival("F");
        Integer expectedPrice = 200;
        Integer expectedDepartZone = 4;
        Integer expectedArrivalZone = 3;

        InvoicedJourney result = journeyEvaluator.calculatePrice(departure,arrival);

        assertThat(result).isNotNull();
        checkResult(result,expectedPrice,expectedDepartZone,expectedArrivalZone);
    }

    private Tap getArrival(String codeArrive) {
        Tap arrival = new Tap();
        arrival.setCustomerId(1);
        arrival.setStation(codeArrive);
        arrival.setUnixTimestamp(2);
        return arrival;
    }

    private Tap getDeparture(String codeDepart) {
        Tap departure = new Tap();
        departure.setCustomerId(1);
        departure.setStation(codeDepart);
        departure.setUnixTimestamp(1);
        return departure;
    }

    private void checkResult(InvoicedJourney result,
                             Integer expectedPrice,
                             Integer expectedDepartZone,
                             Integer expectedArrivalZone){
        should.assertThat(result.getPrice()).isEqualTo(expectedPrice);
        should.assertThat(result.getJourney().departZone().getZoneNumber()).isEqualTo(expectedDepartZone);
        should.assertThat(result.getJourney().arrivalZone().getZoneNumber()).isEqualTo(expectedArrivalZone);
    }
}