/* DataBaseRecord is an element of the DataBaseArray.
 * It contains a last name, a first name, and an ID# of a student. */

public class DataBaseRecord {
	 private String lastName;
	 private String firstName;
	 private String id;
	 
	 //constructors
	 DataBaseRecord(String a, String b, String c){
		 lastName = new String(a);
	     firstName = new String(b);
	     id = new String(c);
	 }  
	 public String toString(){
		 return lastName + " " + firstName + " " + id;
	 }
	 
	 //getters
	 public String getLastName() {
		return lastName;
	 }
	 public String getFirstName() {
		return firstName;
	 }
	 /* ID# is the one guaranteed unique attribute, so its constructor is not needed.
	  * ID# will be used to locate the desired last name and first name. */
}
