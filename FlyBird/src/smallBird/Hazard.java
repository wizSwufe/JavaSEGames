package smallBird;

import java.awt.*;

public class Hazard {
	
	public int x,y,height;
	public static final int XSPEED=18;//�汾10--����ϰ�����--������ֻ�ܺ����ƶ�
	public static final int HWIDTH=80;//�ϰ���Ŀ�ȹ̶�
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
		x-=XSPEED;//�Զ����ƹ���
		
		if(x+HWIDTH<0){
			Live=false;//���еķ�������ֱ�ӵ��ñ�������������
			fb.hazards.remove(this);
		}
	}

	public boolean hitBird(Bird b){
		if(this.getRect().intersects(b.getRect())&&b.isLive()){
			this.setLive(false);
			b.setLive(false);//�汾16--���С��--��Ϸ����
			return true;
		}
		return false;
	}

	private Rectangle getRect() {
		return new Rectangle(x,y,HWIDTH,height);
	}
	
	
	public boolean crash(java.util.List<Hazard> hazards){
		for(int i=0;i<hazards.size();i++){
			Hazard h=hazards.get(i);//ÿ���ж��Ƿ��ÿ��̹����ײ
			if(this!=h){
				if(this.isLive()&&h.isLive()&&this.getRect().intersects(h.getRect())){
					disappear(h);//�汾21--����ϰ�����ײ����
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