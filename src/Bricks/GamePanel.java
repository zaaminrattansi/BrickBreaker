package Bricks;


// Import needed libraries
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import javax.swing.JFrame;




public class GamePanel extends JPanel implements Runnable, KeyListener, ActionListener {
	
	
	

	//Program Variables 
	
	public static int WIDTH = 830;
	public static int HEIGHT = 650;
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 50;
	private double averageFPS;
	
	// Ball Variables
	private int ballX = 415;
	private int ballY = 500;
	
	private int bVelX = 7;
	private int bVelY = -12;
	
	//Player Variables
	public int playX = 375;
	public int playY = 560;
	
	private int velX;
	private int velY;
	
	int life = 3;
	
	// Bricks variables 
	boolean [] collision = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	int totalBricks = 24;

	// Game Run boolean, when true, game runs
	public boolean gameRun = false;

// Method used to restore all the variables after the game is done, program returns to main menu	
public void restore() {
		
		// Ball Variables
		ballX = 415;
		ballY = 500;
		
		bVelX = 7;
		bVelY = -12;
		
		//Player Variables
		playX = 375;
		playY = 560;
		
		velX = 0;
		velY = 0;
		
		life = 3;
		
		// Bricks variables 
		for (int x = 0; x < collision.length; x++) {
		collision[x] = false;
		}
		totalBricks = 24;

		// gameRun 
		gameRun = false;
		
	}
	
