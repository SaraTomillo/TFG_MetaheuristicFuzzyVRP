package main.ga.creation;

import main.vpr.Population;
import main.vpr.Problem;

import java.util.Random;

public interface CreationOperator {

    public Population createInitialPopulation(Problem problem, int populationSize);

    public void setRandom(Random random);
}
