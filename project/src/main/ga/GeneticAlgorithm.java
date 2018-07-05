package main.ga;

import main.charts.EvolutionChart;
import main.parsers.configuration.ConfigurationParser;
import main.parsers.results.CSVResultWriter;
import main.parsers.results.ResultWriter;
import main.ga.creation.CreationOperator;
import main.ga.crossover.CrossoverOperator;
import main.ga.mutation.MutationOperator;
import main.ga.replacement.ReplacementOperator;
import main.ga.selection.SelectionOperator;
import main.tfn.TFN;
import main.vpr.Individual;
import main.vpr.Population;
import main.vpr.Problem;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class GeneticAlgorithm {

    Problem problem;
    Population population;

    CreationOperator creationOp;
    SelectionOperator selectionOp;
    CrossoverOperator crossoverOp;
    MutationOperator mutationOp;
    ReplacementOperator replacementOp;

    int populationSize;
    int maxIterations;
    double crossoverProbability;
    double mutationProbability;
    Random random;
    long seed;

    boolean consoleOutput;
    boolean displayCharts;

    ResultWriter solutionWriter;
    ResultWriter iterationWriter;

    public GeneticAlgorithm() {
        this.consoleOutput = false;
        this.displayCharts = false;
    }

    public GeneticAlgorithm(boolean consoleOutput, boolean displayCharts) {
        this.consoleOutput = consoleOutput;
        this.displayCharts = displayCharts;
    }

    public void configureAlgorithm(ConfigurationParser config, Problem problem, String instanceName) throws IOException {
        this.populationSize = config.getPopulationSize();
        this.maxIterations = config.getMaxIterations();
        this.crossoverProbability = config.getCrossoverProbability();
        this.mutationProbability = config.getMutationProbability();
        this.seed = config.getRandomSeed();

        this.creationOp = config.getCreationOperator();
        this.selectionOp = config.getSelectionOperator();
        this.crossoverOp = config.getCrossoverOperator();
        this.mutationOp = config.getMutationOperator();
        this.replacementOp = config.getReplacementOperator();

        this.problem = problem;

        this.solutionWriter = new CSVResultWriter(instanceName + "-solution-" + config.getExecutionId());
        this.iterationWriter = new CSVResultWriter(instanceName + "-solution-iterations-" + config.getExecutionId());

        if(!consoleOutput) {
            PrintStream dummyStream = new PrintStream(new OutputStream() {
                public void write(int b) {
                    // NO-OP
                }
            });
            System.setOut(dummyStream);
        }



    }

    public Individual runAlgorithm() throws IOException {

        Individual bestIndividual;
        Individual solution = null;
        long startTime = System.currentTimeMillis();

        solutionWriter.writeLine("Seed;Best fitness; Mean fitness;Number of trips;Average cost");
        for (int i = 1; i <= seed; i++) {
            setSeed(i);
            //long start = System.currentTimeMillis();
            //long end = start + 120*1000; // 60 seconds * 1000 ms/sec
            System.out.println("Seed = " + i);
            iterationWriter.writeLine("Seed;" + i);
            population = creationOp.createInitialPopulation(problem, populationSize);
            for(int j = 0; j < populationSize; j++){
                split(population.get(j));
            }
            bestIndividual = population.retrieveBestIndividual();
            if(solution == null) {
                solution = bestIndividual;
            }


            EvolutionChart chart = new EvolutionChart(i);
            chart.display(displayCharts);

            int iteration = 0;
            iterationWriter.writeLine("Iteration;Best fitness;Mean fitness;Best genotype;Number of trips;Average cost");
            while(iteration < maxIterations){
               // if (System.currentTimeMillis() >= end)
               //     break;
                iterationWriter.write(iteration+"");

                iterate(population);

                iterationWriter.write(population.getBestFitness() + ";" + population.getMeanFitness());
                bestIndividual = population.retrieveBestIndividual();
                iterationWriter.write(""+bestIndividual.getGenotypeString());
                iterationWriter.write(""+ bestIndividual.getNumberOfTrips());
                iterationWriter.writeLine("" + bestIndividual.getAverageCost());
                if(iteration%10==0) {
                    System.out.println("\tGeneration " + iteration);
                    System.out.println("\t\tBestFitness = " + bestIndividual.getFitness());
                    System.out.println("\t\tMeanFitness = " + population.getMeanFitness());
                    System.out.println();
                }

                chart.updateData(iteration, bestIndividual.getFitness(), population.getMeanFitness());

                iteration++;
                bestIndividual = population.retrieveBestIndividual();
                if(bestIndividual.getFitness() < solution.getFitness()) {
                    solution = bestIndividual;
                }

            }
            solutionWriter.write(i +";" + population.getBestFitness() + ";" + population.getMeanFitness());
            solutionWriter.writeLine("" + bestIndividual.getNumberOfTrips() + ";" + bestIndividual.getAverageCost());
            //solutionWriter.writeLine(""+bestIndividual.getPhenotype());
            iterationWriter.writeLine("");
            System.out.println("\tBestFitness = " + bestIndividual.getFitness());
            System.out.println("\tMeanFitness = " + population.getMeanFitness());
        }


        long stopTime = System.currentTimeMillis();
        long elapsedTime = (stopTime - startTime)*1000;
        solutionWriter.writeLine("");
        solutionWriter.writeLine("ExecutionTime");
        solutionWriter.writeLine(elapsedTime/seed + ";");

        solutionWriter.writeLine("Fitness");
        solutionWriter.writeLine("" + solution.getFitness());
        solutionWriter.writeLine("SolutionFound");
        solutionWriter.writeLine(solution.toString());

        solutionWriter.flush();
        solutionWriter.close();
        iterationWriter.flush();
        iterationWriter.close();
        return solution;
    }

    private void setSeed(long i) {
        random = new Random(i);
        creationOp.setRandom(random);
        selectionOp.setRandom(random);
        crossoverOp.setRandom(random);
        mutationOp.setRandom(random);
    }

    private Individual iterate(Population oldPopulation){
        List<Individual> newGeneration = new ArrayList<Individual>();

        while(newGeneration.size() < populationSize) {
            // SELECTION
            Individual[] parents = selectionOp.selection(oldPopulation);

            // CROSSOVER
            Individual c1, c2;
            if(random.nextDouble() <= this.crossoverProbability) {
                c1  = crossoverOp.cross(parents[0], parents[1]);
                c2 = crossoverOp.cross(parents[1], parents[0]);
                crossoverOp.reset();
            } else {
                c1 = parents[0].clone();
                c1.setParentIdent(parents[0].getIdent());
                c2 = parents[1].clone();
                c2.setParentIdent(parents[1].getIdent());
            }

            // MUTATION
            if(random.nextDouble() <= this.mutationProbability) {
                mutationOp.mutate(c1);
            }

            if(random.nextDouble() <= this.mutationProbability) {
                mutationOp.mutate(c2);
            }

            // EVALUATION
            split(c1);
            split(c2);

            c1.setIdent(newGeneration.size());
            newGeneration.add(c1);

            c2.setIdent(newGeneration.size());
            newGeneration.add(c2);
        }

        // REPLACEMENT
        Population newPopulation = new Population(newGeneration);
        this.population = replacementOp.replace(oldPopulation, newPopulation);

        selectionOp.reset();

        return population.retrieveBestIndividual();
    }


    private void split(Individual individual) {
        int[] t = new int[problem.getNumberOfClients()+1];
        t[0] = 0;
        int[] genotype = individual.getGenotype();
        for (int i = 0; i < genotype.length; i++) {
            t[i+1] = genotype[i];
        }

        double[] v = new double[t.length];
        int[] p = new int[t.length];

        v[0] = 0;
        p[0] = 0;

        for(int i = 1; i < t.length; i++) {
            v[i] = Integer.MAX_VALUE;
        }

        int j;
        int load;
        TFN cost = new TFN(0, 0, 0);
        TFN aux;

        for(int i = 1; i < t.length; i++) {
            j = i;
            load = 0;

            while(!(j >= t.length || load > problem.getVehicleCapacity())) {
                load = load + problem.demand(t[j]);
                if(i == j) {
                    cost = problem.cost(0, t[i]).add(problem.cost(t[i], 0));
                } else {
                    aux = cost.minus(problem.cost(t[j-1], 0));
                    cost = problem.cost(t[j-1], t[j]).add(problem.cost(t[j], 0));
                    cost = cost.add(aux);
                }

                if(load <= problem.getVehicleCapacity() && (v[i-1] + cost.expectedValue() < v[j])) {
                    v[j] = v[i-1] + cost.expectedValue();
                    p[j] = i-1;
                }

                j++;

            }
        }

        evaluate(individual, t, v, p);
    }

    private void evaluate(Individual individual, int[] t, double[] v, int[] p) {
        List<List<Integer>> solution = new ArrayList<>();

        int j =  t.length-1;

        List<Integer> trip;
        while (j!= 0) {
            trip = new ArrayList<>();
            for(int k = p[j]+1; k <= j; k++) {
                trip.add(t[k]);
            }

            solution.add(0, trip);
            j = p[j];
        }

        individual.setPhenotype(solution);
        if(v[t.length-1] != Double.NEGATIVE_INFINITY && v[t.length-1] != Double.POSITIVE_INFINITY)
            individual.setFitness(Math.abs(v[t.length-1]));
        else
            individual.setFitness(Double.MAX_VALUE);
    }
}

