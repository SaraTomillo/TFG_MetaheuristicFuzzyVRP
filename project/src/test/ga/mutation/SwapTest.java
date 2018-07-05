package test.ga.mutation;

import main.ga.mutation.Swap;
import main.vpr.Individual;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class SwapTest {

    private final Swap swap;

    public SwapTest() {
        this.swap = new Swap();
        this.swap.setRandom(new Random(5));
    }

    @Test
    public void testMutation() {
        int[] before = new int[]{1, 3, 2, 4, 5, 8, 7, 6, 9};
        int[] after = new int[]{1, 9, 2, 4, 5, 8, 7, 6, 3};
        Individual individual = new Individual(before);

        swap.mutate(individual);

        assertNotNull(individual);
        assertNotNull(individual.getGenotype());

        assertArrayEquals(after, individual.getGenotype());
    }

}
