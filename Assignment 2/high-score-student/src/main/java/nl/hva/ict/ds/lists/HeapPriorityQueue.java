package nl.hva.ict.ds.lists;

import nl.hva.ict.ds.HighScoreList;
import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class HeapPriorityQueue<E extends Comparable<E>> implements HighScoreList {
    private ArrayList<E> heapArray = new ArrayList<>();

    public HeapPriorityQueue(){}

    /**
     * Create a new heap from an excisting Array
     */
    public HeapPriorityQueue(E[] array){
        for (E item : array) {
            this.add((Player) item);
        }
    }

    @Override
    public void add(Player player) {
        heapArray.add((E) player);
        int lastNodeIndex = this.size() -1; // since the last index is n - 1

        while (lastNodeIndex > 0){
            // Get the index of the parent
            int parentIndex = (lastNodeIndex - 1) / 2;

            // Start reordering
            if (heapArray.get(lastNodeIndex).compareTo(
                    heapArray.get(parentIndex)) > 0) {
                E temp = heapArray.get(lastNodeIndex);
                heapArray.set(lastNodeIndex, heapArray.get(parentIndex));
                heapArray.set(parentIndex, temp);
            }
            else {
                break;
            }
            lastNodeIndex = parentIndex;
        }
    }

    @Override
    public List<Player> getHighScores(int numberOfHighScores) {
        ArrayList<E> heapArrayClone = (ArrayList<E>) heapArray.clone();
        List<Player> result = new ArrayList<>();
        if (numberOfHighScores > this.size())
                numberOfHighScores = this.size();

        for (int i = 0; i < numberOfHighScores; i++) {
            result.add((Player)this.remove());
        }
        heapArray = heapArrayClone;
        return result;
    }

    @Override
    public List<Player> findPlayer(String firstName, String lastName) throws IllegalArgumentException {
        //if both first and last name are empty
        if ((firstName == null || firstName.trim().isEmpty()) && (lastName == null || lastName.trim().isEmpty()))
            throw new IllegalArgumentException("Either a valid first name or a last name has to be supplied");

        List<Player> result = new ArrayList<>();

        for (E player: this.heapArray) {
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
        if (heapArray.size() == 0) return null;

        E removedObject = heapArray.get(0);
        heapArray.set(0, heapArray.get(heapArray.size() - 1));
        heapArray.remove(heapArray.size() - 1);

        int currentIndex = 0;
        while (currentIndex < heapArray.size()) {

            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;

            // Find the maximum between two children
            if (leftChildIndex >= heapArray.size()) break; // The tree is a heap
            int maxIndex = leftChildIndex;
            if (rightChildIndex < heapArray.size()) {
                if (heapArray.get(maxIndex).compareTo(
                        heapArray.get(rightChildIndex)) < 0) {
                    maxIndex = rightChildIndex;
                }
            }

            // Swap if the current node is less than the maximum
            if (heapArray.get(currentIndex).compareTo(
                    heapArray.get(maxIndex)) < 0) {
                E temp = heapArray.get(maxIndex);
                heapArray.set(maxIndex, heapArray.get(currentIndex));
                heapArray.set(currentIndex, temp);
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
        return heapArray.size();
    }

    public static void main(String [] args)
    {
        Player player1 = new Player("Chris", "Jakkes", 3);
        Player player2 = new Player("Joost", "Daag", 2);
        Player player3 = new Player("Jeroen", "Beljaars", 4);


        HeapPriorityQueue pql = new HeapPriorityQueue();
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
