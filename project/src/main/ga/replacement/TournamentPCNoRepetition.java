package main.ga.replacement;

import main.vpr.Individual;
import main.vpr.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TournamentPCNoRepetition implements ReplacementOperator {

    @Override
    public Population replace(Population parents, Population children) {
        List<Individual> individuals = new ArrayList<>();

        List<Individual> aux;
        Individual p1, p2, c1, c2;

        for(int i = 0; i < children.size()-1; i+=2) {
            aux = new ArrayList<>();

            c1 = children.get(i);
            aux.add(c1);
            c2 = children.get(i+1);
            aux.add(c2);

            if(c1.getParentIdent() == -1 && c2.getParentIdent() == -1) {
                individuals.add(c1);
                individuals.add(c2);
            } else {
                p1 = parents.get(c1.getParentIdent());
                p2 = parents.get(c2.getParentIdent());

                if(p1 != null)
                    aux.add(p1);
                if(p2 != null)
                    aux.add(p2);

                individuals.addAll(apply(aux));
            }
        }
        return new Population(individuals);
    }

    public List<Individual> apply(List<Individual> individuals) {
        List<Individual> result = new ArrayList<Individual>();
        Collections.sort(individuals, Comparator.comparingDouble(Individual::getFitness));

        double epsilon = 0.01;
        result = individuals.subList(0,2);

        int i = 2;
        while((result.get(0).getFitness() > result.get(1).getFitness() - epsilon)
                && (result.get(0).getFitness() < result.get(1).getFitness() + epsilon)
        && (i<4)) {
            result.set(1, individuals.get(i));
            i++;
        }

        return result;
    }
}