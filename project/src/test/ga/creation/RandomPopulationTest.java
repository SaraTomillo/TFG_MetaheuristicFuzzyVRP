package test.ga.creation;

import main.ga.creation.RandomPopulation;
import main.tfn.TFN;
import main.vpr.Individual;
import main.vpr.Population;
import main.vpr.Problem;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class RandomPopulationTest {

    private final RandomPopulation randomPopulation;

    public RandomPopulationTest() {
        this.randomPopulation = new RandomPopulation();
        this.randomPopulation.setRandom(new Random(1));
    }

    @Test
    public void testCreateRandomPopulation() {
        String[] clients = new String[] {"a", "b", "c", "d", "e"};
        Problem problem = new Problem(10, clients, new int[1], new TFN[11][11]);
        Population population = randomPopulation.createInitialPopulation(problem, 10);

        assertTrue(population.size() == 10);
        for(int i = 0; i < 10; i++) {
            assertNotNull(population.get(i));
        }
    }

    @Test
    public void testCreateRandomIndividual() {
        Individual individual = randomPopulation.createRandomIndividual(10);

        assertNotNull(individual);
        assertNotNull(individual.getGenotype());
        assertTrue(distinctValues(Arrays.asList(individual.getGenotype())));
    }

    public static boolean  distinctValues(Iterable<Object> objs){
        Set<Object> foundObjects = new HashSet<>();
        for(Object o : objs){
            if(foundObjects.contains(o)){
                return false;
            }
            foundObjects.add(o);
        }
        return true;
    }
}
