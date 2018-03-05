import java.io.*;
import java.util.*;

public class HuffmanCompressor
{
    public static void main(String[] args)
    {
        try
        {
            HuffmanTree huffmanTree = makeTree(args[0]);
            System.out.println(huffmanTree.toEncodingTable());
        }
        catch(IOException err)
        {
            System.out.println(err.getMessage());
        }
    }
    
    private static HuffmanTree makeTree(String filePath) throws IOException
    {
        ArrayList<HuffmanNode> heap = toHuffmanHeap(filePath);
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
        HashMap<Character, Integer> frequencyTable = new HashMap<>();
        File file = new File(filePath);
        
        Scanner scan = new Scanner(file);
        
        //create a frequency table of characters using a HashMap
        while(scan.hasNextLine())
        {
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
        scan.close();
        
        //create an ArrayList of HuffmanNodes, sorted to act as a heap
        ArrayList<HuffmanNode> huffmanHeap = new ArrayList<>();
        
        for(Map.Entry<Character, Integer> entry : frequencyTable.entrySet())
        {
            huffmanHeap.add(new HuffmanNode(entry));
        }
        
        Collections.sort(huffmanHeap);
        
        return huffmanHeap;
    }
}
