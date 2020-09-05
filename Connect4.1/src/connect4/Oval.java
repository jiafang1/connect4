package connect4;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

//draw an oval that can be highlighted
public class Oval extends JPanel{

	private String current="White";
	private int x;

	/**
	 * 
	 * @param a as identity
	 * @param p as current player 
	 */
	public Oval(int a, String p){

		current=p;
		x=a;
		
		//set system property for color
		System.setProperty("Blue", "0X0033FF");
		System.setProperty("Red", "0XFF0000");
		System.setProperty("White", "0XFFFFFF");
	}
	
	/**
	 * 
	 * @param p the current player color
	 */
	public void update(String p){
		current=p;
	}
	
	
	public void paintComponent(Graphics g){
		 super.paintComponent(g);
		 //highlight
		 if (current .equals( "Blue")){
			 g.setColor(Color.green);
			 g.fillOval(25,195,110,110);
		 }else if (current .equals( "Red")){
			 g.setColor(Color.green);
			 g.fillOval(25,195,110,110);
		 }
		 //define color
		 if (x==1){
			 g.setColor(Color.getColor("Blue"));
		 }else if (x==2){
			 g.setColor(Color.getColor("Red"));
		 }
		 //draw red or blue
		 g.fillOval(30,200,100,100);
	 }
	
}
