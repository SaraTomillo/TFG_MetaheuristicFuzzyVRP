package main.ga.selection;

import main.vpr.Individual;
import main.vpr.Population;

import java.util.*;

public class Tournament implements SelectionOperator {

    int tournamentSize;
    Random random;

    public Tournament(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public Individual[] selection(Population population) {
        Individual p1 = tournament(population);
        Individual p2 = tournament(population);

        return new Individual[]{p1, p2};
    }

    private Individual tournament(Population population) {
        List<Individual> chosenPopulation = new ArrayList<>();

        // Select n random individuals
        for(int n = 0; n < tournamentSize; n++) {
            int index = random.nextInt(population.size());
            chosenPopulation.add(population.get(index));
        }

        // Select the best fitness (minimizing fitness)
        Individual selected = chosenPopulation.get(0);
        double bestFitness = selected.getFitness();

        //Collections.sort(chosenPopulation, Comparator.comparingDouble(Individual::getFitness));
        for (int i = 1; i < chosenPopulation.size(); i++) {
            if(chosenPopulation.get(i).getFitness() < bestFitness) {
                selected = chosenPopulation.get(i);
                bestFitness = selected.getFitness();
            }
        }

        return selected;
    }

    @Override
    public void reset() { }
}
