import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;


// The "Main" class.
public class Main extends JFrame implements ActionListener, Runnable
{ /// OPTIONS ///

    public static final int MAX_PLAYERS = 6;
    public static final int PAY_AMOUNT = 200;
    public static final int GROUP_CNT = 10;
    public static final int CARD_CHANCE_CNT = 16;
    public static final int CARD_CHEST_CNT = 16;
    public static final int MAX_OWNABLE_PROPERTIES = 28;
    public static final int MAX_DOUBLE_PEN = 3;
    public static final int NEGATIVE_BALANCE_ALLOWANCE = -1000;

    /// Components declared ///
    InsertPlayers ip;
    static LoadObjects lo;
    Board board = new Board ();
    PropertyManager pm;
    CardViewer cv = new CardViewer ();
    TheDice dice = new TheDice ();
    GameLog gl = new GameLog ();
    GameDialog gd;

    public final ImageIcon THE_ICON = new ImageIcon ("data\\icons\\info.gif");
    public final ImageIcon QUIT_ICON = new ImageIcon ("data\\exit.gif");
    public final ImageIcon LOGO = new ImageIcon ("data\\mini_logo.gif");

    public static final int COM_CHEST_PROP_ID [] = {2, 17, 33};
    public static final int CHANCE_PROP_ID [] = {7, 22, 36};

    public final Image BOARD = board.getBoard ();


    JPanel BoardPanel = new JPanel (true);
    JDialog RentDialog = new JDialog (this);


    /// Declare Arrays ///
    Player ThePlayers [] = new Player [MAX_PLAYERS];
    int Player_Turn_Id = 0;
    Player CurrentPlayer = new Player ();
    int end = 0;
    /// Property beacons ///

    public static Property BEGIN;
    public Property JAIL;
    public Property FREE_PARKING;
    public Property GOTO_JAIL;
    public Property SUPER_TAX;
    public Property INCOME_TAX;


    /// Some booleans to create order ///
    boolean isFirstRound = true;
    boolean draw = false;
    boolean isRepainted = false;
    boolean hasRolledDice = false;
    boolean justWentToJail = false;
    /// PlayerPanel Components ///
    JWindow PlayerWindow = new JWindow ();
    JPanel PlayerTopPanel = new JPanel (new BorderLayout (1, 1));
    JPanel PlayerButtonPanel = new JPanel (new FlowLayout ());
    JPanel PlayerInfoPanel = new JPanel (new FlowLayout ());
    JPanel PlayerMenuPanel = new JPanel (new FlowLayout ());
    JPanel PlayerTokenPanel = new JPanel (new FlowLayout ());

    JLabel TokenLabel = new JLabel ("");
    static JLabel MoneyLabel = new JLabel ("N/A");
    static JLabel JailLabel = new JLabel ("N/A");

    static TitledBorder PlayerTitleBorder = new TitledBorder ("Pending");

    JButton BuyButton = new JButton ("Make Offer");
    JButton RollButton = new JButton ("Roll the dice");
    JButton ViewPropButton = new JButton ("View properties");
    JButton EndTurnButton = new JButton ("End turn");
    JButton CheckRentButton = new JButton ("Check if somebody owes me rent");
    JButton ConfirmRentButton = new JButton ("Confirm");
    JButton CancelRentButton = new JButton ("Cancel");

    /// QuitPanel Components ///
    JWindow QuitWindow = new JWindow ();

    JButton QuitButton = new JButton (QUIT_ICON);
    JButton MinimizeButton = new JButton ("Minimize");

    /// Custom Fonts ///
    Font PlayerFont = new Font ("Arial", 0, 20);
    Font MoneyFont = new Font ("Arial", 0, 20);

    Thread t = new Thread ();

    int doublePenalty = 0;
    int CCcount = 0;
    int Ccount = 0;
    boolean gameJustStarted = true;
    JCheckBox RentProp [] = new JCheckBox [MAX_PLAYERS];

    Property RentOweProps [] = new Property [6];


    Main ()


    {
	RentProp [0] = new JCheckBox ();
	RentProp [1] = new JCheckBox ();
	RentProp [2] = new JCheckBox ();
	RentProp [3] = new JCheckBox ();
	RentProp [4] = new JCheckBox ();
	RentProp [5] = new JCheckBox ();


	initGame ();

	initBoardPanel ();
	initPlayerPanel ();
	initQuitPanel ();
	run ();





    }


