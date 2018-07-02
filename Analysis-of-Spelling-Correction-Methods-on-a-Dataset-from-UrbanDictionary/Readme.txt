
INTRODUCTION
------------

COMP90049 Project 1
Analysis of Spelling Correction Methods on a Dataset from UrbanDictionary SourceCode.

 * @author shajid.mohammad@student.unimelb.edu.au

The build tool used to build this project is MAVEN

REQUIREMENTS
------------

JDK 1.8
Apache MAVEN 

CONTENTS
--------

This zip contains:

    /readme.txt - This file
    
    /pom.xml - MAVEN build file to compile and run the test code manually 
 	
 	/LevenshteinDistance.java - 
 	The LevenshteinDistance program is used to calculate the Levenshtein distance as stated by Levenshtein, Vladimir I. (1966) between the misspelled word and all the words in the dictionary 
 	and the words for which the lowest distance is returned is considered and predicted as the most likely to be the correct spelling. 
 	In the case of ties, i.e. when two or more number of words from the dictionary dataset have the same distance, then all the words are predicted to be the correct spelling. 	
 	
 	/Ngram.java - 
 	The Ngram program is used to predict the correct spelled words for misspelled words using N-Gram distance as stated by Kondrak, Grzegorz (2005) 
 	It is used to predict the correct word for each possible misspelled word, m(Gn(m)) and a word in the dictionary dataset, d (Gn(d)) by calculating the distance using the formulae, |Gn(m)| + |Gn(d)|− 2×|Gn(m)∩Gn(d)|.
 	
 	/Soundex.java - 
 	The Soundex program is used to predict the correct spelled words for misspelled words using Soundex as stated by Zobel, Justin and Dart, Philip (1996)
	The goal is for both misspelled and dictionary words to be encoded to the same representation so that they can be matched and if there is a match that dictionary word is predicted as the correct word and when two or more number of words from the dictionary dataset have the same Soundex Code, then all those words are predicted to be the correct spelling.
	
	/LevenshteinDistanceFollowedByNGram.java - 
	A Potential improvement considered is the reduction of the number of tie breaking predicted dictionary words for the misspelled word with equal Levenshtein distance by further processing this list of words for their respective N-Grams distance over the misspelled word and the words with the least N-Grams distance are the final list of predicted correct words for the misspelled word. This increases precision over spelling correction methods like Levenshtein distance and N-Gram Distance.
	
	/FindMIsspelledWordsInDictionary.java - 
	 FindMIsspelledWordsInDictionary class is used to Find the MIsspelledWords In the Dictionary
	 
	/FindCorrectWordsNotInDictionary.java -
	 FindCorrectWordsNotInDictionary class is used to Find the CorrectWords Not In Dictionary
	 
PRE-REQUISITES
--------------

	Copy the three Datasets (misspell.txt, dictionary.txt, correct.txt) in to the KnowledgeTechnologiesAssignment1 folder.
	

EXECUTION
------------
		Navigate to the KnowledgeTechnologiesAssignment1 folder from the terminal 
		
	 	To execute LevenshteinDistance.java from terminal: 
	 	mvn exec:java -Dexec.mainClass="au.edu.unimelb.KnowledgeTechnologiesAssignment1.LevenshteinDistance"
	 	
	 	To execute Ngram.java from terminal: 
	 	mvn exec:java -Dexec.mainClass="au.edu.unimelb.KnowledgeTechnologiesAssignment1.Ngram"
	 	
	 	To execute Soundex.java from terminal: 
	 	mvn exec:java -Dexec.mainClass="au.edu.unimelb.KnowledgeTechnologiesAssignment1.Soundex"
	 	
	 	To execute LevenshteinDistanceFollowedByNGram.java from terminal: 
	 	mvn exec:java -Dexec.mainClass="au.edu.unimelb.KnowledgeTechnologiesAssignment1.LevenshteinDistanceFollowedByNGram"
	 	
	 	To execute FindMIsspelledWordsInDictionary.java from terminal: 
	 	mvn exec:java -Dexec.mainClass="au.edu.unimelb.KnowledgeTechnologiesAssignment1.FindMIsspelledWordsInDictionary"
	 	
	 	To execute FindCorrectWordsNotInDictionary.java from terminal: 
	 	mvn exec:java -Dexec.mainClass="au.edu.unimelb.KnowledgeTechnologiesAssignment1.FindCorrectWordsNotInDictionary"
	 	

Sample OUTPUT for LevenshteinDistance,java
------------------------------------------
	2018-04-10T09:36:07.302
	Total Predictions made is :5528
	Total Correct Predictions made is :253
	Precision is :0.045767006
	Recall is :0.35335195
	2018-04-10T09:45:02.077	 	
	 	
	
	
	