package fr.uge.jee.criteria;

import java.util.List;
import java.util.Objects;

public class Validator {

    private final List<Criterium> criteria;

    public Validator(List<Criterium> criteria) {
        this.criteria = List.copyOf(criteria);
    }

    public boolean check(String query) {
        Objects.requireNonNull(query);
        for (var criterium : criteria) {
            if (!criterium.satisfy(query)) {
                return false;
            }
        }
        return true;
    }
}
