import java.awt.Point;
import java.awt.Color;
import java.util.StringTokenizer;
public class Property
{

    private int Id;
    private String Title;
    private String TypeOfProperty;
    private String CostOfProperty;
    private String HouseCost;
    private int HouseCount;
    private String Mortage;
    private String Rgb;
    private Point Location;
    private int Owner_Player_Id;
    private boolean AlreadyOwned = false;
    private boolean isOwnable = false;
    Tariff t[] = new Tariff [6];

    Property ()
    {
    }


    Property (String a, String t, String q, String p, String h, String m, String r, int x, int y)
    {
	Id = Integer.parseInt (a) - 1;
	Title = t.trim ();
	TypeOfProperty = q;
	CostOfProperty = p;
	HouseCost = h;
	Mortage = m;
	Rgb = r;
	Location = new Point (x, y);
	if (Mortage != null)
	    isOwnable = true;



    }


    int getId ()
    {
	return Id;
    }


    String getTitle ()
    {
	return Title;
    }


    String getType ()
    {
	return TypeOfProperty;
    }


    String getCaption ()
    {
	return Title + " " + TypeOfProperty;
    }


    String getPropCost ()
    {
	return CostOfProperty;
    }


    String getHouseCost ()
    {
	return HouseCost;
    }


    void setHouseCount (int h)
    {
	HouseCount = h;
    }


    int getHouseCount ()
    {
	return HouseCount;
    }


    String getMortageValue ()
    {
	return Mortage;
    }


    String getRGB ()
    {
	return Rgb;
    }


    Color getRGBColor ()
    {
	if (Rgb == null)
	{
	    return Color.white;
	}
	else
	{
	    //  System.out.println (Rgb);
	    StringTokenizer ST = new StringTokenizer (Rgb, ",");
	    int r = Integer.parseInt (ST.nextToken ());
	    int g = Integer.parseInt (ST.nextToken ());
	    int b = Integer.parseInt (ST.nextToken ());

	    return new Color (r, g, b);
	}
    }


    void setLocation (Point p)
    {
	Location = p;
    }


    Point getLocation ()
    {
	return Location;
    }


    void setOwnedProperty (boolean b, int p)
    {
	AlreadyOwned = b;
	Owner_Player_Id = p;
    }


    int getOwnerId ()
    {
	return Owner_Player_Id;
    }


    boolean isOwnable ()
    {
	return isOwnable;
    }


    Tariff getTariff (int index)
    {
	return t [index];
	
    }


    int getTotalRent ()
    {
	int total = Integer.parseInt(t[0].getCost());
	return total;
    }


    boolean isAlreadyOwned ()
    {
	return AlreadyOwned;
    }


    public String toString ()
    {

	return getCaption ();
    }
}
