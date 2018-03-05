import java.util.HashMap;

public class HuffmanTree
{
    private final HuffmanNode root;
    
    public HuffmanTree(HuffmanNode root)
    {
        this.root = root;
    }
    
    public HuffmanNode getRoot()
    {
        return root;
    }
    
    public HashMap<Character, String> toEncodingTable()
    {
        HashMap<Character, String> encodingTable = new HashMap<>();
        
        toEncodingTable(encodingTable, getRoot(), "");
        
        return encodingTable;
    }
    
    private void toEncodingTable(HashMap<Character, String> encodingTable, HuffmanNode root, String encoding)
    {
        if(root == null)
        {
            return;
        }
        if(root.isLeafNode())
        {
            encodingTable.put(root.getInChar(), encoding);
        }
        else
        {
            toEncodingTable(encodingTable, root.getRightChild(), encoding + "1");
            
            toEncodingTable(encodingTable, root.getLeftChild(), encoding + "0");
        }
    }
}
