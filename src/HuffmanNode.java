import java.util.Map;

/**
 * a node of a Huffman Tree - all nodes contain a frequency, leaf nodes will contain a character
 */
public class HuffmanNode implements Comparable<HuffmanNode>
{
    private final Character inChar;
    private final Integer frequency;
    private final HuffmanNode rightChild;
    private final HuffmanNode leftChild;
    
    /**
     * Constructor to initialize leaf nodes from HashMap entries
     *
     * @param entry an entry from a HashMap to be parsed
     */
    public HuffmanNode(Map.Entry<Character, Integer> entry)
    {
        inChar = entry.getKey();
        frequency = entry.getValue();
        rightChild = leftChild = null;
    }
    
    /**
     * Constructor to initialize inner nodes - only leaf nodes contain character values, so inChar = null by default
     *
     * @param frequency  the frequency of the character in a given file
     * @param leftChild  the left child of this node
     * @param rightChild the right child of this node
     */
    public HuffmanNode(Integer frequency, HuffmanNode leftChild, HuffmanNode rightChild)
    {
        this.inChar = null;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
    
    /**
     * Determine whether the node has children
     *
     * @return whether the node is a leaf node
     */
    public boolean isLeafNode()
    {
        return getInChar() != null;
    }
    
    /**
     * Retrieve the node's right child
     *
     * @return the right child of this node
     */
    public HuffmanNode getRightChild()
    {
        return rightChild;
    }
    
    /**
     * Retrieve the node's left child
     *
     * @return the left child of this node
     */
    public HuffmanNode getLeftChild()
    {
        return leftChild;
    }
    
    /**
     * Return the character at this node or null if none exists
     *
     * @return the character held by this node
     */
    public Character getInChar()
    {
        return inChar;
    }
    
    /**
     * Merge two nodes into one - combines the frequencies of two nodes into a new node, where the two nodes
     * become the child nodes of the new root node
     *
     * @param left  the lesser frequency of the nodes to be merged
     * @param right the greater frequency of the nodes to be merged
     * @return the combined node
     */
    public static HuffmanNode mergeNodes(HuffmanNode left, HuffmanNode right)
    {
        //merge left and right node frequencies, merged nodes become children
        return new HuffmanNode((left.getFrequency() + right.getFrequency()), left, right);
    }
    
    /**
     * Retrieve the frequency of this node
     *
     * @return the character frequency
     */
    public Integer getFrequency()
    {
        return frequency;
    }
    
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param node the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(HuffmanNode node)
    {
        return frequency.compareTo(node.getFrequency());
    }
    
    /**
     * Generate a string representation of the node in the form [char, frequency]
     *
     * @return the string representation of this node
     */
    @Override
    public String toString()
    {
        return "[" + getInChar() + " = " + getFrequency() + "]";
    }
}
