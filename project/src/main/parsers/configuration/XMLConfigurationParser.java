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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import main.ga.selection.*;
import org.w3c.dom.Document;

import java.io.File;


public class XMLConfigurationParser implements ConfigurationParser {

    CreationOperator creationOp;
    SelectionOperator selectionOp;
    CrossoverOperator crossoverOp;
    MutationOperator mutationOp;
    ReplacementOperator replacementOp;

    String executionId;
    int populationSize;
    int maxIterations;
    double crossoverProbability;
    double mutationProbability;
    long seed;


    String filename;

    public XMLConfigurationParser(String filename) {
        this.filename = filename;
    }

    @Override
    public void parse() {
        try {

            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(xmlFile);

            this.executionId = document.getElementsByTagName("execution-id").item(0).getTextContent();
            this.populationSize = Integer.valueOf(document.getElementsByTagName("populationSize").item(0).getTextContent());
            this.maxIterations = Integer.valueOf(document.getElementsByTagName("maxGenerations").item(0).getTextContent());
            this.crossoverProbability = Double.valueOf(document.getElementsByTagName("crossoverProbability").item(0).getTextContent());
            this.mutationProbability = Double.valueOf(document.getElementsByTagName("mutationProbability").item(0).getTextContent());
            this.seed = Long.valueOf(document.getElementsByTagName("randomSeed").item(0).getTextContent());

            String creation = document.getElementsByTagName("creationOperator").item(0).getTextContent();
            switch (creation) {
                case "RandomPopulation":
                    this.creationOp = new RandomPopulation();
                    break;
            }

            String selection = document.getElementsByTagName("selectionOperator").item(0).getTextContent();
            switch (selection) {
                case "RandomPairs":
                    this.selectionOp = new RandomPairs();
                    break;

                case "Tournament":
                    this.selectionOp = new Tournament(4);
                    break;
            }


            String crossover = document.getElementsByTagName("crossoverOperator").item(0).getTextContent();
            switch (crossover) {
                case "OX":
                    this.crossoverOp = new OrderBasedCrossover();
                    break;

                case "LOX":
                    this.crossoverOp = new LinearOrderBasedCrossover();
                    break;

                case "POS":
                    this.crossoverOp = new PositionBasedCrossover();
                    break;
            }

            String mutation = document.getElementsByTagName("mutationOperator").item(0).getTextContent();
            switch (mutation) {
                case "Insertion":
                    this.mutationOp = new Insertion();
                    break;

                case "Swap":
                    this.mutationOp = new Swap();
                    break;

                case "SwapSubstring":
                    this.mutationOp = new SwapSubstring();
                    break;
            }

            String replacement = document.getElementsByTagName("replacementOperator").item(0).getTextContent();
            switch (replacement) {
                case "Elitism":
                    this.replacementOp = new Elitism();
                    break;

                case "TournamentPC":
                    this.replacementOp = new TournamentPC();
                    break;

                case "TournamentPCNoRepetition":
                    this.replacementOp = new TournamentPCNoRepetition();
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getExecutionId() {
        return this.executionId;
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
