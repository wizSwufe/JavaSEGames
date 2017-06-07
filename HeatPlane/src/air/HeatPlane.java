package air;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class HeatPlane extends Frame{
	
	public static final int GAME_WIDTH=800;//�汾5.0--������ع�--�����׸Ķ���������Ϊ����
	public static final int GAME_HEIGHT=600;

	Plane myPlane=new Plane(350,520,true,this);
	List<Missile> missiles=new ArrayList<Missile>();
	List<Explode> explodes=new ArrayList<Explode>();
	List<Plane> planes=new ArrayList<Plane>();
	Image offScreenImage=null;
	int time=1000;

	
	public void update(Graphics g){//�汾4.1--�ػ�ͼƬ--��������ͼƬ��Ȼ��ճ��
		if(offScreenImage==null){
			offScreenImage =this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();//�õ�����ͼƬ�Ļ���
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.pink);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);//ˢ�±���ͼƬ
		gOffScreen.setColor(c);//�ظ�����ͼƬ���ʵ���ɫ
		
		paint(gOffScreen);//��ͼ���ڱ���ͼƬ��
		g.drawImage(offScreenImage,0,0,null);//��ͼƬ������Ļ
		
	}
	
	public void paint(Graphics g) {
		g.drawString("Missiles count:"+missiles.size(), 10, 300);
		g.drawString("explodes count:"+explodes.size(), 10, 320);
		g.drawString("planes count:"+planes.size(), 10, 340);
		g.drawString("��Ŀǰ�ĵ÷��ǣ�"+myPlane.getPoint()+"�֣�", 10,360);
		g.drawString("��Ŀǰ����߼�¼�ǣ�"+myPlane.getMaxpoint()+"�֣�",10,380);
		g.drawString("Ŀǰ��ʣ��ʱ�䣺"+time+"����", 10, 400);
		
		if(planes.size()<=0&&time>0){//�汾26--�о����⣬��ӵо�
			g.drawString("��ϲ���أ������밴F1����ʼ��һ�أ�", 250, 300);
			
		}
		
		if(!myPlane.isLive()){
			g.drawString("���������ˣ�����Ϸʧ�ܣ��밴F2�����¿�ʼ��", 250, 300);
		}
		
		if(myPlane.isLive()&&time<=0){
			g.drawString("ʱ�䵽����Ϸʧ�ܣ��밴F2�����¿�ʼ��", 250, 300);
			myPlane.clear();
		}
		
		for(int i=0;i<missiles.size();i++){//ֻװ��ȥ����������û��ȡ��
			Missile m=missiles.get(i);
			m.hitPlanes(planes);
			m.hitPlane(myPlane);
			m.draw(g);
		}
		
		for(int i=0;i<explodes.size();i++){
			Explode e=explodes.get(i);
			e.draw(g);//������ж���̹��ʱ�Ķ�α�ը
		}
		
		for(int i=0;i<planes.size();i++){
			Plane e=planes.get(i);
			e.draw(g);//������ж���̹��ʱ�Ķ�α�ը
		}
		
		myPlane.draw(g);
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void launchFrame(){//�汾1.0--��������
		//�汾18--��ӵط�̹��
				for(int i=0;i<7;i++){
					for(int j=0;j<3;j++)
					planes.add(new Plane(80*i+100,100*j+10,false,this) );
				}
		
			this.setLocation(250,100);
			this.setSize(GAME_WIDTH,GAME_HEIGHT);
			this.setTitle("С�۷���Ϯ������---��ɻ�");
			this.addWindowListener(new WindowAdapter(){

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
				
			});//�汾2.0--�رմ��ڣ��̶���С
			this.setResizable(false);
			this.setBackground(Color.pink);//���ô��ڱ���ɫ
			this.addKeyListener(new KeyMonitor());//���������Ӽ��̼���
			setVisible(true);
			
			new Thread(new PaintThread()).start();//�����������߳�
	}
		
	public static void main(String[] args) {
			HeatPlane hp=new HeatPlane();
			hp.launchFrame();
	}

	
	private class PaintThread implements Runnable{
		public void run() {//�汾4.0--��̹�˶���������ͣ���ػ�
			while(true){
				repaint();
				try {
					Thread.sleep(100);//ϵͳ����
					if( time>0 && planes.size()>0 && myPlane.isLive()  )  time--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

	private class KeyMonitor extends KeyAdapter{//�汾6.0--���Ӽ��̼���
		
		public void keyPressed(KeyEvent e) {
			myPlane.keyPressed(e);
		}
	}
	
}
	
	
