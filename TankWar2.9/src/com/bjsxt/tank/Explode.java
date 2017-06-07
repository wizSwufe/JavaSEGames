package com.bjsxt.tank;
import  java.awt.*;

public class Explode {//版本17--构造爆炸类
	int x,y;
	private boolean live=true;
	private TankClient tc;
	
	//版本28，添加爆炸类图片
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();//工具包类，能够获取硬盘上的数据和图片
	
	private static Image[] imgs={
		tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),//通过装载器获取路径下的数据和图片
		tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};//静态初始化,用逗号隔开
	int step=0;
	private boolean init=false;
	
	public Explode(int x,int y,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	
	public void draw(Graphics g){
		if(!init){//事先将 图片装载到内存中
			for (int i= 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init=true;
		}
		
		if(!live){
			tc.explodes.remove(this);
			return ;
		}
		
		if(step==imgs.length){
			live=false;
			step=0;
			return ;
		}
		
		//这里可能是异步的IO导致第1张图片来不及画
		g.drawImage(imgs[step], x, y, null);
		
		step++;
	}
	
	
	
	
	
}
