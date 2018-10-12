package nl.hva.ict.ds;

import nl.hva.ict.ds.lists.BucketSortList;
import nl.hva.ict.ds.lists.PriorityQueue;
import nl.hva.ict.ds.lists.SelectionSortList;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class contains some unit tests. They by no means ensure that all the requirements are implemented
 * correctly.
 */
public class HighScoreListTest {
    private static final int MAX_HIGH_SCORE = 100000;
    private Random randomizer = new SecureRandom();
    private HighScoreList highScores;
    private Player nearlyHeadlessNick;
    private Player dumbledore;

    @Before
    public void setup() {
        // Here you should select your implementation to be tested.
        //highScores = new DummyHighScores();
//        highScores = new SelectionSortList();
        highScores = new BucketSortList();
//        highScores = new PriorityQueue<>();

        nearlyHeadlessNick = new Player("Nicholas", "de Mimsy-Porpington", getHighScore() % 200);
        dumbledore = new Player("Albus", "Dumbledore", nearlyHeadlessNick.getHighScore());
    }

    @Test
    public void noPlayerNoHighScore() {
        assertTrue("There are high-score while there should be no high-scores!", highScores.getHighScores(1).isEmpty());
    }

    @Test
    public void whenNoHighScoreIsAskedForNonShouldBeGiven() {
        highScores.add(dumbledore);

        assertEquals(0, highScores.getHighScores(0).size());
    }

    @Test
    public void noMoreHighScoresCanBeGivenThenPresent() {
        highScores.add(nearlyHeadlessNick);
        highScores.add(dumbledore);

        assertEquals(2, highScores.getHighScores(10).size());
    }

    @Test
    public void keepAllHighScores() {
        highScores.add(nearlyHeadlessNick);
        highScores.add(dumbledore);

        assertEquals(2, highScores.getHighScores(2).size());
    }

    @Test
    public void singlePlayerHasHighScore() {
        highScores.add(dumbledore);

        assertEquals(dumbledore, highScores.getHighScores(1).get(0));
    }

    @Test
    public void harryBeatsDumbledore() {
        highScores.add(dumbledore);
        Player harry = new Player("Harry", "Potter", dumbledore.getHighScore() + 1);

        //harry should be added to the collection right..?
        highScores.add(harry);

        assertEquals(harry, highScores.getHighScores(1).get(0));
    }

    // Extra unit tests go here

    private long getHighScore() {
        return randomizer.nextInt(MAX_HIGH_SCORE);
    }


    //TODO make a test to test if it goes ok when going over the max index, it should double the current size of the array

    @Test
    public void manyPlayersWhoWins() {
        Player harry = new Player("Harry Last", "Potter", dumbledore.getHighScore() + 1);
        Player harry1 = new Player("Harry1", "Potter", 1000);
        Player harry2 = new Player("Harry2", "Potter", 4000);
        Player harrySeoccond = new Player("HarrySeccond", "Potter", dumbledore.getHighScore() + 4000);
        Player harryWin = new Player("HarryWinner", "Potter", MAX_HIGH_SCORE);

        highScores.add(dumbledore);
        highScores.add(harry);
        highScores.add(harry);
        highScores.add(harry1);
        highScores.add(harry2);
        highScores.add(harryWin);
        highScores.add(harrySeoccond);



        assertEquals(harryWin, highScores.getHighScores(1).get(0));
    }

    @Test
    public void searchPlayers() {
        Player harry = new Player("Harry", "Potter", 10342);
        Player dumbledore = new Player("Albus", "Dumbledore", this.dumbledore.getHighScore());
        Player hermione = new Player("hermione","granger", 10342);
        Player ron = new Player("Ron", "Weasley", 10000);

        highScores.add(harry);
        highScores.add(dumbledore);
        highScores.add(hermione);
        highScores.add(ron);

        assertEquals(harry, highScores.findPlayer("Harry", "Potter").get(0));
        assertEquals(dumbledore, highScores.findPlayer("Albus", "Dumbledore").get(0));
        assertEquals(hermione, highScores.findPlayer("hermione","granger").get(0));
        assertEquals(ron, highScores.findPlayer("Ron", "Weasley").get(0));
    }

    @Test
    public void duel(){
        Player harry = new Player("Harry", "Potter", this.dumbledore.getHighScore() - 100);
        Player dumbledore = new Player("Albus", "Dumbledore", this.dumbledore.getHighScore());
        Player hermione = new Player("hermione","granger", harry.getHighScore() - 40);
        Player ron = new Player("Ron", "Weasley", harry.getHighScore() - 1000);

        highScores.add(harry);
        highScores.add(dumbledore);
        highScores.add(hermione);
        highScores.add(ron);

        List outcome = highScores.getHighScores(100);
        assertEquals(dumbledore, outcome.get(0));
        assertEquals(harry, outcome.get(1));
        assertEquals(hermione, outcome.get(2));
        assertEquals(ron, outcome.get(3));
    }

    @Test
    public void addALot(){
        Random r = new Random();
        List players = new ArrayList();
        int amountOfHogwartsStudents = 100;


        for (int i = 0; i < amountOfHogwartsStudents; i++) {
            Player player = new Player(String.valueOf(i), String.valueOf(i), i * 100);
            players.add(player);
        }
        for (int i = 0; i < amountOfHogwartsStudents; i++) {
            highScores.add((Player) players.remove(r.nextInt(players.size())));
        }
        List<Player> highScoreList = highScores.getHighScores(1000);
        for (int i = 0; i < amountOfHogwartsStudents; i++) {
            Player p = highScoreList.get(i);
            System.out.println(i);
            assertEquals(p.getHighScore(), (amountOfHogwartsStudents - i) * 100);
            assertEquals(p.getFirstName(), String.valueOf(amountOfHogwartsStudents-i));
        }
    }

    @Test
    public void findStudents(){
        Player harry = new Player("Harry", "Potter", this.dumbledore.getHighScore() - 100);
        Player hermione = new Player("hermione","granger", harry.getHighScore() - 40);
        Player ron = new Player("Ron", "Weasley", harry.getHighScore() - 1000);
        Player harryWannabe = new Player("Harry", "Pooter", 10);

        highScores.add(harry);
        highScores.add(this.dumbledore);
        highScores.add(hermione);
        highScores.add(ron);
        highScores.add(harryWannabe);

        List<Player> yerAWizardHarry = highScores.findPlayer("Harry", "Potter");
        assertEquals(harry, yerAWizardHarry.get(0));
        assertEquals(harryWannabe, yerAWizardHarry.get(1));

    }
}