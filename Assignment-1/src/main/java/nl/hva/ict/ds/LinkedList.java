package nl.hva.ict.ds;

/**
 * A linked list that adds elements to the end of the list and that retrieves elements from the end of the list as fast
 * as from the head of the list.
 * For example if a list contains 1000 elements the time needed to retrieve element at index 999 should be (almost) the
 * same as is needed for retrieving element at index 0. Retrieving element at index 800 should take (almost) the same
 * time as retrieving element at index 199.
 * When deleting an element all elements with the same value are deleted. So when deleting "don't" from a list that
 * contains<br/>
 * {"I", "don't", "like", "Datastructures", "as", "much", "as", "I", "don't", "like", "Sorting", "and", "Searching"}
 * this should result in a list containing:<br/>
 * {"I", "like", "Datastructures", "as", "much", "as", "I", "like", "Sorting", "and", "Searching"}
 *
 * @param <T> defines the type (class) that is stored in this list.
 */
public class LinkedList<T> {
    private class Node {
        private T value;
        private Node next;
        private Node previous;

        private Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node first;
    private Node last;
    private int itemCount;


    /**
     * Adds a new element to the end of this list. The performance of this method is guaranteed to be constant, in other
     * words, the number of elements already stored in the list should have no influence on the time needed to add a new
     * element.
     *
     * @param element the element that is added to the list.
     */
    public void add(T element) {

        //make a new node element and align the current last element to its previous property
        Node newNode = new Node(element, null);
        newNode.previous = last;

        //if the list is empty
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            //add the new node to the linkChain by putting it into the next element of the current last node
            last.next = newNode;
            //now change the last element to the newly added node
            last = newNode;
        }

        //since we added an item to the linkChain increase the itemCount by 1
        itemCount++;
    }

    /**
     * Returns an element from the list. If the index is negative or the element does not exists
     * an IllegalArgumentException is throw containing the reason in the message.
     *
     * @param index the index, counted from the first element, of the element that must be returned.
     */
    public T get(int index) {

        //check if the index is valid
        if (index < 0 || index > size()) throw new IllegalArgumentException();

        //if the index you want is past the halfway point of the collection size, start at the end and work your way down.
        if (size() / 2 > index) {
            //start at the last node and calculate a reverse index to know how many steps you'll have to take back from the end
            //instead of the beginning to get to the item of the requested index.
            Node node = last;
            int reverseIndex = Math.round(size()) - index;

            while (reverseIndex-- > 0) {
                node = node.previous;
            }
            return node.value;
        } else {
            //get the first node
            Node node = first;
            while (index-- > 0) {
                node = node.next;
            }
            return node.value;
        }
    }

    /**
     * Deletes the element (if it exists) from the list. In case of multiple occurrences all the occurrences are
     * deleted.
     *
     * @param element the element to delete.
     */
    public void deleteWithLoop(T element) {

        //if there are no items in the list there is nothing to be deleted
        if (first == null) return;

        //start at the first node
        Node node = first;

        int startSize = size();
        //go trough all nodes in the list
        for (int i = 0; i < startSize; i++) {

            //if there is a match with the delete criteria
            if (node.value == element) {

                //if it is the first node
                if (node.previous == null) {

                    //if this is the first and only item in the list, completely clear the list
                    if (node.next == null) {
                        first = null;
                        last = null;
                    } else {
                        //Assign the next node opposed to the current first node to be the new first
                        first = node.next;

                        //remove the reference to the previous first node to remove it from the list
                        first.previous = null;
                    }

                } else {
                    //Remove the node from the chain to delete it from the list
                    //set the previous nodes next property to be the node after the current node
                    node.previous.next = node.next;

                    //Do this only if this is not the last node
                    if (node.next != null) {
                        //set the next nodes previous property to be the previous node of this node
                        node.next.previous = node.previous;
                    }

                }
                //subtract one from the item count
                itemCount--;
            }
            //go to the next node
            node = node.next;
        }
    }


    Node currentNode;

    /**
     * Deletes the element (if it exists) from the list. In case of multiple occurrences all the occurrences are
     * deleted.
     *
     * @param element the element to delete.
     */
    public void delete(T element) {
        //if there are no items in the list there is nothing to be deleted
        if (first == null) return;

        //if the current node is not initialized, start its value with the first node in the list
        if(currentNode == null) currentNode = first;

        //if there is a match with the delete criteria
        if (currentNode.value == element) {
            //if it is the first node
            if (currentNode.previous == null) {

                //if this is the first and only item in the list, completely clear the list
                if (currentNode.next == null) {
                    first = null;
                    last = null;
                } else {
                    //Assign the next node opposed to the current first node to be the new first
                    first = currentNode.next;

                    //remove the reference to the previous first node to remove it from the list
                    first.previous = null;
                }
            } else {
                //Remove the node from the chain to delete it from the list
                //set the previous nodes next property to be the node after the current node
                currentNode.previous.next = currentNode.next;

                //Do this only if this is not the last node
                if (currentNode.next != null) {
                    //set the next nodes previous property to be the previous node of this node
                    currentNode.next.previous = currentNode.previous;
                }

            }
            //subtract one from the item count
            itemCount--;
        }

        //if there is a next node, go to the next node and run the method algorithm again
        if(currentNode.next != null){
            currentNode = currentNode.next;
            delete(element);
        }else{
            //if the current node does not have a next node's value and let the stack finish
            currentNode = null;
        }
    }


    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in the list.
     */
    public int size() {
        return itemCount;
    }

}
