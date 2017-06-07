package smallBird;
import java.awt.*;
import java.awt.event.*;

public class Bird {
	int x,y;
	
	public static final int BWIDTH=40;//����Ŀ��
	public static final int BHEIGHT=40;
	private static final int YSPEED=80;//������ֻ�������ƶ�
	private  boolean live=true;
	FlyBird fb=null;
	
	private static int point;//����
	private static int maxpoint;
	
	
	public static int getMaxpoint() {
		return maxpoint;
	}

	public static void setMaxpoint(int maxpoint) {
		Bird.maxpoint = maxpoint;
	}

	public static int getPoint() {
		return point;
	}

	public static void setPoint(int point) {
		Bird.point = point;
	}

	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean live){
		this.live=live;
	}
	
	public Bird(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Bird(int x, int y,FlyBird fb) {
		this(x,y);
		this.fb=fb;//�汾11��12--����һ���������
	}
	
	public void draw(Graphics g){
		if(!live) return;
		Color c=g.getColor();//�汾3.0--����С��
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, BWIDTH,BHEIGHT);
		g.setColor(Color.red);
		g.fillOval(x+BWIDTH/2,y+BHEIGHT/4,BWIDTH/2,BHEIGHT/2);
		g.setColor(Color.green);
		g.fillOval(x+5*BWIDTH/8, y+3*BHEIGHT/8,BWIDTH/4, BHEIGHT/4);
		g.setColor(c);//���滭�ʱ�������ɫ
		
		move();//�汾8.0��9.0һ�ʴ��������ﲻ��Ҫ��Щ�����ܡ�--��һЩ�ƶ�д�ɷ���
	}
	
	public void move() {
		y+=10;//�Զ���׹����
	}

	public void keyReleased(KeyEvent e) {//�汾7.0--������󣬽�С���װ����
		int key=e.getKeyCode();
		switch(key){
		
		case KeyEvent.VK_F2:
			if(!live){//���¿�ʼ
				clear();
				if(maxpoint<point){
					maxpoint=point;
				}
				point=0;
				live=true;
				this.setX(fb.Bird_X);
				this.setY(fb.Bird_Y);
			}
			break;
		case KeyEvent.VK_UP:
			y-=YSPEED;//caseĬ�Ϻ������ŵĹ���
			createHazard();
			if(y<=0){
				this.live=false;
			}
			break;
		}
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

	public void createHazard(){
		Hazard h=null;
		int yy=fb.getR().nextInt(600);//�汾13��19��20--����һ����������ϰ���ĸ߶�+С��ĸ߶�ҪԶԶС����Ļ�߶�
		if(yy<=300){
			h=new Hazard(fb.GAME_WIDTH,0,yy,fb);//�汾11--�����ϰ��������--�����濪ʼ����
			fb.hazards.add(h);
			h=new Hazard(fb.GAME_WIDTH,yy+250,fb.GAME_HEIGHT-yy-250,fb);//�汾11--�����ϰ��������
			fb.hazards.add(h);
		}else {//������ϰ���
			h=new Hazard(fb.GAME_WIDTH,yy,fb.GAME_HEIGHT-yy,fb);
			fb.hazards.add(h);
			h=new Hazard(fb.GAME_WIDTH,0,yy-250,fb);
			fb.hazards.add(h);
		}

	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,BWIDTH,BHEIGHT);
	}

	
	public void clear(){
		for(int i=0;i<fb.hazards.size();i++){//����ظ�����
			fb.hazards.remove(fb.hazards.get(i));//ȥ���ϰ���
		}
		for(int i=0;i<fb.hazards.size();i++){//����ظ�����
			fb.hazards.remove(fb.hazards.get(i));//ȥ���ϰ���
		}
		for(int i=0;i<fb.hazards.size();i++){//����ظ�����
			fb.hazards.remove(fb.hazards.get(i));//ȥ���ϰ���
		}
		for(int i=0;i<fb.hazards.size();i++){//����ظ�����
			fb.hazards.remove(fb.hazards.get(i));//ȥ���ϰ���
		}
				
	}
	
	
	public boolean pass(java.util.List<Hazard> hazards){//�汾26--���ӵ÷�ϵͳ
		
		for(int i=0;i<hazards.size();i++){
			if(this.isLive()&&this.getX()>(hazards.get(i).getX()+hazards.get(i).XSPEED) && hazards.get(i).isLive()){
					fb.hazards.remove(fb.hazards.get(i));
					return true;
			}
			
		}
		return false;
	}
}