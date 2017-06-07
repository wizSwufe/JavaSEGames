package com.bjsxt.tank;
import java.awt.*;
import java.awt.event.*;
import java.util.*;//�汾19--�õз�̹���������������ڵ���

public class Tank {
	private int x,y;
	private static int point;//����
	private static int maxpoint;

	public static final int XSPEED=5;
	public static final int YSPEED=5;
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	private BloodBar bb=new BloodBar();
	private int step=r.nextInt(12)+3;//�������
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();//���߰��࣬�ܹ���ȡӲ���ϵ����ݺ�ͼƬ
	private static Image[] tankImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static{
		tankImages=new Image[]{
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),//ͨ��װ������ȡ·���µ����ݺ�ͼƬ
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif"))
		//װ�������ԴӶ�����л�ã�����һ����
		};
		
		imgs.put("L", tankImages[0]);//��̬����ʵ�����򣬿���������������ĳ�ʼ��
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);
		
	};//��̬��ʼ��,�ö��Ÿ���
	
	private int oldX,oldY;
	private int  life=60;//�汾23--�����ս̹������ֵ
	private boolean good;
	
	private boolean live=true;
	private static Random r=new  Random();//�������������һ���͹���
	
	
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

	TankClient tc=null;//�õ�����
	
	private boolean bL=false,bU=false,bR=false,bD=false;//�������
	
	private Direction dir=Direction.STOP;//�������ȷ������仯
	private Direction ptDir=Direction.U;//������ͲԪ�أ��з���
	
	
	private class BloodBar{//�汾2.4--�����ڲ���--��ʾѪ��
		public void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x+5, y-10, WIDTH, 10);//���ⲿ����
			int w=WIDTH*life/60;//Ѫ�����ƿ��
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
		this(x,y,good);//���жԷ����������
		this.dir=dir;
		this.tc=tc;//�汾11--�õ�̹�˿ͻ��˵�Ӧ�ã������ӵ���Ȼ�� �ػ�����
	}
	
	public void draw(Graphics g){
		if(!live){
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
		
		if(good) bb.draw(g);
		
		switch(ptDir){//�汾12--̹���ϻ���Ͳ������ӵ��ķ�������
		case L:
			g.drawImage(imgs.get("L"), x, y, null);//������Ͳ�ķ��򻭳�̹��
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
				step=r.nextInt(12)+3;//��������
				int rn=r.nextInt(dirs.length);//���������
				dir=dirs[rn];
			}			
			step--;
			if(r.nextInt(40) > 36) this.fire();//�ȼ��ڿ�����Ϸ�Ѷ�
		}
		
		
	}
	
	public void keyPressed(KeyEvent e){//�汾7.0--������󣬽�̹�˰�װ����
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
		
		locateDirection();//ȷ������
	}
	
	public BloodBar getBb() {
		return bb;
	}

	public void setBb(BloodBar bb) {
		this.bb = bb;
	}

	public void locateDirection(){//�汾8.0--���̹�˵�8���ƶ������õ�ö�ٷ���
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

	public void keyReleased(KeyEvent e) {//�汾9.0--̹���ƶ��ɹ�--��ʹ�ͷ� ���̿���
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
			if(!live){//����
				clear();
				
				if(maxpoint<point){
					maxpoint=point;
				}
				point=0;
				tc.setTime(1000);
				//��������̹��
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
				//��������̹��
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
			
		case KeyEvent.VK_S://�ո����������ȼ���ͻ��������
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
		
		locateDirection();//ȷ������
		
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
	
	
	private void superFire() {//�汾22--��ӳ����ڵ�
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
			Tank t=tanks.get(i);//ÿ���ж��Ƿ��ÿ��̹����ײ
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
		for(int i=0;i<tc.tanks.size();i++){//����ظ�����
			tc.tanks.remove(tc.tanks.get(i));//ȥ��̹��
		}
		
		for(int i=0;i<tc.tanks.size();i++){
			tc.tanks.remove(tc.tanks.get(i));//ȥ��̹��
		}
		
		for(int i=0;i<tc.tanks.size();i++){
			tc.tanks.remove(tc.tanks.get(i));//ȥ��̹��
		}
		
		for(int i=0;i<tc.tanks.size();i++){
			tc.tanks.remove(tc.tanks.get(i));//ȥ��̹��
		}
		
		//ȥ���ӵ�
		for(int i=0;i<tc.missiles.size();i++){//����ظ�����
			tc.missiles.remove(tc.missiles.get(i));//ȥ��̹��
		}
		for(int i=0;i<tc.missiles.size();i++){//����ظ�����
			tc.missiles.remove(tc.missiles.get(i));//ȥ��̹��
		}
		for(int i=0;i<tc.missiles.size();i++){//����ظ�����
			tc.missiles.remove(tc.missiles.get(i));//ȥ��̹��
		}
		for(int i=0;i<tc.missiles.size();i++){//����ظ�����
			tc.missiles.remove(tc.missiles.get(i));//ȥ��̹��
		}
		
	}
	
	
}
