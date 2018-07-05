package main.ga.creation;

import main.vpr.Individual;
import main.vpr.Population;
import main.vpr.Problem;

import java.util.*;

public class RandomPopulation implements CreationOperator {

    List<Individual> population;
    Random random;

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public Population createInitialPopulation(Problem problem, int populationSize) {
        population = new ArrayList<Individual>();
        Individual individual;
        for(int i = 0; i < populationSize; i++) {
            individual = createRandomIndividual(problem.getNumberOfClients());
            individual.setIdent(population.size());
            population.add(individual);
        }
        return new Population(population);
    }

    public Individual createRandomIndividual(int size) {
        int[] genotype = new int[size];

        List<Integer> aux = new ArrayList<>();
        for(int i = 0; i < genotype.length; i++) {
            aux.add(i+1);
        }
        Collections.shuffle(aux, random);
        genotype = aux.parallelStream().mapToInt(Integer::valueOf).toArray();
        return new Individual(genotype);
    }
}
