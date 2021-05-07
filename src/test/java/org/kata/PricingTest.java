package org.kata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PricingTest {

    @Test
    void the_program_should_return_an_error_if_not_exactly_two_parameter() {
        String[] args = {"input","output","foo"};
        assertThrows(Exception.class, () -> Pricing.main(args));
    }
}