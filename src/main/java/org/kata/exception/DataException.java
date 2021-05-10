package org.kata.exception;

public class DataException extends RuntimeException {

    public DataException(Integer customerID) {
        super("Tag missing for customer with ID : "+customerID);
    }
}
