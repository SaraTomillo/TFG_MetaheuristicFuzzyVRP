package test;

import main.ga.replacement.TournamentPCNoRepetition;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.ga.creation.RandomPopulationTest;
import test.ga.crossover.LinearOrderBasedCrossoverTest;
import test.ga.crossover.OrderBasedCrossoverTest;
import test.ga.crossover.PositionBasedCrossoverTest;
import test.ga.mutation.InsertionTest;
import test.ga.mutation.SwapSubstringTest;
import test.ga.mutation.SwapTest;
import test.ga.replacement.ElitismTest;
import test.ga.replacement.TournamentPCNoRepetitionTest;
import test.ga.replacement.TournamentPCTest;
import test.ga.selection.RandomPairsTest;
import test.ga.selection.TournamentTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ RandomPopulationTest.class, RandomPairsTest.class, TournamentTest.class,
        OrderBasedCrossoverTest.class, LinearOrderBasedCrossoverTest.class, PositionBasedCrossoverTest.class,
        InsertionTest.class, SwapTest.class, SwapSubstringTest.class,
        ElitismTest.class, TournamentPCTest.class, TournamentPCNoRepetitionTest.class})
public class AllTests {


    @BeforeClass
    public static void setUpClass() {
        System.out.println("Master setup");

    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Master tearDown");
    }

}