    /*
	public void windowStateChanged (WindowEvent e)
	{
	    int state = e.getNewState ();
	    if (state != 0)
	    {
		if (state == 1)
		{

		    dice.hide ();
		    pm.hide ();
		    PlayerWindow.hide ();
		    gl.hide ();
		    QuitWindow.hide ();
		}

	    }
	    else
	    {
		dice.show ();
		pm.show ();
		PlayerWindow.show ();
		gl.show ();
		QuitWindow.show ();
	    }

	}
    */

    public void actionPerformed (ActionEvent e)
    {
	if (e.getSource () == QuitButton)
	{
	    int response = JOptionPane.showOptionDialog (
		    null                     // Center in window.
		    , "Are you sure you want to quit?"        // Message
		    , "Monopoly"               // Title in titlebar
		    , JOptionPane.YES_NO_OPTION  // Option type
		    , JOptionPane.PLAIN_MESSAGE  // messageType
		    , null                       // Icon (none)
		    , null                   // Button text as above.
		    , "No"    // Default button's label
		    );

	    switch (response)
	    {
		case 0:
		    System.exit (0);
		    break;
		case 1:

		    break;
		case - 1:
		    //... Both the quit button (3) and the close box(-1) handled here.
		    break;   // It would be better to exit loop, but...
		default:
		    break;
	    }
	}



	if (e.getSource () == MinimizeButton)
	{
	    setState (Frame.ICONIFIED);
	    pm.hide ();
	    dice.hide ();
	    PlayerWindow.hide ();
	    gl.hide ();
	}


	if (e.getSource () == EndTurnButton)
	{
	    CurrentPlayer.endTurn ();
	    RollButton.setEnabled (true);
	}


	if (e.getSource () == RollButton)
	{

	    dice.rollDice ();

	}
	if (e.getSource () == BuyButton)
	{
	    if (CurrentPlayer.getAtProperty ().isOwnable ())
	    {
		showBuyDialog ();
		String buy = "";
		pm.addPropArray (CurrentPlayer.getOwnProperties (), "Own");

	    }
	    else
	    {
		BuyButton.setEnabled (false);
	    }

	}
	if (e.getSource () == CheckRentButton)
	{
	    //showRentDialog ();
	}

	if (e.getSource () == CancelRentButton)
	{
	    RentDialog.dispose ();
	}
	if (e.getSource () == ConfirmRentButton)
	{
	    // getRentOfProps ();
	    CheckRentButton.setEnabled (false);
	    RentDialog.dispose ();
	}

	MoneyLabel.setText ("Cash : R " + CurrentPlayer.getAmountOfCash ());
	PlayerTopPanel.updateUI ();
    }




