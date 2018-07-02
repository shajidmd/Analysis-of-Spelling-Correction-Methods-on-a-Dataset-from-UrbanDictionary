package au.edu.unimelb.KnowledgeTechnologiesAssignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * The LevenshteinDistance program is used to calculate the Levenshtein distance
 * as stated by Levenshtein, Vladimir I. (1966) is a measure of the similarity
 * between the misspelled word and word in the dictionary. Levenshtein distance
 * between each possible misspelled word and a word in the dictionary dataset is
 * calculated and the word for which the lowest distance is returned is
 * considered and predicted as the most likely to be the correct spelling. In
 * the case of ties, i.e. when two or more number of words from the dictionary
 * dataset have the same distance, then all the words are predicted to be the
 * correct spelling.
 *
 * @author shajid.mohammad@student.unimelb.edu.au
 * @version 1.0
 */

public class LevenshteinDistance {
	static int calculate(String misspelledWord, String dictionaryWord) {
		int[][] dp = new int[misspelledWord.length() + 1][dictionaryWord.length() + 1];

		for (int i = 0; i <= misspelledWord.length(); i++) {
			for (int j = 0; j <= dictionaryWord.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = min(
							dp[i - 1][j - 1]
									+ costOfSubstitution(misspelledWord.charAt(i - 1), dictionaryWord.charAt(j - 1)),
							dp[i - 1][j] + 1, dp[i][j - 1] + 1);
				}
			}
		}

		return dp[misspelledWord.length()][dictionaryWord.length()];
	}

	public static int costOfSubstitution(char a, char b) {
		return a == b ? 0 : 1;
	}

	public static int min(int... numbers) {
		return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
	}

	public static void main(String[] args) {
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
			int maxWordDistance = misspelledWord.length();
			int closestWordDistance = maxWordDistance;
			String closestWord = "";
			int totalTempPredictions = 1;
			boolean encounterCorrectWord = false;
			for (String dictionaryWord : dictionaryList) {
				int distanceValue = calculate(misspelledWord, dictionaryWord);
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
