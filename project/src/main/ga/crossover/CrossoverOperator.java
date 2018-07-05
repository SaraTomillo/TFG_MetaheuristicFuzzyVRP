package main.ga.crossover;


import main.vpr.Individual;

import java.util.Random;

public interface CrossoverOperator {

    public Individual cross(Individual parent1, Individual parent2);

    public void reset();

    public void setRandom(Random random);

}