	// Method used to initialize the Game screen (size, etc)
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();	
	}
	
	// Necessary Functions In order to run a JPanel game
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
	}
		
		
	
	
		
		
	
	// Method that runs game, the run method is from the runnable interface, automatically runs when program runs, does not need to be called on
	public void run() {
		
		
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		
		// Frame Rate variables 
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = 1000 / FPS;
		
		
		
		//GAME LOOP
		running = true;
		
		while (running) {
			
			startTime = System.nanoTime();
			
			// call on the methods that are needed to run the game
			gameUpdate();
			gameRender();
			gameDraw();	
			
			
			// Setting a set Frame rate 
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			
			waitTime = targetTime - URDTimeMillis;
			
			try {
				Thread.sleep(waitTime);
			}
			catch(Exception e){
			}
			
			totalTime += System.nanoTime() -  startTime;
			frameCount++;
			if(frameCount == maxFrameCount) {
				averageFPS = 1000.0 / ((totalTime/ frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
				
			}
			
			
			
			
		}

	}
	
	
	    
	    
	    
	// Method to make bricks
	public void bricks (int brickX, int brickY, int colour) {
		
		g.setColor(new Color(colour, 0, 0));	
	    g.fillRect(brickX, brickY, 70, 40);
	    collision (brickX, brickY);
	
	}
	    
	// Method for collision between bricks and ball
	public void collision (int brickX, int brickY) {
		
		if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(brickX, brickY, 70, 1))) {
	    	bVelY = -bVelY;
	    	
	    		
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(brickX, brickY + 40, 70, 1))) {
	    	bVelY = -bVelY;
	    	
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(brickX, brickY, 1, 40))) {
	    	bVelX = -bVelX;
	    	
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(brickX + 70, brickY, 1, 40))) {
	    	bVelX = -bVelX;
	    	
		}
	}
	    
		
	
	
	
	// this method is empty, but is inherited by the runnable class, it is required to run the game and keep updating the screen
	private void gameUpdate() {
		
		
	}
	// the method where all the game code is programmed
	private void gameRender(){
		
		
		
		
		
		
		// Title Screen 
		if (gameRun == false) {
		// Background
		g.setColor(new Color(230, 250, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
			
		Font h = new Font ("Dialog", Font.PLAIN, 60);
		g.setColor(new Color(0, 0, 0));
	    g.setFont(h);
		g.drawString("BREAKING OF THE BRICKS", 45, 170);
		
		Font m = new Font ("Dialog", Font.PLAIN, 30);
	    g.setFont(m);
		g.drawString("Press Enter to Play", 290, 470);
		
		}
		
		
		
		// If enter is pressed, and game run is true, run the game
		if (gameRun) {
		
		// Background
		g.setColor(new Color(210, 220, 225));
		g.fillRect(0, 0, WIDTH, HEIGHT);	
		
		// Collision with brick, brick must not be on the screen, and collision detection must be turned off when collision is true (the brick was hit) until line 841 ( for all the bricks) 	
		if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 190, 70, 1)) && collision[0] == false) {
			bVelY = -bVelY;
	    	collision[0] = true;	
	    	totalBricks--;
	    	
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 190 + 40, 70, 1)) && collision[0] == false) {
	    	bVelY = -bVelY;
	    	collision[0] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 190, 1, 40)) && collision[0] == false) {
	    	bVelX = -bVelX;
	    	collision[0] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100 + 70, 190, 1, 40)) && collision[0] == false) {
	    	bVelX = -bVelX;
	    	collision[0] = true;
	    	totalBricks--;
		}
	    if (collision[0] == false) {
	    bricks(100, 190, 170);
	    }
		
	    
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 190, 70, 1)) && collision[1] == false) {
	    	bVelY = -bVelY;
	    	collision[1] = true;	
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 190 + 40, 70, 1)) && collision[1] == false) {
	    	bVelY = -bVelY;
	    	collision[1] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 190, 1, 40)) && collision[1] == false) {
	    	bVelX = -bVelX;
	    	collision[1] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180 + 70, 190, 1, 40)) && collision[1] == false) {
	    	bVelX = -bVelX;
	    	collision[1] = true;
	    	totalBricks--;
		}
	    if (collision[1] == false) {
		bricks(180, 190, 170);
	    }
		
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 190, 70, 1)) && collision[2] == false) {
	    	bVelY = -bVelY;
	    	collision[2] = true;	
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 190 + 40, 70, 1)) && collision[2] == false) {
	    	bVelY = -bVelY;
	    	collision[2] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 190, 1, 40)) && collision[2] == false) {
	    	bVelX = -bVelX;
	    	collision[2] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260 + 70, 190, 1, 40)) && collision[2] == false) {
	    	bVelX = -bVelX;
	    	collision[2] = true;
	    	totalBricks--;
		}
	    if (collision[2] == false) {
		bricks(260, 190, 170);
	    }
	    
	    
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 190, 70, 1)) && collision[3] == false) {
	    	bVelY = -bVelY;
	    	collision[3] = true;	
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 190 + 40, 70, 1)) && collision[3] == false) {
	    	bVelY = -bVelY;
	    	collision[3] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 190, 1, 40)) && collision[3] == false) {
	    	bVelX = -bVelX;
	    	collision[3] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340 + 70, 190, 1, 40)) && collision[3] == false) {
	    	bVelX = -bVelX;
	    	collision[3] = true;
	    	totalBricks--;
		}
	    if (collision[3] == false) {
		bricks(340, 190, 170);
	    }
	    
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 190, 70, 1)) && collision[4] == false) {
	    	bVelY = -bVelY;
	    	collision[4] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 190 + 40, 70, 1)) && collision[4] == false) {
	    	bVelY = -bVelY;
	    	collision[4] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 190, 1, 40)) && collision[4] == false) {
	    	bVelX = -bVelX;
	    	collision[4] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420 + 70, 190, 1, 40)) && collision[4] == false) {
	    	bVelX = -bVelX;
	    	collision[4] = true;
	    	totalBricks--;
		}
	    if (collision[4] == false) {
		bricks(420, 190, 170);
	    }
	    
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 190, 70, 1)) && collision[5] == false) {
	    	bVelY = -bVelY;
	    	collision[5] = true;	
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 190 + 40, 70, 1)) && collision[5] == false) {
	    	bVelY = -bVelY;
	    	collision[5] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 190, 1, 40)) && collision[5] == false) {
	    	bVelX = -bVelX;
	    	collision[5] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500 + 70, 190, 1, 40)) && collision[5] == false) {
	    	bVelX = -bVelX;
	    	collision[5] = true;
	    	totalBricks--;
		}
	    if (collision[5] == false) {
		bricks(500, 190, 170);
	    }
	    
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 190, 70, 1)) && collision[6] == false) {
	    	bVelY = -bVelY;
	    	collision[6] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 190 + 40, 70, 1)) && collision[6] == false) {
	    	bVelY = -bVelY;
	    	collision[6] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 190, 1, 40)) && collision[6] == false) {
	    	bVelX = -bVelX;
	    	collision[6] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580 + 70, 190, 1, 40)) && collision[6] == false) {
	    	bVelX = -bVelX;
	    	collision[6] = true;
	    	totalBricks--;
		}
	    if (collision[6] == false) {
		bricks(580, 190, 170);
	    }
	    
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 190, 70, 1)) && collision[7] == false) {
	    	bVelY = -bVelY;
	    	collision[7] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 190 + 40, 70, 1)) && collision[7] == false) {
	    	bVelY = -bVelY;
	    	collision[7] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 190, 1, 40)) && collision[7] == false) {
	    	bVelX = -bVelX;
	    	collision[7] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660 + 70, 190, 1, 40)) && collision[7] == false) {
	    	bVelX = -bVelX;
	    	collision[7] = true;
	    	totalBricks--;
		}
	    if (collision[7] == false) {
		bricks(660, 190, 170);
	    }
		
		
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 140, 70, 1)) && collision[8] == false) {
	    	bVelY = -bVelY;
	    	collision[8] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 140 + 40, 70, 1)) && collision[8] == false) {
	    	bVelY = -bVelY;
	    	collision[8] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 140, 1, 40)) && collision[8] == false) {
	    	bVelX = -bVelX;
	    	collision[8] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100 + 70, 140, 1, 40)) && collision[8] == false) {
	    	bVelX = -bVelX;
	    	collision[8] = true;
	    	totalBricks--;
		}
	    if (collision[8] == false) {
		bricks(100, 140, 170);
	    }
		
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 140, 70, 1)) && collision[9] == false) {
	    	bVelY = -bVelY;
	    	collision[9] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 140 + 40, 70, 1)) && collision[9] == false) {
	    	bVelY = -bVelY;
	    	collision[9] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 140, 1, 40)) && collision[9] == false) {
	    	bVelX = -bVelX;
	    	collision[9] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180 + 70, 140, 1, 40)) && collision[9] == false) {
	    	bVelX = -bVelX;
	    	collision[9] = true;
	    	totalBricks--;
		}
	    if (collision[9] == false) {
		bricks(180, 140, 170);
	    }
	    
	    
		
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 140, 70, 1)) && collision[10] == false) {
	    	bVelY = -bVelY;
	    	collision[10] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 140 + 40, 70, 1)) && collision[10] == false) {
	    	bVelY = -bVelY;
	    	collision[10] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 140, 1, 40)) && collision[10] == false) {
	    	bVelX = -bVelX;
	    	collision[10] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260 + 70, 140, 1, 40)) && collision[10] == false) {
	    	bVelX = -bVelX;
	    	collision[10] = true;
	    	totalBricks--;
		}
	    if (collision[10] == false) {
		bricks(260, 140, 170);
	    }
	    
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 140, 70, 1)) && collision[11] == false) {
	    	bVelY = -bVelY;
	    	collision[11] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 140 + 40, 70, 1)) && collision[11] == false) {
	    	bVelY = -bVelY;
	    	collision[11] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 140, 1, 40)) && collision[11] == false) {
	    	bVelX = -bVelX;
	    	collision[11] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340 + 70, 140, 1, 40)) && collision[11] == false) {
	    	bVelX = -bVelX;
	    	collision[11] = true;
	    	totalBricks--;
		}
	    if (collision[11] == false) {
		bricks(340, 140, 170);
	    }
	   
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 140, 70, 1)) && collision[12] == false) {
	    	bVelY = -bVelY;
	    	collision[12] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 140 + 40, 70, 1)) && collision[12] == false) {
	    	bVelY = -bVelY;
	    	collision[12] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 140, 1, 40)) && collision[12] == false) {
	    	bVelX = -bVelX;
	    	collision[12] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420 + 70, 140, 1, 40)) && collision[12] == false) {
	    	bVelX = -bVelX;
	    	collision[12] = true;
	    	totalBricks--;
		}
	    if (collision[12] == false) {
		bricks(420, 140, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 140, 70, 1)) && collision[13] == false) {
	    	bVelY = -bVelY;
	    	collision[13] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 140 + 40, 70, 1)) && collision[13] == false) {
	    	bVelY = -bVelY;
	    	collision[13] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 140, 1, 40)) && collision[13] == false) {
	    	bVelX = -bVelX;
	    	collision[13] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500 + 70, 140, 1, 40)) && collision[13] == false) {
	    	bVelX = -bVelX;
	    	collision[13] = true;
	    	totalBricks--;
		}
	    if (collision[13] == false) {
		bricks(500, 140, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 140, 70, 1)) && collision[14] == false) {
	    	bVelY = -bVelY;
	    	collision[14] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 140 + 40, 70, 1)) && collision[14] == false) {
	    	bVelY = -bVelY;
	    	collision[14] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 140, 1, 40)) && collision[14] == false) {
	    	bVelX = -bVelX;
	    	collision[14] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580 + 70, 140, 1, 40)) && collision[14] == false) {
	    	bVelX = -bVelX;
	    	collision[14] = true;
	    	totalBricks--;
		}
	    if (collision[14] == false) {
		bricks(580, 140, 170);
	    }
		
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 140, 70, 1)) && collision[15] == false) {
	    	bVelY = -bVelY;
	    	collision[15] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 140 + 40, 70, 1)) && collision[15] == false) {
	    	bVelY = -bVelY;
	    	collision[15] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 140, 1, 40)) && collision[15] == false) {
	    	bVelX = -bVelX;
	    	collision[15] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660 + 70, 140, 1, 40)) && collision[15] == false) {
	    	bVelX = -bVelX;
	    	collision[15] = true;
	    	totalBricks--;
		}
	    if (collision[15] == false) {
	    bricks(660, 140, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 90, 70, 1)) && collision[16] == false) {
	    	bVelY = -bVelY;
	    	collision[16] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 90 + 40, 70, 1)) && collision[16] == false) {
	    	bVelY = -bVelY;
	    	collision[16] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100, 90, 1, 40)) && collision[16] == false) {
	    	bVelX = -bVelX;
	    	collision[16] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(100 + 70, 90, 1, 40)) && collision[16] == false) {
	    	bVelX = -bVelX;
	    	collision[16] = true;
	    	totalBricks--;
		}
	    if (collision[16] == false) {
		bricks(100, 90, 170);
	    }

	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 90, 70, 1)) && collision[17] == false) {
	    	bVelY = -bVelY;
	    	collision[17] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 90 + 40, 70, 1)) && collision[17] == false) {
	    	bVelY = -bVelY;
	    	collision[17] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180, 90, 1, 40)) && collision[17] == false) {
	    	bVelX = -bVelX;
	    	collision[17] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(180 + 70, 90, 1, 40)) && collision[17] == false) {
	    	bVelX = -bVelX;
	    	collision[17] = true;
	    	totalBricks--;
		}
	    if (collision[17] == false) {
		bricks(180, 90, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 90, 70, 1)) && collision[18] == false) {
	    	bVelY = -bVelY;
	    	collision[18] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 90 + 40, 70, 1)) && collision[18] == false) {
	    	bVelY = -bVelY;
	    	collision[18] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260, 90, 1, 40)) && collision[18] == false) {
	    	bVelX = -bVelX;
	    	collision[18] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(260 + 70, 90, 1, 40)) && collision[18] == false) {
	    	bVelX = -bVelX;
	    	collision[18] = true;
	    	totalBricks--;
		}
	    if (collision[18] == false) {
		bricks(260, 90, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 90, 70, 1)) && collision[19] == false) {
	    	bVelY = -bVelY;
	    	collision[19] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 90 + 40, 70, 1)) && collision[19] == false) {
	    	bVelY = -bVelY;
	    	collision[19] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340, 90, 1, 40)) && collision[19] == false) {
	    	bVelX = -bVelX;
	    	collision[19] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(340 + 70, 90, 1, 40)) && collision[19] == false) {
	    	bVelX = -bVelX;
	    	collision[19] = true;
	    	totalBricks--;
		}
	    if (collision[19] == false) {
		bricks(340, 90, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 90, 70, 1)) && collision[20] == false) {
	    	bVelY = -bVelY;
	    	collision[20] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 90 + 40, 70, 1)) && collision[20] == false) {
	    	bVelY = -bVelY;
	    	collision[20] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420, 90, 1, 40)) && collision[20] == false) {
	    	bVelX = -bVelX;
	    	collision[20] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(420 + 70, 90, 1, 40)) && collision[20] == false) {
	    	bVelX = -bVelX;
	    	collision[20] = true;
	    	totalBricks--;
		}
	    if (collision[20] == false) {
		bricks(420, 90, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 90, 70, 1)) && collision[21] == false) {
	    	bVelY = -bVelY;
	    	collision[21] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 90 + 40, 70, 1)) && collision[21] == false) {
	    	bVelY = -bVelY;
	    	collision[21] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500, 90, 1, 40)) && collision[21] == false) {
	    	bVelX = -bVelX;
	    	collision[21] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(500 + 70, 90, 1, 40)) && collision[21] == false) {
	    	bVelX = -bVelX;
	    	collision[21] = true;
	    	totalBricks--;
		}
	    if (collision[21] == false) {
		bricks(500, 90, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 90, 70, 1)) && collision[22] == false) {
	    	bVelY = -bVelY;
	    	collision[22] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 90 + 40, 70, 1)) && collision[22] == false) {
	    	bVelY = -bVelY;
	    	collision[22] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580, 90, 1, 40)) && collision[22] == false) {
	    	bVelX = -bVelX;
	    	collision[22] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(580 + 70, 90, 1, 40)) && collision[22] == false) {
	    	bVelX = -bVelX;
	    	collision[22] = true;
	    	totalBricks--;
		}
	    if (collision[22] == false) {
		bricks(580, 90, 170);
	    }
	    
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 90, 70, 1)) && collision[23] == false) {
	    	bVelY = -bVelY;
	    	collision[23] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 90 + 40, 70, 1)) && collision[23] == false) {
	    	bVelY = -bVelY;
	    	collision[23] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660, 90, 1, 40)) && collision[23] == false) {
	    	bVelX = -bVelX;
	    	collision[23] = true;
	    	totalBricks--;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(660 + 70, 90, 1, 40)) && collision[23] == false) {
	    	bVelX = -bVelX;
	    	collision[23] = true;
	    	totalBricks--;
		}
	    if (collision[23] == false) {
		bricks(660, 90, 170);
	    }
	    
	    
	    
		
	    
	    
	   
	    // Player/paddle Rectangle 
	    g.setColor(new Color(0,100,200));
	    g.fillRect(playX += velX, playY, 120, 15);
	    
	    g.setColor(new Color(0,150,250));
	    g.fillRect(playX, playY, 60, 15);
	   
	    
	    
	    
	    // Ball, collision detection with ball to walls
	    g.setColor(new Color(200,0,40));
	    g.fillOval(ballX += bVelX, ballY += bVelY, 20, 20);
	    
	    // check if the ball collides with the wall
	    if (ballX < 0) {
	    	bVelX = -bVelX;
	    }
	    
	    if (ballY < 0) {
	    	bVelY = -bVelY;
	    }
	    
	    if (ballX > 810) {
	    	bVelX = -bVelX;
	    	
	    }
	    // if the ball hits the ground
	    if (ballY > 630) {
	    	bVelY = -bVelY;
	    	
	    	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	    	life--;
	    	
	    }
	    
	    
	    // Ball to paddle collision detection
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playX + 1, playY, 118, 15))) {
	    	bVelY = -bVelY;
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playX, playY, 60, 15))) {
	    	if (bVelX > 0) {
	    	bVelX = -bVelX;
	    	}
	    }
	    if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playX + 60, playY, 60, 15))) {
	    	if (bVelX < 0) {
	    	bVelX = -bVelX;
	    }
	    }
	    
	    // Displaying number of bricks and number of lives at the top of screen
	    Font f = new Font ("Dialog", Font.PLAIN, 17);
	    g.setColor(new Color(0,100,200));
	    g.setFont(f);
	    g.drawString("Bricks: " + totalBricks, 10, 17);
	    g.drawString("Lives: " + life, 750, 17);
	    
	   
	    // When life is zero, game ends, because player lost
	    if (life == 0) {

	    	restore();
	
	    }
	    
	    // When total bricks is zero, game ends, player wins, restore and go back to title screen
	    if (totalBricks == 0) {
	    	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	restore();
	    }
	    
	     
		}
	}

	// required to draw anything to the screen
	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	
	
	// Detects keyboard and mouse actions, all methods till line 1007 
	public void actionPerformed(ActionEvent e) {
		
		
	}

	
	public void keyTyped(KeyEvent e) {
		
		
	}

	
	public void keyPressed(KeyEvent e) {
		// if right key is pressed, move paddle right
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playX > 681) {	
			playX = 681;
			}
			else {
			velX = 17;
			}
		}
		
		
		// if left key is pressed, move paddle left
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playX < 25) {	
				playX = 25;
				}
				else {
				velX = -17;
				}
		}
		// if enter key is pressed, game run is true, run game	
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			gameRun = true;
					
		}
		
			
	}
	
	
	public void keyReleased(KeyEvent e) {
		// if right key is released, stop moving paddle
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			velX = 0;
		}
		
		// if left key is released, stop moving paddle
	if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			velX = 0;
		}

	}


// Main Method
	
	public static void main(String[] args) {
		
		// Initializing JPanel
		JFrame window = new JFrame("Breaking Of The Bricks");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		window.add(new GamePanel());
		
		window.pack();
		window.setVisible(true);

	}

}