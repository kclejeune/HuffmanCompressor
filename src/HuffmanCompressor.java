import java.io.*;
import java.util.*;

public class HuffmanCompressor
{
    public static void main(String[] args)
    {
        String inputFile = args[0];
        String encodingFile = args[1];
        String outputFile = args[2];
        
        /*
        //encoding files generated with the following command:
        try
        {
            makeTree("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanCompressor/src/Gadsby" +
                    ".txt").encodingTableToFile
                    ("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanCompressor/src" +
                            "/GadsbyEncoding.txt");
            makeTree("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanCompressor/src/Dictionary_rev" +
                    ".txt").encodingTableToFile
                    ("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanCompressor/src" +
                            "/DictionaryEncoding.txt");
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        */
    
        System.out.println(huffmanEncoder(inputFile, encodingFile, outputFile));
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
            encodingTable = makeTree(encodingFile).getEncodingTable();
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
                    //will not affect lossless encoded files
                    if(encodingTable.containsKey(c))
                    {
                        //write the character's corresponding Huffman Code in place of the character
                        bufferedWriter.write(encodingTable.get(c));
    
                        //num bits of each compressed character value
                        compressedNumBits += encodingTable.get(c).length();
    
                        //original characters all 8 bits
                        originalNumBits += 8;
                    }
                }
                bufferedWriter.newLine();
            }
            scan.close();
            bufferedWriter.close();
            fileWriter.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return "Input File Error";
        }
        
        double relativeSize = ((double) compressedNumBits * 100 / originalNumBits);
        System.out.println((100 - relativeSize) + "% savings");
        System.out.println(relativeSize + "% of Original Size");
        
        return "Successful Output";
    }
    
    /**
     * Decodes a Huffman Compressed file to a user specified output path using the compressed file and the encoded file
     *
     * @param encodedFilePath the path of the file to be decoded
     * @param encodingFile    the path of the file used for encoding
     * @param decodedFilePath the path of the file after decoding
     * @return the status of the file decoding
     */
    public static String huffmanDecoder(String encodedFilePath, String encodingFile, String decodedFilePath)
    {
        try
        {
            HashMap<String, Character> decodingTable = makeTree(encodingFile).getDecodingTable();
            
            File encodedFile = new File(encodedFilePath);
            Scanner scan = new Scanner(encodedFile);
            FileWriter fileWriter = new FileWriter(decodedFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    
            //traverse the entire file
            while(scan.hasNextLine())
            {
                StringBuilder build = new StringBuilder();
                for(Character c : scan.nextLine().toCharArray())
                {
                    //append values until the prefix matches a valid encoding
                    build.append(c);
    
                    //once the prefix matches
                    if(decodingTable.containsKey(build.toString()))
                    {
                        //write the decoded value to the output file
                        bufferedWriter.write(decodingTable.get(build.toString()));
    
                        //clear the StringBuilder for the next iteration
                        build.setLength(0);
                    }
                }
                bufferedWriter.newLine();
            }
            scan.close();
            bufferedWriter.close();
            fileWriter.close();
            return "File Successfully Decoded";
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return "Encoding File Error";
        }
    }
    
    /**
     * Form a HuffmanTree based on respective character frequencies in the input file
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
     * Creates a HuffmanTree of HuffmanNodes given a sorted list of HuffmanNodes
     *
     * @param heap the array to be converted to a tree
     * @return the HuffmanTree used to represent the character codes
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
            char[] line = scan.nextLine().toCharArray();
    
            for(char element : line)
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
