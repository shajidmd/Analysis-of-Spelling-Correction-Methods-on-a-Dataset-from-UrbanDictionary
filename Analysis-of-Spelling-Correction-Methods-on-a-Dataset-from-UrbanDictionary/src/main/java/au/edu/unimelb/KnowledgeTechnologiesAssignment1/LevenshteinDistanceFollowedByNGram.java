package au.edu.unimelb.KnowledgeTechnologiesAssignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * A Potential improvement considered is the reduction of the number of tie
 * breaking predicted dictionary words for the misspelled word with equal
 * Levenshtein distance by further processing this list of words for their
 * respective N-Grams distance over the misspelled word and the words with the
 * least N-Grams distance are the final list of predicted correct words for the
 * misspelled word. This increases precision over spelling correction methods
 * like Levenshtein distance and N-Gram Distance.
 *
 * 
 * @author shajid.mohammad@student.unimelb.edu.au
 * @version 1.0
 */

public class LevenshteinDistanceFollowedByNGram {

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
			// for (int i = 0; i < 3; i++) {
			String misspelledWord = misspellList.get(i);
			String correctWord = correctWordList.get(i);
			int maxWordDistance = misspelledWord.length();
			int closestWordDistance = maxWordDistance;
			String closestWord = "";
			int totalTempPredictions = 1;
			boolean encounterCorrectWord = false;
			List<String> closestDictionaryWordList = new ArrayList<String>();
			for (String dictionaryWord : dictionaryList) {
				int distanceValue = LevenshteinDistance.calculate(misspelledWord, dictionaryWord);
				if (distanceValue < closestWordDistance) {
					closestWordDistance = distanceValue;
					closestWord = dictionaryWord;
					for (int ix = 0; ix < closestDictionaryWordList.size(); ix++) {
						closestDictionaryWordList.remove(ix);
					}
				} else if ((distanceValue == closestWordDistance)) {
					closestDictionaryWordList.add(dictionaryWord);
				}
			}

			if ((closestWord.equals(correctWord))) {
				closestDictionaryWordList.add(correctWord);
			}
			closestWordDistance = maxWordDistance;
			closestWord = "";
			for (String dictionaryPredictedWord : closestDictionaryWordList) {
				int distanceValue = gram.getDistance(misspelledWord, dictionaryPredictedWord, 2);
				if (distanceValue < closestWordDistance) {
					encounterCorrectWord = false;
					closestWordDistance = distanceValue;
					closestWord = dictionaryPredictedWord;
					totalTempPredictions = 1;

				} else if ((distanceValue == closestWordDistance)) {

					totalTempPredictions++;
					if (correctWord.equals(dictionaryPredictedWord)) {
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
