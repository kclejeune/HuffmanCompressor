import java.io.*;
import java.util.*;

public class HuffmanCompressor
{
    public static void main(String[] args)
    {
        String inputFile = args[0];
        String encodingFile = args[1];
        String outputFile = args[2];
    
        huffmanEncoder(args[0], args[1], args[2]);
    }
    
    /**
     * Create a Huffman Encoded file based on an input file, an encoding file, and an output file
     *
     * @param inputFile    the filepath of the file to be compressed
     * @param encodingFile the filepath of the file for which huffman codes should be established
     * @param outputFile   the filepath of the file to which the encoded file will be written
     * @return the status of the file encoding
     */
    public static String huffmanEncoder(String inputFile, String encodingFile, String outputFile)
    {
        HashMap<Character, String> encodingTable;
        int originalNumBits = 0;
        int compressedNumBits = 0;
        try
        {
            encodingTable = makeTree(encodingFile).toEncodingTable();
        }
        catch(IOException e)
        {
            return "Encoding Error";
        }
        try
        {
            Scanner scan = new Scanner(new File(inputFile));
            FileWriter fileWriter = new FileWriter(outputFile);
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
        System.out.println((100 - relativeSize) + "% savings");
        System.out.println(relativeSize + "% of Original Size)");
    
        return "Successful Output";
    }
    
    public static String huffmanDecoder(String encodedFilePath, String encodingFile, String decodedFilePath)
    {
        try
        {
            HashMap<String, Character> decodingTable = new HashMap<>();
            makeTree(encodingFile).toEncodingTable().forEach((key, value) -> decodingTable.put(value, key));
            
            File encodedFile = new File(encodedFilePath);
            Scanner scan = new Scanner(encodedFile);
            FileWriter fileWriter = new FileWriter(decodedFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            while(scan.hasNextLine())
            {
                StringBuilder build = new StringBuilder();
                for(Character c : scan.nextLine().toCharArray())
                {
                    build.append(c);
                    if(decodingTable.containsKey(build.toString()))
                    {
                        bufferedWriter.write(decodingTable.get(build.toString()));
                        build = new StringBuilder();
                    }
                }
                bufferedWriter.newLine();
            }
            
            bufferedWriter.close();
            fileWriter.close();
            scan.close();
            System.out.println(decodedFilePath);
            return "File Successfully Decoded";
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return "Encoding File Error";
        }
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
     * Creates a HuffmanTree of HuffmanNodes given
     *
     * @param heap
     * @return
     */
    private static HuffmanTree makeTree(ArrayList<HuffmanNode> heap)
    {
        System.out.println(heap);
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
