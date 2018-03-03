import java.io.IOException;
import java.util.HashMap;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            HashMap<Character, Integer> map = FileParser.toFrequencyTable
                    ("/Users/kennanlejeune/Documents/IdeaProjects" +
                            "/HuffmanCompressor/src/Gadsby.txt");
            System.out.println(map);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
