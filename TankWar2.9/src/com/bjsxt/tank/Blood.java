package com.bjsxt.tank;
import java.awt.*;

public class Blood {//版本25--添加血块类--加血作用
	int x,y,w,h;
	TankClient tc;
	int step=0;
	
	private boolean live=true;;
	
	private int[][] pos={
			{350,300},{355,320},{360,300},{370,300},{375,275},{390,250},{400,230},{380,240},{365,252},{340,280}
					};
	
	
	public Blood(){
		x=pos[0][0];
		y=pos[0][1];
		w=h=15;
		
		
	}
	
	
	public void draw(Graphics g){
		if(!live) return ;
		
		Color c=g.getColor();
		g.setColor(Color.magenta);//品红
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
		
	}


	public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}


	private void move() {
		step++;
		if(step==pos.length){
			step=0;
		}
		x=pos[step][0];
		y=pos[step][1];
		
	}
	
	
	public	Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
	
}
