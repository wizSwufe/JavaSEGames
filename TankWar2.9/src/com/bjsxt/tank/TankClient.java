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
 *�汾26--���ע�ͣ�---����ͷ������о�̬����
 */

public class TankClient extends Frame{

	public static final int GAME_WIDTH=800;//�汾5.0--������ع�--�����׸Ķ���������Ϊ����
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
	
	//������˸��������˫����,���ֺ���Ҫ
	public void update(Graphics g){//�汾4.1--�ػ�ͼƬ--��������ͼƬ��Ȼ��ճ��
		if(offScreenImage==null){
			offScreenImage =this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();//�õ�����ͼƬ�Ļ���
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);//ˢ�±���ͼƬ
		gOffScreen.setColor(c);//�ظ�����ͼƬ���ʵ���ɫ
			
		paint(gOffScreen);//��ͼ���ڱ���ͼƬ��
		g.drawImage(offScreenImage,0,0,null);//��ͼƬ������Ļ
			
	}
	
	
	public void paint(Graphics g) {
		g.drawString("Missiles count:"+missiles.size(), 10, 50);
		g.drawString("explodes count:"+explodes.size(), 10, 70);
		g.drawString("tanks count:"+tanks.size(), 10, 90);
		g.drawString("tanks life:"+myTank.getLife(), 10, 110);
		g.drawString("��Ŀǰ����߼�¼�ǣ�"+myTank.getMaxpoint()+"�֣�",10,130);
		g.drawString("Ŀǰ��ʣ��ʱ�䣺"+time+"����", 10, 160);
		
		g.drawString("��������Ʒ����������ң�", 650,530);
		g.drawString("��S�������ӵ���", 650,550);
		g.drawString("��ɫ�ķ����Ǽ�Ѫ�飡", 650,570);
		g.drawString("��Ŀǰ�ĵ÷��ǣ�"+myTank.getPoint()+"�֣�", 650,590);
		
		if(tanks.size()<=0&&time>0){//�汾26--�о����⣬��ӵо�
			g.drawString("��ϲ���أ������밴F1����ʼ��һ�أ�", 250, 300);
			
		}
		
		if(!myTank.isLive()){
			g.drawString("���������ˣ�����Ϸʧ�ܣ��밴F2�����¿�ʼ��", 250, 300);
		}
		
		if(myTank.isLive()&&time<=0){
			g.drawString("ʱ�䵽����Ϸʧ�ܣ��밴F2�����¿�ʼ��", 250, 300);
			myTank.clear();
		}
		
		
		for(int i=0;i<missiles.size();i++){//ֻװ��ȥ����������û��ȡ��
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
			e.draw(g);//������ж���̹��ʱ�Ķ�α�ը
		}
		
		for(int i=0;i<tanks.size();i++){
			Tank t=tanks.get(i);
			t.collidesWithWall(w1);//�з�̹�˲����Դ�ǽ--�ҷ�����
			t.collidesWithWall(w2);
			t.collidesWithWall(w3);
			t.collidesWithWall(w4);
			
			
			myTank.collidesWithWall(w1);
			myTank.collidesWithWall(w2);
			myTank.collidesWithWall(w3);
			myTank.collidesWithWall(w4);
			
			
			
			t.collidesWithTanks(tanks);
			myTank.collidesWithTanks(tanks);//�汾21--̹��֮�䲻�ܻ��ഩԽ
			t.draw(g);//������ж���̹��ʱ�Ķ�α�ը
		}
		
		myTank.draw(g);
		myTank.eat(b);
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		w4.draw(g);
		b.draw(g);
		
		
	}
	
	
	public void launchFrame(){//�汾1.0--��������
		
		Properties props=new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int initTankCount=Integer.parseInt(props.getProperty("initTankCount"));
		
		//�汾18--��ӵط�̹��
		for(int i=0;i<Integer.parseInt(PropertyMgr.getProperty("initTankCount"));i++){
			tanks.add(new Tank(80+40*(i+1),10,false,Direction.D,this) );
		}
		
		
		this.setLocation(100,100);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("��ʹVSħ��---̹�˴�ս");
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});//�汾2.0--�رմ��ڣ��̶���С
		this.setResizable(false);
		this.setBackground(Color.GREEN);//���ô��ڱ���ɫ
		this.addKeyListener(new KeyMonitor());//���������Ӽ��̼���
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();//�����������߳�
		
	}
	
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable{

		public void run() {//�汾4.0--��̹�˶���������ͣ���ػ�
			while(true){
				repaint();
				try {
					Thread.sleep(100);//ϵͳ����,��ʤ�����߱�����ʱ��ʱ��ֹͣ
					if( time>0 && tanks.size()>0 && myTank.isLive()  )  time--;
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

	private class KeyMonitor extends KeyAdapter{//�汾6.0--���Ӽ��̼���
	
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {//˽���ڲ�������--������������
			myTank.keyPressed(e);
		}
		
	}
	
}

