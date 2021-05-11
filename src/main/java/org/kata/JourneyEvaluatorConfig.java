package org.kata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.kata.exception.ConfigurationImportException;
import org.kata.model.process.FactJourney;
import org.kata.model.process.Journey;
import org.kata.model.process.Zone;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JourneyEvaluatorConfig {

    private static final String DELIMITER = ",";
    private final ArrayList<Zone> zones;
    private final ArrayList<FactJourney> pricingBase;

    public JourneyEvaluatorConfig(String zoneConfigFile, String pricingConfigFile) {
        try {
            this.zones = importZonesConfiguration(zoneConfigFile);
            this.pricingBase = importPricingConfiguration(pricingConfigFile);
        } catch (IOException e) {
            throw new ConfigurationImportException(e);
        }
    }

    private ArrayList<Zone> importZonesConfiguration(String fileName) {
        var input = getFileFromResource(fileName);
        Reader reader = new InputStreamReader(input, UTF_8);
        return new Gson().fromJson(reader, new TypeToken<List<Zone>>() {}.getType());
    }

    private ArrayList<FactJourney> importPricingConfiguration(String fileName) throws IOException {
        ArrayList<FactJourney> factJourneys = new ArrayList<>();
        var inputStream = getFileFromResource(fileName);

        try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String skipTheFirstLine = reader.readLine();

            String line;
            var lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                for (var i = 1; i < columns.length; i++) {
                    var departZone = getZoneWithNumber(lineNumber);
                    var arrivalZone = getZoneWithNumber(i);
                    var journey = new Journey(departZone, arrivalZone);
                    var price = Integer.valueOf(columns[i]);
                    factJourneys.add(new FactJourney(journey, price));
                }
                lineNumber++;
            }
        }
        return factJourneys;
    }

    private InputStream getFileFromResource(String fileName) {
        var classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public List<Zone> getZones() {
        return zones;
    }

    public List<FactJourney> getPricingBase() {
        return pricingBase;
    }

    public Zone getZoneWithNumber(Integer zoneNNumber){
        return zones.stream()
                .filter(zone -> zone.getZoneNumber().equals(zoneNNumber))
                .findFirst()
                .orElseThrow();
    }
}
