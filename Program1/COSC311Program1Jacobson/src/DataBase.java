// Sam Jacobson, E02377764, Program #1, COSC 311

/* The DataBase stores student records using a DataBaseArray and 3 IndexArrays.
 * This file creates DataBaseArray, IndexArray, DeleteStack objects defined in other files.
 * Reads in data from file, defines methods for adding, finding, and deleting records
 * and displaying them in ascending or descending order by last name, first name, and ID# */

import java.io.*;
import java.util.*;

public class DataBase {
   
	//create objects to use
    private DataBaseArray myDB;
    private IndexArray Last, First, ID;
    private DeleteStack delStack;
    
	Scanner keyboard = new Scanner(System.in);
   
    public DataBase() {
    	
    	//create DataBaseArray, IndexArrays, DeleteStack defined in other files
        myDB = new DataBaseArray(100);
        
        Last = new IndexArray(100);
        First = new IndexArray(100);
        ID = new IndexArray(100);
        
        delStack = new DeleteStack(100);
                
        //read student data from file to add to database
        try {
        	FileInputStream fiStream = new FileInputStream("data.txt");
        	Scanner fileReader = new Scanner(fiStream);
		
        	//fetch last name, first name, id # from each line
        	while (fileReader.hasNext()) {
        		String lastName = fileReader.next();
        		String firstName = fileReader.next();
        		String id = fileReader.next();
          
        		//call add method to create DataBaseRecord and IndexRecords with student info, add to database
        		addIt(lastName, firstName, id);
        	}
        	fileReader.close();
    	}
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    
    /* This method is called by the constructor (reading data from file)
     * and by the other addIt method (receiving input from user). */
    
    //if ID is not in use, add inputs to DataBaseArray and IndexArray 
	public void addIt(String ln, String fn, String id) {
		boolean idIsAvailable = true;
		if(ID.getSize() > 0) {
			idIsAvailable = (ID.find(id) == -1); //should be false when id is in use
		}
		if(idIsAvailable) {
			int indexToPlaceAt;
			boolean newIndex = false;
			if (delStack.isEmpty()) { //assign a new index to the DataBaseRecord
				indexToPlaceAt = myDB.getSize();
				newIndex = true; 
			}
			else { //reassign an old index to the new DataBaseRecord
				indexToPlaceAt = delStack.pop();
			}
			//create DataBaseRecord and IndexRecords using given parameters
			DataBaseRecord dbr = new DataBaseRecord(ln, fn, id);
			IndexRecord lnIr = new IndexRecord(ln, indexToPlaceAt);
			IndexRecord fnIr = new IndexRecord(fn, indexToPlaceAt);
			IndexRecord idIr = new IndexRecord(id, indexToPlaceAt);
			
			//add DataBaseRecord to DataBaseArray
			//assign record to new index if stack was empty, popped index otherwise
			if(newIndex) myDB.addIt(dbr);
			else myDB.addIt(dbr, indexToPlaceAt);
			
			//add IndexRecords to IndexArray
			Last.insert(lnIr);
			First.insert(fnIr);
			ID.insert(idIr);
			
			//confirm addition of records
			System.out.println("Added!");
		}
		//if ID is in use, don't add record, and return to menu
		else System.out.println("ID is already in use!");
	}
	
	/* This method is for when the when user is adding more students.
	 * Get info for record from user, call addIt with arguments. */
	public void addIt() {
		String ln = ""; String fn = ""; String id = "";
		System.out.print("Enter a last name, first name, and ID on one line, separated by whitespace: ");
		if(keyboard.hasNext()) {
			ln = keyboard.next();
			fn = keyboard.next();
			id = keyboard.next();
		}
		addIt(ln, fn, id);
	}
	
	//prints DataBaseRecord with ID requested by user
	public void findIt() {
		System.out.print("Enter the ID of the person you would like to find: ");
		String id = "";
		id = keyboard.next();
		
		int indexOfKey = ID.find(id); //returns index of key in the IndexArray
		
		if(indexOfKey == -1) System.out.println("ID not found");
		else {
			int where = ID.find(indexOfKey); //returns where of the DataBaseRecord in the DataBaseArray
			String dbRecordString = myDB.getDataBaseRecordToString(where); //returns toString for DataBaseRecord
			System.out.println(dbRecordString);
		}
	}
	
	//removes records for student of specified ID, pushes former index of DataBaseRecord onto DeleteStack
	public void deleteIt() {
		System.out.print("Enter the ID of the person to delete: ");
		String id = "";
		id = keyboard.next();
		
		int indexOfKey = ID.find(id); //returns index of key in the IndexArray
		
		if(indexOfKey == -1) System.out.println("ID not found");
		else {
			int where = ID.find(indexOfKey); //returns where of the DataBaseRecord in the DataBaseArray
			String ln = myDB.getLastName(where);
			String fn = myDB.getFirstName(where);
			
			//delete from IndexArrays
			Last.delete(ln);
			First.delete(fn);
			ID.delete(id);
			
			/* "delete" from DataBaseArray
			 * If DeleteStack isn't full, push former index of DataBaseRecord onto it,
			 * marking the index as available to be overwritten with a new record. */
			if(!delStack.isFull()) delStack.push(where);
			System.out.println("Deleted");
		}
	}
	
	//print DataBaseRecords by attribute, ascending
	public void ListAscending(IndexArray ia) {
		ia.iteratorInitFront();
		while(ia.hasNext()) {
			System.out.println(myDB.getDataBaseRecordToString(ia.getNext()));
		}
	}
	public void ListByIDAscending() {
		ListAscending(ID);
	}
	public void ListByLastAscending() {
		ListAscending(Last);
	}
	public void ListByFirstAscending() {
		ListAscending(First);
	}
	
	//print DataBaseRecords by attribute, descending
	public void ListDescending(IndexArray ia) {
		ia.iteratorInitBack();
		while(ia.getIter() > -1) {
			System.out.println(myDB.getDataBaseRecordToString(ia.getPrevious()));
		}
	}
	public void ListByIDDescending() {
		ListDescending(ID);
	}
	public void ListByLastDescending() {
		ListDescending(Last);
	}
	public void ListByFirstDescending() {
		ListDescending(First);
	}
}