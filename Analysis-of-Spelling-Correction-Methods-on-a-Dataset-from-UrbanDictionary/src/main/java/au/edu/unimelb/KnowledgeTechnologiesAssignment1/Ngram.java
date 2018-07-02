package au.edu.unimelb.KnowledgeTechnologiesAssignment1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * The N-Gram distance as stated by Kondrak, Grzegorz (2005) is used to predict
 * the correct word for each possible misspelled word, m(Gn(m)) and a word in
 * the dictionary dataset, d (Gn(d)) by calculating the distance using the below
 * formulae, |Gn(m)| + |Gn(d)|− 2×|Gn(m)∩Gn(d)|.
 *
 * 
 * @author shajid.mohammad@student.unimelb.edu.au
 * @version 1.0
 */

public class Ngram {
	private class result {
		private String theWord;
		private int theCount;

		public result(String word, int count) {
			theWord = word;
			theCount = count;
		}

		public void setTheCount(int count) {
			theCount = count;
		}

		public String getTheWord() {
			return theWord;
		}

		public int getTheCount() {
			return theCount;
		}
	}

	private List<result> results;

	public Ngram() {
		results = new ArrayList<result>();
	}

	public Ngram(String str, int n) {

	}

	public int getDistance(String misspelledWord, String dictionaryWord, int nGramSize) {

		List<result> nGramsForMispelledWord = wordToNGrams(misspelledWord, nGramSize);

		List<result> nGramsForDictionaryWord = wordToNGrams(dictionaryWord, nGramSize);

		int intersectionOfGramsCount = intersectionOfGrams(nGramsForMispelledWord, nGramsForDictionaryWord);

		int nGramsForMispelledWordSize = nGramsForMispelledWord.size();
		int nGramsForDictionaryWordSize = nGramsForDictionaryWord.size();

		// n-Gram Distance = |G2(crat)| + |G2(arts)| − 2 × |G2(crat) ∩ G2(arts)|
		int nGramDistance = nGramsForMispelledWordSize + nGramsForDictionaryWordSize - (2 * intersectionOfGramsCount);

		return nGramDistance;
	}

	private int intersectionOfGrams(List<result> nGramsForMispelledWord, List<result> nGramsForDictionaryWord) {
		int counter = 0;

		for (int i = 0; i < nGramsForMispelledWord.size(); i++) {
			for (int j = 0; j < nGramsForDictionaryWord.size(); j++) {
				if (nGramsForMispelledWord.get(i).theWord.equalsIgnoreCase(nGramsForDictionaryWord.get(j).theWord))
					counter++;
			}
		}

		return counter;
	}

	private List<result> wordToNGrams(String word, int nGramSize) {
		List<result> t = new ArrayList<result>();

		String spacer = "";
		for (int i = 0; i < nGramSize - 1; i++) {
			spacer = spacer + "#";
		}
		word = spacer + word + spacer;

		for (int i = 0; i < word.length(); i++) {
			if (i <= (word.length() - nGramSize)) {
				if (contains(word.substring(i, nGramSize + i)) > 0) {
					t.get(i).setTheCount(results.get(i).getTheCount() + 1);
				} else {
					t.add(new result(word.substring(i, nGramSize + i), 1));
				}
			}
		}
		return t;
	}

	private int contains(String gram) {
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).theWord.equalsIgnoreCase(gram))
				return i;
		}
		return 0;
	}

	public static void main(String[] args) {
		Ngram gram = new Ngram();

		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println(localDateTime);

		String dictionaryFile = "dictionary.txt";
		String misspellFile = "misspell.txt";
		String correctFile = "correct.txt";

		List<String> dictionaryList = new ArrayList<String>();
		List<String> misspellList = new ArrayList<String>();
		List<String> correctWordList = new ArrayList<String>();

		// Start Load Dictionary
		try {
			FileReader fileReader = new FileReader(dictionaryFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null) {
				dictionaryList.add(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// End Load Dictionary

		// Start Load Misspelled Words
		try {
			FileReader fileReader = new FileReader(misspellFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null) {
				misspellList.add(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// End Load Misspelled Words

		// Start Load Correct Words
		try {
			FileReader fileReader = new FileReader(correctFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null) {
				correctWordList.add(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// End Load Correct Words

		Dictionary dict = new Hashtable();
		int totalPredictions = 0;
		int totalCorrectPredictions = 0;
		for (int i = 0; i < misspellList.size(); i++) {
			String misspelledWord = misspellList.get(i);
			String correctWord = correctWordList.get(i);
			int maxWordDistance = (misspelledWord.length() * 2);
			int closestWordDistance = maxWordDistance;
			String closestWord = "";
			int totalTempPredictions = 1;
			boolean encounterCorrectWord = false;
			for (String dictionaryWord : dictionaryList) {
				int distanceValue = gram.getDistance(misspelledWord, dictionaryWord, 2);
				if (distanceValue < closestWordDistance) {
					encounterCorrectWord = false;
					closestWordDistance = distanceValue;
					closestWord = dictionaryWord;
					totalTempPredictions = 1;
				} else if ((distanceValue == closestWordDistance)) {
					totalTempPredictions++;
					if (correctWord.equals(dictionaryWord)) {
						encounterCorrectWord = true;
					}
				}

			}
			totalPredictions = totalPredictions + totalTempPredictions;
			if ((closestWord.equals(correctWord))) {
				encounterCorrectWord = true;
			}
			if (encounterCorrectWord) {
				totalCorrectPredictions++;
			}
		}
		System.out.println("Total Predictions made is :" + totalPredictions);
		System.out.println("Total Correct Predictions made is :" + totalCorrectPredictions);
		float Precision = (float) totalCorrectPredictions / totalPredictions;
		float Recall = (float) totalCorrectPredictions / misspellList.size();
		System.out.println("Precision is :" + Precision);
		System.out.println("Recall is :" + Recall);
		LocalDateTime localDateTime2 = LocalDateTime.now();
		System.out.println(localDateTime2);
	}
}