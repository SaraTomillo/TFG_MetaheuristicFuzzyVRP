package test.ga.selection;

import main.ga.creation.RandomPopulation;
import main.ga.selection.RandomPairs;
import main.tfn.TFN;
import main.vpr.Individual;
import main.vpr.Population;
import main.vpr.Problem;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RandomPairsTest {

    private final RandomPairs randomPairs;
    private final RandomPopulation randomPopulation;

    public RandomPairsTest() {
        this.randomPairs = new RandomPairs();
        this.randomPairs.setRandom(new Random(1));

        this.randomPopulation = new RandomPopulation();
        this.randomPopulation.setRandom(new Random(1));
    }

    @Test
    public void testSelection() {
        String[] clients = new String[] {"a", "b", "c", "d", "e"};
        Problem problem = new Problem(10, clients, new int[1], new TFN[11][11]);
        Population population = randomPopulation.createInitialPopulation(problem, 10);

        for(int i = 0; i < population.size()/2; i++){
            Individual[] parents = randomPairs.selection(population);
            assertTrue(parents.length == 2);
            assertNotNull(parents[0]);
            assertNotNull(parents[1]);
            assertTrue(parents[0] != parents[1]);
            assertTrue(notSelectedBefore(Arrays.asList(parents[0])));
            assertTrue(notSelectedBefore(Arrays.asList(parents[1])));
        }


        assertTrue(population.size() == 10);
        for(int i = 0; i < 10; i++) {
            assertNotNull(population.get(i));
        }
    }

    public static boolean  notSelectedBefore(Iterable<Object> objs){
        Set<Object> foundObjects = null;
        if(foundObjects == null)
            foundObjects = new HashSet<>();

        for(Object o : objs){
            if(foundObjects.contains(o)){
                return false;
            }
            foundObjects.add(o);
        }
        return true;
    }

}
