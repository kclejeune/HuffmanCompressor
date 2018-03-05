import java.util.Map;

public class HuffmanNode implements Comparable<HuffmanNode>
{
    private final Character inChar;
    
    /**
     * Special constructor to parse HuffmanNodes from LinkedHashMap entries
     *
     * @param entry an entry from a HashMap to be parsed
     */
    public HuffmanNode(Map.Entry<Character, Integer> entry)
    {
        inChar = entry.getKey();
        frequency = entry.getValue();
        rightChild = leftChild = null;
    }
    
    public HuffmanNode(Character inChar, Integer frequency, HuffmanNode leftChild, HuffmanNode rightChild)
    {
        this.inChar = inChar;
        this.frequency = frequency;
        this.leftChild = leftChild;
        
        this.rightChild = rightChild;
    }
    
    private final Integer frequency;
    private final HuffmanNode rightChild;
    private final HuffmanNode leftChild;
    
    public boolean isLeafNode()
    {
        return getInChar() != null;
    }
    
    public HuffmanNode getRightChild()
    {
        return rightChild;
    }
    
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
    
    public static HuffmanNode mergeNodes(HuffmanNode left, HuffmanNode right)
    {
        return new HuffmanNode(null, (left.getFrequency() + right.getFrequency()), left, right);
    }
    
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
    
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof HuffmanNode &&
                ((HuffmanNode) obj).getFrequency().equals(getFrequency()) && ((HuffmanNode) obj).getInChar().equals
                (getInChar());
    }
}
