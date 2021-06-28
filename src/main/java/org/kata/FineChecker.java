package org.kata;

import org.kata.exception.DataException;
import org.kata.model.input.Tap;
import org.kata.model.output.CustomerSummary;

import java.util.List;

public class FineChecker implements PriceModificator {


    @Override
    public void applyChecking(CustomerSummary customer) {
        if (haveOddNumberOfTap(customerTap)) {
            throw new DataException(customerSummary.getCustomerId());
        }
    }

    private boolean haveOddNumberOfTap(List<Tap> customerTaps) {
        return customerTaps.size() % 2 != 0;
    }

}
