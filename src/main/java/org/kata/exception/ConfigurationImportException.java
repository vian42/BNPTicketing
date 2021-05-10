package org.kata.exception;

public class ConfigurationImportException extends RuntimeException {

    public ConfigurationImportException(Exception e) {
        super("Error during the recuperation of the configuration",e);
    }
}
