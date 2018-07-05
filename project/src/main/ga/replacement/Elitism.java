package main.ga.replacement;

import main.vpr.Individual;
import main.vpr.Population;

public class Elitism implements ReplacementOperator {

    @Override
    public Population replace(Population parents, Population children) {
        // get the best of parents
        Individual bestIndividual = parents.get(0);
        double bestFitness = bestIndividual.getFitness();
        for(int i = 1; i < parents.size(); i++) {
            if(parents.get(i).getFitness() < bestFitness) {
                bestIndividual = parents.get(i);
                bestFitness = bestIndividual.getFitness();
            }
        }

        // get the worst of children
        double worstFitness = children.get(0).getFitness();
        int indexWorst = 0;
        for(int i = 1; i < children.size(); i++) {
            if(children.get(i).getFitness() > worstFitness) {
                worstFitness = children.get(i).getFitness();
                indexWorst = i;
            }
        }

        // change the worst for the best
        children.set(indexWorst, bestIndividual);
        return children;
    }
}
