// This program finds and prints out combinations of anagrams 
// from a given word or phrase based on what's in the dictionary. 
// Number of printed combinations depend on user's input.

import java.util.*;

public class AnagramSolver {
   // List of words in the same order as the given dictionary
   private List<String> listDictionary;
   // Each key consists of words from the given dictionary, 
   // and their respective values are inventories of the
   // word's letters
	private Map<String, LetterInventory> mapDictionary;
	
   // Initializes an AnagramSolver with the given list of
   // strings. The given list is nonempty, and consists of 
   // nonempty strings (no duplicates)
	public AnagramSolver(List<String> dictionary) {
      listDictionary = dictionary;
		mapDictionary = new HashMap<String, LetterInventory>();
      
		for (String word : dictionary) { 
			mapDictionary.put(word, new LetterInventory(word));
		}
      
	}
	
   // Finds every combination of anagrams based on the given 
   // string and prints out each combination (in dictionary order) 
   // up to the given integer, representing the max number of
   // printed combinations. If given int is less than zero, throw
   // IllegalArgumentException. If the max is zero, then it prints
   // out all the possible combinations
	public void print(String text, int max) {
		if (max < 0) {
			throw new IllegalArgumentException();
		}
      // Desired letters from the given string text
		LetterInventory textLetters = new LetterInventory(text);
		List<String> listOptions = new ArrayList<String>();
      
      // Loops through every word in dictionary to find words
      // that could be a part of an anagram of the given text
		for (String word : listDictionary) {
      
         // Gets the mapDictionary value (a LetterInventory obect) 
         // of the given word
			LetterInventory inventory = mapDictionary.get(word);
         // If the word's letter inventory can be subtracted from
         // the given text's inventory, then it is relevant
			if (textLetters.subtract(inventory) != null) {
            // listOptions consists of all the words from the dictionary
            // that could be part of the anagram
				listOptions.add(word);
			}
         
		}
      
		print(new Stack<String>(), textLetters, listOptions, max);
      
	}
	
   // Prints out every stack of possible anagram combinations if the 
   // given int "max" is zero or all the anagrams up to the max number
   // of combinations. Anagrams are printed in dictionary order. Takes 
   // in a stack of strings to hold the anagrams, a LetterInventory
   // object representing the desired text, a list of strings 
   // representing all the possible anagrams, and an integer max
	private void print(Stack<String> stackAnswer, LetterInventory textLetters, 
                     List<String> listOptions, int max) {
		// Base case. If there are no more letters that need an anagram for it
      if(textLetters.isEmpty()){
         System.out.println(stackAnswer);
      }
      // If max is zero or if the size of the stack is still not up to 
      // the max number of anagram combinations, loop through every
      // word in the list of possible anagrams and put them in a stack
      if (max == 0 || max > stackAnswer.size()) {
      
			for (String word : listOptions) {
            // Gets the LetterInventory object for every word in listOptions from
            // mapDictionary's values
				LetterInventory inventory = mapDictionary.get(word);
            // Gets the remaining letters that the program must find a word for
            // to make it a complete anagram
				LetterInventory remainingLetters = textLetters.subtract(inventory);
				
            // If there are still remaining letters, push the recently found 
            // word into the stack and keep searching for other words that
            // could complete the anagram
            if (remainingLetters != null) {
					stackAnswer.push(word); 			
					print(stackAnswer, remainingLetters, listOptions, max); 
					stackAnswer.pop(); 
				}
            
			}	
         
		}
      
	} 
   
}