    public void run ()
    {

	dice.show ();
	pm.show ();
	gl.show ();
	BuyButton.setEnabled (false);
	PlayerWindow.show ();
	CurrentPlayer = ThePlayers [Player_Turn_Id];
	int loop = 0;
	int throwChances = 2;
	boolean panelUpdated = false;

	while (t != null)
	{



	    isRepainted = false;
	    panelUpdated = false;

	    PlayerTitleBorder.setTitle ("It's " + CurrentPlayer + " 's turn now | Your currently at : " + CurrentPlayer.getAtProperty ());

	    pm.addPropArray (CurrentPlayer.getOwnProperties (), "Own");
	    TokenLabel.setIcon (CurrentPlayer.getToken ());
	    CurrentPlayer.startTurn ();
	    CheckRentButton.setEnabled (true);
	    RollButton.setEnabled (true);
	    if (CurrentPlayer.getBankrupt ())
	    {
		CurrentPlayer.setId (99);
	    }
	    else
	    {
		while (CurrentPlayer.stillBusy ())
		{
		    if (!panelUpdated)
		    {

			MoneyLabel.setText ("Cash : R " + CurrentPlayer.getAmountOfCash ());
			TokenLabel.setIcon (CurrentPlayer.getToken ());
			PlayerTopPanel.updateUI ();
			panelUpdated = true;
		    }
		    if (dice.isAlreadyThrown ())
		    {
			if (CurrentPlayer.getInJail ())
			{
			    isRepainted = true;
			    if (!justWentToJail)
			    {
				if (throwChances >= 0)
				{
				    PlayerTitleBorder.setTitle (throwChances + " attempts left");
				    if (dice.hasDouble ())
				    {
					CurrentPlayer.setInJail (false);
					PlayerTitleBorder.setTitle ("You've managed to escape!");
				    }
				    else
				    {
					throwChances--;
					dice.reset ();
				    }
				}
				else
				{
				    PlayerTitleBorder.setTitle ("Yet you've not managed to escape, maybe next time");
				    RollButton.setEnabled (false);


				}
			    }




			}

			if (!isRepainted)
			{

			    CurrentPlayer.move ("Auto");
			    PlayerTitleBorder.setTitle ("You are now at : " + CurrentPlayer.getAtProperty ());
			    panelUpdated = false;
			    isRepainted = true;
			    if (dice.hasDouble ())
			    {
				PlayerTitleBorder.setTitle ("You've thrown a double, throw again");
				doublePenalty++;
				RollButton.setEnabled (true);
				isRepainted = false;
				dice.reset ();
			    }
			    else
			    {
				RollButton.setEnabled (false);
				for (int x = 0 ; x < COM_CHEST_PROP_ID.length ; x++)
				{
				    if (CurrentPlayer.getAtProperty ().getId () == COM_CHEST_PROP_ID [x])
				    {
					showCardDialog ();
				    }
				}
				for (int x = 0 ; x < CHANCE_PROP_ID.length ; x++)
				{
				    if (CurrentPlayer.getAtProperty ().getId () == CHANCE_PROP_ID [x])
				    {
					showCardDialog ();
				    }
				}

				if (CurrentPlayer.getAtProperty () == SUPER_TAX)
				{
				    CurrentPlayer.setAmountOfCash (CurrentPlayer.getAmountOfCash () - Integer.parseInt (SUPER_TAX.getPropCost ()));
				    showMsg ("A amount of R " + SUPER_TAX.getPropCost () + " has been taken for Tax", "The Receiver");
				}
				if (CurrentPlayer.getAtProperty () == INCOME_TAX)
				{
				    CurrentPlayer.setAmountOfCash (CurrentPlayer.getAmountOfCash () - Integer.parseInt (INCOME_TAX.getPropCost ()));
				    showMsg ("A amount of R " + INCOME_TAX.getPropCost () + " has been taken for Tax", "The Receiver");
				}
				if (CurrentPlayer.getAtProperty () == GOTO_JAIL)
				{
				    CurrentPlayer.setInJail (true);
				    CurrentPlayer.setAtProperty (JAIL);
				    showMsg ("You must go straight to jail", "The Police");
				    justWentToJail = true;
				    isRepainted = false;
				}
				if (CurrentPlayer.getAtProperty ().isOwnable () && !CurrentPlayer.getAtProperty ().isAlreadyOwned ())
				{
				    BuyButton.setEnabled (true);
				    String buy = " - buy it for R " + CurrentPlayer.getAtProperty ().getPropCost ();
				    PlayerTitleBorder.setTitle ("You are now at : " + CurrentPlayer.getAtProperty () + buy);
				    panelUpdated = false;
				}

			    }
			    if (doublePenalty == MAX_DOUBLE_PEN)
			    {
				if (!CurrentPlayer.getInJail ())
				{
				    PlayerTitleBorder.setTitle ("Bad luck !! You have to go to jail, because you have thrown " + MAX_DOUBLE_PEN + " doubles");
				    CurrentPlayer.setInJail (true);
				    CurrentPlayer.setAtProperty (JAIL);
				    justWentToJail = true;
				}
				isRepainted = false;
				RollButton.setEnabled (false);
				doublePenalty = 0;
				panelUpdated = false;
			    }
			    if (CurrentPlayer.getAmountOfCash () <= NEGATIVE_BALANCE_ALLOWANCE)
			    {
				showMsg ("YOU ARE BANKRUPT!!", "The Furious Banker");
				end++;
				CurrentPlayer.setBankrupt (true);

			    }
			    if (end == ip.getCount ())
			    {
				showMsg ("Thank you for playing!", " Jean Britz ");
				System.exit (0);
			    }
			    repaint ();

			}
		    }

		}
		if (!CurrentPlayer.stillBusy ())
		{
		    if (Player_Turn_Id == ip.getCount ())
			Player_Turn_Id = 0;
		    else
			Player_Turn_Id++;


		    doublePenalty = 0;
		    throwChances = 2;
		    justWentToJail = false;
		    CurrentPlayer = ThePlayers [Player_Turn_Id];
		}

	    }
	}

    }


