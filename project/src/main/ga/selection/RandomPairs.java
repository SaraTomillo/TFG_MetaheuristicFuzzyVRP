package main.ga.selection;

import main.vpr.Individual;
import main.vpr.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomPairs implements SelectionOperator {

    Random random;
    Individual[] individuals;
    int index;

    @Override
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public Individual[] selection(Population population) {
        Individual[] parents = new Individual[2];

        if(individuals == null) {
            this.individuals = new Individual[population.size()];
            ArrayList<Individual> newPopulation = (ArrayList<Individual>) ((ArrayList<Individual>) population.getPopulation()).clone();
            Collections.shuffle(newPopulation, random);
            for(int i = 0; i < individuals.length; i++) {
                individuals[i] = newPopulation.get(i);
            }
        } else {
            index++;
        }

        parents[0] = individuals[index];
        index++;
        parents[1] = individuals[index];

        return parents;
    }

    @Override
    public void reset() {
        this.individuals = null;
        this.index = 0;
    }
}
