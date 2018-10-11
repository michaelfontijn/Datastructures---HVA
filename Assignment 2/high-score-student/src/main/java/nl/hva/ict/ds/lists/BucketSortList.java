package nl.hva.ict.ds.lists;

import nl.hva.ict.ds.HighScoreList;
import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BucketSortList implements HighScoreList {
    Player[] playerCollection = new Player[100];
    int itemCount = 0;


    public void doBucketSort() {
        //Create 100 buckets, a bucket for each jump of 1000 since the max highscore is 100000
        Bucket[] sortBuckets = new Bucket[100];


        //Now put the players in the correct buckets
        for (int i = 0; i < itemCount; i++) {
            Player currentP = playerCollection[i];

            //To determine the bucket in which the player should be placed, divide the highscore of the player by 1000,
            //and subtract one since we work with indexes that start couting from 0
            // if the index becomes -1 after subtracting 1 set it to 0
            int bucketIndex = (int) (currentP.getHighScore() / 1000) - 1;
            if (bucketIndex < 0) bucketIndex = 0;

            //Only make the bucket when it is needed.
            if (sortBuckets[bucketIndex] == null) sortBuckets[bucketIndex] = new Bucket();

            //Add the player to its correct bucket
            sortBuckets[bucketIndex].insert(currentP);
        }

        //now sort each bucket that is not null using the insertion sort algorithm
        for (int i = 0; i < sortBuckets.length; i++) {

            //Sort all buckets that have content
            if (sortBuckets[i] != null) {
                sortBuckets[i].sortBucket();
            }
        }

        //And for the final step, add all the sorted buckets back together in reverse order to end up with a sorted array
        //in descending order
        int index = 0;
        for (int i = sortBuckets.length - 1; i >= 0; i--) {
            if (sortBuckets[i] != null) {
                Bucket currentBucket = sortBuckets[i];
                for (int j = 0; j < sortBuckets[i].getSize(); j++) {
                    playerCollection[index++] = currentBucket.getPlayer(j);
                }
            }
        }

    }

    /***
     * A simple method to double the current max size of the collection
     */
    private void doubleArraySize() {
        playerCollection = Arrays.copyOf(playerCollection, playerCollection.length * 2);

    }

    @Override
    public void add(Player player) {
        //in case the max size has been reached, increase the max size
        if (itemCount >= playerCollection.length) doubleArraySize();

        //you can use the item count as new index since it counts from 1 and therefor is always the highest index +1
        playerCollection[itemCount] = player;

        itemCount++;

        //make sure to always re-sortBucket the collection after altering the content of the collection
        doBucketSort();
    }

    @Override
    public List<Player> getHighScores(int numberOfHighScores) {
        List<Player> result = new ArrayList<>();

        //loop an x amount of times based on the number of high scores asked by the user
        for (int i = 0; i < numberOfHighScores; i++) {
            //if the index lies beyond the length of the array return the current result
            if (i > playerCollection.length) return result;
            //when the current player is null this means there are no more players in the collection, return the current result
            if (playerCollection[i] == null) return result;

            result.add(playerCollection[i]);
        }
        return result;
    }

    @Override
    public List<Player> findPlayer(String firstName, String lastName) throws IllegalArgumentException {
        //if both first and last name are empty
        if ((firstName == null || firstName.trim().isEmpty()) && (lastName == null || lastName.trim().isEmpty()))
            throw new IllegalArgumentException("Either a valid first name or a last name has to be supplied");

        List<Player> result = new ArrayList<>();

        //go trough all items in the collection to find matches with the search criteria
        for (int i = 0; i < itemCount; i++) {
            String pFirstname = playerCollection[i].getFirstName();
            String pLastname = playerCollection[i].getLastName();

            if (pFirstname.startsWith(firstName) || pFirstname == firstName
                    || pLastname.startsWith(lastName) || pLastname == lastName) {
                result.add(playerCollection[i]);
            }
        }
        return result;
    }
}


/***
 * A class that represents a bucket in the bucket sortBucket
 */
class Bucket {

    Player[] players = new Player[100];
    int itemCount;

    /***
     * Adds a new player the the collection
     * @param obj An object of type Player
     */
    public void insert(Player obj) {

        //in case the max size has been reached, increase the max size
        if (itemCount >= players.length) doubleArraySize();

        //you can use the item count as new index since it counts from 1 and therefor is always the highest index +1
        players[itemCount] = obj;

        itemCount++;

    }

    /***
     * Retreives a single player from the collection of players based on a given index
     * @param index the index of the player you want to retrieve
     * @return An object of type Player
     */
    public Player getPlayer(int index) {
        if (index > players.length) return null;

        return players[index];
    }

    /***
     * A simple method to double the current max size of the collection
     */
    private void doubleArraySize() {
        players = Arrays.copyOf(players, players.length * 2);

    }


    /***
     * A method that sorts a bucket using a insertion sort algorithm. Its sorts the bucket in descending order
     */
    public void sortBucket() {
        //if there is no more than 1 item in the list, no sort is required
        //if (this.getSize() <= 1) return;


        Player key;

        //Go through all elements in the bucket
        for (int i = 1; i < this.getSize(); i++) {

            //the first unsorted element becomes the key
            key = players[i];

            //since we start the array counting from 1 we need to do -1 to get the actual index
            int index = i - 1;

            //now iterate trough the sorted half of the array(the left part) and check if each item is smaller than the key,
            //if it is smaller move it 1 to the right (check for smaller because its sorted in descending order)
            while (index >= 0 && players[index].getHighScore() < key.getHighScore()) {
                //move the element one position to the right, overwriting the key does not mater because we have stored
                //it in a local variable and we re-add it later in the algorithm
                players[index + 1] = players[index];

                index--;
            }

            //and when a value is found that is not bigger re-insert the key where the gap is
            players[index + 1] = key;
        }
    }

    public int getSize() {
        return itemCount;
    }
}
