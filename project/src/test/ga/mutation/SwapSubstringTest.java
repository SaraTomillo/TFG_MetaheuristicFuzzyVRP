package test.ga.mutation;

import main.ga.mutation.SwapSubstring;
import main.vpr.Individual;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class SwapSubstringTest {

    private final SwapSubstring swapSubstring;

    public SwapSubstringTest() {
        this.swapSubstring = new SwapSubstring();
        this.swapSubstring.setRandom(new Random(1));
    }

    @Test
    public void testMutation() {
        int[] before = new int[]{19, 1, 3, 14, 20, 2, 11, 16, 4, 5, 15, 18, 8, 7, 17, 6, 9, 13, 12, 10};
        int[] after = new int[]{19, 1, 3, 14, 20, 2, 11, 16, 4, 5, 6, 18, 8, 7, 17, 15, 9, 13, 12, 10};
        Individual individual = new Individual(before);

        swapSubstring.mutate(individual);

        assertNotNull(individual);
        assertNotNull(individual.getGenotype());

        assertArrayEquals(after, individual.getGenotype());
    }

}
