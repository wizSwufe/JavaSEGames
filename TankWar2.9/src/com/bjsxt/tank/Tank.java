package com.bjsxt.tank;
import java.awt.*;
import java.awt.event.*;
import java.util.*;//版本19--让敌方坦克智能起来（发炮弹）

public class Tank {
	private int x,y;
	private static int point;//分数
	private static int maxpoint;

	public static final int XSPEED=5;
	public static final int YSPEED=5;
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	private BloodBar bb=new BloodBar();
	private int step=r.nextInt(12)+3;//随机步数
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();//工具包类，能够获取硬盘上的数据和图片
	private static Image[] tankImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static{
		tankImages=new Image[]{
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),//通过装载器获取路径下的数据和图片
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif"))
		//装载器可以从多个类中获得，都是一样的
		};
		
		imgs.put("L", tankImages[0]);//静态代码实现区域，可以用来处理变量的初始化
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);
		
	};//静态初始化,用逗号隔开
	
	private int oldX,oldY;
	private int  life=60;//版本23--添加主战坦克生命值
	private boolean good;
	
	private boolean live=true;
	private static Random r=new  Random();//随机数产生器有一个就够了
	
	
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

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	public boolean isGood() {
		return good;
	}
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	TankClient tc=null;//得到引用
	
	private boolean bL=false,bU=false,bR=false,bD=false;//方向变量
	
	private Direction dir=Direction.STOP;//根据这个确定坐标变化
	private Direction ptDir=Direction.U;//创建炮筒元素，有方向
	
	
	private class BloodBar{//版本2.4--创建内部类--表示血条
		public void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x+5, y-10, WIDTH, 10);//画外部空心
			int w=WIDTH*life/60;//血条控制宽度
			g.fillRect(x+5, y-10, w, 10);
			g.setColor(c);
			
		}
	}
	
	
	
	public Tank(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.oldX=x;
		this.oldY=y;
		this.good=good;
	}
	
	public Tank(int x,int y,boolean good,Direction dir, TankClient tc){
		this(x,y,good);//持有对方对象的引用
		this.dir=dir;
		this.tc=tc;//版本11--得到坦克客户端的应用，生成子弹，然后 重画方法
	}
	
	public void draw(Graphics g){
		if(!live){
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
		
		if(good) bb.draw(g);
		
		switch(ptDir){//版本12--坦克上画炮筒，解决子弹的发射问题
		case L:
			g.drawImage(imgs.get("L"), x, y, null);//根据炮筒的方向画出坦克
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
	
	void move(){
		this.oldX=x;
		this.oldY=y;
		
		
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
		case STOP:
			break;
		}
		
		if(this.dir!=Direction.STOP){
			this.ptDir=this.dir;
		}
		
		if(x<0) x=0;
		if(y<30) y=30;
		if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH;
		if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT) y=TankClient.GAME_HEIGHT-Tank.HEIGHT;
		
		if(!good){
			Direction[] dirs = Direction.values();
			
			if(step==0){
				step=r.nextInt(12)+3;//真正智能
				int rn=r.nextInt(dirs.length);//产生随机数
				dir=dirs[rn];
			}			
			step--;
			if(r.nextInt(40) > 36) this.fire();//等价于控制游戏难度
		}
		
		
	}
	
	public void keyPressed(KeyEvent e){//版本7.0--面向对象，将坦克包装成类
		int key=e.getKeyCode();
		switch(key){
		
		case KeyEvent.VK_LEFT:
			bL=true;
			break;
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
		}
		
		locateDirection();//确定方向
	}
	
	public BloodBar getBb() {
		return bb;
	}

	public void setBb(BloodBar bb) {
		this.bb = bb;
	}

	public void locateDirection(){//版本8.0--添加坦克的8个移动方向，用到枚举方向
		if( bL && !bU && !bR && !bD ) dir=Direction.L;
		else if( bL && bU && !bR && !bD ) dir=Direction.LU;
		else if( !bL && bU && !bR && !bD ) dir=Direction.U;
		else if( !bL && bU && bR && !bD ) dir=Direction.RU;
		else if( !bL && !bU && bR && !bD ) dir=Direction.R;
		else if( !bL && !bU && bR && bD ) dir=Direction.RD;
		else if( !bL && !bU && !bR && bD ) dir=Direction.D;
		else if( bL && !bU && !bR && bD ) dir=Direction.LD;
		else if( !bL && !bU && !bR && !bD ) dir=Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {//版本9.0--坦克移动成功--即使释放 键盘控制
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_F1:
			for(int i=0;i<Integer.parseInt(PropertyMgr.getProperty("reProduceTankCount"));i++){
				tc.tanks.add(new Tank(10+40*(i+1),10+40*(i+2),false,Direction.D,tc) );
			}
			
			tc.setTime(1000);
			setX(700);
			setY(500);
			setLife(60);
			tc.b.setLive(true);
			break;
		case KeyEvent.VK_F2:
			if(!live){//复活
				clear();
				
				if(maxpoint<point){
					maxpoint=point;
				}
				point=0;
				tc.setTime(1000);
				//重新生成坦克
				for(int i=0;i<10;i++){
					tc.tanks.add(new Tank(80+40*(i+1),10,false,Direction.D,tc) );
				}
				
				live=true;
				life=60;
				setX(700);
				setY(500);
				tc.b.setLive(true);
			}else if(tc.getTime()==0){
				
				if(maxpoint<point){
					maxpoint=point;
					point=0;
					
				}
				
				tc.setTime(500);
				//重新生成坦克
				for(int i=0;i<10;i++){
					tc.tanks.add(new Tank(80+40*(i+1),10,false,Direction.D,tc) );
				}
				
				live=true;
				life=60;
				setX(700);
				setY(500);
				tc.b.setLive(true);
			}
			break;
			
		case KeyEvent.VK_S://空格键与左键有热键冲突！！！！
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL=false;
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		
		locateDirection();//确定方向
		
	}
	
	

	public static int getMaxpoint() {
		return maxpoint;
	}

	public static void setMaxpoint(int maxpoint) {
		Tank.maxpoint = maxpoint;
	}

	public Missile fire(){
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m=new Missile(x,y,good,ptDir,tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir){
		if(!live) return null;
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m=new Missile(x,y,good,dir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	
	private void superFire() {//版本22--添加超级炮弹
		Direction[] dirs=Direction.values();
		for(int i=0;i<8;i++){
			fire(dirs[i]);
		}
	}

	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}

	
	public boolean collidesWithWall(Wall w){
		if(this.live&&this.getRect().intersects(w.getRect())){
			stay();
			return true;
		}
		return false;
	}

	private void stay() {
		x=this.oldX;
		y=this.oldY;
	}
	
	
	public boolean collidesWithTanks(java.util.List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);//每次判断是否和每个坦克相撞
			if(this!=t){
				if(this.live&&t.isLive()&&this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean eat(Blood b){
		if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())){
			this.life=60;
			b.setLive(false);
			return true;
		}
		return false;
	}
	
	public void clear(){
		for(int i=0;i<tc.tanks.size();i++){//多次重复清理
			tc.tanks.remove(tc.tanks.get(i));//去掉坦克
		}
		
		for(int i=0;i<tc.tanks.size();i++){
			tc.tanks.remove(tc.tanks.get(i));//去掉坦克
		}
		
		for(int i=0;i<tc.tanks.size();i++){
			tc.tanks.remove(tc.tanks.get(i));//去掉坦克
		}
		
		for(int i=0;i<tc.tanks.size();i++){
			tc.tanks.remove(tc.tanks.get(i));//去掉坦克
		}
		
		//去除子弹
		for(int i=0;i<tc.missiles.size();i++){//多次重复清理
			tc.missiles.remove(tc.missiles.get(i));//去掉坦克
		}
		for(int i=0;i<tc.missiles.size();i++){//多次重复清理
			tc.missiles.remove(tc.missiles.get(i));//去掉坦克
		}
		for(int i=0;i<tc.missiles.size();i++){//多次重复清理
			tc.missiles.remove(tc.missiles.get(i));//去掉坦克
		}
		for(int i=0;i<tc.missiles.size();i++){//多次重复清理
			tc.missiles.remove(tc.missiles.get(i));//去掉坦克
		}
		
	}
	
	
}
