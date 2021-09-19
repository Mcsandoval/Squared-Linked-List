/** 
    File Name: InnerList.java
    @author Mario Sandoval
    Section: 700
    @version 07/29/21
    UIN: 629001077
    Purpose: Implementing Singly Linked list for SquaredList data structure
*/

public class InnerList {
    // implement a sinlgy linked list with dummy header and tail 
    private Node header, tail, beforeLast; // the tail points to the last node
    private int size; // size of the array 

    static class Node{
        private Integer data;
        Node next; 
        

        // Node constructor 
        Node(Integer d){
            data = d;
            next = null;
        }

        Node(Integer d, Node n){
            data = d;
            next = n;
        }

        // setters and getters 
        public Integer getData(){
            return data;
        }

        public void setData(Integer d){
            data = d;
        }
    }
    
    // Inner list constructor 
    public InnerList(){
       // set the header and tail to new nodes with no data 
       header = new Node(null);
       tail = new Node(null);
       beforeLast = new Node(null); // this doesnt get assigned to nothing until an item is added 

       // link the nodes together 
       header.next = tail;
       size = 0;
    }

    public boolean isEmpty(){ // flag for being empty 
        return header.next == tail;
    }

    // getters ------------------------

    public int size(){ // method for size
        return size;
    }

    public Node getHeader(){
        return header;
    }

    public Node getTail(){
        return tail;
    }

    public Node getBeforeLast(){
        return beforeLast;
    }

    // advanced object methods 

    public String toString(){
        // this will be set up as the dump from the instructions

        Node curNode = header.next;
        String data = new String();
        while(curNode != tail){
            data += "[" + curNode.data.toString() + "] ";
            curNode = curNode.next;
        }
        return data;
    }

    public boolean equals(InnerList other){
        if(other.size == size){
            Node curNode = header.next;
            Node otherNode = other.header.next;
            while(curNode != tail){
                if(!curNode.data.equals(otherNode.data)){
                    return false; // if the data in the nodes do not match 
                }
            }
            return true; // if data is same and size is the same
        }
        return false;
    }

    // add methods 

    public void append(Integer data){ // add at the end 
        // create a new node with the data 
        Node newNode = new Node(data);
        if(isEmpty()){
            header.next = newNode;
            newNode.next = tail;
            beforeLast = newNode;
            ++size;
            return;
        }
        
        newNode.next = tail;
        beforeLast.next = newNode;
        beforeLast = newNode;
        ++size;
    }

    public void inFront(Integer data){// add at front
        // create the new node 
        Node newNode = new Node(data);

        // check if it is empty 
        if(isEmpty()){
            header.next = newNode;
            newNode.next = tail;
            beforeLast = newNode;
            ++size;
            return;
        }

        // otherwise add it to the front
        newNode.next = header.next; // set to the current front
        header.next = newNode;

        ++size;
    }

    public void insert(int pos, Integer data){
        if (isEmpty()){
            throw new IndexOutOfBoundsException("List is empty");
        }

        if (pos >= size){
            throw new IndexOutOfBoundsException("Invalid Index: " + pos);
        }

        Node newNode = new Node(data); // create a new node of data 


        // check if the list is empty and pos == 0
        if (isEmpty() && pos == 0){
            header.next = newNode;
            newNode.next = tail;
            beforeLast = newNode;
            ++size;
            return;
        }

        if(pos == 0){ // this takes care of the addition in constant time 
            inFront(data);
            return;
        }

        if(pos == size){ // this makes it easier to manage the beforeLast Node
            append(data);
            return;
        }

        // create a new index and node to reach the position
        int index = 0; 
        Node curNode = header.next; // header.next == 0 index 
        while(index != (pos-1)){ // pos - 1 to reach the previous index to add it in front 
            curNode = curNode.next;
            ++index;
        }

        newNode.next = curNode.next;
        curNode.next = newNode;
        ++size;
    }


