package org.kata;

import org.kata.model.output.CustomerSummary;

public interface PriceModificator {

    public void applyChecking(CustomerSummary customer);
}
