package air;

import java.awt.*;

public class Explode {//版本17--构造爆炸类
	int x,y;
	private boolean live=true;
	private HeatPlane hp;
	
	int[] diameter={4,7,12,18,26,32,49,55,47,30,14,6};
	int step=0;
	
	public Explode(int x,int y,HeatPlane hp){
		this.x=x;
		this.y=y;
		this.hp=hp;
	}
	
	public void draw(Graphics g){
		if(!live){
			hp.explodes.remove(this);
			return ;
		}
		
		if(step==diameter.length){
			live=false;
			step=0;
			return ;
		}
		
		Color c=g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		
		step++;
	}
}

