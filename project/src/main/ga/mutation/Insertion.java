package main.ga.mutation;

import main.vpr.Individual;

import java.util.Random;

public class Insertion implements MutationOperator {

    Random random;

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public void mutate(Individual individual) {
        int[] genotype = individual.getGenotype();
        int[] newGenotype = genotype.clone();

        int pos1 = random.nextInt(genotype.length);
        int pos2 = random.nextInt(genotype.length);

        while(pos1 >= pos2) {
            pos1 = random.nextInt(genotype.length);
            pos2 = random.nextInt(genotype.length);
        }

        newGenotype[pos1] = genotype[pos2];

        for(int i = pos2 -1; i >= pos1; i--) {
            newGenotype[i+1] = genotype[i];
        }

        individual.setGenotype(newGenotype);
    }
}
