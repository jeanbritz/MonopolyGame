import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.border.*;

///////////////////////////////////////////////////////////////////////
/////// A simple JWindow to display properties with ///////////////////
///////////////////////////////////////////////////////////////////////

// The "PropertyManager" class.
public class PropertyManager extends JWindow implements ActionListener, ListSelectionListener
{

    ImageIcon InfoIcon = new ImageIcon ("data\\icons\\info.gif");

    JPanel TopPanel = new JPanel (new BorderLayout (1, 1));
    JTabbedPane TabbedPane = new JTabbedPane ();
    JPanel AllPanel = new JPanel (new BorderLayout ());
    JPanel OwnPanel = new JPanel (new BorderLayout ());
    JPanel ButtonPanel = new JPanel (new FlowLayout (FlowLayout.RIGHT));

    JList AllList = new JList ();
    JList OwnList = new JList ();
  

    Vector AllVector = new Vector (40);
    Vector OwnVector = new Vector (28);
   

    JButton ViewButton = new JButton ("View Property");

    JScrollPane AllScroll = new JScrollPane (AllList);
    JScrollPane OwnScroll = new JScrollPane (OwnList);
  

    Font ListFont = new Font ("Arial", 0, 20);

    int Prop_Id_Selected = -1;
    CardViewer cv = new CardViewer ();


    Property TheProperties[] = new Property [40];
    Property ForSaleProps[] = new Property [40];


    PropertyManager ()
    {
    /// Initiates app and inserts the "All" properties, because it never changes through the course of the game ///
	    init ();
	setBounds (660, 175, 340, 300);
	addPropArray (TheProperties, "All");
    }


    public void actionPerformed (ActionEvent e)
    {
	if (e.getSource () == ViewButton)
	{
/// A method called from CardViewer.java to display the property card visually ///
	    cv.viewCard (TheProperties, Prop_Id_Selected);

	}
    }


    public void valueChanged (ListSelectionEvent lse)
    {
    /// List of action that must take place if the ListSelectionEvent detects something ///
    
	if (lse.getValueIsAdjusting ())
	{
	    Object s = new Object ();

	    if (lse.getSource () == AllList)
	    {
		s = AllList.getSelectedValue ();
		ViewButton.setEnabled(true);


	    }
	    if (lse.getSource () == OwnList)
	    {
		s = OwnList.getSelectedValue ();
		ViewButton.setEnabled(false);

	    }
	    
	    PropEntry tmp = (PropEntry) s;


	    if (tmp.getId () != "")
	    {
		Prop_Id_Selected = Integer.parseInt (tmp.getId ());
	       

	    }
	    else
	    {
		Prop_Id_Selected = -1;
		AllList.setSelectedIndex (Prop_Id_Selected);
	    }
	}


    }

/// A method to find add properties into a JList on the specific panel on the JTabbedPane ///
    public void addPropArray (Property p[], String pane)
    {
	OwnVector.removeAllElements ();
		if (pane == "All")
	{
	    PropEntry entry[] = new PropEntry [AllVector.capacity ()];
	    int count = 0;
	    for (int x = 0 ; x < p.length ; x++)
	    {

		if (p [x].isOwnable () | p [x] == null)
		{

		    entry [count] = new PropEntry ("" + p [x].getId (), p [x].getTitle (), p [x].getType ());
		    AllVector.addElement (entry [count]);

		    count++;
		}


	    }

	    AllList.setListData (AllVector.toArray ());
	    AllScroll.revalidate ();
	    AllScroll.repaint ();

	}
	if (pane == "Own")
	{
	    PropEntry entry[] = new PropEntry [OwnVector.capacity ()];
	    int count = 0;
	    for (int x = 0 ; x < p.length ; x++)
	    {
		if (p [x] == null)
		{
		    break;
		}

		entry [count] = new PropEntry ("" + p [x].getId (), p [x].getTitle (), p [x].getType ());
		OwnVector.addElement (entry [count]);
		count++;

	    }

	    OwnList.setListData (OwnVector.toArray ());

	    OwnScroll.revalidate ();
	    OwnScroll.repaint ();

	}
	


    }


/// Yet not been used, its written because if there is a add function there must also be a remove function ///
/// This code is for future use ///
    public void removeProp (Property array[], Property prop)
    {
	for (int x = 0 ; x < array.length ; x++)
	{
	    if (array [x] == prop)
	    {
		array [x] = null;
	    }
	}

    }

/// A method to get a specific property's data quickly ///
/// It is used in the main program ///
    public Property getPropAt (int index)
    {
	return TheProperties [index];
    }

/// Also for future use ///
/// Does not work correctly ///
/// Later it will be developed to sort properties accordingly to their colour ///

