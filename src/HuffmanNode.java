import java.util.Map;

public class HuffmanNode implements Comparable<HuffmanNode>
{
    private final Character inChar;
    private final Integer frequency;
    private HuffmanNode rightChild;
    private HuffmanNode leftChild;
    
    public HuffmanNode(Map.Entry<Character, Integer> entry)
    {
        inChar = entry.getKey();
        frequency = entry.getValue();
        rightChild = leftChild = null;
    }
    
    public Character getInChar()
    {
        return inChar;
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
    
    @Override
    public String toString()
    {
        return "[Character = " + getInChar() + ", " + "Frequency = " + getFrequency() + "]";
    }
}
