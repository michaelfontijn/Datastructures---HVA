package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class QuadraticProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    Player[] players;
    private int arraySize;
    private int curSize = 0;
    int collisions = 0;

    public QuadraticProbingMultiValueSymbolTable(int arraySize) {
        players = new Player[arraySize];
        this.arraySize = arraySize;
    }

    @Override
    public void put(String key, Player value) {
        if (curSize == arraySize) return;

        int index = hash(key);
        if(players[index] == null) {
            players[index] = value;
        }
        else {
            int i = 1;
            while(true){
                collisions++;

                index = (index + (int) Math.pow(i, 2)) % arraySize;
                if (players[index] == null) {
                    players[index] = value;
                    break;
                }
                i++;
            }
        }
        curSize++;
    }

    @Override
    public List<Player> get(String key) {
        List<Player> result = new ArrayList<>();

        int index = hash(key);
        int start = index;
        int i = 1;
        while(players[index] != null) {
            if (players[index].getLastName().equals(key)) {
                result.add(players[index]);
            }
            index = (index + (int) Math.pow(i, 2)) % arraySize;
            if (index == start) break;
            i++;
        }
        return result;
    }

    public int hash(String lastName){
        int result = lastName.hashCode() % arraySize;

        //for some reason the hashCode method sometimes creates negative hashes, just make it absolute to get a positive int
        if(result < 0) result = Math.abs(result);

        return result;
    }

    public static void main(String [] args)
    {
        QuadraticProbingMultiValueSymbolTable q = new QuadraticProbingMultiValueSymbolTable(100);
        Player p1 = new Player("Nicholas", "de Mimsy-Porpington", 95);
        Player p2 = new Player("Albus", "Dumbledore", p1.getHighScore() * 1000);
        Player p3 = new Player("Harry", "Potter", p2.getHighScore() + 1000);
        Player p4 = new Player("James", "Potter", p3.getHighScore() - 4000);
        Player p5 = new Player("Lily", "Potter", p4.getHighScore() - 4000);
        Player p6 = new Player("polygene", "lubricants", p5.getHighScore() - 10);

        q.put(p1.getLastName(), p1);
        q.put(p2.getLastName(), p2);
        q.put(p3.getLastName(), p3);
        q.put(p4.getLastName(), p4);
        q.put(p5.getLastName(), p5);
        q.put(p6.getLastName(), p6);

        q.get("Potter");
    }

}