    public void sort (Property array[])
    {
	for (int i = array.length - 1 ; i > 1 ; i--)
	{
	    for (int j = 0 ; j < i ; j++)
	    {

		if (array [j + 1].getId () < array [j].getId ())
		{
		    Property tmp = array [j];
		    array [j] = array [j + 1];
		    array [j + 1] = tmp;
		}
	    }


	}
    }


  


   
/// The default initiation of the property manager
    public void init ()
    {
	TheProperties = Main.lo.getProperties ();

	Border border = BorderFactory.createMatteBorder (
		5, 5, 5, 5, new Color (184, 0, 0));
	TitledBorder title = new TitledBorder (border, "Property Manager", 1, 1, new Font ("Arial", 1, 15), Color.black);
	TopPanel.setBorder (title);



	AllList.setCellRenderer (new PropListCell (Color.white, Color.black, ListFont));
	AllPanel.add (AllScroll, BorderLayout.CENTER);
	AllList.setListData (AllVector);

	OwnList.setCellRenderer (new PropListCell (Color.white, Color.black, ListFont));
	OwnPanel.add (OwnScroll);
	OwnList.setListData (OwnVector);
      
	TabbedPane.addTab ("All", null, AllPanel, "All properties");
	TabbedPane.addTab ("Owned", null, OwnPanel, "Properties that you own");

	ButtonPanel.add (ViewButton);

	TopPanel.add (TabbedPane, BorderLayout.CENTER);
	TopPanel.add (ButtonPanel, BorderLayout.SOUTH);

	getContentPane ().add (TopPanel);
	ViewButton.addActionListener (this);
	AllList.addListSelectionListener (this);
       


    }


    public static void main (String[] args)
    {
	new PropertyManager ();
    } // main method
} // PropertyManager class

/*class PropListCell extends JLabel implements ListCellRenderer
{
    private Color Background;
    private Color Foreground;
    private Font CustomFont;

    public final ImageIcon TRAIN_ICON = new ImageIcon ("data\\icons\\train.gif");
    public final ImageIcon PROP_ICON = new ImageIcon ("data\\icons\\prop.gif");
    public final ImageIcon ELEC_ICON = new ImageIcon ("data\\icons\\elec.gif");
    public final ImageIcon WATER_ICON = new ImageIcon ("data\\icons\\water.gif");

    public PropListCell (Color background, Color foreground, Font f)
    {
	Background = background;
	Foreground = foreground;
	CustomFont = f;
	setOpaque (true);


    }


    public Component getListCellRendererComponent (
	    JList list, Object value, int index,
	    boolean isSelected, boolean cellHasFocus)
    {
	PropEntry entry = (PropEntry) value;

	if (entry != null)
	{
	    setText (entry.getCaption ());
	    if (entry.getType ().equals ("Station"))
	    {
		setIcon (TRAIN_ICON);
	    }
	    if (entry.getType ().equals ("Street") |
		    entry.getType ().equals ("Avenue") |
		    entry.getType ().equals ("Road") |
		    entry.getType ().equals ("Square") |
		    entry.getType ().equals ("Parade"))

		{
		    setIcon (PROP_ICON);
		}
	    if (entry.getTitle ().equals ("Water"))
	    {
		setIcon (WATER_ICON);
	    }
	    if (entry.getTitle ().equals ("Electricity"))
	    {
		setIcon (ELEC_ICON);
	    }

	}



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
}*/

/// Above is the backup code for the ListCellRenderer, just in case I screw ///
/// the code below up ///
/// A ListCellRenderer is used to make the program more modern and professional///
class PropListCell extends JLabel implements ListCellRenderer
{
    private Color Background;
    private Color Foreground;
    private Font CustomFont;
/// I have created icons for each type of property using Paint ///
    public final ImageIcon TRAIN_ICON = new ImageIcon ("data\\icons\\train.gif");
    public final ImageIcon PROP_ICON = new ImageIcon ("data\\icons\\prop.gif");
    public final ImageIcon ELEC_ICON = new ImageIcon ("data\\icons\\elec.gif");
    public final ImageIcon WATER_ICON = new ImageIcon ("data\\icons\\water.gif");
    public final ImageIcon HOUSE_ICON = new ImageIcon ("data\\icons\\house.gif");

    public PropListCell (Color background, Color foreground, Font f)
    {
	Background = background;
	Foreground = foreground;
	CustomFont = f;
	setOpaque (true);


    }


    public Component getListCellRendererComponent (
	    JList list, Object value, int index,
	    boolean isSelected, boolean cellHasFocus)
    {
	PropEntry entry = (PropEntry) value;

	if (entry != null)
	{
	    setText (entry.getCaption ());
	    if (entry.getType ().equals ("Station"))
	    {
		setIcon (TRAIN_ICON);
	    }
	    if (entry.getType ().equals ("Street") |
		    entry.getType ().equals ("Avenue") |
		    entry.getType ().equals ("Road") |
		    entry.getType ().equals ("Square") |
		    entry.getType ().equals ("Parade"))

		{
		    setIcon (PROP_ICON);
		}
	    if (entry.getTitle ().equals ("Water"))
	    {
		setIcon (WATER_ICON);
	    }
	    if (entry.getTitle ().equals ("Electricity"))
	    {
		setIcon (ELEC_ICON);
	    }

	}



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

class PropEntry
{
    private final String id;
    private final String title;
    private final String type;


    public PropEntry (String id, String title, String type)
    {
	this.id = id;
	this.title = title;
	this.type = type;

    }


    public String getId ()
    {
	return id;
    }


    public String getTitle ()
    {
	return title;
    }


    public String getType ()
    {

	return type;
    }

/// A reference type ///
    public String getCaption ()
    {
	return title + " " + type;
    }



    public String toString ()
    {
	return title + " " + type;
    }
}

