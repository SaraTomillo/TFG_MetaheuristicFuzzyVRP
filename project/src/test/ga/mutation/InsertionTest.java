package test.ga.mutation;

import main.ga.mutation.Insertion;
import main.vpr.Individual;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class InsertionTest {

    private final Insertion insertion;

    public InsertionTest() {
        this.insertion = new Insertion();
        this.insertion.setRandom(new Random(5));
    }

    @Test
    public void testMutation() {
        int[] before = new int[]{1, 3, 2, 4, 5, 8, 7, 6, 9};
        int[] after = new int[]{1, 3, 2, 9, 4, 5, 8, 7, 6};
        Individual individual = new Individual(before);

        insertion.mutate(individual);

        assertNotNull(individual);
        assertNotNull(individual.getGenotype());

        assertThat(before, IsNot.not(IsEqual.equalTo(individual.getGenotype())));
        assertArrayEquals(after, individual.getGenotype());
    }

}
