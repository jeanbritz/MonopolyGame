import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

class GameDialog extends JDialog implements PropertyChangeListener

{

    JOptionPane pane;
    Container MainC = this.getContentPane ();
    Container MainPane = new Container ();
    int action = -1;

    GameDialog ()
    {

    }


    GameDialog (Container mainComp)
    {



	setTitle ("The Banker");
	MainC.setLayout (new BorderLayout (1, 1));

	setLocationRelativeTo (null);
	MainPane.setLayout (new FlowLayout ());
	MainPane.add (mainComp);
	MainC.add (MainPane, BorderLayout.CENTER);
	pane = new JOptionPane ("Would you buy this?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
	MainC.add (pane, BorderLayout.SOUTH);
	pane.addPropertyChangeListener (this);
	setSize (300, 400);
	setContentPane (MainC);
	setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
	addWindowListener (new WindowAdapter ()
	{
	    public void windowClosing (WindowEvent we)
	    {
		/*
		 * Instead of directly closing the window,
		 * we're going to change the JOptionPane's
		 * value property.
		 */
		pane.setValue (new Integer (
			    JOptionPane.CLOSED_OPTION));
	    }
	}
	);

	show ();
    }


    int getAction ()
    {
	return action;
    }


    public void propertyChange (PropertyChangeEvent e)
    {
	String prop = e.getPropertyName ();

	if (isVisible ()
		&& (e.getSource () == pane)
		&& (JOptionPane.VALUE_PROPERTY.equals (prop) ||
		    JOptionPane.INPUT_VALUE_PROPERTY.equals (prop)))
	{
	    Object value = pane.getValue ();

System.out.println(value);
	    if (value == JOptionPane.UNINITIALIZED_VALUE)
	    {
		//ignore reset
		return;
	    }


	    if (Integer.parseInt (value.toString ()) == 0)
	    {
	       action=0;
	    }
	    else
	    {

		dispose ();

	    }

	}
    }
}



