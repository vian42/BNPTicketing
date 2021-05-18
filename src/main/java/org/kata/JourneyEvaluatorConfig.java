package org.kata;

import com.google.gson.reflect.TypeToken;
import org.kata.exception.ConfigurationImportException;
import org.kata.model.process.InvoicedJourney;
import org.kata.model.process.Journey;
import org.kata.model.process.Zone;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JourneyEvaluatorConfig {

    private static final String DELIMITER = ",";
    private final ArrayList<Zone> zones;
    private final ArrayList<InvoicedJourney> pricingBase;
    public static final FileManager FILE_MANAGER = new FileManager();

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
    private ArrayList<Zone> importZonesConfiguration(String fileName) throws FileNotFoundException {
        return FILE_MANAGER.getDataFromJsonFile(fileName,new TypeToken<List<Zone>>() {}.getType());
    }

    /**
     * Import the price for each kind a journey
     * @param fileName csv file with the pricing
     * @return the list of journey with their price
     * @throws IOException from the BufferedReader
     */
    private ArrayList<InvoicedJourney> importPricingConfiguration(String fileName) throws IOException {
        ArrayList<InvoicedJourney> invoicedJourneys = new ArrayList<>();
        var inputStream = FILE_MANAGER.getFileFromResource(fileName);

        try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String skipTheFirstLine = reader.readLine();

            String line;
            var lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                for (var i = 1; i < columns.length; i++) {
                    invoicedJourneys.add(createInvoicedJourney(lineNumber, columns[i], i));
                }
                lineNumber++;
            }
        }
        return invoicedJourneys;
    }

    private InvoicedJourney createInvoicedJourney(int lineNumber, String column, int i) {
        var departZone = getZoneWithNumber(lineNumber);
        var arrivalZone = getZoneWithNumber(i);
        var journey = new Journey(departZone, arrivalZone);
        var price = Integer.valueOf(column);
        return new InvoicedJourney(journey, price);
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
