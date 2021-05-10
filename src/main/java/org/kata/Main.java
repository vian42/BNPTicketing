package org.kata;

import org.kata.exception.InputException;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main (String[] args) {
        if(args.length!=2){
            throw new InputException();
        } else {
            executeTheProcess(args);
        }
        
    }

    private static void executeTheProcess(String[] args) {
        var input = new File(args[0]);
        var output = new File(args[1]);
        PricingProcessor processor = new PricingProcessor(input,output);
        try {
            processor.process();
        } catch (IOException e) {
            throw new InputException(e);
        }
    }
}
