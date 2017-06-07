package com.bjsxt.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 
 * @author xiongjie 
 *版本26--添加注释，---给类和方法还有静态变量
 */

public class TankClient extends Frame{

	public static final int GAME_WIDTH=800;//版本5.0--版代码重构--将容易改动的量定义为常量
	public static final int GAME_HEIGHT=600;
	
	private  int time=1000;
	
	Tank myTank=new Tank(700,480,true,Direction.STOP,this);
	Wall w1=new Wall(100,200,20,150,this);
	Wall w2=new Wall(400,150,300,20,this);
	Wall w3=new Wall(600,450,22,150,this);
	Wall w4=new Wall(250,330,150,15,this);
	

	List<Explode> explodes=new ArrayList<Explode>();
	List<Missile> missiles=new ArrayList<Missile>();
	List<Tank> tanks=new ArrayList<Tank>();
	Blood b=new Blood();

	Image offScreenImage=null;

	
	public  int getTime() {
		return time;
	}


	public  void setTime(int time) {
		this.time = time;
	}
	
	//消除闪烁现象！运用双缓冲,名字很重要
	public void update(Graphics g){//版本4.1--重画图片--设置虚拟图片，然后粘贴
		if(offScreenImage==null){
			offScreenImage =this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();//得到背后图片的画笔
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);//刷新背景图片
		gOffScreen.setColor(c);//回复背景图片画笔的颜色
			
		paint(gOffScreen);//画图案在背景图片上
		g.drawImage(offScreenImage,0,0,null);//将图片放入屏幕
			
	}
	
	
	public void paint(Graphics g) {
		g.drawString("Missiles count:"+missiles.size(), 10, 50);
		g.drawString("explodes count:"+explodes.size(), 10, 70);
		g.drawString("tanks count:"+tanks.size(), 10, 90);
		g.drawString("tanks life:"+myTank.getLife(), 10, 110);
		g.drawString("您目前的最高记录是："+myTank.getMaxpoint()+"分！",10,130);
		g.drawString("目前还剩下时间："+time+"毫秒", 10, 160);
		
		g.drawString("方向键控制方向：上下左右！", 650,530);
		g.drawString("按S键发射子弹！", 650,550);
		g.drawString("紫色的方块是加血块！", 650,570);
		g.drawString("你目前的得分是："+myTank.getPoint()+"分！", 650,590);
		
		if(tanks.size()<=0&&time>0){//版本26--敌军死光，添加敌军
			g.drawString("恭喜过关！！！请按F1键开始下一关！", 250, 300);
			
		}
		
		if(!myTank.isLive()){
			g.drawString("您被击毙了！！游戏失败！请按F2键重新开始！", 250, 300);
		}
		
		if(myTank.isLive()&&time<=0){
			g.drawString("时间到！游戏失败！请按F2键重新开始！", 250, 300);
			myTank.clear();
		}
		
		
		for(int i=0;i<missiles.size();i++){//只装进去画出来，并没有取出
			Missile m=missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.hitWall(w3);
			m.hitWall(w4);
			m.draw(g);
			
		}
		
		for(int i=0;i<explodes.size();i++){
			Explode e=explodes.get(i);
			e.draw(g);//制造打中多辆坦克时的多次爆炸
		}
		
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			t.collidesWithWall(w1);//敌方坦克不可以穿墙--我方可以
			t.collidesWithWall(w2);
			t.collidesWithWall(w3);
			t.collidesWithWall(w4);
			
			
			myTank.collidesWithWall(w1);
			myTank.collidesWithWall(w2);
			myTank.collidesWithWall(w3);
			myTank.collidesWithWall(w4);
			
			
			
			t.collidesWithTanks(tanks);
			myTank.collidesWithTanks(tanks);//版本21--坦克之间不能互相穿越
			t.draw(g);//制造打中多辆坦克时的多次爆炸
		}
		
		myTank.draw(g);
		myTank.eat(b);
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		w4.draw(g);
		b.draw(g);
		
		
	}
	
	
	public void launchFrame(){//版本1.0--创建窗口
		
		Properties props=new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int initTankCount=Integer.parseInt(props.getProperty("initTankCount"));
		
		//版本18--添加地方坦克
		for(int i=0;i<Integer.parseInt(PropertyMgr.getProperty("initTankCount"));i++){
			tanks.add(new Tank(80+40*(i+1),10,false,Direction.D,this) );
		}
		
		
		this.setLocation(100,100);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("天使VS魔鬼！---坦克大战");
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});//版本2.0--关闭窗口，固定大小
		this.setResizable(false);
		this.setBackground(Color.GREEN);//设置窗口背景色
		this.addKeyListener(new KeyMonitor());//给对象增加键盘监听
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();//匿名累启动线程
		
	}
	
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable{

		public void run() {//版本4.0--让坦克动起来，不停的重画
			while(true){
				repaint();
				try {
					Thread.sleep(100);//系统调用,当胜利或者被击毙时，时间停止
					if( time>0 && tanks.size()>0 && myTank.isLive()  )  time--;
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

	private class KeyMonitor extends KeyAdapter{//版本6.0--增加键盘监听
	
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {//私有内部监听类--螺旋递增检验
			myTank.keyPressed(e);
		}
		
	}
	
}

