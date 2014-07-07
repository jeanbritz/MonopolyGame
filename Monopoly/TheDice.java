import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
///////////////////////////////////////////////////////////////
/// This app generates two random Integers between 1 and 6 ////
/// Because of the random integers it acts as a dice //////////
/// I have also created images to make the dice more visual ///
///////////////////////////////////////////////////////////////
// The "TheDice" class.
public class TheDice extends JWindow
{
    public static final String FOLDER = "data\\dice\\";
    JLabel FirstDice = new JLabel ();
    JLabel SecondDice = new JLabel ();
    JPanel TopPanel = new JPanel (new BorderLayout ());
    JPanel DicePanel = new JPanel (new FlowLayout ());
    JLabel CounterLabel = new JLabel ("X");

    Font CounterFont = new Font ("Arial", 1, 50);

    int total = 0;
    int first;
    int second;

    boolean hasBeenThrown = false;
    boolean isDouble = false;
    TheDice ()
    {
	init ();

    }


    public void init ()
    {

	CounterLabel.setFont (CounterFont);
	CounterLabel.setHorizontalAlignment (JLabel.CENTER);
	setBounds (660, 485, 340, 165);
	getContentPane ().setLayout (new BorderLayout ());
	DicePanel.add (FirstDice);
	DicePanel.add (SecondDice);
	TopPanel.add (DicePanel, BorderLayout.CENTER);
	TopPanel.add (CounterLabel, BorderLayout.SOUTH);
	Border border = BorderFactory.createMatteBorder (
		5, 5, 5, 5, new Color (184, 0, 0));
	TitledBorder title = new TitledBorder (border, "The Dices", 1, 1, new Font ("Arial", 1, 15), Color.black);
	TopPanel.setBorder (title);
	getContentPane ().add (TopPanel, BorderLayout.CENTER);

    }

/// Basic function ///
    public void rollDice ()
    {
	reset ();

	first = (int) (Math.random () * 6) + 1;
	second = (int) (Math.random () * 6) + 1;

	ImageIcon i1 = new ImageIcon (FOLDER + first + ".jpg");
	ImageIcon i2 = new ImageIcon (FOLDER + second + ".jpg");
	FirstDice.setIcon (i1);
	SecondDice.setIcon (i2);
	TopPanel.updateUI ();
	total = first + second;
	CounterLabel.setText ("" + total);
	TopPanel.updateUI ();
	hasBeenThrown = true;
	/// If the first dice and second dice is equal to eachother ///
	/// then it is a double ///
	if (first == second)
	{
	    isDouble = true;
	 
	    CounterLabel.setText ("" + total + " - double");
	}


    }

/// For future use ///
/// e.g. creating new cards that commands the player to move backwards
/// to the total of one dice /////////////////////////////////////////
    public void rollOneDice ()
    {
	reset ();
	first = (int) (Math.random () * 6) + 1;
	ImageIcon i = new ImageIcon (FOLDER + first + ".jpg");
	FirstDice.setIcon (i);
	total = first;
	this.add (FirstDice);
	CounterLabel.setText ("" + total);
	hasBeenThrown = true;

    }


    void reset ()
    {
       
	hasBeenThrown = false;
	isDouble = false;

    }

/// A boolean that helps to determine if the dice has already been thrown
/// during the current turn ///
    boolean isAlreadyThrown ()
    {
	return hasBeenThrown;
    }


    boolean hasDouble ()
    {
	return isDouble;
    }


    int getTotal ()
    {
	return total;
    }


    public static void main (String[] args)
    {
	new TheDice ();

    } // main method
} // TheDice class
