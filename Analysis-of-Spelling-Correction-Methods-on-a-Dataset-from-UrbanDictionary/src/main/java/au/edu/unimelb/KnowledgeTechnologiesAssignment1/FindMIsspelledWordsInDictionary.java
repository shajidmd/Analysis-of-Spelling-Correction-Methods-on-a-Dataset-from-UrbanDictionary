package au.edu.unimelb.KnowledgeTechnologiesAssignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FindMIsspelledWordsInDictionary class is used to Find the MIsspelledWords In the Dictionary
 * @author shajid.mohammad@student.unimelb.edu.au
 * @version 1.0
 */
public class FindMIsspelledWordsInDictionary {

	public static void main(String[] args) {

		String dictionaryFile = "dictionary.txt";
		String correctFile = "misspell.txt";

		List<String> dictionaryList = new ArrayList<String>();
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

		for (int i = 0; i < correctWordList.size(); i++) {
			boolean contains = false;
			String correctWord = correctWordList.get(i);
			for (String dictionaryWord : dictionaryList) {
				if (correctWord.equalsIgnoreCase(dictionaryWord)) {
					contains = true;
					break;
				}

			}
			if (contains) {
				System.out.println(correctWord);
			}

		}
	}

}
