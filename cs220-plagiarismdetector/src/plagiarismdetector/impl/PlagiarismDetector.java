package plagiarismdetector.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.sun.javafx.collections.MappingChange.Map;

import plagiarismdetector.IPlagiarismDetector;

public class PlagiarismDetector implements IPlagiarismDetector
{
	private int n;
	private HashMap<String, PlagiarismData> data = new HashMap<String, PlagiarismData>();

	public PlagiarismDetector(int n) {
		this.n = n;
	}

	public int getN() {
		return n;
	}

	public void readFilesInDirectory(File dir) throws IOException {
		for (File f : dir.listFiles()) {
			data.put(f.getName(), new PlagiarismData(f, n));
		}

	}

	public Collection<String> getFilenames() {
		return data.keySet();
	}

	public int getNumNGramsInCommon(String file1, String file2) throws FileNotFoundException {
		int x = 0;
		Set<String> first = data.get(file1).getNGrams();
		Set<String> second = data.get(file2).getNGrams();
		for (String i : first) 
			if (second.contains(i))
				x++;
		return x;
	}

	public Collection<String> getNgramsInFile(String filename) throws FileNotFoundException {
		return data.get(filename).getNGrams();
	}

	public int getNumNgramsInFile(String filename) throws FileNotFoundException {
		return getNgramsInFile(filename).size();
	}

	public Collection<String> getSuspiciousPairs(int minNgrams) throws FileNotFoundException {
		int[][] grid = new int[data.size()][data.size()];
		String putTogether = "";
		Set<String> returner = new HashSet<String>();
		Collection<String> p = getFilenames();
		String[] scar = new String[p.size()];
		int x = 0;
		for (String scanIn : p) {
			scar[x] = scanIn;
			x++;
		}
		for (int r = 0; r < grid.length; r++)
			for (int c = 0; c < grid.length; c++) 
				if(r != c) {
					grid[r][c] = getNumNGramsInCommon(scar[r], scar[c]);
				}
		for (int r = 0; r < grid.length; r++)
			for (int c = 0; c < grid.length; c++) 
				if (grid[r][c] >= minNgrams) {
					if (0 > scar[r].compareTo(scar[c]))
						putTogether = scar[r] + " " + scar[c] + " " + getNumNGramsInCommon(scar[r], scar[c]);
					else
						putTogether = scar[c] + " " + scar[r] + " " + getNumNGramsInCommon(scar[r], scar[c]);
					returner.add(putTogether);
					grid[r][c] = -1;
					grid[c][r] = -1;
				}
		return returner;
	}

}
