import javax.swing.*;
import java.awt.*;
import java.util.*;

//////////////////////////////////////////////////////////////////////////
//// A basic card viewer. Specially designed for this game. //////////////
//// Due to Java's sophisticated graphics this app may seem //////////////
//// to do many functions, but it only displays a property card //////////
//////////////////////////////////////////////////////////////////////////

// The "CardViewer" class.
public class CardViewer extends JDialog
{

    JLabel IdLabel = new JLabel ();
    JLabel TitleLabel = new JLabel ("Title");
    JLabel TypeLabel = new JLabel ("Type");
    JLabel PropLabel = new JLabel ("Cost of property");
    JLabel HouseLabel = new JLabel ("Cost of house");
    JLabel MortageLabel = new JLabel ("Mortage value");
    JPanel TitlePanel = new JPanel ();
    JPanel RentPanel = new JPanel ();
    JLabel RentLabels[] = new JLabel [6];
    JPanel InfoPanel = new JPanel ();

    JPanel CenterPanel = new JPanel ();
    JPanel BottomPanel = new JPanel ();

    Font LargeFont = new Font ("Arial", 1, 25);
    Font MedFont = new Font ("Arial", 1, 18);
    Font SmallFont = new Font ("Arial", 1, 17);
    GridLayout grid = new GridLayout (5, 1);
    BoxLayout RentGrid = new BoxLayout (RentPanel, BoxLayout.Y_AXIS);

    public static final int OWNABLE_PROP_ID[] = {1, 3, 5, 6, 8, 9,
	11, 12, 13, 14, 15, 16,
	18, 19, 21, 23, 24, 25,
	26, 27, 28, 29, 31, 32,
	34, 35, 37, 39};

    public static final int NON_OWNABLE_PROP_ID[] = {0, 2, 4, 7, 10, 17,
	20, 22, 30, 33, 36, 38};


    public static final int TAX_PROP_ID[] = {2, 38};


    CardViewer ()
    {
	initPropertyCard ();
    }


    public Container getCard (Property p[], int index)
    {
	showProperty (p, index);
	return getContentPane ();
    }


    public void viewCard (Property p[], int index)
    {
	if (index != -1)
	{
	    showProperty (p, index);
	}
    }


    public void initPropertyCard ()
    {
	getContentPane ().setLayout (new BorderLayout (0, 2));
	TitleLabel.setFont (LargeFont);
	TypeLabel.setFont (SmallFont);
	TitlePanel.add (TitleLabel);
	TitlePanel.add (TypeLabel);
	TitlePanel.setBorder (BorderFactory.createMatteBorder (
		    5, 5, 5, 5, Color.white));
	getContentPane ().add (TitlePanel, BorderLayout.NORTH);
	for (int x = 0 ; x < RentLabels.length ; x++)
	{
	    RentLabels [x] = new JLabel (" ");
	    RentLabels [x].setFont (MedFont);
	    RentPanel.add (RentLabels [x]);
	}
	RentPanel.setLayout (RentGrid);
	RentPanel.setBackground (Color.white);
	CenterPanel.setLayout (new FlowLayout ());
	CenterPanel.setBorder (BorderFactory.createEtchedBorder (1));
	CenterPanel.add (RentPanel);
	getContentPane ().add (CenterPanel, BorderLayout.CENTER);
	InfoPanel.setLayout (new GridLayout (3, 0));
	PropLabel.setFont (SmallFont);
	InfoPanel.add (PropLabel);
	HouseLabel.setFont (SmallFont);
	InfoPanel.add (HouseLabel);
	MortageLabel.setFont (SmallFont);
	InfoPanel.add (MortageLabel);
	BottomPanel.setLayout (new FlowLayout ());
	InfoPanel.setBackground (Color.white);
	BottomPanel.setBorder (BorderFactory.createEtchedBorder (1));

	BottomPanel.add (InfoPanel);
	getContentPane ().add (BottomPanel, BorderLayout.SOUTH);
	pack ();
	setBounds (70, 70, 280, 375);

	setResizable (false);
	setDefaultCloseOperation (1);
	CenterPanel.setBackground (Color.white);
	BottomPanel.setBackground (Color.white);



    }


