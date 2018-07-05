package main.ga.crossover;

import main.vpr.Individual;

import java.util.Random;

public class PositionBasedCrossover implements CrossoverOperator {

    int[] mask;
    Random random;

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public Individual cross(Individual parent1, Individual parent2) {
        int [] p1 = parent1.getGenotype();
        int [] p2 = parent2.getGenotype();
        int [] genotype = new int [p1.length];

        if(mask == null) {
            generateRandomMask(genotype.length);
        }


        for (int i = 0; i < genotype.length; i++)
            genotype[i] = -1;

        for(int i = 0; i < genotype.length; i++) {
            if(mask[i] == 1) {
                genotype[i] = p1[i];
            }
        }

        int j = 0;
        for (int i = 0; i < genotype.length; i++) {
            if(genotype[i] < 0) {
                while(j < p2.length) {
                    if(!contains(genotype, p2[j])) {
                        genotype[i] = p2[j];
                        break;
                    }
                    j++;
                }
            }
        }

        Individual individual = new Individual(genotype);
        individual.setParentIdent(parent1.getIdent());
        return individual;
    }

    private void generateRandomMask(int length) {
        this.mask = new int[length];
        for(int i = 0; i < mask.length; i++) {
            mask[i] = random.nextInt(2);
        }
    }

    private boolean contains(int[] array, int number) {
        for(int i = 0; i < array.length; i++) {
            if(array[i] == number) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        this.mask = null;
    }

}
