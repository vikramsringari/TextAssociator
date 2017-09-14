import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
/* This Client code takes a curse word inputed by the user
 * It then takes a word (group of words' associations as well) to replace this curse word.  
 * It then takes a text filled with that curse word and replaces it with associations or the word that 
 * were chosen to be replaced. This is cumulative so if the user decides to keep inputting new curse words 
 * and new replacements and inputs texts with old curse words, the old curse words will be replaced 
 * (for that session). Users can end a session by typing "exit".
 * This is all bassed on the data from the large_thesaurus
 */
public class MyClient {
	
	// Path to desired thesaurus file to read
	public final static String THESAURUS_FILE = "large_thesaurus.txt";
	
	public static void main(String[] args) throws IOException {
		File file = new File(THESAURUS_FILE);
		
		// Create new empty TextAssociator
		TextAssociator sc = new TextAssociator();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String text = null;
		while ((text = reader.readLine()) != null) {
			String[] words = text.split(",");
			String currWord = words[0].trim();
			sc.addNewWord(currWord);
			for (int i = 1; i < words.length; i++) {
				sc.addAssociation(currWord, words[i].trim());
			}
		}
		Scanner scan= new Scanner(System.in);
		String inputString = "";
		Random rand = new Random();
		List<String> curses = new ArrayList<String>();
		while (true) {
			System.out.println("Please input a curse word you would want to censor (from text that you will input later):");
			String curse = scan.nextLine();
			sc.addNewWord(curse);
			curses.add(curse);
			System.out.println("Please input a word or phrase you would want to replace the curse word with (from text that you will input later)");
			System.out.println("If the word or phrase is common the text you see replaced will have related words. if not then the word or phrase");
			System.out.print("If not then the word or phrase you request to replace will be exactly replaced (from text that you will input later):");
			String replace = scan.nextLine();
			if(sc.addNewWord(replace)==false){
				Set<String> words = sc.getAssociations(replace);
				for(String word: words){
					sc.addAssociation(curse, word);
				}
			}
			sc.addAssociation(curse, replace);
			System.out.print("Please input some inaproriate curse filled text you would like to convert? (enter \"exit\" to exit):");
			inputString = scan.nextLine();
			if (inputString.equals("exit")) {
				break;
			}
			String[] tokens  = inputString.split(" ");
			String result = "";
			Set<String> replacementWords = sc.getAssociations(curse.toLowerCase());
			for (String token : tokens) {
				if(curses.contains(token.toLowerCase())){
					result += " " + replacementWords.toArray()[rand.nextInt(replacementWords.size())];
				}else  {
					result += " " + token;
				}
			}
			System.out.println(result.trim());
			System.out.println();	
		}
		reader.close();
	}
}
