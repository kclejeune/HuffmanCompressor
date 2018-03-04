import java.io.IOException;
import java.util.LinkedHashMap;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            LinkedHashMap<Character, Integer> map = FileParser.toFrequencyTable
                    ("/Users/kennanlejeune/Documents/IdeaProjects" +
                            "/HuffmanCompressor/src/Gadsby.txt");
            
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
