package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class LinearProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {

    Player[] table;
    int currentItems = 0;
    int collisions = 0;

    public LinearProbingMultiValueSymbolTable(int arraySize) {
        //initialize the array size //TODO the static size is temp for test, but idk why the unit test class initializes the array with an size of 7, this is ofc never goign to work..
        table = new Player[arraySize];
    }

    @Override
    public void put(String key, Player value) {
        //if there is no more space in the array
        if(currentItems >= table.length) return;

        int desiredIndex = customHash(key);

        //check if there already is an index on the desired index, find the next open index
        if(table[desiredIndex] != null){

            //find the next open index
            int openIndex = desiredIndex;
            while(table[openIndex] != null){

                collisions++;
                openIndex = (openIndex +1) % table.length;
            }

            //place the player on the open index
            table[openIndex] = value;
        }
        else{
            table[desiredIndex] = value;
        }

        currentItems++;
    }

    @Override
    public List<Player> get(String key) {
        List<Player> result =  new ArrayList<>();

        //get the index by using the same hashing algorithm on the key that was used in the put
        int startIndex = customHash(key);

        //find a match with the key
        for(int i = startIndex; i < table.length; i = (i+ 1 ) % table.length){
            if(table[i] == null) return result;

            if(table[i].getFirstName() == key){
                result.add(table[i]);
            }
        }
        return result;
    }

    /***
     * Creates a hash from a string
     * @param stringToHash The string to be hashed
     * @return a hash of type int
     */
    private int customHash(String stringToHash){
        int result = stringToHash.hashCode() % table.length ;

        //for some reason the hashCode method sometimes creates negative hashes, just make it absolute to get a positive int
        if(result < 0) result = Math.abs(result);


        return result;
    }
}





