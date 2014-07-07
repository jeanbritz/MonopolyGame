import java.awt.event.*;
import java.util.Vector;
import java.awt.*;
import javax.swing.*;
import java.io.*;

///////////////////////////////////////////////////////////////////////
/////// A simple user-friendly app to insert players into the game ////
///////////////////////////////////////////////////////////////////////
// The "InsertPlayers" class.
public class InsertPlayers
    extends JDialog
    implements ActionListener, KeyListener

{

    Vector v = new Vector ();
    /// Maximum amount of players that can join ///
    public static final int MAX_PLAYERS = 6;

    /// Amount of money that each player starts with ///
    public final int START_AMOUNT = 1500;


    PlayerEntry p[] = new PlayerEntry [v.capacity ()];
    int entryCount = 0;

    static Player TempPlayers[] = new Player [MAX_PLAYERS];

    Font MyFont = new Font ("Arial", 1, 16);

    /// Default images for tokens to avoid errors    ///
    /// This tokens is also recommended for the game ///
    public final String DEFAULT_TOKENS[] = {"data\\tokens\\1.gif",
	"data\\tokens\\2.gif",
	"data\\tokens\\3.gif",
	"data\\tokens\\4.gif",
	"data\\tokens\\5.gif",
	"data\\tokens\\6.gif",
	""};


    JPanel MainPanel;
    JButton OkButton;
    JButton AddButton;
    JButton RemoveButton;
    JList ListOfPlayers;
    JScrollPane Scroll;
    JTextField txt;
    JFileChooser FC;
    File F = new File (DEFAULT_TOKENS [entryCount]);
    JLabel StatusLabel;
    JLabel ImageLabel;
    JTextField TextName;
    JMenuItem ChangeToken;
    JPopupMenu TokenMenu;

    static boolean Finished = false;

    InsertPlayers ()
    {
	/////////////////////////////////////////////////
	//////// Initiates all the components ///////////
	/////////////////////////////////////////////////
	FC = new JFileChooser ();
	setTitle ("Insert Players");
	MainPanel = new JPanel ();
	MainPanel.setLayout (new BorderLayout ());
	getContentPane ().add (MainPanel);

	ListOfPlayers = new JList ();
	Font ListFont = new Font ("Arial", 1, 24);
	ListOfPlayers.setCellRenderer (new PlayerListCell (24, Color.red, Color.white, ListFont));
	Scroll = new JScrollPane ();
	Scroll.getViewport ().add (ListOfPlayers);

	MainPanel.add (Scroll, BorderLayout.CENTER);

	JPanel ButtonPanel = new JPanel ();
	JPanel InterPanel = new JPanel ();
	InterPanel.setLayout (new BorderLayout ());
	ButtonPanel.setLayout (new FlowLayout (0));


	AddButton = new JButton ("Add");
	ButtonPanel.add (AddButton);
	AddButton.addActionListener (this);

	RemoveButton = new JButton ("Remove");
	ButtonPanel.add (RemoveButton);
	RemoveButton.addActionListener (this);

	OkButton = new JButton ("Okay");
	ButtonPanel.add (OkButton);
	OkButton.addActionListener (this);

	InterPanel.add (ButtonPanel, BorderLayout.NORTH);
	JPanel TextPanel = new JPanel ();
	TextPanel.setBackground (Color.red);
	TextPanel.setLayout (new FlowLayout ());

	ImageLabel = new JLabel (new ImageIcon (F.getAbsolutePath ()));
	TextPanel.add (ImageLabel);
	TextPanel.add (new JSeparator ());
	TextName = new JTextField (15);
	TextName.setFont (MyFont);
	TextPanel.add (TextName);

	StatusLabel = new JLabel (" ");
	StatusLabel.setBorder (BorderFactory.createLoweredBevelBorder ());
	InterPanel.add (TextPanel, BorderLayout.CENTER);
	InterPanel.add (StatusLabel, BorderLayout.SOUTH);
	MainPanel.add (InterPanel, BorderLayout.SOUTH);


	TokenMenu = new JPopupMenu ();
	ChangeToken = new JMenuItem ("Browse...");
	TokenMenu.add (ChangeToken);
	TextPanel.add (TokenMenu);
	ChangeToken.addActionListener (this);
	enableEvents (AWTEvent.MOUSE_EVENT_MASK);
	setSize (300, 400);
	setLocationRelativeTo (null);

	setResizable (false);
	show ();
	TextName.addKeyListener (this);
	ListOfPlayers.addKeyListener (this);
	TextName.requestFocus ();

	///////////////////////////////////////////////

	addWindowListener (new WindowAdapter ()
	{
	    public void windowClosing (WindowEvent winEvt)
	    {
		/// If the window is closed the program ends also ///
		System.exit (0);

	    }
	}
	);
    }


    public void keyPressed (KeyEvent ke)
    {
	/// When the 'ENTER' key is pressed it will add the player ///
	if (ke.getKeyCode () == KeyEvent.VK_ENTER)
	{
	    add ();

	}
	/// When the 'DELETE' key is pressed it will remove the selected player ///
	if (ke.getKeyCode () == KeyEvent.VK_DELETE)
	{
	    remove ();
	}


    }


    public void keyReleased (KeyEvent ke)
    {
    }


    public void keyTyped (KeyEvent ke)
    {
    }


    public void processMouseEvent (MouseEvent me)
    {
	/// This event comes in action when the user clicks the right mouse button on the token image ///
	/// When this happens it will open a dialog for the user to change its token image ///
	if (me.isPopupTrigger ())
	{
	    TokenMenu.show (me.getComponent (), me.getX (), me.getY ());
	}
	super.processMouseEvent (me);
    }


    public int getCount ()
    {
	return entryCount - 1;
    }


    public int getRealCount ()
    {
	return entryCount;
    }


    /// This method comes into action when the user wants to change his token image ///
    public void editToken ()
    {
	FC.setFileFilter (new ImgFilter ());
	FC.showOpenDialog (null);
	if (FC.getSelectedFile () != null)
	{
	    F = FC.getSelectedFile ();
	}
	if (FC.accept (F))
	{
	    String f = "";
	    if (F != null)
	    {
		f = F.getName ();
		Image i = Toolkit.getDefaultToolkit ().getImage ("" + F);
		ImageLabel.setIcon (new ImageIcon (i));
		StatusLabel.setText (f + " loaded");
		TextName.setText ("");
	    }

	}
	else
	{
	    StatusLabel.setText ("Not able to load the file as image");
	}

    }


    /// When a button is pressed it will run this method ///
    public void actionPerformed (ActionEvent e)
    {
	if (e.getSource () == ChangeToken)
	{
	    editToken ();
	}


	if (e.getSource () == AddButton)
	{
	    add ();
	}


	if (e.getSource () == RemoveButton)
	{

	    remove ();

	}

	/// When the user has finished adding players, he clicks on 'Okay' to start the game ///
	if (e.getSource () == OkButton)
	{
	    /// Checks if there is enough players to make it worthwhile, minimum of 2 players can make it worthwhile ///
	    if (entryCount >= 2)
	    {
		for (int x = 0 ; x < entryCount ; x++)
		{
		    TempPlayers [x] = new Player (x + 1, p [x].getTitle (),
			    START_AMOUNT,
			    p [x].getImage (),
			    null);

		}

		dispose ();
		Finished = true;

	    }
	    else
	    {
		StatusLabel.setText ("Not enough players to make it worthwhile");
	    }

	}
    }


    public void add ()
    {
	/// Check if there is enough space to add another player ///
	if (entryCount < MAX_PLAYERS)
	{
	    TextName.setEnabled (true);
	    String stringValue = TextName.getText ().trim ();
	    String imagePath = F.getAbsolutePath ();

	    if (!stringValue.equals ("") & ImageLabel != null)
	    {
		p [entryCount] = new PlayerEntry (stringValue, imagePath);
		v.addElement (p [entryCount]);
		ListOfPlayers.setListData (v.toArray ());
		Scroll.revalidate ();
		Scroll.repaint ();
		entryCount++;
		F = new File (DEFAULT_TOKENS [entryCount]);
		ImageLabel.setIcon (new ImageIcon (F.getAbsolutePath ()));

	    }
	    /// Some validation to force the user to enter a name for the player ///
	    if (TextName.getText ().equals (""))
	    {

		StatusLabel.setText ("Enter your name please");
		Toolkit.getDefaultToolkit ().beep ();
	    }
	    TextName.setText ("");
	}
	else
	{
	    StatusLabel.setText ("Maximum of " + MAX_PLAYERS + " players has been reached");
	    TextName.setEnabled (false);
	}
	/// Request focus for text field to enter a name. Makes it faster to enter players ///
	TextName.requestFocus ();
    }



    public void remove ()
    {
	int selection = ListOfPlayers.getSelectedIndex ();
	if (selection >= 0)
	{

	    v.removeElementAt (selection);
	    ListOfPlayers.setListData (v);
	    Scroll.revalidate ();
	    Scroll.repaint ();


	    if (selection >= v.size ())
		selection = v.size () - 1;
	    ListOfPlayers.setSelectedIndex (selection);
	    if (entryCount > 0)
	    {
		entryCount--;
	    }
	}
	/// When a player is deleted/removed then the token image will change back to default again ///
	F = new File (DEFAULT_TOKENS [entryCount]);
	ImageLabel.setIcon (new ImageIcon (F.getAbsolutePath ()));
	TextName.setEnabled (true);
    }



    /// A method to accually import the players into the game ///
    Player[] getPlayers ()
    {
	return TempPlayers;
    }


    public static void main (String[] args)
    {

	new InsertPlayers ();

    } // main method
} // InsertPlayers class


