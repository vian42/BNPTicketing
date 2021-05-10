package org.kata.model.input;

import java.util.List;

public class Taps {
    private List<Tap> taps;

    public List<Tap> getTaps() {
        return taps;
    }

    public void setTaps(List<Tap> taps) {
        this.taps = taps;
    }

    @Override
    public String toString() {
        return "Taps{" +
                "taps=" + taps +
                '}';
    }
}
