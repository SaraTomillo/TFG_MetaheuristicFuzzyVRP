package main.vpr;

import java.util.List;

public class Population {
    
    List<Individual> population;

    public Population(List<Individual> population) {
        this.population = population;
        int i = 0;
        for (Individual individual: this.population) {
            individual.setIdent(i++);
        }
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public Individual get(int index) {
        return population.get(index);
    }

    public int size() {
        return population.size();
    }

    public void set(int index, Individual individual) {
        this.population.set(index, individual);
    }

    public Individual retrieveBestIndividual() {
        Individual bestIndividual = population.get(0);
        double bestFitness = bestIndividual.getFitness();

        for (Individual individual : population) {
            double fitness = individual.getFitness();
            if (fitness < bestFitness) {
                bestIndividual = individual;
                bestFitness = fitness;
            }
        }
        return bestIndividual;
    }

    public double getBestFitness() {
        return retrieveBestIndividual().getFitness();
    }

    public double getMeanFitness() {
        double mean = 0.0;
        for(Individual individual : population) {
            mean += individual.getFitness();
        }
        return mean/population.size();
    }
}
