package test.ga.selection;

import main.ga.creation.RandomPopulation;
import main.ga.selection.Tournament;
import main.tfn.TFN;
import main.vpr.Individual;
import main.vpr.Population;
import main.vpr.Problem;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TournamentTest {


    private final Tournament tournament;
    private final RandomPopulation randomPopulation;
    private final Random random;

    public TournamentTest() {
        this.tournament = new Tournament(4);
        this.random = new Random(1);
        this.tournament.setRandom(random);

        this.randomPopulation = new RandomPopulation();
        this.randomPopulation.setRandom(random);
    }

    @Test
    public void testSelection() {
        String[] clients = new String[] {"a", "b", "c", "d"};
        Problem problem = new Problem(10, clients, new int[1], new TFN[11][11]);
        Population population = randomPopulation.createInitialPopulation(problem, 10);

        Individual ind1, ind2;
        for(int i = 0; i < population.size(); i++){
            population.get(i).setFitness(i);
            population.get(i).setIdent(i);
        }
        ind1 = population.get(0).clone();
        ind2 = population.get(3).clone();

        Individual[] parents = tournament.selection(population);
        assertTrue(parents.length == 2);
        assertNotNull(parents);
        for(int i = 0; i < 4; i++) {
            assertEquals(ind1.getGenotype()[i], parents[0].getGenotype()[i]);
            assertEquals(ind2.getGenotype()[i], parents[1].getGenotype()[i]);
        }

        assertEquals(ind1.getIdent(), parents[0].getIdent());
        assertEquals(ind1.getIdent(), parents[1].getIdent());
    }
}
