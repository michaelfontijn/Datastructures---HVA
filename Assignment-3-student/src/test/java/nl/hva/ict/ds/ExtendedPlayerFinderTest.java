package nl.hva.ict.ds;

import nl.hva.ict.ds.util.LinearProbingMultiValueSymbolTable;
import org.junit.Test;

/**
 * If you have any tests that should be overwritten or added please put them to this class.
 */
public class ExtendedPlayerFinderTest extends HighScorePlayerFinderTest {

    @Test
    public void doMagic(){
        Player nearlyHeadlessNick = new Player("Nicholas", "de Mimsy-Porpington", 95);

        LinearProbingMultiValueSymbolTable sb = new LinearProbingMultiValueSymbolTable(10501);
        sb.put(nearlyHeadlessNick.getFirstName(), nearlyHeadlessNick);
    }

}
