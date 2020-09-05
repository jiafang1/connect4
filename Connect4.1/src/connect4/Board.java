package connect4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Board extends JFrame {
	//board constant
	private final int boardLength =1030;
	private final int boardHeight = 800;
	private JPanel boardPanel = new JPanel();
	private static JLabel text=new JLabel();
	private static JTextArea  Fplayer;
	private static JButton save,load,twoPlayer,onePlayer;
	
	//class objects
	private static Board board;
	private static Chess chess;
	private static Model data;
	private static Oval leftO,rightO;
	//private variables
	private static String show="Two Player Mode";
	private static int mode=2; 
	private static int counter=0;
	Board(){
		//title
		super("Connect Four");
		//size and exit
		this.setBounds(0,0, boardLength, boardHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//set layout
		boardPanel.setLayout(new BorderLayout());
		//generate new data
		data=new Model();
		//new chess board and add to center of border layout
		chess= new Chess(data.allColor(),data.getX(),data.getY());
		boardPanel.add(chess,BorderLayout.CENTER);
		//textbox and button
		Fplayer= new JTextArea(show);
		save= new JButton("Save Game");
		load= new JButton("Load Game");
		onePlayer= new JButton("Single Player");
		twoPlayer= new JButton("Two Players");
		//add the panel to frame
		super.add(boardPanel);
		
	}
	

	public static void main(String[] args){
		//new board
		board = new Board();
		
		//setup left side
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left,  BoxLayout.Y_AXIS));
		
		leftO=new Oval(1, "White");
		left.add(leftO);
		left.add(Fplayer);
		left.add(text);
		left.add(onePlayer);
		left.add(twoPlayer);
		left.add(save);
		left.add(load);
		left.setPreferredSize(new Dimension(150, 750));
		
		onePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		onePlayer.setMaximumSize(new Dimension(150, 30));
		onePlayer.setMinimumSize(new Dimension(150, 30));
		
		twoPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		twoPlayer.setMaximumSize(new Dimension(150, 30));
		twoPlayer.setMinimumSize(new Dimension(150, 30));
		
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		save.setMaximumSize(new Dimension(150, 30));
		save.setMinimumSize(new Dimension(150, 30));
		

		load.setAlignmentX(Component.CENTER_ALIGNMENT);
		load.setMaximumSize(new Dimension(150, 30));
		load.setMinimumSize(new Dimension(150, 30));
		
		Fplayer.setMinimumSize(new Dimension(150, 100));
		Fplayer.setMaximumSize(new Dimension(150, 100));
		Fplayer.setEditable(false);
		leftO.setMaximumSize(new Dimension(300, 350));
		
		//setup right side
		JPanel right = new JPanel();
		rightO=new Oval(2,"White");
		right.setLayout(new BorderLayout());
		right.add(rightO);

		right.setPreferredSize(new Dimension(150, 750));
		rightO.setMaximumSize(new Dimension(300, 350));
		
		//add the main frame: board
		board.add(left,BorderLayout.LINE_START);
		board.add(right,BorderLayout.LINE_END);
		board.setVisible(true);
		
		
		// action response to mouse click
		board.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // This is only called when the user press the mouse button.
            	//get the point clicked
            	Point p = e.getPoint();
            	//convert to index x,y
            	int a =Math.round((p.x - 58)/(100)-1);
            	int b =Math.round((p.y - 30)/(100)-1);
            	//check valid move
            	if (a>=0 && b>=0){
            		if (150<p.x && p.x<858 && 130<p.y && p.y<730 && data.validCheck(a, b)){
            			//setColor to the player's color if there is no winner yet(counter==0)
            			if(counter==0){
            				data.setColor(a,b);
            				board.repaint();
            				chess.update(data.allColor(),data.getX(),data.getY());
            				chess.repaint();
            				counter=check();
            				if (counter==0)
            				if (mode==2){
            					//indicate the next player by highlight in green
            					if (data.getColor(a,b).equals("Blue")){
            						rightO.update("Red");
            						leftO.update("White");
            					}else if(data.getColor(a,b).equals("Red")){
            						leftO.update("Blue");
            						rightO.update("White");
            					}
            					
            				}else{
            					if (counter==0){
            						leftO.update("Blue");
            						rightO.update("White");
            						data.runAI();
            					}
            				}
						}
            			if (counter==0){
            				board.repaint();
            				chess.update(data.allColor(),data.getX(),data.getY());
            				chess.repaint();
            				counter=check();
            			}
            		}
            	}
            	

            	//save the game in file
            	save.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						data.save();
						Fplayer.setText(show+"\nGame Saved");
						board.repaint();
						return;
			        }
				});
            	
            	//load the game from file
            	load.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						data.load();
						mode=data.getMode();
						if(mode==2){
							show="Two Player Mode";
							Fplayer.setText(show+"\nGame Loaded");
						}else{
							show="Single Player Mode";
							Fplayer.setText(show+"\nGame Loaded");
						}
						board.repaint();
						chess.reset();
						chess.update(data.allColor(),data.getX(),data.getY());
						chess.repaint();
						rightO.update("White");
						leftO.update("White");
			        }
				});
            	
            	//restart twoPlayer game
            	twoPlayer.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						counter=0;
						data= new Model();
						mode=2;
						data.changeMode(2);
						show="Two Player Mode";
						Fplayer.setText(show+"\nNew Game");
						board.repaint();
						chess.reset();
						chess.update(data.allColor(),data.getX(),data.getY());
						
						chess.repaint();
						rightO.update("White");
						leftO.update("White");
						return;
			        }
				});
            	
            	//restart onePlayer game
            	onePlayer.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						counter=0;
						data= new Model();
						mode=1;
						data.changeMode(1);
						show="Single Player Mode";
						Fplayer.setText(show+"\nNew Game");
						//if the computer play first
						if(data.getFirst()==2){
							data.runAI();
						}
						board.repaint();
						chess.reset();
						chess.update(data.allColor(),data.getX(),data.getY());
						chess.repaint();
						rightO.update("White");
						leftO.update("White");
						return;
			        }
				});
            	
            	//update to view
            	chess.update(data.allColor(),data.getX(),data.getY());
                
                //show updated contents
                board.repaint();
                
            }
        });
		
	}
	public static int check(){
		//if winner[0] ==1 red win
		// save the winner array in dummy array
		int[] dummy=data.winnerCheck();
		
		if (dummy[0]==1){
			// to avoid assigning values after the existence of winner
			int[] x=new int[4];
			int[] y=new int[4];
			int n=0;
			// split the winner detail into two array
			// x as x index y as y index 
			for (int i=1;i<9;i+=2){
				x[n]=dummy[i];
				y[n]=dummy[i+1];
				n++;
			}
			chess.win( x, y);
			Fplayer.setText(show+"\nRed WINS");
			rightO.update("White");
			leftO.update("White");
			return 1;
		}else if (dummy[0]==2){
		//if winner[0] ==2 blue win

			int[] x=new int[4];
			int[] y=new int[4];
			int n=0;
			// split the winner detail into two array
			// x as x index y as y index 
			for (int i=1;i<9;i+=2){
				x[n]=dummy[i];
				y[n]=dummy[i+1];
				n++;
			}
			chess.win(x, y);
			Fplayer.setText(show+"\nBLUE WINS");
			rightO.update("White");
			leftO.update("White");
			return 2;
		}else if (dummy[0]==3){
		//if winner[0] ==3 game drawn

			Fplayer.setText(show+"\nGame Drawn");
			rightO.update("White");
			leftO.update("White");
			return 3;
		}else{
			//if winner[0] ==0 game drawn
			Fplayer.setText(show+"\nGame In Progress");
			return 0;
		}
	}
}