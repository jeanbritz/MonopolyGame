import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// The "Game" class.
public class Game implements Runnable
{
    JFrame f = new JFrame ();
    Game ()
    {
	f.setSize (300, 300);
	f.show ();
    }


    public void run ()
    {

    }


    public static void main (String[] args)
    {
	new Game ();
    } // main method
} // Game class
