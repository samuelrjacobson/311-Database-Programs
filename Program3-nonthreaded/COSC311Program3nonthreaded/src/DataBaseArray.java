/* DataBaseArray is an array of DataBaseRecords, ordered by when they were placed into it.
 * New records may replace previously deleted records' indices due to DeleteStack. */

public class DataBaseArray {
	
	private DataBaseRecord[] dbArray;
	private int size = 0; //next unused index in DataBaseArray, not necessarily number of records
	
	//constructors
	public DataBaseArray(){
		dbArray = new DataBaseRecord[100];
	}
	public DataBaseArray(int sz) {
		dbArray = new DataBaseRecord[sz];
	}
	
	//add DataBaseRecord to DataBaseArray
	//using new index
	public void addIt(DataBaseRecord dbr) {
		dbArray[size] = dbr;
		size++;
	}
	//add DataBaseRecord to DataBaseArray
	//re-using old index in DataBaseArray (DeleteStack was popped)
	public void addIt(DataBaseRecord dbr, int where) {
		dbArray[where] = dbr;
		//don't increment size--it points to the next unused index in DataBaseArray
	}
	
	//getters
	public int getSize() {
		return size;
	}
	//get attributes from specific DataBaseRecord
	public String getLastName(int where) {
		return dbArray[where].getLastName();
	}
	public String getFirstName(int where) {
		return dbArray[where].getFirstName();
	}
	//gets toString for specified DataBaseRecord
	public String getDataBaseRecordToString(int where) {
		return dbArray[where].toString();
	}
}