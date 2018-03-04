import java.io.*;
import java.util.*;

public class FileParser
{
    File dictionary = new File("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanEncoder/src/Dictionary.txt");
    File gadsby = new File("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanEncoder/src/Gadsby.txt");
    
    /**
     * Parses a given input file to a sorted ArrayList of HuffmanNodes
     *
     * @param filePath the file to be converted to Huffman frequencies
     * @return the ArrayList of HuffmanNodes containing Characters and their respective frequencies in the file
     * @throws IOException if the file is nonexistent
     */
    public static ArrayList<HuffmanNode> toHuffmanHeap(String filePath) throws IOException
    {
        HashMap<Character, Integer> frequencyTable = new HashMap<>();
        File file = new File(filePath);
        
        Scanner scan = new Scanner(file);
    
        //create a frequency table of characters using a HashMap
        while(scan.hasNextLine())
        {
            char[] temp = scan.nextLine().toCharArray();
            for(char element : temp)
            {
                //add 1 to the key if this is the first occurrence of the character
                if(!frequencyTable.containsKey(element))
                {
                    frequencyTable.put(element, 1);
                }
                else
                {
                    //otherwise add one to the existing value
                    frequencyTable.put(element, frequencyTable.get(element) + 1);
                }
            }
        }
    
        //create an ArrayList of HuffmanNodes, sorted to act as a heap
        ArrayList<HuffmanNode> huffmanHeap = new ArrayList<>();
    
        for(Map.Entry<Character, Integer> entry : frequencyTable.entrySet())
        {
            huffmanHeap.add(new HuffmanNode(entry));
        }
        
        Collections.sort(huffmanHeap);
        
        return huffmanHeap;
    }
}
