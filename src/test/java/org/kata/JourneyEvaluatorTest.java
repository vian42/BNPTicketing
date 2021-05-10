package org.kata;

import org.junit.jupiter.api.Test;
import org.kata.model.input.Tap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JourneyEvaluatorTest {
//    final static Logger LOGGER = LoggerFactory.getLogger(JourneyEvaluatorTest.class);

    @Test
    void a_journey_from_zone_1_to_zone_1_should_cost_240() {
        Tap departure = getDeparture("A");
        Tap arrival = getArrival("B");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(240,result);
    }

    @Test
    void a_journey_from_zone_1_to_zone_2_should_cost_240() {
        Tap departure = getDeparture("A");
        Tap arrival = getArrival("D");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(240,result);
    }

    @Test
    void a_journey_from_zone_4_to_zone_4_should_cost_200() {
        Tap departure = getDeparture("G");
        Tap arrival = getArrival("I");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(200,result);
    }
    @Test
    void a_journey_from_zone_1_to_zone_2_and_3_should_cost_240() {
        Tap departure = getDeparture("B");
        Tap arrival = getArrival("C");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(240,result);
    }

    @Test
    void a_journey_from_zone_2_and_3_to_zone_1_or_2_should_cost_240() {
        Tap departure = getDeparture("C");
        Tap arrival = getArrival("B");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(240,result);
    }
    @Test
    void a_journey_from_zone_3_and_4_to_zone_1_or_2_should_cost_280() {
        Tap departure = getDeparture("F");
        Tap arrival = getArrival("D");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(280,result);
    }

    @Test
    void a_journey_from_zone_2_and_3_to_zone_2_and_3_should_cost_200() {
        Tap departure = getDeparture("E");
        Tap arrival = getArrival("C");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(200,result);
    }

    @Test
    void a_journey_from_zone_1_to_zone_4_should_cost_300() {
        Tap departure = getDeparture("B");
        Tap arrival = getArrival("I");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(300,result);
    }

    @Test
    void a_journey_from_zone_1_to_zone_3_and_4_should_cost_280() {
        Tap departure = getDeparture("A");
        Tap arrival = getArrival("F");

        JourneyEvaluator doStuff = new JourneyEvaluator();

        Integer result = doStuff.calculatePrice(departure,arrival).getPrice();

        assertEquals(280,result);
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
}