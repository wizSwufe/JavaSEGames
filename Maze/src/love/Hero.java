package love;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Hero {
	int x,y;
	int c;
	int keynum;
	Maze m=null;
	boolean live=true;
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private int oldX=80;
	private int oldY=80;

	public Hero(int x, int y,int c,Maze m) {
		this.x = x;
		this.y = y;
		this.c=c;
		this.m=m;
	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		if(this.c==0){
			g.setColor(Color.RED);
		}else{
			g.setColor(Color.yellow);
		}
		g.fillOval(x, y, 40, 40);
		
		g.setColor(Color.black);//画眼睛
		g.fillRect(x+5, y+8, 10, 2);
		g.fillRect(x+25,y+8, 10, 2);
		g.fillRect(x+18,y+28, 10,2);
		
		
		g.setColor(c);
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,40,40);
	}
	
	private void stay() {
		x=this.oldX;
		y=this.oldY;
	}
	
	public boolean collidesWithWall(Wall w){
		if(this.getRect().intersects(w.getRect())){
			stay();
			return true;
		}
		return false;
	}
	
	public boolean hit(Hero h1){
		if(this.getRect().intersects(h1.getRect())){
			return true;
		}
		return false;
	}
	
	public boolean hitDoor(Door d){
		if(this.getRect().intersects(d.getRect()) && keynum==1 ){
			d.setLive(false);
			return true;
		}else if(this.getRect().intersects(d.getRect()) && keynum==0){
			stay();
		}
		return false;
		
	}
	
	public boolean hitKey(Key k){
		if(this.getRect().intersects(k.getRect())&&m.getTime()>0){
			k.setLive(false);//时间到了就吃不到钥匙了
			this.setKeynum(1);
			return true;
		}
		return false;
	}

	
	public int getKeynum() {
		return keynum;
	}

	public void setKeynum(int keynum) {
		this.keynum = keynum;
	}

	public void keyReleased(KeyEvent e) {//版本9.0--Hero移动成功--即使释放 键盘控制
		int key=e.getKeyCode();
		this.oldX=x;
		this.oldY=y;
		
		switch(key){
		case KeyEvent.VK_F2:
			if(this.x==680||m.getTime()==0){
				this.x=80;
				this.y=80;
				this.setKeynum(0);
				m.d.setLive(true);
				m.k.setLive(true);
				m.setTime(500);
				m.h1.setLive(true);
			}
			break;
		case KeyEvent.VK_LEFT:
			x-=40;
			break;
		case KeyEvent.VK_UP:
			y-=40;
			break;
		case KeyEvent.VK_RIGHT:
			x+=40;
			break;
		case KeyEvent.VK_DOWN:
			y+=40;
			break;
		}
		
	}
	
	
}
