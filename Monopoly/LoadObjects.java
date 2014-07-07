import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.util.*;
// The "LoadObjects" class.
public class LoadObjects extends JWindow
{
    ///Global connection///
    Connection con;

    ///Constants///
    private final int TotalCards = 32;
    private final int TotalProperties = 40;
    private final String DatabaseName = "data";

    /// Declare Properties ///
    Property prop [] = new Property [TotalProperties];
    /// Declare cards ///
    Card ChanceCard [] = new Card [16];
    Card ComChestCard [] = new Card [16];

    /// Count variables ///
    int ChanceCount = 0;
    int ComChestCount = 0;
    int PropCount = 0;

    JProgressBar bar;
    JLabel stat = new JLabel (" ");
    double progress = 0;
    static boolean Finished = false;

    LoadObjects ()
    {
	showProgress ();
	openDB ();
	getPropertyData ();
	getCardData ();
	if (Finished)
	    dispose ();


    }

	
    public void openDB ()
    {
	boolean Err = false;

 try
         {
         
            //conn = DriverManager.getConnection ("jdbc:odbc:diseases.mdb");
            
             
            
         
          String filename = "data/data.mdb";
          
            String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            database += filename.trim () + ";DriverID=22;READONLY=true}"; 
            con = DriverManager.getConnection (database, "", "");
         
                    
         }
             catch (Exception e)
            {
               Err = true;
            }


	if (Err)
	{
	    showMsg ("General Error : \n Make sure that '" + DatabaseName + ".mdb' and its path exists");
	    System.exit (0);
	}
    }


    void showProgress ()
    {

	bar = new JProgressBar (JProgressBar.HORIZONTAL);
	stat.setText ("Loading game...");
	stat.setForeground (Color.white);
	getContentPane ().setLayout (new FlowLayout ());
	//getContentPane().setBorder(BorderFactory.createRaisedBevelBorder ());
	getContentPane ().setBackground (new Color (156, 20, 20, 255));
	getContentPane ().add (stat);
	getContentPane ().add (bar);
	setSize (getPreferredSize ());
	//        setLocationRelativeTo (null);
	setVisible (true);

    }


    void getPropertyData ()
    {
	int tCount;
	try
	{
	    Statement stmt1 = con.createStatement ();
	    Statement stmt2 = con.createStatement ();
	    String query1 = "SELECT * FROM Properties";
	    String query2 = "SELECT * FROM Tariffs";
	    ResultSet rs1 = stmt1.executeQuery (query1);
	    ResultSet rs2 = stmt2.executeQuery (query2);

	    while (rs1.next ())
	    {
		String PropID = rs1.getString ("PropID");
		String PropName = rs1.getString ("PropName");
		String PropCost = rs1.getString ("CostForProp");
		String PropType = rs1.getString ("PropType");
		String COHouse = rs1.getString ("CostOfHouse");
		String MV = rs1.getString ("MortageValue");
		String RGB = rs1.getString ("RGB");
		int posX = rs1.getInt ("PosX");
		int posY = rs1.getInt ("PosY");
		prop [PropCount] = new Property (PropID,
			PropName,
			PropType,
			PropCost,
			COHouse,
			MV,
			RGB, posX, posY);

		tCount = 6;

		if (prop [PropCount].getType ().equals ("Tax") |
			prop [PropCount].getType ().equals ("Board") |
			prop [PropCount].getType ().equals ("Card") |
			prop [PropCount].getType ().equals ("Corner"))

		    {
			tCount = 0;
		    }
		if (prop [PropCount].getType ().equals ("Station"))
		{
		    tCount = 4;
		}

		for (int y = 0 ; y < tCount ; y++)
		{
		    rs2.next ();
		    String Code = rs2.getString ("Code");
		    String Cost = rs2.getString ("Cost");
		    prop [PropCount].t [y] = new Tariff (Code, Cost);
		}

		PropCount++;
		progress = (double) PropCount / TotalProperties * 100;
		bar.setValue ((int) progress);
	    }
	}
	catch (SQLException ex)
	{
	    showMsg ("SQLException: " + ex.getMessage () + " " + ex.getSQLState ());
	    ex.printStackTrace ();
	}

	if (progress != 100)
	{
	    showMsg ("Some of the game's components, Properties, cannot be found or is missing," + "\n" + "check your database for errors.");
	    System.exit (0);
	}
    }


    Property [] getProperties ()
    {
	return prop;
    }


    void getCardData ()
    {
	try
	{
	    Statement stmt = con.createStatement ();
	    String query = "SELECT * FROM Cards";
	    ResultSet rs = stmt.executeQuery (query);


	    while (rs.next ())
	    {
		String CardGroup = rs.getString ("Group");
		String Type = rs.getString ("Type");
		String Con = rs.getString ("Consequence");
		String Msg = rs.getString ("Message");
		if (CardGroup.equals ("C"))
		{
		    ChanceCard [ChanceCount] = new Card ("C", Type, Con, Msg);
		    ChanceCount++;

		}
		if (CardGroup.equals ("CC"))
		{
		    ComChestCard [ComChestCount] = new Card ("CC", Type, Con, Msg);
		    ComChestCount++;

		}

		progress = (double) (ChanceCount + ComChestCount) / TotalCards * 100;
		bar.setValue ((int) progress);


	    }
	    if (progress == 100)
	    {
		Finished = true;
	    }
	    else
	    {
		showMsg ("Some of the game's components, Cards, cannot be found or is missing," + "\n" + "check your database for errors.");
		System.exit (0);
	    }

	}
	catch (SQLException ex)
	{
	    showMsg ("SQLException: " + ex.getMessage ());
	}

    }


    Card [] getChanceCards ()
    {
	return ChanceCard;

    }


    Card [] getComChestCards ()
    {
	return ComChestCard;
    }


    public static void showMsg (String text)
    {
	JOptionPane.showMessageDialog (null, text);
    }


    public static void main (String [] args)
    {
	new LoadObjects ();

    } // main method
} // LoadObjects class


