public class HuffmanNode implements Comparable<Integer>
{
    private final Character inChar;
    private final Integer frequency;
    
    public HuffmanNode(Character inChar, Integer frequency)
    {
        this.inChar = inChar;
        this.frequency = frequency;
    }
    
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param value the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Integer value)
    {
        return frequency.compareTo(value);
    }
}
