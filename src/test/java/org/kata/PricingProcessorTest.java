package org.kata;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PricingProcessorTest {

    @Test
    void should_return_customers_summaries_from_inputFile_and_create_an_output_json_file() throws IOException {
        var inputFile = "CandidateInputExample.json";
        //var expectedOutputFile = new File(getClass().getClassLoader().getResource("CandidateOutputExample.json").getFile());
        var outputFileName = "result.json";

        PricingProcessor processor = new PricingProcessor(inputFile, outputFileName);

        processor.process();

        File file = new File(outputFileName);
        assertThat(file).exists();
        //assertThat(outputFile).hasSameTextualContentAs(expectedOutputFile);
        file.delete();
    }
}