    /*
	public void showRentDialog ()
	{

	    RentDialog.setTitle ("The Banker");
	    JPanel RentPanel = new JPanel (new BorderLayout (2, 2));
	    JPanel CaptionPanel = new JPanel (new FlowLayout ());
	    JPanel CenterPanel = new JPanel ();
	    JPanel ButtonPanel = new JPanel (new FlowLayout ());
	    CenterPanel.setLayout (new BoxLayout (CenterPanel, BoxLayout.PAGE_AXIS));
	    JLabel caption = new JLabel ("Who owes rent for you? Tick the boxes");
	    caption.setFont (new Font ("Arial", 1, 20));

	    for (int x = 0 ; x < ip.getCount () ; x++)
	    {
		RentProp [x] = new JCheckBox ("");
		RentProp [x].setFont (new Font ("Arial", 1, 18));

		CenterPanel.add (RentProp [x]);
	    }

	    ButtonPanel.add (ConfirmRentButton);
	    ButtonPanel.add (CancelRentButton);
	    CaptionPanel.add (caption);

	    RentDialog.setSize (500, 300);

	    RentPanel.add (CenterPanel, BorderLayout.CENTER);
	    RentPanel.add (CaptionPanel, BorderLayout.NORTH);
	    RentPanel.add (ButtonPanel, BorderLayout.SOUTH);
	    ConfirmRentButton.addActionListener (this);
	    CancelRentButton.addActionListener (this);
	    RentDialog.setContentPane (RentPanel);
	    checkPropForRent ();


	}
    */
    /*
	public void checkPropForRent ()
	{
	    boolean match = false;
	    int count = 0;
	    int Rcount = 0;
	    Property props[] = new Property [ip.getRealCount ()];
	    Property selectedProps[] = new Property [props.length];
	    for (int x = 0 ; x < props.length ; x++)
	    {
		props [x] = ThePlayers [x].getAtProperty ();

	    }

	    for (int y = 0 ; y < CurrentPlayer.getPropertyCount () ; y++)
	    {
		for (int z = 0 ; z < props.length ; z++)
		{
		    if (CurrentPlayer.getAtProperty () != props [z])
		    {
			if (CurrentPlayer.getOwnProperties () [y] == props [z])
			{
			    match = true;
			    String s = "";
			    if (!props [z].getType ().equals ("Board"))
			    {
				s = ThePlayers [props [z].getOwnerId ()].getNick () + " At " + props [z].getCaption () + " Of R " + props [z].getTotalRent ();
			    }
			    else
			    {
				showMsg ("Feature not yet available", "Jean ;-)");
			    }
			    RentProp [count].setText (s);
			    selectedProps [count] = props [z];
			    count++;

			}
			else
			{

			}

		    }
		}
	    }

	    if (match)
	    {
		RentDialog.show ();
	    }
	    else
	    {
		showMsg ("No one owes you rent", "The Banker");
	    }
	    RentOweProps = selectedProps;
	    count = 0;
	    selectedProps = null;
	}


	public void getRentOfProps ()
	{
	    int total = 0;
	    String print = "";

	    for (int x = 0 ; x < RentProp.length ; x++)
	    {

		if (RentProp [x].isSelected ())
		{
		    total = total + RentOweProps [x].getTotalRent ();

		    System.out.println (total);
		    ThePlayers [RentOweProps [x].getOwnerId ()-1].setAmountOfCash (ThePlayers [RentOweProps [x].getOwnerId ()].getAmountOfCash () - RentOweProps [x].getTotalRent ());
		    print = CurrentPlayer + " has received R " + total + " from " + ThePlayers [RentOweProps [x].getOwnerId ()];
		    gl.log (print);

		}
		System.out.println(x);
	    }
	    CurrentPlayer.setAmountOfCash (CurrentPlayer.getAmountOfCash () + total);
    total=0;
	}

    */
    public void showBuyDialog ()
    {

	cv.viewCard (lo.getProperties (), CurrentPlayer.getAtProperty ().getId ());
	boolean Responded = false;

	while (!Responded)
	{
	    int response = JOptionPane.showConfirmDialog (null, "Do you want to buy this?", "The Banker", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);


	    switch (response)
	    {
		case 0:
		    CurrentPlayer.buyProperty (CurrentPlayer.getAtProperty ());
		    showMsg ("Congrats, you are the new owner of '" + CurrentPlayer.getAtProperty () + "'", "The Banker");
		    cv.hide ();
		    gl.log (CurrentPlayer.getNick () + " has bought : " + CurrentPlayer.getAtProperty ());
		    BuyButton.setEnabled (false);
		    Responded = true;
		case 1:
		    Responded = true;
		    cv.hide ();
		case - 1:


		default:
		    Responded = true;
	    }

	}
    }


