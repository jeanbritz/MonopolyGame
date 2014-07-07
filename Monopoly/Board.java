import java.awt.*;
import javax.swing.*;
public class Board  
{
    private String imgPath = "data\\board.jpg";
    private Image Background;
    private final int HEIGHT = 550;
    private final int WIDTH = 560;

    Board ()
    {
	loadBackground ();
    }


    public void loadBackground ()
    {
	Image img = Toolkit.getDefaultToolkit ().getImage (imgPath);
	Background = img;

    }


    Image getBoard ()
    {
  
	return Background;
    }
    int getBWidth()
    {
    return WIDTH;
    }
    int getBHeight()
    {
    return HEIGHT;
    }
}


