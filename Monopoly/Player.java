import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
class Player
{
    private int Id;
    private String Nick;
    private Point Pos;
    private int AmountOfCash;
    private ImageIcon Token;
    private Property AtProperty;
    private Property OwnProperty[] = new Property [27];
    private int GetOutOfJailFree;
    private boolean InJail;
    private boolean Bankrupt;
    private boolean Busy = false;

    private TheDice dice;
    private PropertyManager manager;
    private GameLog log;

    boolean gotMoneyAtBegin = false;
    public final int BAIL = 50;
    int PropertyCount = 0;

    Player ()
    {

    }


    Player (int i, String n, int aoc, ImageIcon t, Property a)
    {

	Id = i;
	Nick = n;
	AmountOfCash = aoc;
	Token = t;
	AtProperty = a;




    }


    boolean stillBusy ()
    {
	return Busy;
    }


    void setDice (TheDice d)
    {
	dice = d;
    }


    TheDice getDice ()
    {
	return dice;
    }


    void setPropManager (PropertyManager pm)
    {
	manager = pm;
    }


    PropertyManager getPropManager ()
    {
	return manager;
    }


    void setLog (GameLog l)
    {
	log = l;
    }


    GameLog getLog ()
    {
	return log;
    }


    int getId ()
    {
	return Id;
    }


    void setId (int i)
    {
	Id = i;
    }


    String getNick ()
    {
	return Nick;
    }


    Point getPos ()
    {
	return Pos;
    }


    void setPos (Point p)
    {
	Pos = p;
    }


    int getPosX ()
    {
	int tmp = (int) Pos.getX ();
	return tmp;
    }


    int getPosY ()
    {
	int tmp = (int) Pos.getY ();
	return tmp;
    }


    int getAmountOfCash ()
    {
	return AmountOfCash;
    }


    void setAmountOfCash (int a)
    {
	AmountOfCash = a;
    }


    ImageIcon getToken ()
    {
	return Token;
    }


    Image getTokenImage ()
    {
	Image tmp = Token.getImage ();
	return tmp;
    }


    void setToken (ImageIcon i)
    {
	Token = i;
    }


    void addGetOutJailCard ()
    {
	GetOutOfJailFree++;
    }


    int getOutOfJailFree ()
    {
	return GetOutOfJailFree;
    }


    boolean getInJail ()
    {
	return InJail;

    }


    void setInJail (boolean b)
    {
	InJail = b;

    }





    public void startTurn ()
    {
      
	if (getInJail ())
	{

	    offerBail ();

	}
	else
	{

	}
	getDice ().reset ();
	String s = "<HTML>" + getOutOfJailFree () + " 'Get Free Out Of Jail' card(s) in hand</HTML>";
	Main.JailLabel.setText (s);
	Busy = true;
    }


    public void endTurn ()
    {


	if (!getDice ().isAlreadyThrown () && !getInJail ())
	{
	    Main.showMsg ("You still have to throw the dice", "The Banker");
	}
	else
	{
	    Busy = false;
	    getDice ().reset ();


	}
	gotMoneyAtBegin = false;
    }


    public void move (String spaces)
    {
	int after;
	if (spaces.equals ("Auto"))
	{
	    after = getAtProperty ().getId () + getDice ().getTotal ();

	}
	else
	{
	    after = getAtProperty ().getId () + Integer.parseInt (spaces);
	}

	if (after > 39)
	{
	    after = 0;
	   

	}
	setAtProperty (getPropManager ().TheProperties [after]);
	if (getAtProperty () == getPropManager ().getPropAt (0))
	{
	    setAmountOfCash (getAmountOfCash () + 200);
	}
    }


    public void offerBail ()
    {
	String[] choices = {"Pay 50 bucks", "Use Card", "Cancel"};
	boolean Response = false;
	while (!Response)
	{
	    int response = JOptionPane.showOptionDialog (
		    null
		    , "You have a choice below : " + "\n" +
		    "(if you want to throw the dices just click Cancel)"   // Message
		    , "Monopoly - Jail"                         // Title
		    , JOptionPane.YES_NO_OPTION                 // Option type
		    , JOptionPane.PLAIN_MESSAGE                 // Message type
		    , null                                      // Icon (none)
		    , choices                                   // Button text as above
		    , choices [0]                          // Default option
		    );

	    if (response == 0)
	    {
		setInJail (false);
		AmountOfCash = AmountOfCash - BAIL; // Paid your bail
		getLog ().log (getNick () + " has bailed it out by paying R" + BAIL);
		Response = true;

	    }
	    if (response == 1)
	    {
		if (GetOutOfJailFree != 0)
		{
		    GetOutOfJailFree--; // Used a get out of jail card
		    setInJail (false);
		    Response = true;
		    getLog ().log (getNick () + " has bailed it out by using a card");
		}
		else
		{
		    Main.showMsg ("You do not have any cards to use", "Jail");
		    Response = false;
		}

	    }
	    if (response == 2)
	    { //  Stay, but can still throw the dices//
		getLog ().log (getNick () + " has rejected the bail");
		Response = true;
	    }

	}

    }



    void setAtProperty (Property a)
    {
	AtProperty = a;
	setPos (AtProperty.getLocation ());
    }


    Property getAtProperty ()
    {
	return AtProperty;
    }




    void buyProperty (Property a)
    {

	OwnProperty [PropertyCount] = new Property ();
	OwnProperty [PropertyCount] = a;
	PropertyCount++;
	getPropManager ().getPropAt (a.getId ()).setOwnedProperty (true, Id);
	int cost = Integer.parseInt (a.getPropCost ());
	setAmountOfCash (getAmountOfCash () - cost);
    }


    void sellProperty (int index)
    {
	OwnProperty [index] = null;
	PropertyCount--;
    }


    Property[] getOwnProperties ()
    {
	return OwnProperty;
    }


    int getPropertyCount ()
    {
	return PropertyCount;
    }


    boolean getBankrupt ()
    {
	return Bankrupt;
    }


    void setBankrupt (boolean b)
    {
	Bankrupt = b;
    }


    public static void showMsg (String msg)
    {
	JOptionPane.showMessageDialog (null, msg);
    }


    public String toString ()
    {
	return getNick ();
    }
}


