package test.ga.crossover;

import main.ga.crossover.PositionBasedCrossover;
import main.vpr.Individual;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class PositionBasedCrossoverTest {

    private final PositionBasedCrossover positionBasedCrossover;

    public PositionBasedCrossoverTest() {
        this.positionBasedCrossover = new PositionBasedCrossover();
        this.positionBasedCrossover.setRandom(new Random(5));
    }

    @Test
    public void testCrossover() {
        int[] expected1 = new int[] {1, 3, 2, 4, 7, 8, 9, 6, 5};
        int[] expected2 = new int[] {3, 1, 2, 6, 4, 8, 7, 5, 9};

        Individual parent1 = new Individual( new int[]{1, 3, 2, 4, 5, 8, 7, 6, 9});
        Individual parent2 = new Individual( new int[]{3, 2, 4, 6, 7, 8, 9, 5, 1});

        Individual child1 = positionBasedCrossover.cross(parent1, parent2);
        Individual child2 = positionBasedCrossover.cross(parent2, parent1);

        assertNotNull(child1);
        assertNotNull(child2);

        assertNotNull(child1.getGenotype());
        assertNotNull(child2.getGenotype());

        int[] genotype1 = child1.getGenotype();
        int[] genotype2 = child2.getGenotype();
        assertArrayEquals(expected1, genotype1);
        assertArrayEquals(expected2, genotype2);
    }

}