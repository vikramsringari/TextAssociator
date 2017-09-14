import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* CSE 373 TextAssociator 
 * Vikram Sringari
 * 
 * TextAssociator represents a collection of associations between words.
 * It organizes words together with their associations. In this Class one
 * can add words to the TextAssociator Object. One can remove words from 
 * the TextAssociator. One can add new Associated words to the words that 
 * have been inserted to the TextAssociator. One can get all associated words
 * to a word in the TextAssociator. One can also print an easy to see reperesentation
 * of the TextAssociator Object.
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;
	
	/* **Private class comments only for TAs (Not for client of TextAssociator)**
	 * INNER CLASS
	 * This represents a separate chain in your implementation of the hash table.
	 * A WordInfoSeparateChain is a list of WordInfo objects that are all
	 * hashed to the same index of the hash table of the TextAssociator.
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* This creates an empty WordInfoSeparateChain Objects without 
		 * any WordInfo Objects in it.
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/* This adds a WordInfo wi (parameter) object to the SeparateChain.
		 * Returns true if the WordInfo was successfully added. 
		 * If the wordInfo object already exists in the SeparateChain it returns 
		 * false.
		 */
		public boolean add(WordInfo wi) {
			if (!chain.contains(wi)) {
				chain.add(wi);
				return true;
			}
			return false;
			// TODO: implement as explained in spec
		}
		
		/* This removes the given WordInfo wi (parameter) object from the separate chain.
		 * Returns true if the WordInfo was successfully removed meaning that WordInfo 
		 * Object existed before you removed it. It returns false otherwise.
		 */
		public boolean remove(WordInfo wi) {
			if (chain.contains(wi)) {
				chain.remove(wi);
				return true;
			}
			return false;
			// TODO: implement as explained in spec
		}
		
		// This returns the size of this WordInfoSeparateChain Object.
		public int size() {
			return chain.size();
		}
		
		// This returns a string representation of this WordInfoSeparateChain Object.
		public String toString() {
			return chain.toString();
		}
		
		// This returns the list of WordInfo objects in this WordInfoSeparateChain Object.
		public List<WordInfo> getElements() {
			return chain;
		}
	}
	
	
	/* This Constructor creates a new TextAssociator Object without any words or
	 * their associations.
	 */
	public TextAssociator() {
		table = new WordInfoSeparateChain[11];
		size = 0;
	}
	
	
	/* The method adds the String word (parameter) with no associations to the TextAssociator object.
	 * It will return false if this word is already contained in the TextAssociator
	 * It will returns true if the word is successfully added, meaning it is not 
	 * contained in the TextAssociator.
	 */
	public boolean addNewWord(String word) {
		if( (double)(size / table.length) >= 1.5) {
			resizeTable();
		}
		int index = findTableIndex(word, table.length);
		WordInfo newWord = new WordInfo(word);
		if(table[index] == null || retrieveWordInfo(index,word) == null){
			if(table[index] == null){
				table[index] = new WordInfoSeparateChain();
			}
			size++;
			return table[index].add(newWord);
		}
		return false;
	}
	
	
	/* This method adds an association (from 2nd parameter) for a specific existing word (from 1st parameter). 
	 * It returns true if association correctly added meaning the word that the association
	 * is added to exists within the TextAssociator and that association word does not exist 
	 * for that specific word (first parameter). It returns false if first parameter meaning the 
	 * word does not already exist in the TextAssociator Object or if the association for between the 
	 * two words already exists.
	 */
	public boolean addAssociation(String word, String association) {
		int index = findTableIndex(word, table.length);
		if(table[index] != null && retrieveWordInfo(index,word) != null){
			WordInfo wordForAssociation = retrieveWordInfo(index,word);
			return wordForAssociation.addAssociation(association);
			
		}
		return false;

	}
	
	
	/* This method removes the given String word (parameter) from the TextAssociator if that word exists
	 * in the TextAssociator Object and returns true. Otherwise it returns false.
	 * Only a source word can be removed by this method from the TextAssciator, not an association to 
	 * that source word .
	 */
	public boolean remove(String word) {
		int index = findTableIndex(word, table.length);
		if(table[index] != null && retrieveWordInfo(index,word) != null){
			size--;
			WordInfo wordForRemoval = retrieveWordInfo(index,word);
			return table[index].remove(wordForRemoval);
			
		}
		return false;

	}
	
	
	/* This method returns a set strings of all the words associated with the 
	 * inputed String word (parameter) from the TextAssociator Object.
	 * This returns null if the given word does not exist in the TextAssociator 
	 * as a word (not as an associate to a word).
	 */
	public Set<String> getAssociations(String word) {
		int index = findTableIndex(word, table.length);
		if(table[index] != null && retrieveWordInfo(index,word) != null){
			WordInfo wordforAssociations = retrieveWordInfo(index,word);
			return wordforAssociations.getAssociations();
		}
		return null;
	}
	
	
	/* This Prints the current associations between words being stored
	 * to System.out. It prints out in a nice format that displays important
	 * information about the TextAssociation object
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		// Walk through every possible index in the table.
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				// For each separate chain, grab each individual WordInfo.
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
	
	/* **Private method comments only for TAs (Not for client of TextAssociator)**
	 * This function takes a String word and the size of table (parameter) 
	 * This function creates and returns a hash value based on
	 * these values. This value is used for an index for the hash table
	 */
	private int findTableIndex(String word, int sizeOfTable) {
		return (Math.abs(word.hashCode()) % sizeOfTable);
	}
	
	/* **Private method comments only for TAs (Not for client of TextAssociator)**
	 * This takes an index (from the hashtable) and String table and
	 * finds the wordInfo object in the hashtable and returns it.
	 * This will return null if it cant find the word info object based on the 
	 * word and the index for which the word exsists in the hashtable 
	 */
	private WordInfo retrieveWordInfo(int index, String word){
		List<WordInfo> chain = table[index].getElements();
		for(WordInfo wiObject: chain){
			if(wiObject.getWord().equals(word)){
				return wiObject;
			}
		}
		return null;
	}
	
	/* **Private method comments only for TAs (Not for client of TextAssociator)**
	 * This resizes the table and rehashes all the Wordinfo Objects into the large table
	 * This new table size stays prime based on the 2*P + 1 formula
	 * The new table with a new size will be updated after this function runs
	 */
	private void resizeTable(){
		int newTablesize = 2*table.length +1; //creates new 'safe primes'
		WordInfoSeparateChain[] newTable = new WordInfoSeparateChain[newTablesize];
		for(WordInfoSeparateChain chain: table){
			if(chain != null){
				for(WordInfo wiObject: chain.getElements()){
					int index = findTableIndex(wiObject.getWord(), newTablesize);
					if(newTable[index] == null){
						newTable[index] = new WordInfoSeparateChain();
					}
					newTable[index].add(wiObject);
				}
			}
		}
		table = newTable;
	}
	
}
