package nl.hva.ict.ds;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

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

    @Before
    public final void setup2() {
        highscores2 = new HighScorePlayerFinder(7);

        nearlyHeadlessNick2 = new Player("Nicholas", "de Mimsy-Porpington", 95);
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
    public final void noOneIsThere() {
        List<Player> list = highscores2.findPlayer("LOL", null);

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
    public final void invalidNameLastName() {
        List<Player> headless = highscores2.findPlayer(null, "Kip");

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
    public final void invalidNameFullName() {
        List<Player> headless = highscores2.findPlayer("Geen", "Kip");

        assertEquals(0, headless.size());
    }

}
