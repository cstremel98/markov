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
	private static String BEGINS_SENTENCE;	
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

	String randomWord(String currentHash) {
		int size = words.get(currentHash).size();
		Random r = new Random();
		int ran = r.nextInt(size);
		
		Collection <String> hashWords = words.get(currentHash);
	
		String currentWord = hashWords.toArray()[ran].toString();

		return currentWord;	
	}

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
			//System.out.println(newWord);
			currentWord = randomWord(newWord);
		}
		if(endsWithPunctuation(currentWord)) {
			sb.append(currentWord);
		}
		return sb.toString();
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
			System.out.println("Empty Line");
		} else {
			
			//System.out.print(currentLine + " ");
			//System.out.println(currentLine.length());
			
			String [] words = currentLine.split(" ");
			//System.out.println(words.length);
			prevWord = "X";

			for(int i=0; i<words.length; i++) {
				//System.out.println("Adding " + words[i]);
				this.addWord(words[i]);
			}
		}
		//System.out.println(words);
		//System.out.println(endsWithPunctuation(currentLine));
		//System.out.println(getSentence());
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
			System.out.println("Done.");
			return;
		}
	}

	public static void main(String [] args) {
		Markov markov = new Markov();
		
		Scanner sIn = new Scanner(System.in);
		System.out.print("Enter filename: ");
		String filename = sIn.next();
		
		markov.addFromFile(filename);
		
		System.out.println(markov);
		System.out.println(markov.getWords());
		System.out.println(markov.getSentence());
	}
}

