package connect4;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

//the chess board and fill
public class Chess extends JPanel{
	
	//What color on the board
	private String[][] WColor;
	private int [][] positionX,positionY;
	//coordination of winning chess position
	private int []a,b;
	//winning chess color
	private String c;


	/**
	 * 
	 * @param c the array of all colors
	 * @param a the x index
	 * @param b the y index
	 */
	public Chess(String[][] c, int[][] a,int[][] b){
		WColor=c;
		positionX=a;
		positionY=b;

		
		//set system property for color
		System.setProperty("Blue", "0X0033FF");
		System.setProperty("Red", "0XFF0000");
		System.setProperty("White", "0XFFFFFF");
	}
	
	//@param get the passed value of color, position 
	/**
	 * 
	 * @param c the array of all colors
	 * @param a the x index
	 * @param b the y index
	 */
	public void update(String[][] c, int[][] a,int[][] b){
		WColor=c;
		positionX=a;
		positionY=b;
	}
	
	/**
	 * 
	 * @param a as x index
	 * @param b as y index
	 */
	public void win(int[] a,int[] b){
		this.a=a;
		this.b=b;
	}
	
	/**
	 * reset the winner x y index as null
	 */
	public void reset(){
		this.a=null;
		this.b=null;
	}
	
	public void paintComponent(Graphics g){
		 super.paintComponent(g);
		 //last line
		 g.drawLine(700, 0, 700, 130);
		 
		 for(int x =0; x<= 600; x += 100){
			 //lines above the board
			 g.drawLine(x, 0 , x, 130);
			 //all the square blocks and circles
			for(int y= 100; y <= 600; y+=100){
				 if(!coordination((int) (x/(100)),(int) (y/(100)-1)) ) {
					 g.setColor(Color.getColor(WColor[(int) (x/(100))][(int) (y/(100)-1)]));
					 g.fillOval(x,y,100,100);
				 }else if (coordination((int) (x/(100)),(int) (y/(100)-1))){
					 
					 // print the filled oval and add black oval inside  
					 g.setColor(Color.getColor(WColor[(int) (x/(100))][(int) (y/(100)-1)]));
					 g.fillOval(x,y,100,100);
					 g.setColor(Color.black);
					 g.fillOval(x+35,y+35,30,30);
				 }
				
				 g.setColor(Color.black);
				 g.drawRect(x,y,100,100);
				 g.drawOval(x,y,100,100);
				 
			 }
			
	
		 }  
	 }
	//check if it is at the winner coordination x,y
	private boolean coordination(int x, int y){
		if (a==null)return false;
		boolean bool=false;
		for (int i=0;i<4;i++){
			bool=bool || (a[i]==x && b[i]==y);
		}
		return bool;
		
	}
	
}
