import java.awt.*;
import java.util.*;

public class Unit extends Entity {

	final int speed = 3;
	int owner;
	int movesRemaining, possibleMoves;
 	double destinationX = -1, destinationY = -1;
 	double velX=0, velY=0;
	double rad;
	Stack<Tile> history = new Stack<Tile>();
	Tile tile;
	boolean moving = false;
	
	public Unit(int owner, Tile tile, double radius) {
		
		x = tile.getX();
		y = tile.getY();
		this.rad = radius;
		this.tile = tile;
		history.push(tile);
		
		if(owner == 1 || owner == 0) 
			this.owner = owner;
		else
			System.out.println("Owner out of bounds, player number either 0 or 1.");
		
		movesRemaining = possibleMoves;
	}
	
	public Tile[] getPossibleMoveLocations() {
		
		System.out.println(this.tile.id);
		
		return this.tile.getAdjs();
		
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	void render(Graphics2D g) {
	
		g.setPaint(new GradientPaint(0, 0, new Color(6, 28, 100), 20, 20,
	       new Color(5, 7, 100, 27), true));
		g.fillOval((int) (x-rad), (int) (y-rad), (int) rad*2, (int) rad*2);
	}
	
	public void clearHistory(){
		
		history.clear(); 
		
	}

	void update() {
		
		double tx = destinationX - x, ty = destinationY - y;
		
		double dist = Math.sqrt(tx*tx+ty*ty); //Distance formula
			
		velX = (velX == 0 && destinationX !=-1) ? ((tx/dist)*speed) : velX;
		velY = (velY == 0 && destinationY !=-1) ? ((ty/dist)*speed) : velY;
		
        if(dist > 1){
          x += (destinationX != -1) ? velX : 0;
          y += (destinationY != -1) ? velY : 0; 
        }
        
        if(Math.abs(destinationX - x) <= 2) {
        	x = destinationX;
        	destinationX = -1;
        	velX=0;
        	moving=false;
        }
        
        
        if(Math.abs(destinationY - y) <= 2) {
        	y = destinationY;
        	destinationY = -1;
        	velY = 0;
        	moving=false;
        }
        
	}

	public void setDestination(Tile tile) {
		
		Tile last = (history.size() == 0) ? null : history.peek();
		if(last == tile) {
			
			history.pop();
			movesRemaining+=2; //Counteract decrement
			
		}
		else {
			history.push(this.tile);
		}
		
		this.tile.setUnit(null);
		this.tile = tile; 
		
		moving=true;
		destinationX = tile.getX();
		destinationY = tile.getY();
	}

	public int getPlayer() {return owner;}
	
	public void setMovesRemaining(int movesRemaining) {this.movesRemaining = movesRemaining;}
	
	public int getMovesRemaining() {return movesRemaining;}
	
	public int getPossibleMoves() {return possibleMoves;}
	
}