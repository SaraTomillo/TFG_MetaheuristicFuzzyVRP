package main.parsers.configuration;

import main.ga.creation.CreationOperator;
import main.ga.creation.RandomPopulation;
import main.ga.crossover.CrossoverOperator;
import main.ga.crossover.LinearOrderBasedCrossover;
import main.ga.crossover.OrderBasedCrossover;
import main.ga.crossover.PositionBasedCrossover;
import main.ga.mutation.Insertion;
import main.ga.mutation.MutationOperator;
import main.ga.mutation.Swap;
import main.ga.mutation.SwapSubstring;
import main.ga.replacement.Elitism;
import main.ga.replacement.ReplacementOperator;
import main.ga.replacement.TournamentPC;
import main.ga.replacement.TournamentPCNoRepetition;
import main.ga.selection.RandomPairs;
import main.ga.selection.SelectionOperator;
import main.ga.selection.Tournament;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVConfigurationParser implements ConfigurationParser {

    CreationOperator creationOp;
    SelectionOperator selectionOp;
    CrossoverOperator crossoverOp;
    MutationOperator mutationOp;
    ReplacementOperator replacementOp;

    int executionId = -1;
    int populationSize;
    int maxIterations;
    double crossoverProbability;
    double mutationProbability;
    long seed;

    String filename;
    BufferedReader br;
    String line;
    String cvsSplitBy = ";";
    String[] config;

    public CSVConfigurationParser(String filename) {
        this.filename = filename;
    }

    public void parse() {
        try {
            br = new BufferedReader(new FileReader(filename));
            line = br.readLine();
            if(line != null) {
                config = line.split(cvsSplitBy);
            }
            //br.readLine();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void readConfig(int i) throws IOException {
        parse();
        while(executionId != i) {
                line = br.readLine();
                if(line != null) {
                config = line.split(cvsSplitBy);
                this.executionId = Integer.valueOf(config[0]);
            }
        }

        if(line != null) {
            this.populationSize = Integer.valueOf(config[1]);
            this.maxIterations = Integer.valueOf(config[2]);
            this.seed = Long.valueOf(config[3]);

            String creation = config[4];
            switch (creation) {
                case "RandomPopulation":
                    this.creationOp = new RandomPopulation();
                    break;
            }

            String selection = config [5];
            switch (selection) {
                case "RandomPairs":
                    this.selectionOp = new RandomPairs();
                    break;

                case "TournamentTest":
                    this.selectionOp = new Tournament(4);
                    break;
            }


            String crossover = config[6];
            switch (crossover) {
                case "OX":
                    this.crossoverOp = new OrderBasedCrossover();
                    break;

                case "LOX":
                    this.crossoverOp = new LinearOrderBasedCrossover();
                    break;

                case "POS":
                    this.crossoverOp = new PositionBasedCrossover();
            }

            String mutation = config[7];
            switch (mutation) {
                case "Insertion":
                    this.mutationOp = new Insertion();
                    break;

                case "Swap":
                    this.mutationOp = new Swap();

                case "SwapSubstring":
                    this.mutationOp = new SwapSubstring();
            }

            String replacement = config[8];
            switch (replacement) {
                case "Elitism":
                    this.replacementOp = new Elitism();
                    break;

                case "TournamentPC":
                    this.replacementOp = new TournamentPC();

                case "TournamentPCNoRepetition":
                    this.replacementOp = new TournamentPCNoRepetition();
            }

            this.crossoverProbability = Double.valueOf(config[9]);
            this.mutationProbability = Double.valueOf(config[10]);

        }
    }

    @Override
    public String getExecutionId() {
        return String.valueOf(this.executionId);
    }

    @Override
    public int getPopulationSize() {
        return this.populationSize;
    }

    @Override
    public int getMaxIterations() {
        return this.maxIterations;
    }

    @Override
    public double getCrossoverProbability() {
        return this.crossoverProbability;
    }

    @Override
    public double getMutationProbability() {
        return this.mutationProbability;
    }

    @Override
    public long getRandomSeed() {
        return this.seed;
    }

    @Override
    public CreationOperator getCreationOperator() {
        return this.creationOp;
    }

    @Override
    public SelectionOperator getSelectionOperator() {
        return this.selectionOp;
    }

    @Override
    public CrossoverOperator getCrossoverOperator() {
        return this.crossoverOp;
    }

    @Override
    public MutationOperator getMutationOperator() {
        return this.mutationOp;
    }

    @Override
    public ReplacementOperator getReplacementOperator() {
        return this.replacementOp;
    }


}
