package main.ga.mutation;

import main.vpr.Individual;

import java.util.Random;

public class Swap implements MutationOperator {

    Random random;

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public void mutate(Individual individual) {
        int[] genotype = individual.getGenotype();

        int pos1 = random.nextInt(genotype.length);
        int pos2 = random.nextInt(genotype.length);

        int aux = genotype[pos2];
        genotype[pos2] = genotype[pos1];
        genotype[pos1] = aux;

        individual.setGenotype(genotype);
    }
}