    public void showProperty (Property[] p, int i)
    {
	hide ();

	setTitle ("Viewing Lot No: " + p [i].getId ());


	TitleLabel.setText (p [i].getTitle ());
	TypeLabel.setText (p [i].getType ());
	TitlePanel.setBackground (p [i].getRGBColor ());

	if (p [i].getHouseCost () != null)
	{
	    HouseLabel.setText ("Cost of houses  : R " + p [i].getHouseCost ());
	}
	else
	{
	    HouseLabel.setForeground (Color.gray);
	    HouseLabel.setText ("Cost of houses : N/a");
	}
	MortageLabel.setText ("Mortage value : R " + p [i].getMortageValue ());
	PropLabel.setText ("Cost of property : R " + p [i].getPropCost ());
	InfoPanel.updateUI ();
	if (p [i].getType ().equals ("Board"))
	{

	    String captions[] = {"If one 'Utility' is owned, <br> rent is 4 times amount <br> shown on dice(s) ",
		"If both 'Utilities' are owned,<br> rent is 10 times amount<br> shown on dice ",
		"", "", "", ""};



	    for (int x = 0 ; x < captions.length ; x++)
	    {
		String s = "<HTML>" + captions [x] + "</HTML>";
		RentLabels [x].setText (s);
		RentPanel.updateUI ();
	    }
	}
	else
	{
	    if (p [i].getType ().equals ("Station"))
	    {

		String captions[] = {"  Rent - Site Only : ",
		    "If 2 Stations <br> are  owned : ",
		    "In Case Of 3 <br> are  owned : ",
		    "In Case Of 4 <br> are  owned : ",
		    "", ""};

		for (int x = 0 ; x < captions.length ; x++)
		{
		    String k = "";
		    if (p [i].getTariff (x) == null)
		    {
			k = "";
		    }
		    else
		    {
			k = captions [x] + "R " + p [i].getTariff (x).getCost ();
		    }


		    String s = "<HTML>" + k + "</HTML>";
		    RentLabels [x].setText (s);
		    RentPanel.updateUI ();
		}
	    }
	    else
	    {
		HouseLabel.setForeground (Color.black);
		if (p [i].getType ().equals ("Street") |
			p [i].getType ().equals ("Avenue") |
			p [i].getType ().equals ("Road") |
			p [i].getType ().equals ("Square") |
			p [i].getType ().equals ("Parade"))
		{
		    String captions[] = {"Rent - Site Only : ",
			"With 1 House : ",
			"-   2 Houses : ",
			"-   3 Houses : ",
			"-   4 Houses : ",
			"-   Hotel : "};

		    for (int x = 0 ; x < captions.length ; x++)
		    {
			String s = "<HTML>" + captions [x] + "R " + p [i].getTariff (x).getCost () + "</HTML>";
			RentLabels [x].setText (s);
			RentPanel.updateUI ();
		    }
		}
	    }
	}
	show ();
    }


    public void initMiscCards (String text)
    {
	setTitle (text);

	TitleLabel.setFont (SmallFont);
	TypeLabel.setFont (SmallFont);
	TitlePanel.add (TitleLabel);
	TitlePanel.add (TypeLabel);
	TitlePanel.setBorder (BorderFactory.createMatteBorder (
		    5, 5, 5, 5, Color.white));
	getContentPane ().add (TitlePanel, BorderLayout.NORTH);

	pack ();
	setSize (280, 375);
	setLocationRelativeTo (null);
	setResizable (false);
	setDefaultCloseOperation (1);

    }


    public static void main (String[] args)
    {

	new CardViewer ();

    } // main method
} // CardViewer class

