import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class Card extends JDialog implements ActionListener
{
    private String CardGroup;
    private String CardType;
    private String CardCons;
    private String CardMsg;
    private boolean inUse = false;

    Font LargeFont = new Font ("Arial", 1, 25);
    Font MedFont = new Font ("Arial", 1, 18);
    Font SmallFont = new Font ("Arial", 1, 17);

    JPanel TitlePanel = new JPanel (new FlowLayout ());
    JPanel MsgPanel = new JPanel (new FlowLayout ());
    JPanel ButtonPanel = new JPanel (new FlowLayout ());

    JLabel TitleLabel = new JLabel ("Title");
    JLabel MsgLabel = new JLabel ("Message");
    Color CardColour;

    JButton ExitButton = new JButton ("Continue");

    boolean busy = true;

    Card ()
    {
    }


    Card (String g,
	    String t,
	    String c,
	    String m)
    {
	CardGroup = g;
	CardType = t;
	CardCons = c;
	CardMsg = m;
	if (CardGroup.equals ("C"))
	{
	    CardColour = new Color (255, 255, 170);
	    CardGroup = "Chance";
	}
	else
	{
	    CardColour = new Color (250, 200, 240);
	    CardGroup = "Community Chest";
	}
	initPanel ();
    }


    public void actionPerformed (ActionEvent e)
    {

	if (e.getSource() == ExitButton)
	{
	busy=false;
	    dispose ();
	}
    }


    public void setInUse (boolean b)
    {
	inUse = b;
    }


    public void stillBusy ()
    {

    }


    public void showCard ()
    {
	setTitle ("Monopoly");
	setBounds (70, 70, 270, 300);

	show ();
    }


    String getType ()
    {
	return CardType;
    }


    String getCons ()
    {
	return CardCons;
    }


    void initPanel ()
    {

	getContentPane ().setBackground (CardColour);
	getContentPane ().setLayout (new BorderLayout (1, 1));

	ExitButton.addActionListener (this);
	TitleLabel.setFont (LargeFont);
	TitleLabel.setHorizontalAlignment (JLabel.CENTER);
	TitleLabel.setVerticalAlignment (JLabel.CENTER);
	TitlePanel.setBackground (CardColour);
	TitleLabel.setText (CardGroup);
	TitlePanel.add (TitleLabel);

	MsgLabel.setFont (MedFont);
	MsgLabel.setText ("<HTML>" + CardMsg + "</HTML>");
	MsgPanel.add (MsgLabel);
	MsgPanel.setBackground (CardColour);
	ExitButton.setBackground (CardColour);
	ButtonPanel.setBackground (CardColour);
	      
	ButtonPanel.add (ExitButton);

	getContentPane ().add (TitlePanel, BorderLayout.NORTH);
	getContentPane ().add (MsgPanel, BorderLayout.CENTER);
	getContentPane ().add (ButtonPanel, BorderLayout.SOUTH);


    }
}
