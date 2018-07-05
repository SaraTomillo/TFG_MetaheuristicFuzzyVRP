package main.ga.replacement;

import main.vpr.Population;

public interface ReplacementOperator {

    public Population replace(Population parents, Population children);

}
