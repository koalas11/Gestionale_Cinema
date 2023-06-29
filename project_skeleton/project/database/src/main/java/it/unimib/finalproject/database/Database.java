package it.unimib.finalproject.database;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Database {
	private static List<ConcurrentHashMap<String, String>> stringDB;
	private static List<ConcurrentHashMap<String, ArrayList<AtomicReference<String>>>> listDB;
	private static final int MaxStringDB = 5;
	private static final int MaxListDB = 5;
	
	public Database() {
		stringDB = new ArrayList<ConcurrentHashMap<String, String>>(MaxStringDB);
		
		for (int i = 0; i < MaxStringDB; i++) {
			stringDB.add(new ConcurrentHashMap<String, String>());
		}
		
		listDB = new ArrayList<ConcurrentHashMap<String, ArrayList<AtomicReference<String>>>>(MaxListDB);
		
		for (int i = 0; i < MaxListDB; i++) {
			listDB.add(new ConcurrentHashMap<String, ArrayList<AtomicReference<String>>>());
		}
	}
	
    /**
     * Method to get the HashMap of Strings.
     *
     * @param key key for finding the HashMap associated.
     *
     * @return HashMap where key is stored.
     */
	private ConcurrentHashMap<String, String> getSDB(String key) {
		int hashCode = key.hashCode();
		if (hashCode < 0)
			hashCode *= -1;
		return stringDB.get(hashCode % MaxStringDB);
	}
	
    /**
     * Method to get the HashMap of Lists.
     *
     * @param key key for finding the HashMap associated.
     *
     * @return HashMap where key is stored.
     */
	private ConcurrentHashMap<String, ArrayList<AtomicReference<String>>> getLDB(String key) {
		int hashCode = key.hashCode();
		if (hashCode < 0)
			hashCode *= -1;
		return listDB.get(hashCode % MaxListDB);
	}
	
	
	/**
	 * Methods for Strings
	 */
	
    /**
     * Method to set (or replace) a value associated with key.
     *
     * @param key key in the HashMap.
     * @param value value to assoicate with key.
     *
     */
	public void set(String key, String value) {
		var db = getSDB(key);
		
		db.put(key, value);
	}
	
    /**
     * Method to set (or replace) a value associated with key for multiple keys.
     *
     * @param list list made of multiple key, value.
     *
     */
	public void mSet(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			set(list.get(i), list.get(++i));
		}
	}
	
    /**
     * Method to set (or replace) a newValue associated with key only if the value at key is oldValue.
     *
     * @param key key in the HashMap. 
     * @param newValue newValue for the key.
     * @param oldValue oldValue associated with key.
     *
     * @return true if it was successful else false.
     */
	public boolean setIf(String key, String newValue, String oldValue) {
		var db = getSDB(key);
		
		return db.replace(key, oldValue, newValue);
	}
	
    /**
     * Method to set (or replace) a newValue associated with key only if the value at key is oldValue for multiple keys.
     *
     * @param list list made of multiple key, newValue, oldValue.
     *
     * @return true if it was successful else false.
     */
	public boolean mSetIf(ArrayList<String> list) {
		String key, value, oldvalue;
		var listR = new ArrayList<String>();
		boolean check = true;
		
		for (int i = 0; i < list.size(); i++) {
			key = list.get(i);
			value = list.get(++i);
			oldvalue = list.get(++i);
			check = setIf(key, value, oldvalue);
			
			if (!check)
				break;
			listR.add(key);
			listR.add(oldvalue);
		}
		
		if (!check)
			mSet(listR);
		
		return check;
	}
	
    /**
     * Method to get the value associated to a key.
     *
     * @param key key in the HashMap.
     *
     * @return String of the result.
     */
	public String get(String key) {
		var db = getSDB(key);
		var value = db.get(key);
		
		String result = "\"" + key + "\":";
		if (value == null)
			result += null;
		else
			result += "\"" + value + "\"";
		
		return result;
	}
	
    /**
     * Method to get the value associated to multiple keys.
     *
     * @param keys List of multiple keys.
     *
     * @return The values associated with each key and null if there was none for that key.
     */
	public String mGet(ArrayList<String> keys) {
		String result = "{";
		
		for (int i = 0; i < keys.size(); i++) {
			result += get(keys.get(i));
			
			if (i != keys.size() - 1)
				result += ",";
		}
		
		return result + "}";
	}
	
    /**
     * Method to delete the key in the HashMap.
     *
     * @param key key to remove from the HashMap.
     *
     * @return true if it was successful or false if there wasn't a key to remove.
     */
	public boolean delete(String key) {
		var db = getSDB(key);
		
		return db.remove(key) != null;
	}
	
	/**
	 * Methods for Lists
	 */
	
    /**
     * Method to add into the list associated with key, a value as the last element.
     *
     * @param key key to retrive the list from the HashMap.
     * @param value value to add to the list.
     *
     */
	public void add(String key, String value) {
		var db = getLDB(key);
		
		var list = db.get(key);
		
		if (list == null) {
			list = new ArrayList<AtomicReference<String>>();
			db.put(key, list);
		}
			
		list.add(new AtomicReference<String>(value));

	}
	
    /**
     * Method to remove the last value from the list associated with key.
     *
     * @param key key to find the list.
     *
     * @return true if it was successful or false if there wasn't any list associated with key.
     */
	public boolean remove(String key) {
		var db = getLDB(key);
		
		var list = db.get(key);
		
		if (list == null)
			return false;
		
		list.remove(list.size() - 1);
		
		if (list.size() == 0)
			db.remove(key);
		
		return true;
	}
	
    /**
     * Method to set into the list associated with key, a value in the position index.
     *
     * @param key key to retrive the list from the HashMap.
     * @param index index of the list.
     * @param value value to add to the list at position index.
     *
     */
	public void setL(String key, String index, String value) {
		var db = getLDB(key);
		
		var list = db.get(key);
		
		list.set(Integer.parseInt(index), new AtomicReference<String>(value));
	}
	
    /**
     * Method to set (or replace if it was already associated a value at the key) a new value at index for multiple keys.
     *
     * @param list list made of multiple key, index, value.
     *
     */
	public void mSetL(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			setL(list.get(i), list.get(++i), list.get(++i));
		}
	}
	
    /**
     * Method to set into the list associated with key, a value in the position index
     * only if at that position there was the value oldValue.
     *
     * @param key key to retrive the list from the HashMap.
     * @param index index of the list.
     * @param newValue new value to add to the list at position index.
     * @param oldValue old value at position index.
     *
     * @return true if it was successful or false if not.
     */
	public boolean setIfL(String key, String index, String newValue, String oldValue) {
		var db = getLDB(key);
		
		var list = db.get(key);
		
		return list.get(Integer.parseInt(index)).compareAndSet(oldValue, newValue);
	}
	
    /**
     * Method to set into the list associated with key, a value in the position index
     * only if at that position there was the value oldValue for multiple keys.
     *
     * @param list list made of multiple key, index, value, oldvalue.
     *
     * @return true if it was successful or false if not.
     */
	public boolean mSetIfL(ArrayList<String> list) {
		String key, index, value, oldvalue;
		var listR = new ArrayList<String>();
		boolean check = true;
		
		for (int i = 0; i < list.size(); i++) {
			key = list.get(i);
			index = list.get(++i);
			value = list.get(++i);
			oldvalue = list.get(++i);
			check = setIfL(key, index, value, oldvalue);
			
			if (!check)
				break;
			listR.add(key);
			listR.add(index);
			listR.add(oldvalue);
		}
		
		if (!check)
			mSetL(listR);
		
		return check;
	}
	
    /**
     * Method to get the value at position index in the list associated with key.
     *
     * @param key key to retrive the list from the HashMap.
     * @param index index of the list.
     *
     * @return String of the result.
     */
	public String getL(String key, int index) {
		var db = getLDB(key);
		var list = db.get(key);
		
		String result = "{" + key + ":";
		
		String value = list.get(index).get();
		
		if (value == null)
			result += null + "}";
		else
			result += "\"" + value + "\"}";
		
		return result;
	}
	
	/**
	 * Method to get the values in the list associated with key.
	 *
	 * @param key key to retrive the list from the HashMap.
	 *
	 * @return String of the result.
	 */
	public String getAll(String key) {
		var db = getLDB(key);
		var list = db.get(key);
		
		String result = "\"" + key + "\":";
		
		if (list == null) {
			result += null;
		} else {
			result += "[";
			String value;
			
			for (int i = 0; i < list.size(); i++) {			
				value = list.get(i).get();
				
				if (value == null)
					result += null;
				else
					result += "\"" + value + "\"";
				
				if (i != list.size() - 1)
					result += ",";
			}
			result += "]";
		}
		
		return  result;
	}
	
    /**
     * Method to get the values in the list associated with keys.
     *
     * @param keys keys to retrive each list from the HashMap.
     *
     * @return String of the result.
     */
	public String mGetAll(ArrayList<String> keys) {
		String result = "{";
		
		for (int k = 0; k < keys.size(); k++) {
			result += getAll(keys.get(k));
			
			if (k != keys.size() - 1)
				result += ",";
			else
				result += "";
		}
		
		return result + "}";
	}
	
    /**
     * Method to delete the list associated with key.
     *
     * @param key key to retrive the list from the HashMap.
     *
     * @return true if successful or false if there was no list associated with key.
     */
	public boolean deleteL(String key) {
		var db = getLDB(key);
		
		return db.remove(key) != null;
	}
}
