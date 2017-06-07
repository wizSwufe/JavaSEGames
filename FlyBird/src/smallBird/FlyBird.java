package smallBird;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlyBird extends Frame {

	public static final int GAME_WIDTH=800;//版本5.0--版代码重构--将容易改动的量定义为常量
	public static final int GAME_HEIGHT=600;
	public static final int Bird_X=200;//鸟儿的初始坐标点
	public static final int Bird_Y=270;
	
	
	Bird bird=new Bird(Bird_X,Bird_Y,this);
	Image offScreenImage=null;
	
	private static Random r=new  Random();//随机数产生器有一个就够了
	public static Random getR() {
		return r;
	}

	public static void setR(Random r) {
		FlyBird.r = r;
	}
	
	
	
	List<Hazard> hazards=new ArrayList<Hazard>();//版本13--添加一堆障碍物
	
	public void paint(Graphics g) {
		if(bird.pass(hazards)){
			bird.setPoint(bird.getPoint()+100);
		}
		
		if(bird.getY()+bird.BHEIGHT>this.GAME_HEIGHT){
			bird.setLive(false);
		}
		
		g.drawString("按↑键控制小鸟的跳跃！", 10, 70);
		
		g.drawString("您当前的最高纪录是："+bird.getMaxpoint()+"分", 10, 110);
		g.drawString("您的当前得分是："+bird.getPoint()+"分", 10, 130);
		if(!bird.isLive()){
			g.drawString("游戏失败！您的小鸟挂了！请按F2键重新开始！", 220, 200);
			g.drawString("您之前的最高纪录是："+bird.getMaxpoint()+"分", 220, 230);
			g.drawString("您的当前得分是："+bird.getPoint()+"分", 220, 260);
		}
		
		
		for(int i=0;i<hazards.size();i++){//只装进去画出来，并没有取出
			Hazard h=hazards.get(i);//版本14和15--将障碍物去掉
			h.hitBird(bird);//版本16和17和18--碰撞检测
			h.crash(hazards);//版本21和22和23和24--障碍之间不能相互连接
			h.draw(g);
			
			
			
			if(!h.isLive()){
				hazards.remove(h);
			}
		}
		
		bird.draw(g);
	}
	
	public void update(Graphics g){//版本4.1--重画图片--设置虚拟图片，然后粘贴--注意重画的名字
		if(offScreenImage==null){
			offScreenImage =this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();//得到背后图片的画笔
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.CYAN);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);//刷新背景图片
		gOffScreen.setColor(c);//回复背景图片画笔的颜色
		
		paint(gOffScreen);//画图案在背景图片上
		g.drawImage(offScreenImage,0,0,null);//将图片放入屏幕
		
	}
	
	public void launchFrame(){//版本1.0--创建窗口
		this.setLocation(200,100);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("Loving Bird！");
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});//版本2.0--增加窗口监听，关闭窗口，固定大小
		this.setResizable(false);
		this.setBackground(Color.CYAN);//设置窗口背景色
		this.addKeyListener(new KeyMonitor());//给对象增加键盘监听
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();//版本4.0--让小鸟动起来，不停的重画--匿名累启动线程
		
	}
	
	public static void main(String[] args) {
		FlyBird fb=new FlyBird();
		fb.launchFrame();
	}

	private class PaintThread implements Runnable{

		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(100);//系统调用
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	
	private class KeyMonitor extends KeyAdapter{//版本6.0--增加键盘监听

		
		public void keyPressed(KeyEvent e) {//私有内部监听类--螺旋递增检验
			bird.keyReleased(e);
		}
	}
		
}
	

