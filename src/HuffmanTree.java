import java.io.*;
import java.util.*;
//TODO - Method commenting

/**
 * A special case of a HuffmanNode when the Node the root of a Huffman tree
 */
public class HuffmanTree
{
    //the root of the HuffmanTree
    private final HuffmanNode root;
    
    private final HashMap<Character, String> encodingTable;
    private final HashMap<String, Character> decodingTable;
    
    private ArrayList<String> encodingList;
    
    /**
     * Create a HuffmanTree based on the tree root
     *
     * @param root the root of the tree
     */
    public HuffmanTree(HuffmanNode root)
    {
        this.root = root;
        encodingList = new ArrayList<>();
        encodingTable = toEncodingTable();
        decodingTable = toDecodingTable();
    }
    
    /**
     * Retrieve the encoding table for a given instance of a Huffman Tree
     *
     * @return the encoding values for each leaf node in the Huffman Tree
     */
    public HashMap<Character, String> getEncodingTable()
    {
        return encodingTable;
    }
    
    /**
     * Retrieve the decoding table for a given instance of a Huffman Tree
     *
     * @return the decoding values for each leaf node in the Huffman Tree
     */
    public HashMap<String, Character> getDecodingTable()
    {
        return decodingTable;
    }
    
    /**
     * Retrieve the root of the HuffmanTree
     *
     * @return the root of the tree
     */
    public HuffmanNode getRoot()
    {
        return root;
    }
    
    /**
     * Wrapper method for toEncodingTable(HashMap, HuffmanNode, String)
     *
     * @return the encoding table represented by the instance of a Huffman Tree
     */
    public HashMap<Character, String> toEncodingTable()
    {
        //create encoding table using HashMap for O(1) access and insertion
        HashMap<Character, String> encodingTable = new HashMap<>();
        
        toEncodingTable(encodingTable, getRoot(), "");
        
        return encodingTable;
    }
    
    /**
     * Create a formatted file of the leaf node character encodings
     *
     * @param filePath the path to write the file
     * @return true unless the encoding fails
     */
    public boolean encodingTableToFile(String filePath)
    {
        try
        {
            File file = new File(filePath);
            if(!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(int i = 0; i < encodingList.size(); i++)
            {
                bufferedWriter.write(encodingList.get(i));
            }
            bufferedWriter.close();
            fileWriter.close();
            
            return true;
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * Recursive method to fill the Character keys in a HashMap with encoding strings
     *
     * @param encodingTable the table containing characters mapped to their encodings
     * @param root          the root of the subtree being encoded
     * @param encoding      the calculated huffman representation of the character
     */
    private void toEncodingTable(HashMap<Character, String> encodingTable, HuffmanNode root, String encoding)
    {
        //base case - root is no longer in tree
        if(root == null)
        {
            return;
        }
        //leaf nodes hold characters
        if(root.isLeafNode())
        {
            //map the character to its respective encoding
            encodingTable.put(root.getInChar(), encoding);
    
            encodingList.add(String.format("%s : %d : %s%n", root.getInChar(), root.getFrequency(), encoding));
        }
        else
        {
            //encoding string adds a 0 if the child is along the left branch
            toEncodingTable(encodingTable, root.getLeftChild(), encoding + "0");
    
            //encoding string adds a 1 if the child is along the right branch
            toEncodingTable(encodingTable, root.getRightChild(), encoding + "1");
        }
    }
    
    /**
     * Compute a decoding table given the encoding table of a Huffman Tree
     *
     * @return the table used to decode a Huffman Encoded file
     */
    private HashMap<String, Character> toDecodingTable()
    {
        HashMap<String, Character> decodingTable = new HashMap<>();
        
        //invert encodingTable values
        getEncodingTable().forEach((key, value) -> decodingTable.put(value, key));
        
        //return the inverted map
        return decodingTable;
    }
}
