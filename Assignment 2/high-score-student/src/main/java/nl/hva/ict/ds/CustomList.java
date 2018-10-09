package nl.hva.ict.ds;

import java.util.List;

//TODO use this as a base and extend the rest of the list from this to avoid duplicate code! (Not sure if this would work tho)
public class CustomList implements HighScoreList{

    protected Player[] playerCollection = new Player[100];
    protected int itemCount = 0;

    @Override
    public void add(Player player) {

    }

    @Override
    public List<Player> getHighScores(int numberOfHighScores) {
        return null;
    }

    @Override
    public List<Player> findPlayer(String firstName, String lastName) throws IllegalArgumentException {
        return null;
    }


}
