package air;

import java.awt.*;
import java.util.List;

public class Missile {
	
	//版本10--添加子弹类
	public static final int YSPEED=20;
	public int x,y;
	private boolean live=true;
	HeatPlane hp=null;
	private boolean good;
	
	
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public Missile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Missile(int x, int y,boolean good,HeatPlane hp) {
		this(x,y);
		this.good=good;
		this.hp=hp;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public  void draw(Graphics g){
		if(!live) 
		{
			hp.missiles.remove(this); 
			return ;
		}
		Color c=g.getColor();
		if(good){
			g.setColor(Color.YELLOW);
		}
		else{
			g.setColor(Color.BLACK);
		}
		g.fillOval(x, y, 20, 20);
		g.setColor(c);
		
		move();
	}

	public void move(){
		if(good){
			y-=YSPEED;
		}
		else{
			y+=YSPEED;
		}
		
		if(y<0||y>600){
			live=false;//类中的方法可以直接调用变量，无论属性
			hp.missiles.remove(this);
		}
	}
	
	
	public boolean hitPlane(Plane p){
		if(this.getRect().intersects(p.getRect())&&p.isLive()&&good!=p.isGood()){
			if(p.isGood()){
					p.setLive(false);
			}else{
				p.setPoint(p.getPoint()+100);
				p.setLive(false);//版本16--打掉坦克并消灭
			}
			
			this.live=false;
			Explode e=new Explode(x,y,hp);
			hp.explodes.add(e);
			return true;
		}
		return false;
	}

	private Rectangle getRect() {
		return new Rectangle(x,y,20,20);
	}

	public boolean hitPlanes(List<Plane> planes){
		for(int i=0;i<planes.size();i++){
			if(hitPlane(planes.get(i))){
				return true;
			}
		}
		return false;
	}
	
		
	
}

