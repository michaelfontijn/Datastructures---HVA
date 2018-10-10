package nl.hva.ict.ds.lists;

import nl.hva.ict.ds.HighScoreList;
import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements HighScoreList {
    private ArrayList<E> players = new ArrayList<>();

    public PriorityQueue(){}

    /**
     * Create a new heap from an excisting Array
     */
    public PriorityQueue(E[] array){
        for (E item : array) {
            this.add((Player) item);
        }
    }

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
                E temp = players.get(currentPlayer);
                players.set(currentPlayer, players.get(parentIndex));
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
        ArrayList<E> heapArrayClone = (ArrayList<E>) players.clone();
        List<Player> result = new ArrayList<>();
        if (numberOfHighScores > this.size())
                numberOfHighScores = this.size();

        for (int i = 0; i < numberOfHighScores; i++) {
            result.add((Player)this.remove());
        }
        players = heapArrayClone;
        return result;
    }

    @Override
    public List<Player> findPlayer(String firstName, String lastName) throws IllegalArgumentException {
        //if both first and last name are empty
        if ((firstName == null || firstName.trim().isEmpty()) && (lastName == null || lastName.trim().isEmpty()))
            throw new IllegalArgumentException("Either a valid first name or a last name has to be supplied");

        List<Player> result = new ArrayList<>();

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
        if (players.size() == 0) return null;

        E removedObject = players.get(0);
        players.set(0, players.get(players.size() - 1));
        players.remove(players.size() - 1);

        int currentIndex = 0;
        while (currentIndex < players.size()) {

            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;

            // Find the maximum between two children
            if (leftChildIndex >= players.size()) break; // The tree is a heap
            int maxIndex = leftChildIndex;
            if (rightChildIndex < players.size()) {
                if (players.get(maxIndex).compareTo(
                        players.get(rightChildIndex)) < 0) {
                    maxIndex = rightChildIndex;
                }
            }

            // Swap if the current node is less than the maximum
            if (players.get(currentIndex).compareTo(
                    players.get(maxIndex)) < 0) {
                E temp = players.get(maxIndex);
                players.set(maxIndex, players.get(currentIndex));
                players.set(currentIndex, temp);
                currentIndex = maxIndex;
            } else
                break; // The tree is a heap
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
