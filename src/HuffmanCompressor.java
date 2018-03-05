import java.io.*;
import java.util.*;

public class HuffmanCompressor
{
    public static void main(String[] args)
    {
        String gadsby = "/Users/kennanlejeune/Documents/IdeaProjects/HuffmanCompressor/src/Gadsby.txt";
        String dictionary = "/Users/kennanlejeune/Documents/IdeaProjects/HuffmanCompressor/src/Dictionary_rev.txt";
    
        System.out.println("Gadsby compressed w/ Gadsby");
        System.out.println(huffmanEncoder(gadsby, gadsby, "/Users/kennanlejeune/Documents/IdeaProjects" +
                "/HuffmanCompressor/src/GadsbySelfCompressed.txt"));
    
        System.out.println("Gadsby compressed w/ Dictionary");
        System.out.println(huffmanEncoder(gadsby, dictionary, "/Users/kennanlejeune/Documents/IdeaProjects" +
                "/HuffmanCompressor/src/GadsbyDictionaryCompressed.txt"));
    }
    
    public static String huffmanEncoder(String inputFileName, String encodingFileName, String outputFilename)
    {
        HashMap<Character, String> encodingTable;
        int originalNumBits = 0;
        int compressedNumBits = 0;
        try
        {
            encodingTable = makeTree(encodingFileName).toEncodingTable();
        }
        catch(IOException e)
        {
            return "Encoding Error";
        }
        try
        {
            Scanner scan = new Scanner(new File(inputFileName));
            FileWriter fileWriter = new FileWriter(outputFilename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            while(scan.hasNextLine())
            {
                String nextLine = scan.nextLine();
                for(Character c : nextLine.toCharArray())
                {
                    
                    bufferedWriter.write(encodingTable.get(c));
                    originalNumBits += 8;
                    compressedNumBits += encodingTable.get(c).length();
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch(IOException e)
        {
            return "Input File Error";
        }
        
        return "Successful Output: " + ((double) (originalNumBits - compressedNumBits) * 100 / originalNumBits) + "% " +
                "savings";
    }
    
    /**
     * Form a HuffmanTree based on respective character frequencies
     *
     * @param filePath the file to be queried
     * @return the Huffman Tree which represents characters in the file at the input file path
     * @throws IOException if the file does not exist
     */
    public static HuffmanTree makeTree(String filePath) throws IOException
    {
        return makeTree(toHuffmanHeap(filePath));
    }
    
    /**
     * @param heap
     * @return
     * @throws IOException
     */
    private static HuffmanTree makeTree(ArrayList<HuffmanNode> heap)
    {
        while(heap.size() > 1)
        {
            heap.add(0, HuffmanNode.mergeNodes(heap.remove(0), heap.remove(0)));
            Collections.sort(heap);
        }
        
        return new HuffmanTree(heap.get(0));
    }
    
    /**
     * Parses a given input file to a sorted ArrayList of HuffmanNodes
     *
     * @param filePath the file to be converted to Huffman frequencies
     * @return the ArrayList of HuffmanNodes containing Characters and their respective frequencies in the file
     * @throws IOException if the file is nonexistent
     */
    private static ArrayList<HuffmanNode> toHuffmanHeap(String filePath) throws IOException
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
        scan.close();
        
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
