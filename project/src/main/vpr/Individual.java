package main.vpr;

import java.util.ArrayList;
import java.util.List;

public class Individual {

    int[] genotype;
    double fitness;
    List<List<Integer>> phenotype;

    int ident;
    int parentIdent;

    public Individual() {
        this.phenotype = new ArrayList<>();
        this.fitness = Integer.MAX_VALUE;
    }

    public Individual(int[] genotype) {
        this.genotype = genotype;
        this.phenotype = new ArrayList<>();
        this.fitness = Integer.MAX_VALUE;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    public double getFitness() { return this.fitness; }

    public void setGenotype(int[] genotype) {
        this.genotype = genotype;
    }
    public int[] getGenotype() { return this.genotype; }

    public void setPhenotype(List<List<Integer>> phenotype) {
        this.phenotype = phenotype;
    }

    public List<List<Integer>> getPhenotype() {
        return phenotype;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public int getParentIdent() {
        return parentIdent;
    }

    public void setParentIdent(int parentIdent) {
        this.parentIdent = parentIdent;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for(List<Integer> route : phenotype) {
            sb.append("\nRoute " + i +"\n");
            for(int j = 0; j < route.size(); j++) {
                sb.append(route.get(j) + " ");
            }
            sb.append("\n");
            i++;
        }
        return sb.toString();
    }

    @Override
    public Individual clone() {
        Individual copy = new Individual(this.genotype);
        copy.fitness = this.fitness;
        copy.ident = this.ident;
        copy.parentIdent = this.parentIdent;
        copy.phenotype.addAll(this.phenotype);
        return copy;
    }

    public int getNumberOfTrips() {
        return this.phenotype.size();
    }

    public double getAverageCost() {
        return fitness/getNumberOfTrips();
    }

    public String getGenotypeString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < genotype.length-1; i++) {
            sb.append(genotype[i] + ", ");
        }

        sb.append(genotype[genotype.length-1] + "]");
        return sb.toString();
    }
}
