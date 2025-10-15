package fr.uge.jee.criteria;

@FunctionalInterface
public interface Criterium {
    boolean satisfy(String query);
}
