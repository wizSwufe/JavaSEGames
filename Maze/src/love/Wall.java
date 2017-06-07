package love;

import java.awt.*;

public class Wall {
	int x,y;
	int widgh,height;
	
	public Wall(int x, int y,int widgh,int height) {
		this.x = x;
		this.y = y;
		this.widgh=widgh;
		this.height=height;
	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.blue);
		g.fillRect(x, y, widgh, height);
		g.setColor(c);
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,widgh,height);
	}
}
