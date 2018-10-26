package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class DoubleHashingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    Player[] players;
    private int arraySize = 0;
    private int curSize;

    public DoubleHashingMultiValueSymbolTable(int arraySize) {
        players = new Player[arraySize];
        this.arraySize = arraySize;
    }

    @Override
    public void put(String key, Player value) {
        if (curSize == arraySize) return; // array is full

        int index = hash(key);
        if(players[index] == null) {
            players[index] = value;
        }
        else {
            int index2 = hash2(key);
            int i = 1;
            int newIndex;
            while(true){
                newIndex = (index + i * index2) % arraySize;
                if (players[newIndex] == null) {
                    players[newIndex] = value;
                    break;
                }
                if (newIndex == index) break;
                i++;
            }
        }
        curSize++;
    }

    @Override
    public List<Player> get(String key) {
        List<Player> result = new ArrayList<>();

        int index = hash(key);
        if (players[index] == null) return result;

        if((players[index].getFirstName() + players[index].getLastName()).equals(key)) {
            result.add(players[index]);
        }
        int index2 = hash2(key);
        int i = 1;
        int newIndex;
        do {
            newIndex = (index + i * index2) % arraySize;
            if (newIndex >= arraySize) { break; }
            if (players[newIndex] != null && (players[newIndex].getFirstName() + players[newIndex].getLastName()).equals(key)) {
                result.add(players[newIndex]);
            }
            i++;
        } while (players[newIndex] != null && newIndex != index);
        return result;
    }

    public int hash(String firstAndLastName){
        int result = firstAndLastName.hashCode() % arraySize;

        //for some reason the hashCode method sometimes creates negative hashes, just make it absolute to get a positive int
        if(result < 0) result = Math.abs(result);

        return result;
    }

    public int hash2(String firstAndLastName){
        int prime = 6;
        int result = (prime - (firstAndLastName.hashCode() % prime));

        if(result < 0) result = Math.abs(result);

        return result;
    }
}
