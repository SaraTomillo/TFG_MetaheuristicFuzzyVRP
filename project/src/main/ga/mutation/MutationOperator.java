package main.ga.mutation;

import main.vpr.Individual;

import java.util.Random;

public interface MutationOperator {

    public void mutate(Individual individual);

    public void setRandom(Random random);
}
