package plagiarismdetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

/**
 * Interface for a plagiarism detector based on n-grams.
 * 
 * This is a relatively simple plagiarism detector
 * based on a SIGCSE Nifty Assignment. It is not designed
 * to be used in practice, is unlikely to work on code,
 * and is designed to process a batch of documents all at once,
 * with no obvious way to update the results with new documents.
 * 
 * @author jspacco
 *
 */
public interface IPlagiarismDetector
{
    /**
     * Get n, the number of words in each n-gram
     * 
     * @return
     */
    int getN();
    
    /**
     * Return a collection of the names of the files that have been processed.
     * 
     * A collection can be a List or a Set.
     * 
     * @return
     */
    Collection<String> getFilenames();

    /**
     * Given the names of two files,
     * get the number of n-grams in common between the two files.
     * 
     * @param file1
     * @param file2
     * @return
     * @throws FileNotFoundException 
     */
    int getNumNGramsInCommon(String file1, String file2) throws FileNotFoundException;
    
    /**
     * Given the name of a file, return a collection of the unique n-grams in that file.
     * 
     * If a particular n-gram occurs 50 times in a document, we would only count it
     * as one occurrence, because a simplifying assumption we are making is that
     * we do not care how many times an n-gram occurs in a file.
     * 
     * @param filename
     * @return
     * @throws FileNotFoundException 
     */
    Collection<String> getNgramsInFile(String filename) throws FileNotFoundException;

    /**
     * Return the number of unique n-grams in a given document. 
     * 
     * Remember that if a particular n-gram occurs 50 times in a document, 
     * we would only count it as one occurrence.
     * 
     * @param filename
     * @return
     * @throws FileNotFoundException 
     */
    int getNumNgramsInFile(String filename) throws FileNotFoundException;

    /**
     * <b>Do this method first</b>
     * 
     * Read all of the files in the given directory. Convert
     * the text of the files into n-grams and figure out how many
     * n-grams are in common between every pair of files.
     * 
     * So, store all of this information in instance variables.
     * This function is what populates all of your data structures.
     * 
     * <b>Note:</b> This method should do almost all of the work: Read the files,
     * create the map from filenames to sets of ngrams, and the map of maps of ngrams in
     * common. All of the other methods read data that this method creates.
     * 
     * <b>Note</b>: There is no easy way to add new documents to check for
     * plagiarism.
     * 
     * @param dir
     * @throws IOException
     */
    void readFilesInDirectory(File dir) throws IOException;
    
    /**
     * Return a collection of pairs of documents that have at least minNgrams
     * n-grams in common. The format for each pair is a String with
     * the first file, second file, and number of n-grams in common, all separated by a space.
     * So for example, if file1 and file2 have 99 n-grams in common, and minNGrams is
     * 90, we would make sure that this String is in the resulting collection:
     * <pre>
        file1 file2 99
     * </pre>
     * You should put file1 and file2 in alphabetical order. You can check 
     * alphabetical order with Strings in Java very easily using the compareTo() method:
     * 
     * s1.compareTo(s2)
     * 
     * will return a negative is s1 comes before s2, positive if s1 comes after s2,
     * and 0 if they are equals (i.e. s1.equals(s2)).
     * 
     * <b>Note</b>: This method requires that we have already called
     * the method {@link #readFilesInDirectory(File)}. 
     * 
     * @param minNgrams
     * @return
     * @throws FileNotFoundException 
     */
    Collection<String> getSuspiciousPairs(int minNgrams) throws FileNotFoundException;

}