    // method for merge
    public void merge(InnerList other){
       // this has to be done in constant time 
       beforeLast.next = other.header.next;

       // unlink the tail
       other.beforeLast.next = tail; // this links it to the tail of this LList
       beforeLast = other.beforeLast; // this sets the right beforeLast
       
       // empty out the list and reset 
       other.header.next = null;
       other.tail = other.header.next;

       size += other.size;

    }
    // method for split 
    public InnerList split(){
        Node curNode = at((size/2)-1); // go half of list

        InnerList newList = new InnerList();
        newList.header.next = curNode.next; // get the node after and add it to the list 
        
        // set the right tails 
        curNode.next = tail;
        beforeLast.next = newList.tail;

        // set the right beforeLast 
        newList.beforeLast = beforeLast;
        beforeLast = curNode;

        // set the sizes    NOTE: redoing this function for the right input
        newList.size = size - (size/2);
        size /= 2; // this gets the right calculation needs to be ceil(size/2)
        
        return newList;
    }

    private Node at(int pos){
        // get the current node at position 
        if(pos >= size){
            throw new IndexOutOfBoundsException("Invalid index: " + pos);
        }

        if(isEmpty()){
            throw new IndexOutOfBoundsException("List is Empty");
        }

        // make an index and a node to that position 
        int index = 0; 
        Node curNode = header.next;
        while(index != pos){
            curNode = curNode.next;
            ++index;
        }

        return curNode;
    }

    public Integer get(int pos){
        // get the current node at position 
        if(pos >= size){
            return null;
        }

        if(isEmpty()){
            return null;
        }

        // make an index and a node to that position 
        int index = 0; 
        Node curNode = header.next;
        while(index != pos){
            curNode = curNode.next;
            ++index;
        }

        return curNode.data;
    }

    // removing methods 
    public Integer removeFirst(){
        if(isEmpty()){
            return null; // as instructed
        }
        // get the node with the data 
        Node curNode = header.next;

        header.next = curNode.next;
        curNode.next = null;
        --size;
        return curNode.data;
    }

    public Integer removeLast(){
        if(isEmpty()){
            return null;
        }


        Node curNode = header.next;

        // check if the next one is the tail 
        if(curNode.next == tail){
            header.next = tail;
            curNode.next = null;
            --size;
            return curNode.data;
        }
        
        // get the node before the last node 
        while(curNode.next != beforeLast){
            curNode = curNode.next;
        }

        Integer data = beforeLast.data; // get the data from the last
        beforeLast.next = null; // set it to null
        curNode.next = tail;
        beforeLast = curNode; 
        --size; 
        return data;
    }

    public Integer remove(int pos){ // return the integer from that postion 
        if(isEmpty()){
            return null;
        }
        if(pos >= size){
            return null;
        }
        if(pos == 0){
            return removeFirst();
        }
        if(pos == size-1){
            return removeLast();
        }

        int index = 0; 
        Node curNode = header.next;
        while(index != (pos -1)){ // -1 to get the node before
            curNode = curNode.next;
            ++index;
        }

        Node toDelete = curNode.next;

        curNode.next = toDelete.next;
        toDelete.next = null;
        --size;
        return toDelete.data;
    }

    public void set(int pos, Integer data){
        // this is the helper function to set the data
        Node cuNode = at(pos);

        cuNode.data = data;
    }

    public boolean contains(Integer data){ // make sure the list contains the item
        Node curNode = header.next;
        while(curNode != null){
            if(curNode.data.equals(data)){
                return true;
            }
            curNode = curNode.next;
        }
        return false;
    }

    public int searchIndex(Integer data){
        Node curNode = header.next;
        int index = 0;
        while(curNode.data != data){
            if (index > size-1){
                System.out.println("Error: Invalid Index");
                System.out.println(index);
                return -1;
            }
            curNode = curNode.next;
            ++index;
        }

        return index;
    }


    public void dump(){
        // this is the dump for the inner list 
        System.out.println("InnerList dump:");
        System.out.println("size = " + size);
        System.out.println(this);
        System.out.println();
        System.out.println("tail.data = " + beforeLast.data.toString());
    }
}
