/**
 * Takes words from file, then arranges them randomly.
 * @Author Charles Stremel
 */

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Markov {
	private static String BEGINS_SENTENCE;	
	private static String PUNCTUATION_MARKS;

	private String prevWord;
	private HashMap <String, ArrayList<String>> words;

	public Markov() {
		words = new HashMap<>();
	}

	String randomWord(String currentHash) {
		System.out.println("randomWord");
		System.out.println(words.get(currentHash));
		
		//String currentWord = "";
		//String [] tmp = words.size(
		//return currentWord;
		
		return "";	
	}

	public String getSentence() {
		this.randomWord(BEGINS_SENTENCE);	
		return "";
	}

	public static boolean endsWithPunctuation(String currentLine) {
		char last = currentLine.charAt(currentLine.length()-1);
		if(last == '.' || last == '?' || last == '!') {
			return true;
		}
		return false;
	}

	void addWord(String currentWord) {
		ArrayList<String> hmWords = new ArrayList<>();

		if(prevWord.equals("X")){
			BEGINS_SENTENCE = currentWord;
			hmWords.add(currentWord);
			words.put("_$", hmWords);

		} else {
			if(words.get(prevWord) != null) {
				hmWords = words.get(prevWord);
			}
			hmWords.add(currentWord);
			words.put(prevWord, hmWords);
		}
		prevWord = currentWord;
		return;	
	}

	void addLine(String currentLine) {
		if(currentLine.length() == 0) {
			System.out.println("Zero Line");
		} else {
			
			System.out.print(currentLine + " ");
			System.out.println(currentLine.length());
			
			String [] words = currentLine.split(" ");
			System.out.println(words.length);
			prevWord = "X";

			for(int i=0; i<words.length; i++) {
				System.out.println("Adding " + words[i]);
				this.addWord(words[i]);
			}
		}
		System.out.println(words);
		System.out.println(endsWithPunctuation(currentLine));
	}

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
			System.out.println("Successfuly captured words.");
			return;
		}
	}

	public static void main(String [] args) {
		Markov markov = new Markov();
		markov.addFromFile("blan.txt");
	}
}

