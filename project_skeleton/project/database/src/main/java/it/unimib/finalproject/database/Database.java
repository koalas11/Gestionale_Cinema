package it.unimib.finalproject.database;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Database {
	private List<ConcurrentHashMap<String, String>> stringDB;
	private List<ConcurrentHashMap<String, ArrayList<AtomicReference<String>>>> listDB;
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
     * Metodo per ottenere l'HashMap delle stringhe.
     *
     * @param key chiave per la ricerca dell'HashMap in cui e' salvata.
     *
     * @return L'HashMap in cui e' salvata.
     */
	private ConcurrentHashMap<String, String> getSDB(String key) {
		int hashCode = key.hashCode();
		if (hashCode < 0)
			hashCode *= -1;
		return stringDB.get(hashCode % MaxStringDB);
	}
	
    /**
     * Metodo per ottenere l'HashMap delle liste.
     *
     * @param key chiave per la ricerca dell'HashMap in cui e' salvata.
     *
     * @return L'HashMap in cui e' salvata
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
     * Metodo per impostare (o sostituire nel caso sia gia' presente la chiave) un valore.
     *
     * @param key chiave a cui associare un valore
     * @param value valore da associare alla chiave
     *
     */
	public void set(String key, String value) {
		var db = getSDB(key);
		
		db.put(key, value);
	}
	
    /**
     * Metodo per impostare (o sostituire nel caso sia gia' presente la chiave) un valore
     * solo se oldValue corrisponde al valore associato alla chiave.
     *
     * @param key chiave a cui associare un valore
     * @param newValue nuovo valore da associare alla chiave
     * @param oldValue vecchio valore associato alla chiave
     *
     * @return true se ha avuto successo
     */
	public boolean setIf(String key, String newValue, String oldValue) {
		var db = getSDB(key);
		
		return db.replace(key, oldValue, newValue);
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
     * @param keys List of keys.
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
	public void setL(String key, int index, String value) {
		var db = getLDB(key);
		
		var list = db.get(key);
		
		list.set(index, new AtomicReference<String>(value));
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
	public boolean setIfL(String key, int index, String newValue, String oldValue) {
		var db = getLDB(key);
		
		var list = db.get(key);
		
		return list.get(index).compareAndSet(oldValue, newValue);
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
		
		return result;
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
