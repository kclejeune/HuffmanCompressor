import java.io.*;
import java.util.*;

public class HuffmanCompressor
{
    public static void main(String[] args)
    {
        ArrayList<HuffmanNode> tree = new ArrayList<>();
    
        HuffmanNode root = toHuffmanTree(tree);
    
        HashMap<Character, String> encodingTree = new HashMap<>();
        toEncodingTable(encodingTree, root, "");
    
        System.out.println(encodingTree);
    }
    
    public static void toEncodingTable(HashMap<Character, String> encodingTable, HuffmanNode root, String encoding)
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
    
    /**
     * @param heap
     * @return
     */
    private static HuffmanNode toHuffmanTree(ArrayList<HuffmanNode> heap)
    {
        while(heap.size() > 1)
        {
            heap.add(0, HuffmanNode.mergeNodes(heap.remove(0), heap.remove(0)));
            Collections.sort(heap);
        }
    
        return heap.get(0);
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
