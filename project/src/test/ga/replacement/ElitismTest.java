package test.ga.replacement;

import main.ga.creation.RandomPopulation;
import main.ga.replacement.Elitism;
import main.tfn.TFN;
import main.vpr.Individual;
import main.vpr.Population;
import main.vpr.Problem;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ElitismTest {

    private final Elitism elitism;
    private final RandomPopulation randomPopulation;

    public ElitismTest() {
        this.elitism = new Elitism();

        this.randomPopulation = new RandomPopulation();
        this.randomPopulation.setRandom(new Random(1));
    }

    @Test
    public void testSelection() {
        String[] clients = new String[] {"a", "b", "c", "d", "e"};
        Problem problem = new Problem(10, clients, new int[1], new TFN[11][11]);
        Population population = randomPopulation.createInitialPopulation(problem, 10);
        population.get(0).setFitness(0);
        for(int i = 1; i < population.size(); i++) {
            population.get(i).setFitness(Integer.MAX_VALUE);
        }

        Population newPopulation = randomPopulation.createInitialPopulation(problem, 10);
        for(int i = 0; i < newPopulation.size(); i++) {
            newPopulation.get(i).setFitness(i+10);
        }

        Population result = elitism.replace(population, newPopulation);
        assertNotNull(result);
        assertEquals(result.size(), 10);
        assertTrue(contains(result.getPopulation(), population.get(0)));


    }

    private boolean contains(List<Individual> population, Individual individual) {
        for(Individual ind : population) {
            if(ind.equals(individual))
                return true;
        }
        return false;
    }

}
