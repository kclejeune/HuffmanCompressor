import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            ArrayList<HuffmanNode> nodes = FileParser.toHuffmanHeap("/Users/kennanlejeune/Documents/IdeaProjects" +
                    "/HuffmanCompressor/src/Gadsby.txt");
            System.out.println(nodes);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
