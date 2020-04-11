package plagiarismdetector.impl;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PlagiarismData {

	private int n;
	private int nCounter;
	private int scanReset = 0;
	private File file;
	private String nextWord = "";

	public PlagiarismData(File f, int nGrams) {
		n = nGrams;
		file = f;
		nCounter = n;
	}

	public Set<String> getNGrams() throws FileNotFoundException { //TODO, find the error causing 5 words to print (look at photo)
		Scanner scan = new Scanner(new FileInputStream(file));
		String bigWord = "";
		ArrayList<String> words = new ArrayList<String>();
		Set<String> ngrams = new HashSet<String>();
		while (scan.hasNext())
			words.add(scan.next());
		for (int i = 0; i < words.size()-(n-1); i++) {
			for (int x = 0; x < n; x++)
				bigWord = bigWord + words.get(i + x) + " ";
			bigWord = bigWord.trim();
			ngrams.add(bigWord);
			bigWord = "";
		}
		return ngrams;
	}
}
