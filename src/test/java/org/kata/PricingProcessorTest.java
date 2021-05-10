package org.kata;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kata.model.input.Tap;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

class PricingProcessorTest {

    @Test
    void name() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File input = new File(classLoader.getResource("CandidateInputExample.json").getFile());
        File output = new File(classLoader.getResource("result.json").getFile());
        PricingProcessor pricingProcessor = new PricingProcessor(input,output);
      //  doStuff.doIt();
    }

    @Test
    void name2() {
        Tap tap1 = createTap(1);
        Tap tap2 = createTap(2);
        Tap tap1bis = createTap(1);
        List<Tap> tapList = Arrays.asList(tap1,tap1bis,tap2);
        PricingProcessor pricingProcessor = new PricingProcessor(null,null);
        List<Integer> list =  pricingProcessor.getCustomersId(tapList);
        Assertions.assertEquals(2,list.size());
    }

    private Tap createTap(int i) {
        Tap tap = new Tap();
        tap.setCustomerId(i);
        tap.setStation("A");
        tap.setUnixTimestamp(123);
        return tap;
    }
}