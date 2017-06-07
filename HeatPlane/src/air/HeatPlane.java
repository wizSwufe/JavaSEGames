package air;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class HeatPlane extends Frame{
	
	public static final int GAME_WIDTH=800;//版本5.0--版代码重构--将容易改动的量定义为常量
	public static final int GAME_HEIGHT=600;

	Plane myPlane=new Plane(350,520,true,this);
	List<Missile> missiles=new ArrayList<Missile>();
	List<Explode> explodes=new ArrayList<Explode>();
	List<Plane> planes=new ArrayList<Plane>();
	Image offScreenImage=null;
	int time=1000;

	
	public void update(Graphics g){//版本4.1--重画图片--设置虚拟图片，然后粘贴
		if(offScreenImage==null){
			offScreenImage =this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();//得到背后图片的画笔
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.pink);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);//刷新背景图片
		gOffScreen.setColor(c);//回复背景图片画笔的颜色
		
		paint(gOffScreen);//画图案在背景图片上
		g.drawImage(offScreenImage,0,0,null);//将图片放入屏幕
		
	}
	
	public void paint(Graphics g) {
		g.drawString("Missiles count:"+missiles.size(), 10, 300);
		g.drawString("explodes count:"+explodes.size(), 10, 320);
		g.drawString("planes count:"+planes.size(), 10, 340);
		g.drawString("你目前的得分是："+myPlane.getPoint()+"分！", 10,360);
		g.drawString("您目前的最高记录是："+myPlane.getMaxpoint()+"分！",10,380);
		g.drawString("目前还剩下时间："+time+"毫秒", 10, 400);
		
		if(planes.size()<=0&&time>0){//版本26--敌军死光，添加敌军
			g.drawString("恭喜过关！！！请按F1键开始下一关！", 250, 300);
			
		}
		
		if(!myPlane.isLive()){
			g.drawString("您被击毙了！！游戏失败！请按F2键重新开始！", 250, 300);
		}
		
		if(myPlane.isLive()&&time<=0){
			g.drawString("时间到！游戏失败！请按F2键重新开始！", 250, 300);
			myPlane.clear();
		}
		
		for(int i=0;i<missiles.size();i++){//只装进去画出来，并没有取出
			Missile m=missiles.get(i);
			m.hitPlanes(planes);
			m.hitPlane(myPlane);
			m.draw(g);
		}
		
		for(int i=0;i<explodes.size();i++){
			Explode e=explodes.get(i);
			e.draw(g);//制造打中多辆坦克时的多次爆炸
		}
		
		for(int i=0;i<planes.size();i++){
			Plane e=planes.get(i);
			e.draw(g);//制造打中多辆坦克时的多次爆炸
		}
		
		myPlane.draw(g);
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void launchFrame(){//版本1.0--创建窗口
		//版本18--添加地方坦克
				for(int i=0;i<7;i++){
					for(int j=0;j<3;j++)
					planes.add(new Plane(80*i+100,100*j+10,false,this) );
				}
		
			this.setLocation(250,100);
			this.setSize(GAME_WIDTH,GAME_HEIGHT);
			this.setTitle("小蜜蜂来袭！！！---打飞机");
			this.addWindowListener(new WindowAdapter(){

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
				
			});//版本2.0--关闭窗口，固定大小
			this.setResizable(false);
			this.setBackground(Color.pink);//设置窗口背景色
			this.addKeyListener(new KeyMonitor());//给对象增加键盘监听
			setVisible(true);
			
			new Thread(new PaintThread()).start();//匿名累启动线程
	}
		
	public static void main(String[] args) {
			HeatPlane hp=new HeatPlane();
			hp.launchFrame();
	}

	
	private class PaintThread implements Runnable{
		public void run() {//版本4.0--让坦克动起来，不停的重画
			while(true){
				repaint();
				try {
					Thread.sleep(100);//系统调用
					if( time>0 && planes.size()>0 && myPlane.isLive()  )  time--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

	private class KeyMonitor extends KeyAdapter{//版本6.0--增加键盘监听
		
		public void keyPressed(KeyEvent e) {
			myPlane.keyPressed(e);
		}
	}
	
}
	
	
