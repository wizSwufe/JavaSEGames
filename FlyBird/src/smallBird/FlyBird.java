package smallBird;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlyBird extends Frame {

	public static final int GAME_WIDTH=800;//�汾5.0--������ع�--�����׸Ķ���������Ϊ����
	public static final int GAME_HEIGHT=600;
	public static final int Bird_X=200;//����ĳ�ʼ�����
	public static final int Bird_Y=270;
	
	
	Bird bird=new Bird(Bird_X,Bird_Y,this);
	Image offScreenImage=null;
	
	private static Random r=new  Random();//�������������һ���͹���
	public static Random getR() {
		return r;
	}

	public static void setR(Random r) {
		FlyBird.r = r;
	}
	
	
	
	List<Hazard> hazards=new ArrayList<Hazard>();//�汾13--���һ���ϰ���
	
	public void paint(Graphics g) {
		if(bird.pass(hazards)){
			bird.setPoint(bird.getPoint()+100);
		}
		
		if(bird.getY()+bird.BHEIGHT>this.GAME_HEIGHT){
			bird.setLive(false);
		}
		
		g.drawString("����������С�����Ծ��", 10, 70);
		
		g.drawString("����ǰ����߼�¼�ǣ�"+bird.getMaxpoint()+"��", 10, 110);
		g.drawString("���ĵ�ǰ�÷��ǣ�"+bird.getPoint()+"��", 10, 130);
		if(!bird.isLive()){
			g.drawString("��Ϸʧ�ܣ�����С����ˣ��밴F2�����¿�ʼ��", 220, 200);
			g.drawString("��֮ǰ����߼�¼�ǣ�"+bird.getMaxpoint()+"��", 220, 230);
			g.drawString("���ĵ�ǰ�÷��ǣ�"+bird.getPoint()+"��", 220, 260);
		}
		
		
		for(int i=0;i<hazards.size();i++){//ֻװ��ȥ����������û��ȡ��
			Hazard h=hazards.get(i);//�汾14��15--���ϰ���ȥ��
			h.hitBird(bird);//�汾16��17��18--��ײ���
			h.crash(hazards);//�汾21��22��23��24--�ϰ�֮�䲻���໥����
			h.draw(g);
			
			
			
			if(!h.isLive()){
				hazards.remove(h);
			}
		}
		
		bird.draw(g);
	}
	
	public void update(Graphics g){//�汾4.1--�ػ�ͼƬ--��������ͼƬ��Ȼ��ճ��--ע���ػ�������
		if(offScreenImage==null){
			offScreenImage =this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();//�õ�����ͼƬ�Ļ���
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.CYAN);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);//ˢ�±���ͼƬ
		gOffScreen.setColor(c);//�ظ�����ͼƬ���ʵ���ɫ
		
		paint(gOffScreen);//��ͼ���ڱ���ͼƬ��
		g.drawImage(offScreenImage,0,0,null);//��ͼƬ������Ļ
		
	}
	
	public void launchFrame(){//�汾1.0--��������
		this.setLocation(200,100);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("Loving Bird��");
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});//�汾2.0--���Ӵ��ڼ������رմ��ڣ��̶���С
		this.setResizable(false);
		this.setBackground(Color.CYAN);//���ô��ڱ���ɫ
		this.addKeyListener(new KeyMonitor());//���������Ӽ��̼���
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();//�汾4.0--��С����������ͣ���ػ�--�����������߳�
		
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
					Thread.sleep(100);//ϵͳ����
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	
	private class KeyMonitor extends KeyAdapter{//�汾6.0--���Ӽ��̼���

		
		public void keyPressed(KeyEvent e) {//˽���ڲ�������--������������
			bird.keyReleased(e);
		}
	}
		
}
	

