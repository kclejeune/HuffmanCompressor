import java.io.*;
import java.util.*;

public class FileParser
{
    File dictionary = new File("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanEncoder/src/Dictionary.txt");
    File gadsby = new File("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanEncoder/src/Gadsby.txt");
    
    /**
     * Parse relative character frequencies to a LinkedHashMap
     *
     * @param filePath the filepath of the file to be parsed
     * @return a Linked HashMap containing [Character, Frequency] pair
     * @throws IOException when the file does not exist
     */
    public static LinkedHashMap<Character, Integer> toFrequencyTable(String filePath) throws IOException
    {
        LinkedHashMap<Character, Integer> frequencyTable = new LinkedHashMap<>();
        File file = new File(filePath);
        
        Scanner scan = new Scanner(file);
        while(scan.hasNextLine())
        {
            char[] temp = scan.nextLine().toCharArray();
            for(char element : temp)
            {
                if(!frequencyTable.containsKey(element))
                {
                    frequencyTable.put(element, 1);
                }
                else
                {
                    frequencyTable.put(element, frequencyTable.get(element) + 1);
                }
            }
        }
        
        return frequencyTable;
    }
    
}
