package org.kata;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PricingProcessorTest {

    @Test
    void should_return_customers_summaries_from_inputFile_and_create_an_output_json_file() throws IOException {
        var inputFile = new File(getClass().getClassLoader().getResource("CandidateInputExample.json").getFile());
        var outputFile = new File("result.json");

        PricingProcessor processor = new PricingProcessor(inputFile,outputFile);

        processor.process();

        assertThat(outputFile).exists();
    }
}