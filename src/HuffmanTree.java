import java.util.HashMap;

/**
 * A special case of a HuffmanNode when the Node is at the root of the tree
 */
public class HuffmanTree
{
    private final HuffmanNode root;
    
    /**
     * Create a HuffmanTree based on the tree root
     *
     * @param root
     */
    public HuffmanTree(HuffmanNode root)
    {
        this.root = root;
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
        HashMap<Character, String> encodingTable = new HashMap<>();
        
        toEncodingTable(encodingTable, getRoot(), "");
        
        return encodingTable;
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
        }
        else
        {
            //encoding string adds a 0 if the child is along the left branch
            toEncodingTable(encodingTable, root.getLeftChild(), encoding + "0");
    
            //encoding string adds a 1 if the child is along the right branch
            toEncodingTable(encodingTable, root.getRightChild(), encoding + "1");
        }
    }
}
