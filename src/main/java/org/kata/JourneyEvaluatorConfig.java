package org.kata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.kata.exception.ConfigurationImportException;
import org.kata.model.process.InvoicedJourney;
import org.kata.model.process.Journey;
import org.kata.model.process.Zone;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JourneyEvaluatorConfig {

    private static final String DELIMITER = ",";
    private final ArrayList<Zone> zones;
    private final ArrayList<InvoicedJourney> pricingBase;

    public JourneyEvaluatorConfig(String zoneConfigFile, String pricingConfigFile) {
        try {
            this.zones = importZonesConfiguration(zoneConfigFile);
            this.pricingBase = importPricingConfiguration(pricingConfigFile);
        } catch (IOException e) {
            throw new ConfigurationImportException(e);
        }
    }

    /**
     * Import the list of zones with their stations
     * @param fileName json file to import
     * @return the list of zones from the file
     */
    private ArrayList<Zone> importZonesConfiguration(String fileName) {
        var input = getFileFromResource(fileName);
        Reader reader = new InputStreamReader(input, UTF_8);
        return new Gson().fromJson(reader, new TypeToken<List<Zone>>() {}.getType());
    }

    /**
     * Import the price for each kind a journey
     * @param fileName csv file with the pricing
     * @return the list of journey with their price
     * @throws IOException from the BufferedReader
     */
    private ArrayList<InvoicedJourney> importPricingConfiguration(String fileName) throws IOException {
        ArrayList<InvoicedJourney> invoicedJourneys = new ArrayList<>();
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
                    invoicedJourneys.add(new InvoicedJourney(journey, price));
                }
                lineNumber++;
            }
        }
        return invoicedJourneys;
    }

    private InputStream getFileFromResource(String fileName) {
        var classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public List<Zone> getZones() {
        return zones;
    }

    public List<InvoicedJourney> getPricingBase() {
        return pricingBase;
    }

    /**
     * @param zoneNumber number of the expecting zone
     * @return The zone corresponding to the given number
     */
    public Zone getZoneWithNumber(Integer zoneNumber){
        return zones.stream()
                .filter(zone -> zone.getZoneNumber().equals(zoneNumber))
                .findFirst()
                .orElseThrow();
    }
}
