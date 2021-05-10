package org.kata.exception;

import java.io.IOException;

public class InputException extends RuntimeException {

    public InputException() {
        super("Invalid number of parameters, 2 parameters are expected");
    }

    public InputException(IOException e) {
        super("Error wile processing on one of the input file", e);
    }
}
