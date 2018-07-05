package main.ga.selection;

import main.vpr.Individual;
import main.vpr.Population;

import java.util.Random;

public interface SelectionOperator {

    public Individual[] selection(Population population);

    public void setRandom(Random random);

    public void reset();
}
