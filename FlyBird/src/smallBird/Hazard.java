package smallBird;

import java.awt.*;

public class Hazard {
	
	public int x,y,height;
	public static final int XSPEED=18;//版本10--添加障碍物类--该物体只能横向移动
	public static final int HWIDTH=80;//障碍物的宽度固定
	FlyBird fb=null;
	private boolean Live=true;
	
	
	public boolean isLive() {
		return Live;
	}

	public void setLive(boolean live) {
		Live = live;
	}

	public Hazard(int x, int y,int height) {
		this.x = x;
		this.y = y;
		this.height=height;
	}
	
	public Hazard(int x, int y,int height,FlyBird fb) {
		this(x,y,height);
		this.fb=fb;
	}
	
	public  void draw(Graphics g){
		if(!Live) 
		{
			fb.hazards.remove(this); 
			return ;
		}
		
		Color c=g.getColor();
		g.setColor(Color.pink);
		if(y==0){
			g.fillRect(x, y,HWIDTH,height);
		}else{
			g.fillRect(x, y, HWIDTH, height);
		}
		g.setColor(c);
		
		move();
	}
	
	public void move() {
		x-=XSPEED;//自动左移功能
		
		if(x+HWIDTH<0){
			Live=false;//类中的方法可以直接调用变量，无论属性
			fb.hazards.remove(this);
		}
	}

	public boolean hitBird(Bird b){
		if(this.getRect().intersects(b.getRect())&&b.isLive()){
			this.setLive(false);
			b.setLive(false);//版本16--打掉小鸟--游戏结束
			return true;
		}
		return false;
	}

	private Rectangle getRect() {
		return new Rectangle(x,y,HWIDTH,height);
	}
	
	
	public boolean crash(java.util.List<Hazard> hazards){
		for(int i=0;i<hazards.size();i++){
			Hazard h=hazards.get(i);//每次判断是否和每个坦克相撞
			if(this!=h){
				if(this.isLive()&&h.isLive()&&this.getRect().intersects(h.getRect())){
					disappear(h);//版本21--解决障碍物相撞问题
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private void disappear(Hazard h) {
		h.Live=false;
	}
	
	
}