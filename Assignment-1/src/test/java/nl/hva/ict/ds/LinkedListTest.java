package nl.hva.ict.ds;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListTest {
    protected LinkedList<String> testList;

    @Before
    public void setup() {
        testList = new LinkedList<>();
    }

    @Test
    public void describeSettingJVMArguments() {
        //fail("Please describe in you report how you set the JVM arguments for your favorite IDE.");
    }

    @Test
    public void addToEmptyList() {
        testList.add("one");

        assertTrue(testList.size() != 0);
        assertEquals("one", testList.get(0));
    }

    @Test
    public void addToListWithOneElement() {
        testList.add("one");
        testList.add("two");

        assertTrue(testList.size() != 0);
        assertEquals("two", testList.get(1));
    }

    @Test
    public void getFirstElement() {
        testList.add("one");

        assertEquals("one", testList.get(0));
    }

    @Test
    public void getLastElement() {
        testList.add("one");
        testList.add("two");
        testList.add("three");

        assertEquals("three", testList.get(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeIndexIsNotAllowed() {
        testList.get(-1);
    }

    @Test
    public void deleteFromListWithSingleElement() {
        testList.add("one");
        testList.delete("one");

        assertEquals(0, testList.size());
    }

    @Test
    public void deleteFromListWithMultipleOccurences() {
        testList.add("one");
        testList.add("one");
        testList.delete("one");

        assertEquals(0, testList.size());
    }

    @Test
    public void deleteFromListWithMultipleOccurrencesAndMoreElements() {
        testList.add("one");
        testList.add("two");
        testList.add("one");
        testList.delete("one");

        assertEquals(1, testList.size());
    }

    @Test
    public void sizeOfEmptyList() {
        assertEquals(0, testList.size());
    }

    @Test
    public void sizeOfListWithOneElement() {
        testList.add("one");
        assertEquals(1, testList.size());
    }





    // Extra unit tests go here
    @Test
    public void sizeAfterAddAndDelete(){

    }
    @Test(expected = IllegalArgumentException.class)
    public void getIndexNegative(){
        testList.get(-1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void getIndexToBig(){
        testList.get(testList.size()+1);
    }

    @Test
    public void deleteTestBig() {
        testList.add("one");
        testList.add("two");
        testList.add("three");
        testList.add("one");
        testList.add("one");
        testList.add("two");
        testList.add("three");
        testList.add("one");
        testList.delete("one");

        assertEquals(4, testList.size());
    }



}
