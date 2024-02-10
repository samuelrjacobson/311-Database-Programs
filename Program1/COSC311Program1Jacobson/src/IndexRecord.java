/* IndexRecord is the key-where pair, an element of the IndexArrays for each attribute. */

public class IndexRecord{
	private String key; //the last name, first name, or ID#
	private int where; //refers to the index of the corresponding DataBaseRecord in the DataBaseArray
	
	//constructor
	public IndexRecord(String a, int b) {
		key = a;
		where = b;
	}
	
	//getters
	public String getKey() {
		return key;
	}
	public int getWhere() {
		return where;
	}
	
	//compares keys alphabetically for sorting
	public int compareTo(IndexRecord otherIR) {
		return key.compareTo(otherIR.key);
	}
}
