import java.io.*;
import java.util.*;

/** 
    File Name: SquareList.java
    @author Mario Sandoval
    Section: 700
    @version 07/29/21
    UIN: 629001077
    Purpose: Implementation of the squarelist class
*/

public class SquareList {
    // the squareList contains a top-level list 
    private LinkedList<InnerList> topLevelList;
    // the linked list comes with a size 

    // methods for the squared list class
    //1.- default constuctor O(1)
    public SquareList(){
        topLevelList = new LinkedList<InnerList>();
    }

    // advance object methods 
    public String toString(){
        String data = new String();

        for(int i = 0; i < topLevelList.size();++i){
            data += topLevelList.get(i).toString();
        }

        return data;
    }

    public boolean equals(SquareList other){
        if(other.size() == size()){
            for(int i = 0; i < topLevelList.size();++i){
                if(!topLevelList.get(i).equals(other.topLevelList.get(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    //2.- Consolidate() method merges or splits
    public void Consolidate(){
        // this is where the math for the split goes
        // every innerlist has <= 2sqrt(n)
        // short is defined as <= sqrt(n)/2

        // traverse through the list until i find a list greater than desired, or less 

        for(int i = 0; i < topLevelList.size();++i){
            // if an empty list is encountered remove it
            if(topLevelList.get(i).isEmpty()){
                topLevelList.remove(i); 
            }

            if((topLevelList.get(i).size() <= (Math.sqrt(size())/2)) && (topLevelList.get(i+1).size() <= (Math.sqrt(size())/2)) ){
                // merge those list together 
                topLevelList.get(i).merge(topLevelList.get(i+1));
                topLevelList.remove(i+1); // remove the empty list
            }
            //System.out.println(2*Math.sqrt(size()));
            if(topLevelList.get(i).size() > (2*Math.sqrt(size()))){
                // split them into two list of equal lenght
                //System.out.println(2*Math.sqrt(size()));
                topLevelList.add(i+1, topLevelList.get(i).split()); // add it to the next node
            }
        }
    } 

    //3.- two methods of insert
        // addFirst(Integer data)
    public void addFirst(Integer data){
        // check if the list is empty 
        if(topLevelList.isEmpty()){
            // add a new inner list with the data at the front 
            InnerList newList = new InnerList();
            newList.inFront(data);
            topLevelList.addFirst(newList); // add the list at the beggining 
            return;
        }
        try{ // try and catch the get first
            topLevelList.getFirst().inFront(data);
        } catch(Exception e){
            e.printStackTrace();
        }
        Consolidate();
    }
        // addLast(integer data)
    public void addLast(Integer data){
        // check if the list is empty 
        if(topLevelList.isEmpty()){
            InnerList newList = new InnerList();
            newList.append(data);
            topLevelList.addFirst(newList);
            return;
        }
        InnerList lastList = topLevelList.getLast();
        lastList.append(data);
        Consolidate();
    }
        // they should call consolidate after insertion
    //4.- remove first removeFirst()
    public Integer removeFirst(){
        // call the helper function of the inner list
        if(topLevelList.isEmpty()){
            return null;
        } 

        try{
            Integer curData = topLevelList.getFirst().removeFirst();
            // check if it is not null and consolidate 
            if(curData != null){
                Consolidate();
            }
            return curData;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null; // if all goes wrong return null
    }

    //5.- add(int pos, Integer data)
    public void add(int pos, Integer data){
        // check if the toplevel is empty 
        if(topLevelList.isEmpty()){
            InnerList newList = new InnerList();
            newList.inFront(data);
            topLevelList.addFirst(newList);
            return;
        }

        //error handle 
        if(pos > size()){
            System.out.println("Error: pos exceeds size");
            return;
        }

        // get an error handling option 
        try{
            // traverse through all the list until it get to one where the pos <= size-1
            int index = 0;
            while(pos > topLevelList.get(index).size()-1){ // -1 to not overflow
                if(pos == topLevelList.get(index).size()){ // in the event that the number is exact, otherwise it will go to 0 and add it infront of the next list 
                    topLevelList.get(index).append(data);
                    return;
                }
                pos = pos - topLevelList.get(index).size();
                ++index;
            }

            // now that I found the position insert it at that spot
            topLevelList.get(index).insert(pos, data);
            Consolidate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //6.- Remove item and return value remove(int pos)
    public Integer remove(int pos){
        // check if the list is empty
        if(topLevelList.isEmpty()){
            return null;
        }

        //error handle
        if(pos > size()){
            System.out.println("Error: Pos greater than size");
            return null;
        }

        try{
            // try the first and last pos
            if(pos == 0){
                Integer data = topLevelList.getFirst().removeFirst();
                Consolidate();
                return data;
            }

            if(pos == (topLevelList.size()-1)){
                Integer data = topLevelList.getLast().removeLast();
                Consolidate();
                return data;
            }
            // look for the list that contains that position 
            int index = 0; 
            while(pos > topLevelList.get(index).size()-1){
                pos = pos - topLevelList.get(index).size();
                ++index;
            }

            // return the item at the postion 
            Integer curData = topLevelList.get(index).remove(pos);
            Consolidate(); // consolidate after 
            return curData;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //7.- get value from position get(int pos)
    public Integer get(int pos){

        // get the position
        if(topLevelList.isEmpty()){
            return null;
        }

        //error handle
        if(pos > size()){
            System.out.println("Error: Pos greater than size");
            return null;
        }

        int index = 0; 
        while(pos > (topLevelList.get(index).size()-1)){
            pos = pos - topLevelList.get(index).size(); // the parenthesis matters 
            ++index;
        }

       // return the item at the postion 
       return topLevelList.get(index).get(pos);
    }
    //8.- change value set(int pos, Integer data)
    public void set(int pos, Integer data){

        //error handle
        if(pos > size()){
            System.out.println("Error: Pos greater than size");
            return;
        }
        
        int index = 0; 
        while(pos > topLevelList.get(index).size()-1){
            pos = pos - topLevelList.get(index).size();
            ++index;
        }

        topLevelList.get(index).set(pos, data);
    }
    //9.- returns number of items in square list size()
    public int size(){
        // to return the number of check all the list sizes
        int sum = 0;
        for(int i = 0; i < topLevelList.size();++i){
            sum += topLevelList.get(i).size();
        }
        return sum;
    }
    //10.- return the position of first occurence indexOf(Integer data)
    public int indexOf(Integer data){
        int index = 0; 
        int pos = 0; // this will increase to get the position
        while(!topLevelList.get(index).contains(data)){
            pos += topLevelList.get(index).size();
            ++index;
        }
        return pos + topLevelList.get(index).searchIndex(data);
    }
    //11.- debuging method dump()
    public void dump(){
        // do exactly as the testing for correction 
        String enclosers = "*********************************************************" ;
        String dividers = "=========================================================" ;

        System.out.println(enclosers);
        System.out.println("SquareList dump: ");
        System.out.println("Total size = " + size() + ", # of lists = " + topLevelList.size());
        System.out.println(dividers);
        for(int i  = 0; i < topLevelList.size();++i){
            // get the dump of the inner list 
            topLevelList.get(i).dump();
            System.out.println(dividers);
        }
    }

    // part 2 file input stream ------------------

    // read IO from a file into java 
    public void openDataFile(String filename){

        Scanner inFS = null;
        // open the file 
        try{
            inFS = new Scanner(new FileReader(filename));
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }

        Integer data; // data obtained from the file 

        while(inFS.hasNextLine()){
            String line = inFS.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line);

            try{
            data = Integer.valueOf(tokenizer.nextToken()); // get the integer

            addLast(data); // add the data at the back of the list 

            }catch (NumberFormatException e){ // catch it not an integer
                System.out.println("Data Error in File");
                System.exit(0);
            }
        }
        inFS.close(); // close the file at the end 
    }

    // custom exception
    public class IsNotAnInteger extends Exception{
        public IsNotAnInteger(String errorMsg){
            super(errorMsg);
        }
    }
}
