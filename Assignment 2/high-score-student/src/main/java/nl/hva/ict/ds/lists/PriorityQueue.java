package nl.hva.ict.ds.lists;

import nl.hva.ict.ds.HighScoreList;
import nl.hva.ict.ds.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PriorityQueue implements HighScoreList {
    ArrayList<Player> playerList = new ArrayList<>();

    @Override
    public void add(Player player) {
        playerList.add(player);
        playerList.sort(Player::compareTo);
    }

    @Override
    public List<Player> getHighScores(int numberOfHighScores) {
        List<Player> result = new ArrayList<>();

        //loop an x amount of times based on the number of high scores asked by the user
        for (int i = 0; i < numberOfHighScores; i++) {
            //if the index lies beyond the length of the array return the current result
            if (i > playerList.size()) return result;
            //when the current player is null this means there are no more players in the collection, return the current result
            if (playerList.get(i) == null) return result;

            result.add(playerList.get(i));
        }
        return result;
    }

    @Override
    public List<Player> findPlayer(String firstName, String lastName) throws IllegalArgumentException {
        //if both first and last name are empty
        if ((firstName == null || firstName.trim().isEmpty()) && (lastName == null || lastName.trim().isEmpty()))
            throw new IllegalArgumentException("Either a valid first name or a last name has to be supplied");

        List<Player> result = new ArrayList<>();

        for (Player player: this.playerList) {
            String fName = player.getFirstName();
            String lName = player.getLastName();

            if (fName.startsWith(firstName) || fName == firstName
                    || lName.startsWith(lastName) || lName == lastName) {
                result.add(player);
            }
        }
        return result;
    }

    public int getSize(){
        return playerList.size();
    }

    public static void main(String [] args)
    {
        Player player1 = new Player("Chris", "Jakkes", 3);
        Player player2 = new Player("Joost", "Daag", 2);
        Player player3 = new Player("Jeroen", "Beljaars", 4);


        PriorityQueue pql = new PriorityQueue();
        pql.add(player1);
        pql.add(player2);
        pql.add(player3);

        for (Object player: pql.getHighScores(3)) {
            Player pl = (Player) player;
            System.out.println(((Player) player).getFirstName() + " " + ((Player) player).getHighScore());
        }

        List<Player> match = pql.findPlayer("Jeroen", "Beljaars");
        System.out.println(match.get(0).getFirstName());
    }
}
