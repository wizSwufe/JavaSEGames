package com.bjsxt.tank;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	
	public static final int XSPEED=10;//版本10--添加子弹类
	public static final int YSPEED=10;
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	
	public int x,y;
	Direction dir;
	private boolean Live=true;
	private TankClient tc;
	private boolean good;
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();//工具包类，能够获取硬盘上的数据和图片
	private static Image[] missilesImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static{
		missilesImages=new Image[]{
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),//通过装载器获取路径下的数据和图片
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif"))
		//装载器可以从多个类中获得，都是一样的
		};
		
		imgs.put("L", missilesImages[0]);//静态代码实现区域，可以用来处理变量的初始化
		imgs.put("LU",missilesImages[1]);
		imgs.put("U", missilesImages[2]);
		imgs.put("RU",missilesImages[3]);
		imgs.put("R", missilesImages[4]);
		imgs.put("RD",missilesImages[5]);
		imgs.put("D",missilesImages[6]);
		imgs.put("LD",missilesImages[7]);
		
	};//静态初始化,用逗号隔开
	
	public boolean isGood() {
		return good;
	}

	public boolean isLive() {
		return Live;
	}

	public Missile(int x, int y, boolean good,Direction dir, TankClient tc) {
		this(x,y,dir);
		this.good=good;
		this.tc=tc;
	}

	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public  void draw(Graphics g){
		if(!Live) 
		{
			tc.missiles.remove(this); 
			return ;
		}
		
		switch(dir){//版本12--坦克上画炮筒，解决子弹的发射问题
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
		
		move();
	}

	private void move() {
		
		switch(dir){
		case L:
			x-=XSPEED;
			break;
		case LU:
			x-=XSPEED;
			y-=YSPEED;
			break;
		case U:
			y-=YSPEED;
			break;
		case RU:
			x+=XSPEED;
			y-=YSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case RD:
			x+=XSPEED;
			y+=YSPEED;
			break;
		case D:
			y+=YSPEED;
			break;
		case LD:
			x-=XSPEED;
			y+=YSPEED;
			break;
		}
		
		
		if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT){//高度要一一对应
			Live=false;//类中的方法可以直接调用变量，无论属性
		}
		
	}
	
	public boolean hitTank(Tank t){
		if(this.Live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()){
			if(t.isGood()){
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0){
					t.setLive(false);
				}
				
			}else{
				t.setPoint(t.getPoint()+100);
				t.setLive(false);//版本16--打掉坦克并消灭
			}
				this.Live=false;
				Explode e=new Explode(x,y,tc);
				tc.explodes.add(e);
				return true;
		}
		return false;
	}

	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w){
		if(this.Live&&this.getRect().intersects(w.getRect())){
			this.Live=false;
			return true;
		}
		return false;
	}
	
	
	
	
}
