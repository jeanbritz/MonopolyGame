import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// The "MainMenu" class.
public class MainMenu extends JWindow implements ActionListener
{
    JDialog dMain = new JDialog ();
    JButton btnNewGame = new JButton ("New Game");
    JButton btnQuitGame = new JButton ("Exit");
    JLabel lblLogo = new JLabel (new ImageIcon ("data\\logo.gif"));

    Font MyFont = new Font ("Arial", 1, 20);

    Container c = this.getContentPane ();
    MainMenu ()
    {
	showMenu ();
    }


    public void actionPerformed (ActionEvent e)
    {
	if (e.getSource () == btnQuitGame)
	{
	    System.exit (0);
	}
	if (e.getSource () == btnNewGame)
	{
	    dispose ();
	    new Main ();

	}
    }


    void showMenu ()
    {
	setFont (MyFont);
	c.setLayout (new FlowLayout ());
	c.add (lblLogo);
	c.add (btnNewGame);
	btnNewGame.setBackground (Color.red);
	btnNewGame.addActionListener (this);
	btnNewGame.setFont (MyFont);
	c.add (btnQuitGame);
	btnQuitGame.addActionListener (this);
	btnQuitGame.setBackground (Color.red);
	btnQuitGame.setFont (MyFont);
	setSize (450, 500);
	//setLocationRelativeTo (null);
	c.setBackground (Color.black);
	show ();
    }


    public static void main (String[] args)
    {
	MainMenu mc = new MainMenu ();
    } // main method
} // MainMenu class
