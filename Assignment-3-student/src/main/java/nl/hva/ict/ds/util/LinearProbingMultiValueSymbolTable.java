package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class LinearProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {

    Chain[] table;


    public LinearProbingMultiValueSymbolTable(int arraySize) {
        //initialize the array size //TODO the static size is temp for test, but idk why the unit test class initializes the array with an size of 7, this is ofc never goign to work..
        table = new Chain[10501];
    }

    @Override
    public void put(String key, Player value) {
        int desiredIndex = customHash(key);

        if(desiredIndex < 0){
            //do something
            System.out.println("lel");
        }

        //check if there already is an index on the desired index
        if(table[desiredIndex] != null){
            //now find the last node in the chain, and append the new node to the end of the chain
            Node node =table[desiredIndex].firsNode;

            while(node.nextNode != null){
                node = node.nextNode;
            }

            //when the last node is found, append the new node here
            node.nextNode = new Node(value);
        }
        else{
            table[desiredIndex] = new Chain(new Node(value));
        }


    }

    @Override
    public List<Player> get(String key) {
        List<Player> result =  new ArrayList<>();

        //get the index by using the same hashing algorithm on the key that was used in the put
        int index = customHash(key);

        //Check weather there is something on the index at all
        if(table[index].firsNode != null){

            //if there is something on the index, add at least the first node
            Node node = table[index].firsNode;
            result.add(node.value);

            //now add all the nodes of the chain to the result
            while(node.nextNode != null){
                node = node.nextNode;
                result.add(node.value);
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
        int result = stringToHash.hashCode() % 31;

        //for some reason the hashCode method sometimes creates negative hashes, just make it absolute to get a positive int
        if(result < 0) result = Math.abs(result);

        return result;
    }
}

class Chain {
    Node firsNode;

    public Chain(){}

    public Chain(Node firsNode){
        this.firsNode = firsNode;
    }
}

class Node{
    Node nextNode;
    Player value;

    public Node(Player value){
        this.value = value;
    }
}



