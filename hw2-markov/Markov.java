/**
 * Takes words from file, then arranges them randomly.
 * @Author Charles Stremel
 */

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Random;

public class Markov {
	private static String BEGINS_SENTENCE = "_$";	
	private static String PUNCTUATION_MARKS;

	private String prevWord;
	private HashMap <String, ArrayList<String>> words;

	public Markov() {
		words = new HashMap<>();
	}

	public HashMap <String, ArrayList <String>> getWords() {
		return words;
	}

	public String toString() {
		return words.toString();
	}

	/**
	 * Support method for getSentence.
	 * Get a Collection of values from HashMap, then convert to String array.
	 * A random array index is chosen, then returned.
	String randomWord(String currentHash) {
		int size = words.get(currentHash).size();
		
		Random r = new Random();
		int ran = r.nextInt(size);
		
		Collection <String> hashWords = words.get(currentHash);
	
		String currentWord = hashWords.toArray()[ran].toString();

		return currentWord;	
	}

	/**
	 * Final processing method.
	 * Follows outline.
	 * Get a Collection of the keySet from HashMap, then converts to a String array.
	 * A random array index is chosen, then sent to randomWord.
	 */
	public String getSentence() {
		String currentWord  = randomWord(BEGINS_SENTENCE);	
		
		StringBuilder sb = new StringBuilder();
		Random r = new Random();

		while(!endsWithPunctuation(currentWord)) {
			sb.append(currentWord).append(" ");
			
			Collection <String> keyWords = words.keySet();
			int size = keyWords.size();
			int ran = r.nextInt(size);
			
			String newWord = keyWords.toArray()[ran].toString();
			currentWord = randomWord(newWord);
		}
		if(endsWithPunctuation(currentWord)) {
			sb.append(currentWord);
		}
		return sb.toString();
	}

	/**
	 * Checks currentLine's last character.
	 * Return true if punctuation.
	 */
	  public static boolean endsWithPunctuation(String currentLine) {
		char last = currentLine.charAt(currentLine.length()-1);
		if(last == '.' || last == '?' || last == '!' || last == '$') {
			return true;

		}
		return false;
	}

	/**
	 * hmWords: temporary ArrayList used to add into words HashMap.
	 * In both conditional cases, this method checks the HashMap for null with BEGINS_SENTENCE, or the previous word.
	 * If it is has a value, hmWords becomes the output of the HashMap - the temporary variable is matched and I have a working "memory".
	 * HashMap processes are done "backwards", and then prevWord is updated at last.
	 */ 
	void addWord(String currentWord) {
		ArrayList<String> hmWords = new ArrayList<>();
		
		if(currentWord.equals(" ")) {
			System.out.println("Current word is space, ignoring.");
			return;
		}

		if(prevWord.equals("X") || endsWithPunctuation(prevWord)){
			
			if(words.get(BEGINS_SENTENCE) != null) {
				hmWords = words.get(BEGINS_SENTENCE);
			}
			hmWords.add(currentWord);
			words.put(BEGINS_SENTENCE, hmWords);
		} 
		
		else {
			if(words.get(prevWord) != null) {
				hmWords = words.get(prevWord);
			}
			hmWords.add(currentWord);
			words.put(prevWord, hmWords);
			//System.out.println(words);
		}
		
		prevWord = currentWord;
		return;	
	}
	
	/**
	 * If the current line is empty, returns to addFromFile without adding any words.
	 * All words in a line are placed into String array which is split by whitespace.
	 * prevWord becomes a placeholder.
	 * For loop then calls addWord for each word in the array.
	 */ 
	void addLine(String currentLine) {
		//System.out.println("New: " + currentLine);
		if(currentLine.length() == 0) {
			return;
			//System.out.println("Empty Line");
		} else {
			String [] words = currentLine.split(" ");
			
			prevWord = "X";

			for(int i=0; i<words.length; i++) {
				//System.out.println("Adding " + words[i]);
				addWord(words[i]);
			}
		}
	}

	/**
	 * Try-catch structure to open file.
	 * This methods starts the chain which runs all other methods.
	 * It uses a while loop which calls addLine for each line in file.
	 * 
	 * fIn: "file Input"
	 */
	public void addFromFile(String filename) {
		Scanner fIn = null;
		File file = new File(filename);

		try {
			fIn = new Scanner(file);
			while(fIn.hasNextLine()) {
				this.addLine(fIn.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.printf("Could not read %s $n", filename);
			return;
		} finally {
			return;
		}
	}

	/*
	public static void main(String [] args) {
		Markov markov = new Markov();
		
		Scanner sIn = new Scanner(System.in);
		System.out.print("Enter filename: ");
		String filename = sIn.next();
		//String filename = "sham.txt";

		markov.addFromFile(filename);
		
		System.out.println(markov.getSentence());
	}*/
}

