package au.edu.unimelb.KnowledgeTechnologiesAssignment1;

import java.io.BufferedReader;
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
 * Soundex is a phonetic algorithm for indexing words by sound as stated by
 * Zobel, Justin and Dart, Philip (1996). The goal is for both misspelled and
 * dictionary words to be encoded to the same representation so that they can be
 * matched and if there is a match that dictionary word is predicted as the
 * correct word and when two or more number of words from the dictionary dataset
 * have the same Soundex Code, then all those words are predicted to be the
 * correct spelling.
 *
 * 
 * @author shajid.mohammad@student.unimelb.edu.au
 * @version 1.0
 */

public class Soundex {
	public static String getSoundexCodeForWord(String word) {

		char[] wordChars = word.toUpperCase().toCharArray();

		char firstLetter = wordChars[0];

		// Four step process
		// Step 1: Except for initial character, Translate string characters according
		// to
		// the blow table

		// Translation table:
		// aehiouwy → 0 (vowels)
		// bpfv → 1 (labials)
		// cgjkqsxz → 2 (misc:
		// fricatives,velars, etc.)
		// dt → 3 (dentals)
		// l → 4 (lateral)
		// mn → 5 (nasals)
		// r → 6 (rhotic)

		String soundexCodeForWord = "" + firstLetter;

		for (int i = 0; i < wordChars.length; i++) {
			switch (wordChars[i]) {
			case 'B':
			case 'F':
			case 'P':
			case 'V': {
				wordChars[i] = '1';
				break;
			}

			case 'C':
			case 'G':
			case 'J':
			case 'K':
			case 'Q':
			case 'S':
			case 'X':
			case 'Z': {
				wordChars[i] = '2';
				break;
			}

			case 'D':
			case 'T': {
				wordChars[i] = '3';
				break;
			}

			case 'L': {
				wordChars[i] = '4';
				break;
			}

			case 'M':
			case 'N': {
				wordChars[i] = '5';
				break;
			}

			case 'R': {
				wordChars[i] = '6';
				break;
			}

			default: {
				wordChars[i] = '0';
				break;
			}
			}
		}

		// Step 2: Remove duplicates (e.g. 4444 → 4)
		// Step 3: Remove 0s
		for (int i = 1; i < wordChars.length; i++)
			if (wordChars[i] != wordChars[i - 1] && wordChars[i] != '0')
				soundexCodeForWord += wordChars[i];

		// Padding with 0's
		soundexCodeForWord = soundexCodeForWord + "0000";

		// Step 4: Truncate to four symbols
		return soundexCodeForWord.substring(0, 4);
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
			String closestWord = "";
			String getSoundexCodeForMisspelledWord = getSoundexCodeForWord(misspelledWord);
			for (String dictionaryWord : dictionaryList) {
				String getSoundexCodeForDictionaryWord = getSoundexCodeForWord(dictionaryWord);

				if (getSoundexCodeForMisspelledWord.equals(getSoundexCodeForDictionaryWord)) {
					totalPredictions++;
					closestWord = dictionaryWord;
					if (closestWord.equalsIgnoreCase(correctWord)) {
						totalCorrectPredictions++;
					}
				}
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