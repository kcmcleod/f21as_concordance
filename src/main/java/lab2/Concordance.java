/**
 * Demo prog for Collection classes - Maps and Sets
 * Converted by Monica from a program by David Eck
 */
package lab2;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Concordance {

	/* This TreeMap holds the concordance.  
	 * The keys are the distinct words from the file
	 * The values are a Set of line numbers on which the word occurs
	 */
	private TreeMap<String, TreeSet<Integer>> concordance;
	
	public Concordance() {
		concordance = new TreeMap<String, TreeSet<Integer>> ();
	}
	

	/** top-level process to read, create and print concordance
	 * @param filename  The name of the file containing the text
	 */
	public void process(String filename){

		//read input file and create concordance
		readAndCreate(filename);  

		// Print the data to the system.out
		printConcordance();  

		//print the number of words found
		System.out.println(concordance.size() + " distinct words were found.");
	

	}
      
	/*
	 * reads each line from input file, keeping a line count
	 * processes each line
	 */
	private void readAndCreate(String filename) {
		Scanner lineScanner; 
		int lineNum = 1;  // The number of the line in the input

		try {  
			// read and process each line in the input file
			lineScanner = new Scanner(new File(filename));
			while (lineScanner.hasNext()){
				processLine(lineScanner.nextLine(), lineNum);
				lineNum++;  // inc line number
			}
		}
		catch (IOException e) {
			System.out.println("Error: Can't open input file ");
			System.exit(1);
		}
	}
      

	/*
	 * Separate out each word in the line
	 * and add these to the reference     
	 */
	private void processLine(String line, int lineNum) {
		Scanner wordScanner = new Scanner(line);
		while(wordScanner.hasNext()){
			String word = wordScanner.next();
			addReference(word,lineNum);
		}   	  
	}

	/*
	 * returns true if word contains letters , false otherwise 
	 */
	private String removeNonAlpha(String word) {
		String newWord = "";
		for (int count = 0; count<word.length(); count++){
			char ch = word.charAt(count);
			if ( (ch>='A' && ch<='Z') || (ch>='a' && ch <= 'z')) {
				newWord += ch;
			}
		}
		return newWord;
	}

	/*
	 * Adds a line reference to the concordance.
	 * First gets set of line references for the word
	 * If none, make new set including this line number
	 *          add new concordance entry with key=word, 
	 *                     values = set of 1 line number
	 * Else  add this line number to the set
	 */
	private void addReference(String word, int lineNum) {
	   //convert to upper case
		word = word.toUpperCase();
	   //remove non-alpha characters, return if now empty
	   String strippedWord = removeNonAlpha(word);
	   if (strippedWord.length() == 0)
		   return;

		// Get the set of line references for this word
		TreeSet <Integer> references = concordance.get(strippedWord);   

		//If none so far, make new set containing this line number
		//and add to concordance with key =word, value = new set of one Int
		if (references == null){
			TreeSet <Integer> firstRef = new TreeSet<Integer>();
			firstRef.add( new Integer(lineNum) );
			concordance.put(strippedWord,firstRef);
		}
		else { // Add the new line number to existing set.
			//Because it is a set, duplicate line numbers will not be added
			references.add( new Integer(lineNum) );
		}
	}

	/* 
	 * Prints each entry from the concordance, one per line
	 */
	private void printConcordance() {
		
		//Extract Set of key/value pairs
		Set <Map.Entry <String, TreeSet<Integer>> > entries = concordance.entrySet();  

		//for each entry
		//  get and print the word (the key)
		//  get and print the set of integers (the value)
		for (Map.Entry<String, TreeSet<Integer>> entry: entries){
			
            //get and print word 
			String term = entry.getKey();
			System.out.print(term + " ");
			
			//get and print integers
			Set<Integer> lines = entry.getValue();
			Iterator<Integer> iter = lines.iterator();
			System.out.print(iter.next());
			while (iter.hasNext()){
				System.out.print(", " + iter.next());
			}
			
			//print newline
			System.out.println();
		}
	}


	

} // end class Concordance