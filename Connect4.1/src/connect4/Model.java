package connect4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Model {
	//What color on the board
	private static String[][] WColor;
	//x,y position of all blocks
	private int[][] positionX, positionY;
	private String[] PlayerColor = { "Blue", "Red" };
	//first player
	private int first;
	private int player;
	private int mode=2;
	//initiate the board with all white
	//generate the first player
	public Model() {
		WColor = new String[7][6];
		positionX = new int[7][6];
		positionY = new int[7][6];
		// initialize
		int x = 0, y = 0;
		for (int i = 150; i <= 750; i += 100) {
			for (int j = 100 ; j <= 600; j += 100) {
				WColor[x][y] = "White";
				positionX[x][y] = i;
				positionY[x][y] = j;
				y++;
			}
			y = 0;
			x++;
		}
		player = (int) (Math.random() * 2);
		// player = 1 is blue, player = 2 is red - bill
		if (player < 1) {
			player = 0;
		} else {
			player = 1;
		}
		first=player;
	}
	/**
	 * change the game mode
	 * @param m m=1 on single player, m=2 on two players
	 */
	public void changeMode(int m){
		mode=m;
	}
	/**
	 * get the game mode
	 * @return 1 on single player, 2 on two players
	 */
	public int getMode(){
		return mode;
	}
	/**
	 * 
	 * @return All color in chess board
	 * 
	 */
	public String[][] allColor() {
		return WColor;
	}

	/**
	 * 
	 * @param x x index in the array
	 * @param y y index in the array
	 * @return return the color of the index. 
	 */
	public String getColor(int x, int y){
		return WColor[x][y];
	}


	/**
	 * 
	 * @return get the x positions
	 */
	public int[][] getX() {
		return positionX;
	}

	/**
	 * 
	 * @return get the y positions
	 */
	public int[][] getY() {
		return positionY;
	}


	/**
	 * 
	 * @param x the index x 
	 * @param y the index y
	 * 
	 */
	public void setColor(int x, int y) {
		if (mode==2){
			WColor[x][y] = PlayerColor[player];
			if (player == 0) {
				player = 1;
			} else {
				player = 0;
			}
		}else{
			WColor[x][y] = PlayerColor[0];
		}
	}

	// set up this method to check if the 4 are the same so that we could decide
	// if this color is the winner
	private boolean helpCheck(String a, String b, String c, String d) {
		return a.equals(b) && a.equals(c) && a.equals(d);
	}


	/**
	 * 
	 * @return 	
	winner[0] as the determinate;
	winner[0]== 1 as Red;
    winner[0]== 2 as Blue;
	winner[0]== 0 as no winner yet;
	winner[0]== 3 as tie
	 */
	public int[] winnerCheck() {
		//winner field 1-4store the Xs, 5-8store the Ys
		int[] winner= new int[9]; 
		// vertical check if the following 3 items contains the same color
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 3; y++) {
				if (!WColor[x][y].equals("White")
						&& helpCheck(WColor[x][y], WColor[x][y + 1],
								WColor[x][y + 2], WColor[x][y + 3]) == true) {
					if (WColor[x][y].equals("Red")){
						winner[0]=1;
					}else{
						winner[0]=2;
					}
					int n=0;
					for (int i=1;i<9;i+=2){
						winner[i]=x;
						winner[i+1]=y+n;
						n++;
					}
					return winner;
				}
			}
		}
		// horizontal check if the following 3 items contains the same color
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 4; x++) {
				if (!WColor[x][y].equals("White")
						&& helpCheck(WColor[x][y], WColor[x + 1][y],
								WColor[x + 2][y], WColor[x + 3][y]) == true) {
					if (WColor[x][y].equals("Red")){
						winner[0]=1;
					}else{
						winner[0]=2;

					}
					int n=0;
					for (int i=1;i<9;i+=2){
						winner[i]=x+n;
						winner[i+1]=y;
						n++;
					}
					return winner;
				}
			}
		}
		// angled check if the 3 items at right bottom and left top direction contains the
		// same color 
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 3; y++) {
				if (!WColor[x][y].equals("White")
						&& helpCheck(WColor[x][y], WColor[x + 1][y + 1],
								WColor[x + 2][y + 2], WColor[x + 3][y + 3]) == true) {
					if (WColor[x][y].equals("Red")){
						winner[0]=1;
					}else{
						winner[0]=2;
					}
					int n=0;
					for (int i=1;i<9;i+=2){
						winner[i]=x+n;
						winner[i+1]=y+n;
						n++;
					}
					return winner;
				}
			}
		}
		// angled check if the 3 items at left bottom and right top direction contains the
		// same color
		for(int x = 3; x < 7; x++){
			for (int y = 0; y < 3; y++) {
				if (!WColor[x][y].equals("White")
						&& helpCheck(WColor[x][y], WColor[x -1][y + 1],
								WColor[x -2][y + 2], WColor[x- 3][y + 3]) == true) {
					if (WColor[x][y].equals("Red")){
						winner[0]=1;
					}else{
						winner[0]=2;
					}
					int n=0;
					for (int i=1;i<9;i+=2){
						winner[i]=x-n;
						winner[i+1]=y+n;
						n++;
					}
					return winner;
				}
			}
		}

		// full check to check if this game is tie or no winner yet, if the
		// whole game board is fullfilled, but still no winner, it would be a
		// tie. If it's not fullfilled yet, then there is no winner yet
		boolean full = true;
		for (int i = 0; i < 7; i++) {
			full = full && !WColor[i][0].equals("White");
			if (WColor[i][0].equals("White") == true) {
				winner[0]=0;
				return winner;
			}
		}
		if (full == true){
			winner[0]=3;
			return winner;
		}
		// not full therefore continue
		winner[0]=0;
		return winner;
	}

	/**
	 * 
	 * @param x  the x index
	 * @param y  the y index 
	 * @return   true if white  false if not
	 */
	public boolean whiteCheck(int x,int y){
		if (WColor[x][y].equals("White")){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @param x  x index
	 * @param y y index 
	 * @return true if is nothing floating on air else false
	 */

	public boolean validCheck(int x, int y) {
		if (y == 5 && WColor[x][y].equals("White")) {
			return true;
		} else {
			if (WColor[x][y].equals("White")
					&& !WColor[x][y + 1].equals("White")) {
				return true;
			} else {
				return false;
			}
		}
	}


	/**
	 * 
	 * @return get the first player 
	 */
	public  int getFirst(){
		return first+1;
	}

	/**
	 * Load the current state of the board from txt file
	 */
	public void load(){

		try{
			File file=new File("data/save.txt");
			Scanner input =new Scanner(file);
			for(int i=0;i<7;i++){
				WColor[i]=input.nextLine().split(",");
			}
			player=Integer.parseInt(input.nextLine());
			first=Integer.parseInt(input.nextLine());
			mode=Integer.parseInt(input.nextLine());
		}catch(FileNotFoundException e){

		}
	}
	/**
	 * Save the current state of the board into txt file
	 */
	public void save(){
		try {
			PrintStream output = new PrintStream(new File("data/save.txt"));
			for(int i=0;i<7;i++){
				output.println(toString(WColor[i]));
			}
			output.println(player);
			output.println(first);
			output.println(mode);

		} catch (FileNotFoundException e) {
		}
	}
	/**
	 * 
	 * @param x the set of String[] 
	 * @return return the array in string type 
	 */
	public static String toString(String[] x) { 
		String result = "";
		for (int i = 0; i < x.length; i++) {
			if (i!=x.length-1){
				result += x[i].toString()+",";
			}else{
				result += x[i].toString();
			}

		}

		return result;

	}
	//-----------------------------------------AI Part------------------
	//set ai always as player 1
	private final int ai=1;
	private List<String> all=new List();
	private String value;
	String[] legalRange= new String[7];

	//make random move until there is one position available
	//which will cause the game to end
	//then the AI will pick that position
	/**
	 * run the AI
	 */
	public void runAI(){
		aiCheck();
		for(int i=0;i<7;i++){
			for (int n=0;n<6;n++){
				if(n==0&&!whiteCheck(i,n)){
					legalRange[i]="false";break;
				} else if(validCheck(i,n)&&whiteCheck(i,n)){
					legalRange[i]=i+","+n;
				}
			}
		}
		if (all.isEmpty()){
			int r=(int)(Math.random()*6+0.5);
			while(legalRange[r].equals("false")){
				r=(int)(Math.random()*6+0.5);
			}
			String[] index=legalRange[r].split(",");
			WColor[Integer.parseInt(index[0])][Integer.parseInt(index[1])]=PlayerColor[ai];
		}else{
			boolean b=false;
			//check if ai can win on next move
			for(int i=0;i<7;i++){
				b=all.contains("Red"+","+legalRange[i]);
				if (b){
					String[] index=legalRange[i].split(",");
					WColor[Integer.parseInt(index[0])][Integer.parseInt(index[1])]=PlayerColor[ai];
					return;
				}
			}
			//check if player can win on next move 
			for(int i=0;i<7;i++){
				b=all.contains("Blue"+","+legalRange[i]);
				if (b){
					String[] index=legalRange[i].split(",");
					WColor[Integer.parseInt(index[0])][Integer.parseInt(index[1])]=PlayerColor[ai];
					return;
				}
			}
			//no one can win next move
			int r=(int)(Math.random()*6+0.5);
			while(legalRange[r].equals("false")){
				r=(int)(Math.random()*+0.5);
			}
			String[] index=legalRange[r].split(",");
			WColor[Integer.parseInt(index[0])][Integer.parseInt(index[1])]=PlayerColor[ai];
		}
	}
	private boolean helpCheckAI(String a, String b, String c) {
		return a.equals(b) && a.equals(c);
	}
	
	/**check if there is three same color are connected
	 */
	private void aiCheck(){
		
		// vertical check if the following 2 items contains the same color
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 4; y++) {
				if (!WColor[x][y].equals("White")
						&& helpCheckAI(WColor[x][y], WColor[x][y + 1],
								WColor[x][y + 2]) == true) {
					if (WColor[x][y].equals("Red")){
						value="Red";
					}else{
						value="Blue";
					}
					if(y!=0)all.push(value+","+x+","+(y-1));
					if(y!=3)all.push(value+","+x+","+(y+3));
				}
			}
		}
		// horizontal check if the following 2 items contains the same color
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 5; x++) {
				if (!WColor[x][y].equals("White")
						&& helpCheckAI(WColor[x][y], WColor[x + 1][y],
								WColor[x + 2][y]) == true) {
					if (WColor[x][y].equals("Red")){
						value="Red";
					}else{
						value="Blue";

					}
					if(x!=0)all.push(value+","+(x-1)+","+y);
					if(x!=4)all.push(value+","+(x+3)+","+y);
				}
			}
		}
		// angled check if the 2 items at right bottom and left top direction contains the
		// same color 
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				if (!WColor[x][y].equals("White")
						&& helpCheckAI(WColor[x][y], WColor[x + 1][y + 1],
								WColor[x + 2][y + 2]) == true) {
					if (WColor[x][y].equals("Red")){
						value="Red";
					}else{
						value="Blue";
					}
					if(x!=0&&y!=0)all.push(value+","+(x-1)+","+(y-1));
					if(x!=4&&y!=3)all.push(value+","+(x+3)+","+(y+3));
				}
			}
		}
		// angled check if the 2 items at left bottom and right top direction contains the
		// same color
		for(int x = 2; x < 7; x++){
			for (int y = 0; y < 4; y++) {
				if (!WColor[x][y].equals("White")
						&& helpCheckAI(WColor[x][y], WColor[x -1][y + 1],
								WColor[x -2][y + 2]) == true) {
					if (WColor[x][y].equals("Red")){
						value="Red";
					}else{
						value="Blue";
					}
					if(x!=6&&y!=0)all.push(value+","+(x+1)+","+(y-1));
					if(x!=2&&y!=3)all.push(value+","+(x-3)+","+(y+3));
				}
			}
		}
	}

}