    public void showCardDialog ()
    {


	Card chosenCard = new Card ();

	if (CurrentPlayer.getAtProperty ().getTitle ().equals ("Community Chest"))
	{
	    chosenCard = lo.getComChestCards () [CCcount];
	    CCcount++;
	}


	else
	{
	    chosenCard = lo.getChanceCards () [Ccount];
	    Ccount++;
	}


	chosenCard.showCard ();

	if (Ccount == 16)
	{
	    Ccount = 0;
	}


	if (CCcount == 16)
	{
	    CCcount = 0;
	}


	String consequence = chosenCard.getCons ();
	int intCon = 0;
	String type = chosenCard.getType ();

	if (type.equals ("Repairs"))
	{
	    StringTokenizer token = new StringTokenizer (consequence, ";");
	    int house = Integer.parseInt (token.nextToken ());
	    int hotel = Integer.parseInt (token.nextToken ());
	}


	if (type.equals ("Advance"))
	{
	    CurrentPlayer.setAtProperty (lo.getProperties () [intCon]);
	    repaint ();
	}


	if (type.equals ("Earn"))
	{
	    intCon = Integer.parseInt (consequence);
	    CurrentPlayer.setAmountOfCash (CurrentPlayer.getAmountOfCash () + intCon);
	}


	if (type.equals ("Pay"))
	{
	    intCon = Integer.parseInt (consequence);
	    CurrentPlayer.setAmountOfCash (CurrentPlayer.getAmountOfCash () - intCon);
	}


	if (type.equals ("Free"))
	{
	    CurrentPlayer.addGetOutJailCard ();
	    chosenCard.setInUse (true);
	}


	if (type.equals ("Move"))
	{
	    CurrentPlayer.move (consequence);
	}


	if (type.equals ("Birthday"))
	{
	    intCon = Integer.parseInt (consequence);
	    for (int x = 0 ; x < ip.getCount () ; x++)
	    {

		ThePlayers [x].setAmountOfCash (CurrentPlayer.getAmountOfCash () - intCon);
	    }
	    CurrentPlayer.setAmountOfCash (CurrentPlayer.getAmountOfCash () + (intCon * (ip.entryCount + 1)));
	}


	if (type.equals ("Jail"))
	{
	    CurrentPlayer.setInJail (true);
	    CurrentPlayer.setAtProperty (JAIL);
	    justWentToJail = true;
	}


	while (chosenCard.isVisible ())
	{

	}
    }


    public void initGame ()
    {

	setIconImage (THE_ICON.getImage ());
	setTitle ("Monopoly");
	//addWindowStateListener (this);
	/// Insert players ///

	ip = new InsertPlayers ();
	while (!ip.Finished)
	{
	}


	ThePlayers = ip.getPlayers ();

	/// Insert Objects ///
	lo = new LoadObjects ();
	while (!lo.Finished)
	{
	}


	BEGIN = lo.getProperties () [0];
	JAIL = lo.getProperties () [10];
	FREE_PARKING = lo.getProperties () [20];
	GOTO_JAIL = lo.getProperties () [30];
	INCOME_TAX = lo.getProperties () [4];
	SUPER_TAX = lo.getProperties () [38];
    }


    public void initBoardPanel ()
    {

	/// Placing tokens onto the board ///
	pm = new PropertyManager ();

	for (int x = 0 ; x < ip.entryCount ; x++)
	{
	    ThePlayers [x].setAtProperty (BEGIN);
	    ThePlayers [x].setDice (dice);
	    ThePlayers [x].setPropManager (pm);
	    ThePlayers [x].setLog (gl);
	}


	/// Setting up the board ///
	//setUndecorated (true);
	getContentPane ().setLayout (new FlowLayout ());
	setBounds (50, 50, 600, 600);
	BoardPanel.setBackground (Color.black);
	BoardPanel.setSize (1000, 1000);
	BoardPanel.add (new JLabel (new ImageIcon (BOARD)));
	getContentPane ().add (BoardPanel);
	show ();

    }


