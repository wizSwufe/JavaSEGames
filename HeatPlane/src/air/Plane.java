package air;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Plane {
	int x,y;
	private static final int XSPEED=10;
	private boolean good;
	private boolean live=true;
	HeatPlane hp=null;
	private static int point;//����
	private static int maxpoint;

	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getMaxpoint() {
		return maxpoint;
	}

	public void setMaxpoint(int maxpoint) {
		this.maxpoint = maxpoint;
	}


	private static Random r=new  Random();//�������������һ���͹���
	
	public Plane(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.good=good;
	}
	
	public Plane(int x, int y,boolean good,HeatPlane hp) {
		this(x,y,good);
		this.hp=hp;
	}
	
	public void draw(Graphics g){
			if(!live){
				if(!good){
					point+=100;
					hp.planes.remove(this);
				}
				return;
			}
			Color c=g.getColor();//�汾3.9--�����ɻ�
			
			if(good) g.setColor(Color.blue);
			else g.setColor(Color.BLACK);
			
			if(good){
				g.fillOval(x+20, y, 20, 20);
				g.fillOval(x,y+40, 20, 20);
				g.fillOval(x+40,y+40, 20, 20);
			}
			else{
				g.fillOval(x, y, 20, 20);
				g.fillOval(x+40,y, 20, 20);
				g.fillOval(x+20,y+40, 20, 20);
			}
			
			if(good) g.setColor(Color.GREEN);
			else g.setColor(Color.BLACK);
			
			g.fillRect(x, y+20, 60, 20);
			g.setColor(c);//���滭�ʱ�������ɫ
			
			if(!good) 
				if(r.nextInt(40) > 38) 
					this.fire();//�ȼ��ڿ�����Ϸ�Ѷ�
			
	}
	

	public void keyPressed(KeyEvent e){//�汾7.0--������󣬽��ɻ���װ����
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_F1:
			for(int i=0;i<7;i++)
				for(int j=0;j<3;j++)
				hp.planes.add(new Plane(80*i+100,100*j+10,false,hp)) ;
			
			this.setX(350);
			this.setY(520);
			this.setLive(true);
			hp.setTime(1000);
			
		case KeyEvent.VK_F2:
			if(!live){//����
				if(maxpoint<point){
					maxpoint=point;
				}
				point=0;
				
				clear();
				
				for(int i=0;i<7;i++)
					for(int j=0;j<3;j++)
					hp.planes.add(new Plane(80*i+100,100*j+10,false,hp)) ;
				
				live=true;
				hp.setTime(1000);
				setX(350);
				setY(520);
			}else if(hp.getTime()==0){
				
				if(maxpoint<point){
					maxpoint=point;
				}
				point=0;
				
				for(int i=0;i<7;i++)
					for(int j=0;j<3;j++)
					hp.planes.add(new Plane(80*i+100,100*j+10,false,hp)) ;
				
				live=true;
				hp.setTime(1000);
				setX(350);
				setY(520);
			}
			break;
		case KeyEvent.VK_S:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			x-=XSPEED;//caseĬ�Ϻ������ŵĹ���
			break;
		case KeyEvent.VK_RIGHT:
			x+=XSPEED;
			break;
		}
		
		if(x<0) x=0;
		if(x>740) x=740;
		
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

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Missile fire(){
		if(!live) return null;
		int x=this.x+20;
		int y=this.y+20;
		Missile m=null;
		if(good){
			m=new Missile(x,y,true,hp);
		}
		else{
			m=new Missile(x,y,false,hp);
		}
		hp.missiles.add(m);
		return m;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public Rectangle getRect() {
		return new Rectangle(x,y,60,60);
	}
	
	
	public void clear(){
		for(int i=0;i<hp.planes.size();i++){//����ظ�����
			hp.planes.remove(hp.planes.get(i));//ȥ��̹��
		}
		
		for(int i=0;i<hp.planes.size();i++){//����ظ�����
			hp.planes.remove(hp.planes.get(i));//ȥ��̹��
		}
		
		for(int i=0;i<hp.planes.size();i++){//����ظ�����
			hp.planes.remove(hp.planes.get(i));//ȥ��̹��
		}
		
		for(int i=0;i<hp.planes.size();i++){//����ظ�����
			hp.planes.remove(hp.planes.get(i));//ȥ��̹��
		}
		
		//ȥ���ӵ�
		for(int i=0;i<hp.missiles.size();i++){//����ظ�����
			hp.missiles.remove(hp.missiles.get(i));//ȥ��̹��
		}
		for(int i=0;i<hp.missiles.size();i++){//����ظ�����
			hp.missiles.remove(hp.missiles.get(i));//ȥ��̹��
		}
		for(int i=0;i<hp.missiles.size();i++){//����ظ�����
			hp.missiles.remove(hp.missiles.get(i));//ȥ��̹��
		}
		for(int i=0;i<hp.missiles.size();i++){//����ظ�����
			hp.missiles.remove(hp.missiles.get(i));//ȥ��̹��
		}
	}
	
	
}


