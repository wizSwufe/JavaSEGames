package love;

import java.awt.*;

public class Door {
	int x,y;
	boolean live=true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Door(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.pink);
		g.fillRect(x, y, 40, 40);
		g.setColor(c);
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,40,40);
	}
	
}
