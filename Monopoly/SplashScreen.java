import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JWindow
{
    private int duration;
    public SplashScreen (int d)
    {
	duration = d;
    }


    // In die constructor word die tyd gestel oor hoe lank die Splash moet wys//

    public void showSplash ()
    {
	JPanel content = (JPanel) getContentPane ();
	content.setBackground (Color.white);

	//Stel die grense en posisie van die Panel wat center is//
	int width = 440;
	int height = 415;
	Dimension screen = Toolkit.getDefaultToolkit ().getScreenSize ();
	int x = (screen.width - width) / 2;
	int y = (screen.height - height) / 2;
	setBounds (x, y, width, height);

	// Hier is die Label waarop die logo gelaai word//
	JLabel label = new JLabel (new ImageIcon ("data\\logo.gif"));
	content.add (label, BorderLayout.CENTER);
	Color oraRed = new Color (156, 20, 20, 255);
	content.setBackground (oraRed);
	content.setBorder (BorderFactory.createRaisedBevelBorder ());

	//BorderFactory.createLineBorder (oraRed, 10));
	// Word vertoon
	setVisible (true);

	// Wag vir die die hoeveelheid van duration//
	try
	{
	    Thread.sleep (duration);
	}
	catch (Exception e)
	{
	}

	setVisible (false);
    }


    public void showSplashAndExit ()
    {
	showSplash ();
	dispose ();
    }


    public static void main (String[] args)
    {
	try
	{
	    UIManager.setLookAndFeel (new com.sun.java.swing.plaf.windows.WindowsLookAndFeel ());
	    SplashScreen splash = new SplashScreen (3000);
	    splash.showSplashAndExit ();


	}
	catch (Exception e)
	{
	    //showMsg ("LookAndFeel ERROR : " + e.getMessage (), "Monopoly - Error Message");
	}
	Main mc = new Main ();
    }
}


