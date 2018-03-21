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
    
        //get the encoding table or return an encoding error
        try
        {
            encodingTable = makeTree(encodingFile).getEncodingTable();
        }
        catch(IOException e)
        {
            return "Encoding Error";
        }
    
        //write to the encoding file or return input file error
        try
        {
            FileReader fileReader = new FileReader(inputFile);
            FileWriter fileWriter = new FileWriter(outputFile);
            int charValue = fileReader.read();
    
            //repeat until the end of the input file stream
            while(charValue != -1)
            {
                //prevent NullPointerException in the instance of a lossy encoding file
                //will not affect lossless encoded files
                if(encodingTable.containsKey((char) charValue))
                {
                    //write the character's corresponding Huffman Code in place of the character
                    fileWriter.write(encodingTable.get((char) charValue));
            
                    //num bits of each compressed character value
                    compressedNumBits += encodingTable.get((char) charValue).length();
            
                    //original characters all 8 bits
                    originalNumBits += 8;
                }
        
                charValue = fileReader.read();
            }
    
            fileReader.close();
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
     * Lossless file decoding will result in equivalent md5 file hashes
     *
     * @param encodedFilePath the path of the file to be decoded
     * @param encodingFile    the path of the file used for encoding
     * @param decodedFilePath the path of the file after decoding
     * @return the status of the file decoding
     */
    public static String huffmanDecoder(String encodedFilePath, String encodingFile, String decodedFilePath)
    {
    
        //decode the file to the decodedFilePath or return an encoding file error
        try
        {
            HashMap<String, Character> decodingTable = makeTree(encodingFile).getDecodingTable();
    
            FileReader fileReader = new FileReader(encodedFilePath);
            StringBuilder build = new StringBuilder();
            FileWriter fileWriter = new FileWriter(decodedFilePath);
            int charValue = fileReader.read();
            
            //traverse the entire file
            while(charValue != -1)
            {
                //append values until the prefix matches a valid encoding
                build.append((char) charValue);
        
                //once the prefix matches
                if(decodingTable.containsKey(build.toString()))
                {
                    //write the decoded value to the output file
                    fileWriter.write(decodingTable.get(build.toString()));
            
                    //clear the StringBuilder for the next iteration
                    build.setLength(0);
                }
        
                //get next character in the stream
                charValue = fileReader.read();
            }
    
            fileReader.close();
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
        return makeTree(toHuffmanHeap(new FileReader(filePath)));
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
    
            //sort after each iteration to ensure the 0th and 1st nodes are always the left and right children
            Collections.sort(heap);
        }
        
        return new HuffmanTree(heap.get(0));
    }
    
    /**
     * Parses a given input file to a sorted ArrayList of HuffmanNodes
     *
     * @param reader the InputStream to be converted to Huffman frequencies
     * @return the ArrayList of HuffmanNodes containing Characters and their respective frequencies in the file
     * @throws IOException if the file is nonexistent
     */
    private static ArrayList<HuffmanNode> toHuffmanHeap(Reader reader) throws IOException
    {
        //HashMap has amortized O(1) access and insertion - useful for frequency tables
        HashMap<Character, Integer> frequencyTable = new HashMap<>();
    
        int charValue = reader.read();
        //create a frequency table of characters using a HashMap
        while(charValue != -1)
        {
            //add 1 to the key if this is the first occurrence of the character
            if(!frequencyTable.containsKey((char) charValue))
            {
                frequencyTable.put((char) charValue, 1);
            }
            else
            {
                //otherwise add one to the existing value
                frequencyTable.put((char) charValue, frequencyTable.get((char) charValue) + 1);
            }
        
            charValue = reader.read();
        }
        
        //close scanner to prevent memory leak
        reader.close();
        
        //create an ArrayList of HuffmanNodes, will be sorted to act as a heap
        //beneficial due to quick element access and minimal resizing
        ArrayList<HuffmanNode> huffmanHeap = new ArrayList<>();
    
        //using an alternate HuffmanNode constructor, insert the frequency entries into the huffmanHeap
        for(
                Map.Entry<Character, Integer> entry : frequencyTable.entrySet())
        
        {
            huffmanHeap.add(new HuffmanNode(entry));
        }
    
        //sort the ArrayList to turn into a heap
        Collections.sort(huffmanHeap);
        
        return huffmanHeap;
    }
}
