package nl.hva.ict.ds;

import nl.hva.ict.ds.util.NameReader;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * If you have any tests that should be overwritten or added please put them to this class.
 */
public class ExtendedPlayerFinderTest extends HighScorePlayerFinderTest {

    HighScoreList highscores2;

    Player nearlyHeadlessNick2;
    Player dumbledore2;
    Player harry2;
    Player james2 ;
    Player harry3;
    Player voldemort2;
    private Random randomizer = new SecureRandom();

    @Before
    public final void setup2() {
        highscores2 = new HighScorePlayerFinder(7);

        nearlyHeadlessNick2 = new Player("Nicholas", "de Mimsy-Porpington", 34);
        dumbledore2 = new Player("Albus", "Dumbledore", nearlyHeadlessNick.getHighScore() * 1000);
        harry2 = new Player("Harry", "Potter", dumbledore.getHighScore() + 1000);
        james2 = new Player("James", "Potter", harry.getHighScore() - 4000);
        harry3 = new Player("Harry", "Potter", harry.getHighScore() - 4000);
        voldemort2 = new Player("Nicholas", "Dumbledore", harry.getHighScore() - 10);

        highscores2.add(nearlyHeadlessNick2);
        highscores2.add(dumbledore2);
        highscores2.add(harry2);
        highscores2.add(james2);
        highscores2.add(harry3);
        highscores2.add(voldemort2);
    }

    @Test
    public final void nicholassesIsNotUnique() {
        List<Player> nicholasses = highscores2.findPlayer("Nicholas", null);

        assertEquals(2, nicholasses.size());
        assertEquals(nearlyHeadlessNick2, nicholasses.get(0));
        assertEquals(voldemort2, nicholasses.get(1));
    }

    @Test
    public final void noResultsFirstName() {
        List<Player> list = highscores2.findPlayer("Jaapie", null);

        assertEquals(0, list.size());
    }

    @Test
    public final void dumbledoreIsNotUnique() {
        List<Player> dumbledores = highscores2.findPlayer(null, "Dumbledore");

        assertEquals(2, dumbledores.size());
        assertEquals(dumbledore2, dumbledores.get(0));
        assertEquals(voldemort2, dumbledores.get(1));
    }

    @Test
    public final void noMatchesLastName() {
        List<Player> headless = highscores2.findPlayer(null, "Krekel");

        assertEquals(0, headless.size());
    }

    @Test
    public final void multipleHarryPotters() {
        List<Player> headless = highscores2.findPlayer("Harry", "Potter");

        assertEquals(2, headless.size());
        assertEquals(harry2.getFirstName(), headless.get(0).getFirstName());
        assertEquals(harry3.getFirstName(), headless.get(1).getFirstName());
    }

    @Test
    public final void noMatches() {
        List<Player> result = highscores2.findPlayer("Jaapie", "Krekel");

        assertEquals(0, result.size());
    }

    @Test
    public void addAndGet() {
        String [] firstNames = new NameReader("/firstnames.txt").getNames();
        String [] lastNames = new NameReader("/lastnames.txt").getNames();

        highscores = new HighScorePlayerFinder(10501); // Please adjust this size!

        ArrayList<Player> players = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            String firstName = firstNames[randomizer.nextInt(firstNames.length)];
            String lastName = lastNames[randomizer.nextInt(lastNames.length)];
            Player player = new Player(firstName, lastName, randomizer.nextInt(1000));
            players.add(player);
            highscores.add(player);
        }
        for (Player player: players) {
            assertTrue(highscores.findPlayer(player.getFirstName(), "").contains(player));
            assertTrue(highscores.findPlayer("", player.getLastName()).contains(player));
            assertTrue(highscores.findPlayer(player.getFirstName(), player.getLastName()).contains(player));
        }
    }
}
