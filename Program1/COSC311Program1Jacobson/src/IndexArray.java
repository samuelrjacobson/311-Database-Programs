/* This file creates an array of IndexRecords by implementing an OrderedArray.
 * Three Index Arrays will be created, one for each: first name, last name, and ID. */

public class IndexArray {
	
	private IndexRecord[] iArray;
    private int size;
    private int iter;  //used to traverse the array
    
    //constructors
	public IndexArray() {
	     iArray = new IndexRecord[100]; //default size 100
	     size = 0;
	}
	public IndexArray(int sz) {
	     iArray = new IndexRecord[sz];
	     size = 0;
	}

	//insertion loop - insert IndexRecord into IndexArray 
	public void insert(IndexRecord key) {
		int j;
	    for(j = size - 1; j >= 0 ; j--){
	        if ((iArray[j].compareTo(key)) < 0) break;
	        iArray[j + 1] = iArray[j];
	    }
	    iArray[j + 1] = key;
	    size++;
	}

	//binary search - returns index of key in the IndexArray
	//returns -1 if key is not found
	public int find(String key) {
		int hi, lo, mid = 0;
	    lo = 0;
	    hi = size - 1;
        while(lo <= hi){
        	mid=(lo + hi)/2;
	        if (iArray[mid].getKey().compareTo(key) == 0) break;
	        if(iArray[mid].getKey().compareTo(key) < 0)
	        	lo = mid + 1;
	        else
	            hi = mid - 1;
	    }
	    return((iArray[mid].getKey().compareTo(key) == 0) ? mid : -1 );
	}
	
	//returns where of the DataBaseRecord in the DataBaseArray
	//'where' is the index in the DataBaseArray and one data field of the corresponding IndexRecords.
	public int find(int index) {
		int where = -1;
        if(index != - 1) {
        	where = iArray[index].getWhere();
        }
        return where;
	}

	//removes IndexRecord from IndexArray
	public boolean delete(String key) {
	    boolean retVal = true;
	    int where;
	    where = find(key);
	    if(where != -1){
	      for(int j= where + 1; j < size; j++) {
                iArray[j - 1] = iArray[j];
	      }
          size--;
        }
        else retVal = false;
        return(retVal);
    }
	
	//The following methods are used when displaying records in order.
	
	//setting iterator in preparation for listing
	public void iteratorInitFront() {
		iter = 0;
	}
	public void iteratorInitBack() {
		iter = size - 1;
	}
	
	//check whether there is another IndexRecord in IndexArray
	public boolean hasNext() {
		return (iter < size); // size = last index + 1
		// returns true when iter <= highest index being used
	}
	
	//check whether IndexArray has more values of lesser index than current
	public boolean hasPrevious() {
		return (iter > 0);
	}
	
	//return where of current record, increment/decrement through array
	public int getNext() {
		return iArray[iter++].getWhere();
	}
	public int getPrevious() {
		return iArray[iter--].getWhere();
	}
	
	//getters
	public int getIter() {
		return iter;
	}
	public int getSize() {
		return size;
	}
}