package org.kata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


class MainTest {

    @Test
    void the_program_should_return_an_error_if_not_exactly_two_parameter() {
        String[] args = {"input","output","foo"};
        assertThrows(Exception.class, () -> Main.main(args));
    }
}