    public void initPlayerPanel ()
    {
	Border border = BorderFactory.createMatteBorder (
		5, 5, 5, 5, new Color (184, 0, 0));

	PlayerTitleBorder = new TitledBorder (border, "Pending", 1, 1, new Font ("Arial", 1, 18), Color.black);
	PlayerTopPanel.setBorder (PlayerTitleBorder);

	PlayerWindow.setBounds (50, 660, 950, 150);

	PlayerButtonPanel.add (BuyButton);
	PlayerButtonPanel.add (RollButton);
	PlayerButtonPanel.add (EndTurnButton);
	//PlayerButtonPanel.add (CheckRentButton);

	MoneyLabel.setFont (MoneyFont);
	JailLabel.setFont (new Font ("Arial", 1, 14));
	PlayerInfoPanel.setLayout (new BoxLayout (PlayerInfoPanel, 1));
	PlayerTokenPanel.setBorder (BorderFactory.createEtchedBorder (EtchedBorder.LOWERED));
	PlayerTokenPanel.add (TokenLabel);
	PlayerInfoPanel.add (PlayerTokenPanel);
	PlayerInfoPanel.add (MoneyLabel);
	PlayerInfoPanel.add (JailLabel);

	JLabel logo = new JLabel (LOGO);
	logo.setHorizontalAlignment (JLabel.RIGHT);

	PlayerTopPanel.add (PlayerInfoPanel, BorderLayout.WEST);
	PlayerTopPanel.add (PlayerButtonPanel, BorderLayout.CENTER);
	PlayerTopPanel.add (logo, BorderLayout.EAST);
	EndTurnButton.addActionListener (this);
	RollButton.addActionListener (this);
	BuyButton.addActionListener (this);
	CheckRentButton.addActionListener (this);
	PlayerWindow.getContentPane ().add (PlayerTopPanel);

    }


    public void initQuitPanel ()
    {

	JPanel p = new JPanel (new FlowLayout ());
	p.setBorder (BorderFactory.createMatteBorder (
		    5, 5, 5, 5, new Color (184, 0, 0)));
	p.add (QuitButton);
	p.add (MinimizeButton);
	QuitWindow.getContentPane ().add (p);
	QuitWindow.setBounds (1010, 710, 100, 105);
	QuitWindow.show ();
	QuitButton.addActionListener (this);
	MinimizeButton.addActionListener (this);
    }





    public void animation (Graphics g)
    {
	int alterPos = 0;
	g.drawImage (ThePlayers [0].getTokenImage (), ThePlayers [0].getPosX (), ThePlayers [0].getPosY (), BoardPanel);
	for (int x = 1 ; x < ip.entryCount ; x++)
	{
	    if (!ThePlayers [x].getBankrupt ())
	    {

		if (ThePlayers [x].getAtProperty ().getId () >= 30)
		{
		    alterPos = ThePlayers [x].getPosX () - ThePlayers [x].getId () * 5;
		    g.drawImage (ThePlayers [x].getTokenImage (), alterPos, ThePlayers [x].getPosY (), BoardPanel);
		}
		else
		{
		    if (ThePlayers [x].getAtProperty ().getId () >= 20)
		    {
			alterPos = ThePlayers [x].getPosY () + ThePlayers [x].getId () * 5;
			g.drawImage (ThePlayers [x].getTokenImage (), ThePlayers [x].getPosX (), alterPos, BoardPanel);
		    }
		    else
		    {
			if (ThePlayers [x].getAtProperty ().getId () >= 10)
			{
			    alterPos = ThePlayers [x].getPosX () + ThePlayers [x].getId () * 5;
			    g.drawImage (ThePlayers [x].getTokenImage (), alterPos, ThePlayers [x].getPosY (), BoardPanel);
			}
			else
			{
			    if (ThePlayers [x].getAtProperty ().getId () >= 0)
			    {
				alterPos = ThePlayers [x].getPosY () - ThePlayers [x].getId () * 5;
				g.drawImage (ThePlayers [x].getTokenImage (), ThePlayers [x].getPosX (), alterPos, BoardPanel);

			    }
			}
		    }

		}
	    }
	}
    }


    public void paint (Graphics g)
    {
	g.drawImage (BOARD, 0, 0, BoardPanel);
	animation (g);
	draw = true;

    } // paint method


    public static void showMsg (String msg, String title)
    {
	JOptionPane.showMessageDialog (null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main (String [] args)
    {
	try
	{
	    new Main ();
	  //  UIManager.setLookAndFeel (new com.sun.java.swing.plaf.windows.WindowsLookAndFeel ());
	    // UIManager.setLookAndFeel (new com.sun.java.swing.plaf.motif.MotifLookAndFeel ());
	    // UIManager.setLookAndFeel (new javax.swing.plaf.metal.MetalLookAndFeel ());
	    //Main mc = new Main ();
	}


	catch (Exception e)
	{
	    showMsg ("LookAndFeel ERROR : " + e.getMessage (), "Monopoly - Error Message");
	}
    } // main method
} // Main class


