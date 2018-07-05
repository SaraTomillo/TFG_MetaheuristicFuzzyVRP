package test.ga.replacement;

import main.ga.creation.RandomPopulation;
import main.ga.crossover.OrderBasedCrossover;
import main.ga.replacement.TournamentPC;
import main.ga.replacement.TournamentPCNoRepetition;
import main.ga.selection.Tournament;
import main.tfn.TFN;
import main.vpr.Individual;
import main.vpr.Population;
import main.vpr.Problem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class TournamentPCNoRepetitionTest {

    private final Tournament tournament;
    private final OrderBasedCrossover orderBasedCrossover;
    private final TournamentPCNoRepetition tournamentPCNoRepetition;
    private final RandomPopulation randomPopulation;
    private final Random random;

    public TournamentPCNoRepetitionTest() {
        this.tournament = new Tournament(4);
        this.random = new Random(1);
        this.tournament.setRandom(random);

        this.tournamentPCNoRepetition = new TournamentPCNoRepetition();

        this.randomPopulation = new RandomPopulation();
        this.randomPopulation.setRandom(random);

        this.orderBasedCrossover = new OrderBasedCrossover();
        this.orderBasedCrossover.setRandom(random);
    }

    @Test
    public void testSelection() {
        String[] clients = new String[] {"a", "b", "c", "d"};
        Problem problem = new Problem(10, clients, new int[1], new TFN[11][11]);
        Population population = randomPopulation.createInitialPopulation(problem, 2);

        for(int i = 0; i < population.size(); i++){
            population.get(i).setIdent(i);
        }

        Individual[] parents = tournament.selection(population);
        parents[0].setFitness(5);
        parents[1].setFitness(10);

        Individual child1 = orderBasedCrossover.cross(parents[0], parents[1]);
        child1.setFitness(5);
        Individual child2 = orderBasedCrossover.cross(parents[1], parents[0]);
        child2.setFitness(25);
        List<Individual> aux = new ArrayList<>();
        aux.add(child1);
        aux.add(child2);

        Population children = new Population(aux);
        Population newPopulation = tournamentPCNoRepetition.replace(population, children);

        assertNotNull(newPopulation);
        assertNotEquals(newPopulation, population);
        assertNotEquals(newPopulation, children);
        assertEquals(2, newPopulation.size());

        assertTrue(contains(newPopulation.getPopulation(), parents[0]));
        assertTrue(contains(newPopulation.getPopulation(), child1));

    }

    private boolean contains(List<Individual> population, Individual individual) {
        for(Individual ind : population) {
            if(ind.equals(individual))
                return true;
        }
        return false;
    }
}
