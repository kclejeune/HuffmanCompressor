import java.io.*;
import java.util.*;

public class FileParser
{
    File dictionary = new File("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanEncoder/src/Dictionary.txt");
    File gadsby = new File("/Users/kennanlejeune/Documents/IdeaProjects/HuffmanEncoder/src/Gadsby.txt");
    
    public static HashMap<Character, Integer> toFrequencyTable(String filePath) throws IOException
    {
        HashMap<Character, Integer> frequencyTable = new HashMap<>();
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
