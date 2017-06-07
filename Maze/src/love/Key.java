package love;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Key {
	int x,y;
	boolean live=true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Key(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.cyan);
		g.fillRect(695, 80, 10, 40);
		g.fillOval(x+5, y+10,30,30);
		g.setColor(c);
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,40,40);
	}
	
}
