package smallBird;
import java.awt.*;
import java.awt.event.*;

public class Bird {
	int x,y;
	
	public static final int BWIDTH=40;//鸟儿的宽度
	public static final int BHEIGHT=40;
	private static final int YSPEED=80;//该物体只能上下移动
	private  boolean live=true;
	FlyBird fb=null;
	
	private static int point;//分数
	private static int maxpoint;
	
	
	public static int getMaxpoint() {
		return maxpoint;
	}

	public static void setMaxpoint(int maxpoint) {
		Bird.maxpoint = maxpoint;
	}

	public static int getPoint() {
		return point;
	}

	public static void setPoint(int point) {
		Bird.point = point;
	}

	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean live){
		this.live=live;
	}
	
	public Bird(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Bird(int x, int y,FlyBird fb) {
		this(x,y);
		this.fb=fb;//版本11和12--持有一个类的引用
	}
	
	public void draw(Graphics g){
		if(!live) return;
		Color c=g.getColor();//版本3.0--画出小鸟
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, BWIDTH,BHEIGHT);
		g.setColor(Color.red);
		g.fillOval(x+BWIDTH/2,y+BHEIGHT/4,BWIDTH/2,BHEIGHT/2);
		g.setColor(Color.green);
		g.fillOval(x+5*BWIDTH/8, y+3*BHEIGHT/8,BWIDTH/4, BHEIGHT/4);
		g.setColor(c);//保存画笔本来的颜色
		
		move();//版本8.0和9.0一笔带过【这里不需要那些方向功能】--将一些移动写成方法
	}
	
	public void move() {
		y+=10;//自动下坠功能
	}

	public void keyReleased(KeyEvent e) {//版本7.0--面向对象，将小鸟包装成类
		int key=e.getKeyCode();
		switch(key){
		
		case KeyEvent.VK_F2:
			if(!live){//重新开始
				clear();
				if(maxpoint<point){
					maxpoint=point;
				}
				point=0;
				live=true;
				this.setX(fb.Bird_X);
				this.setY(fb.Bird_Y);
			}
			break;
		case KeyEvent.VK_UP:
			y-=YSPEED;//case默认含有括号的功能
			createHazard();
			if(y<=0){
				this.live=false;
			}
			break;
		}
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

	public void createHazard(){
		Hazard h=null;
		int yy=fb.getR().nextInt(600);//版本13和19和20--产生一个随机数，障碍物的高度+小鸟的高度要远远小于屏幕高度
		if(yy<=300){
			h=new Hazard(fb.GAME_WIDTH,0,yy,fb);//版本11--控制障碍物的生成--从上面开始生成
			fb.hazards.add(h);
			h=new Hazard(fb.GAME_WIDTH,yy+250,fb.GAME_HEIGHT-yy-250,fb);//版本11--控制障碍物的生成
			fb.hazards.add(h);
		}else {//下面的障碍物
			h=new Hazard(fb.GAME_WIDTH,yy,fb.GAME_HEIGHT-yy,fb);
			fb.hazards.add(h);
			h=new Hazard(fb.GAME_WIDTH,0,yy-250,fb);
			fb.hazards.add(h);
		}

	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,BWIDTH,BHEIGHT);
	}

	
	public void clear(){
		for(int i=0;i<fb.hazards.size();i++){//多次重复清理
			fb.hazards.remove(fb.hazards.get(i));//去掉障碍物
		}
		for(int i=0;i<fb.hazards.size();i++){//多次重复清理
			fb.hazards.remove(fb.hazards.get(i));//去掉障碍物
		}
		for(int i=0;i<fb.hazards.size();i++){//多次重复清理
			fb.hazards.remove(fb.hazards.get(i));//去掉障碍物
		}
		for(int i=0;i<fb.hazards.size();i++){//多次重复清理
			fb.hazards.remove(fb.hazards.get(i));//去掉障碍物
		}
				
	}
	
	
	public boolean pass(java.util.List<Hazard> hazards){//版本26--增加得分系统
		
		for(int i=0;i<hazards.size();i++){
			if(this.isLive()&&this.getX()>(hazards.get(i).getX()+hazards.get(i).XSPEED) && hazards.get(i).isLive()){
					fb.hazards.remove(fb.hazards.get(i));
					return true;
			}
			
		}
		return false;
	}
}