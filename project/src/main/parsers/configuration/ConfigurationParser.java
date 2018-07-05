package main.parsers.configuration;

import main.ga.creation.CreationOperator;
import main.ga.crossover.CrossoverOperator;
import main.ga.mutation.MutationOperator;
import main.ga.replacement.ReplacementOperator;
import main.ga.selection.SelectionOperator;

public interface ConfigurationParser {

    public void parse();

    public String getExecutionId();
    public int getPopulationSize();
    public int getMaxIterations();
    public double getCrossoverProbability();
    public double getMutationProbability();
    public long getRandomSeed();

    public CreationOperator getCreationOperator();
    public SelectionOperator getSelectionOperator();
    public CrossoverOperator getCrossoverOperator();
    public MutationOperator getMutationOperator();
    public ReplacementOperator getReplacementOperator();
}
