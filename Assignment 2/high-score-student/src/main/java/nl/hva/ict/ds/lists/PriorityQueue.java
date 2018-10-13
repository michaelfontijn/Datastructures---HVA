package nl.hva.ict.ds.lists;

import nl.hva.ict.ds.HighScoreList;
import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements HighScoreList {
    private ArrayList<E> players = new ArrayList<>();

    public PriorityQueue(){}

    @Override
    public void add(Player player) {
        // First we add the player to the list
        players.add((E) player);

        // Then we get the index of the player we just added
        int currentPlayer = this.size() -1; // since the last index is n - 1

        while (currentPlayer > 0){
            // Get the index of the parent
            int parentIndex = (currentPlayer - 1) / 2;

            // Check if the high score of the current player is bigger then the highscore of it's parent
            // If so make them swap places
            if (players.get(currentPlayer).compareTo(
                    players.get(parentIndex)) > 0) {
                // Get the current player
                E temp = players.get(currentPlayer);

                // Set the parent player on the position of the current player
                players.set(currentPlayer, players.get(parentIndex));

                // Set the currentPlayer (which we stored in temp) at the position of the parent
                players.set(parentIndex, temp);
            }
            else {
                break;
            }
            currentPlayer = parentIndex;
        }
    }

    @Override
    public List<Player> getHighScores(int numberOfHighScores) {
        // Clone the players list
        ArrayList<E> playersClone = (ArrayList<E>) players.clone();
        List<Player> result = new ArrayList<>();

        // If the asked numberOfHighScores is bigger then the total amount of highscores
        // set the numberOfHighScores to the size of the player list
        if (numberOfHighScores > this.size())
                numberOfHighScores = this.size();

        //loop an x amount of times based on the number of high scores asked by the user
        for (int i = 0; i < numberOfHighScores; i++) {
            result.add((Player)this.remove());
        }
        players = playersClone;
        return result;
    }

    @Override
    public List<Player> findPlayer(String firstName, String lastName) throws IllegalArgumentException {
        //if both first and last name are empty
        if ((firstName == null || firstName.trim().isEmpty()) && (lastName == null || lastName.trim().isEmpty()))
            throw new IllegalArgumentException("Either a valid first name or a last name has to be supplied");

        List<Player> result = new ArrayList<>();

        //go trough all items in the collection to find matches with the search criteria
        for (E player: this.players) {
            Player pl = (Player) player;
            String fName = pl.getFirstName();
            String lName = pl.getLastName();

            if (fName.startsWith(firstName) || fName == firstName
                    || lName.startsWith(lastName) || lName == lastName) {
                result.add(pl);
            }
        }
        return result;
    }

    public E remove() {
        // If the players list is empty return
        if (players.size() == 0) return null;

        // Store the object we are going to remove so we can return it at the end
        E removedObject = players.get(0);

        // Remove the player with the highest high score
        players.set(0, players.get(players.size() - 1));
        players.remove(players.size() - 1);

        int currentIndex = 0;

        // While the current index is smaller then the total size of the players collection
        while (currentIndex < players.size()) {
            // Get the indes of the left and right child. ( The players underneath the currentPlayer )
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;

            // Find the maximum between two children
            if (leftChildIndex >= players.size()) break;
            int maxIndex = leftChildIndex;
            if (rightChildIndex < players.size()) {
                if (players.get(maxIndex).compareTo(
                        players.get(rightChildIndex)) < 0) {
                    maxIndex = rightChildIndex;
                }
            }

            // Swap if the current players highscore is less than the childs highscore
            if (players.get(currentIndex).compareTo(
                    players.get(maxIndex)) < 0) {
                E temp = players.get(maxIndex);
                players.set(maxIndex, players.get(currentIndex));
                players.set(currentIndex, temp);
                currentIndex = maxIndex;
            } else
                break;
        }

        return removedObject;
    }

    /**
     * Get the size of the array
     *
     * @return size of the array
     */
    private int size(){
        return players.size();
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

        System.out.println(pql.findPlayer("Jeroen", "Beljaars"));
    }
}
