package main.ga.mutation;

import main.vpr.Individual;

import java.util.Random;

public class SwapSubstring implements MutationOperator {

    Random random;

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public void mutate(Individual individual) {
        int[] genotype = individual.getGenotype();

        int pos1 = random.nextInt(genotype.length-3);
        int pos2 = random.nextInt(genotype.length-3);
        int length = random.nextInt(4);

        int aux;
        for(int i = 0; i < length; i++) {
            aux = genotype[pos2+i];
            genotype[pos2+i] = genotype[pos1+i];
            genotype[pos1+i] = aux;
        }

        individual.setGenotype(genotype);
    }
}