/// Custom Objects, its basically the item that will appear in the list of players ///
class PlayerListCell extends JLabel implements ListCellRenderer
{
    private ImageIcon image[];
    private Color Background;
    private Color Foreground;
    private Font CustomFont;

    public PlayerListCell (int iconTxtGap, Color background, Color foreground, Font f)
    {
	Background = background;
	Foreground = foreground;
	CustomFont = f;
	setOpaque (true);
	setIconTextGap (iconTxtGap);
	image = new ImageIcon [6];
    }


    /// The CustomListRenderer code ///
    public Component getListCellRendererComponent (
	    JList list, Object value, int index,
	    boolean isSelected, boolean cellHasFocus)
    {
	PlayerEntry entry = (PlayerEntry) value;

	if (entry != null)
	{
	    setText (entry.getTitle ());
	    setIcon (entry.getImage ());
	}
	/// Appearance of the item in the list when selected ///
	if (isSelected)
	{
	    setBackground (Background);
	    setForeground (Foreground);
	    setFont (CustomFont);
	}
	else
	{

	    setBackground (Foreground);
	    setForeground (Background);
	    setFont (CustomFont);
	}
	return this;
    }
}


class ImgFilter extends javax.swing.filechooser.FileFilter
{ /// A filter for the JFileChooser to avoid errors when a player wants to change his token image ///
    public boolean accept (File f)
    {
	return f.isDirectory () || f.getName ().toLowerCase ().endsWith (".jpg") ||
	    f.getName ().toLowerCase ().endsWith (".gif") ||
	    f.getName ().toLowerCase ().endsWith (".jpeg") ||
	    f.getName ().toLowerCase ().endsWith (".bmp") ||
	    f.getName ().toLowerCase ().endsWith (".png");
    }


    public String getDescription ()
    {
	return "Suitable Formats (*.jpg,*.jpeg,*.gif,*.bmp,*.png)";
    }
}

/// Custom object to combine the player's name with its image ///
class PlayerEntry
{
    private final String title;

    private final String imagePath;

    private ImageIcon image;

    public PlayerEntry (String title, String imagePath)
    {
	this.title = title;
	this.imagePath = imagePath;
    }


    public String getTitle ()
    {
	return title;
    }


    public ImageIcon getImage ()
    {
	if (image == null)
	{
	    image = new ImageIcon (imagePath);
	}
	return image;
    }


    public String toString ()
    {
	return title;
    }
}


