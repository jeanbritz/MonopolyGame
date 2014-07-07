import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
/////////////////////////////////////////////////////////////
/// A basic JTextArea that appends any event in the game  ///
/// that a player can refer to later                      ///
/////////////////////////////////////////////////////////////

// The "GameLog" class.
public class GameLog extends JWindow
{

    GameLog ()
    {
	init ();
    }


    String prevLog = "";
    JTextArea log = new JTextArea ("-- [ Game has started ] --");
    JScrollPane scroll = new JScrollPane (log);
    JPanel TopPanel = new JPanel (new BorderLayout ());
    /// Basic initiation ///
    public void init ()
    {

	Border border = BorderFactory.createMatteBorder (
		5, 5, 5, 5, new Color (184, 0, 0));
	TitledBorder title = new TitledBorder (border, "Log", 1, 1, new Font ("Arial", 1, 15), Color.black);
	TopPanel.setBorder (title);
	TopPanel.add (scroll);
	log.setEditable (false);
	setBounds (660, 50, 340, 110);
	getContentPane ().add (TopPanel);
    }


    /// Basic function ///
    public void log (String msg)
    {

	if (!prevLog.equals (msg))
	{
	    log.append ("\n");
	    log.append (msg);
	}
	prevLog = msg;
	autoScroll ();
    }


    /// This is a method to enable the log to scroll down as the events are been logged ///
    public void autoScroll ()
    {
	log.setCaretPosition (log.getDocument ().getLength ());
    }


    public void clear ()
    {
	log.setText ("");
    }


    public static void main (String[] args)
    {
	new GameLog ();
    } // main method
} // GameLog class
