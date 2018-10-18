package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.Hashtable;
import java.util.List;

public class LinearProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {

    Hashtable<Integer,Node> table = new Hashtable<Integer, Node>();

    public LinearProbingMultiValueSymbolTable(int arraySize) {

    }

    @Override
    public void put(String key, Player value) {
        int hashingKey = value.getFirstName().hashCode();

        //if the table already contains the key
        if(table.containsKey(hashingKey)){

            //get the node that is at the index of the hashkey
            Node node = table.get(hashingKey);

            //find the last node in the current chain on the index of the hashingkey
            while(node.nextNode != null){
                node = node.nextNode;
            }

            //when the last node is found, add the new node to the end of the link chain
            node.nextNode = new Node(value);


        }
        //else just insert it normally
        else{
            table.put(value.getFirstName().hashCode(), new Node(value));
        }

    }

    @Override
    public List<Player> get(String key) {

        //convert the "key" wich is firtname to an hashkey
        //check if there is a node chain on the index of the hashkey
        //if there is no chain just return the player value of the node.
        //if there is a chain on the index of the hashkey, return all player in the chain

        return null;
    }


}

class LinkedList{
    Node firsNode;
}

class Node{
    Node nextNode;
    Player value;


    public Node(Player value){
        this.value = value;
    }
}



