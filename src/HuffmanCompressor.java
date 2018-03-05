import java.io.*;
import java.util.*;

public class HuffmanCompressor
{
    public static void main(String[] args)
    {
        String inputFile = args[0];
        String encodingFile = args[1];
        String outputFile = args[2];
        huffmanEncoder(inputFile, encodingFile, outputFile);
    }
    
    /**
     * Create a Huffman Encoded file
     *
     * @param inputFileName
     * @param encodingFileName
     * @param outputFilename
     * @return
     */
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
                    //prevent NullPointerException in the instance of a lossy encoding file
                    if(encodingTable.containsKey(c))
                    {
                        bufferedWriter.write(encodingTable.get(c));
                        compressedNumBits += encodingTable.get(c).length();
                        originalNumBits += 8;
                    }
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch(IOException e)
        {
            return "Input File Error";
        }
        
        double relativeSize = ((double) compressedNumBits * 100 / originalNumBits);
        return "Successful Output: " + relativeSize + "% of Original Size or " + (100 - relativeSize) + "% savings";
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
        //merge the nodes until only the root node is remaining
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
        //HashMap has amortized O(1) access and insertion - useful for frequency tables
        HashMap<Character, Integer> frequencyTable = new HashMap<>();
    
        //the file to be scanned
        File file = new File(filePath);
    
        //the file scanner
        Scanner scan = new Scanner(file);
        
        //create a frequency table of characters using a HashMap
        while(scan.hasNextLine())
        {
            //convert line to char[] for use with foreach
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
        //close scanner to prevent memory leak
        scan.close();
    
        //create an ArrayList of HuffmanNodes, will be sorted to act as a heap
        //beneficial due to quick element access
        ArrayList<HuffmanNode> huffmanHeap = new ArrayList<>();
    
        //using an alternate HuffmanNode constructor, insert the frequency entries into the huffmanHeap
        for(Map.Entry<Character, Integer> entry : frequencyTable.entrySet())
        {
            huffmanHeap.add(new HuffmanNode(entry));
        }
    
        //sort the ArrayList to turn into a heap
        Collections.sort(huffmanHeap);
        
        return huffmanHeap;
    }
}
