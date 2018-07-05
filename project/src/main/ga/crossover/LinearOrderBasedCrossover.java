package main.ga.crossover;

import main.vpr.Individual;

import java.util.Random;

public class LinearOrderBasedCrossover implements CrossoverOperator {

    int crossingPoint1 = -1;
    int crossingPoint2 = -1;
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
        int aux, j;

        for (int i = 0; i < genotype.length; i++)
            genotype[i] = -1;

        if(crossingPoint1 == -1  && crossingPoint2 == -1) {
            crossingPoint1 = crossingPoint2 = random.nextInt(genotype.length);
            while(crossingPoint2 == crossingPoint1)
                crossingPoint2 = random.nextInt(genotype.length);
            if(crossingPoint2 < crossingPoint1) {
                aux = crossingPoint1;
                crossingPoint1 = crossingPoint2;
                crossingPoint2 = aux;
            }
        }

        for (int i = crossingPoint1; i < crossingPoint2; i++)
            genotype[i] = p1[i];

        j = 0;
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
        this.crossingPoint1 = -1;
        this.crossingPoint2 = -1;
    }
}
