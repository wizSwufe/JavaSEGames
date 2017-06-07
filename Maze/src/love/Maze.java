package love;
import java.awt.*;
import java.awt.event.*;

public class Maze extends Frame{//�汾1.0--��������

	Wall w1=new Wall(40,40,720,40);//���ķ����ǵ���������--������߽�
	Wall w2=new Wall(40,80,40,480);
	Wall w3=new Wall(720,80,40,480);
	Wall w4=new Wall(80,520,680,40);
	
	//��ǽ��
	Wall w5=new Wall(120,80,40, 80);//��һ��80
	Wall w6=new Wall(160, 120, 40, 40);
	Wall w7=new Wall(240, 80, 80, 40);
	Wall w8=new Wall(480, 80, 40, 40);
	Wall w9=new Wall(640, 80, 40, 40);
	Wall w10=new Wall(160, 120, 40, 40);//�ڶ���120
	Wall w11=new Wall(360, 120, 160, 40);
	Wall w12=new Wall(560, 120, 40, 80);
	Wall w13=new Wall(240, 160, 80, 40);//������160
	Wall w14=new Wall(640, 160, 80, 40);
	Wall w15=new Wall(120, 200, 160, 40);//���Ĳ�200
	Wall w16=new Wall(360, 200, 80, 40);
	Wall w17=new Wall(480, 200, 40, 80);
	Wall w18=new Wall(600, 200, 40, 40);
	Wall w19=new Wall(200, 240, 80, 40);//�����240
	Wall w20=new Wall(320, 240, 40, 240);
	Wall w21=new Wall(520, 240, 40, 80);
	Wall w22=new Wall(680, 240, 40, 80);
	Wall w23=new Wall(80, 280, 80, 40);//������280
	Wall w24=new Wall(200, 280, 40, 120);
	Wall w25=new Wall(280, 280, 40, 40);
	Wall w26=new Wall(400, 280, 40, 40);
	Wall w27=new Wall(560, 280, 80, 40);
	Wall w28=new Wall(440, 320, 40, 80);//���߲�320
	Wall w29=new Wall(600, 320, 40, 80);
	Wall w30=new Wall(80, 360, 40, 40);//�ڰ˲�360
	Wall w31=new Wall(160, 360, 120, 40);
	Wall w32=new Wall(360, 360, 160, 40);
	Wall w33=new Wall(560, 360, 120, 40);
	Wall w34=new Wall(480, 400, 80, 40);//��9��400
	Wall w35=new Wall(80, 440, 80, 40);//��ʮ��440
	Wall w36=new Wall(200, 440, 120, 40);
	Wall w37=new Wall(400, 440, 40, 80);
	Wall w38=new Wall(520, 440, 40, 40);
	Wall w39=new Wall(600, 440, 120, 40);
	Wall w40=new Wall(440, 480, 80, 40);//��ʮһ��480
	
	Hero h=new Hero(80,80,0,this);
	Hero h1=new Hero(680,480,1,this);
	Door d=new Door(640,480);
	Key k=new Key(680,80);
	private  int time=500;
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	Image offScreenImage=null;
	
	public void crash(){//ײǽ
		h.collidesWithWall(w1);
		h.collidesWithWall(w2);
		h.collidesWithWall(w3);
		h.collidesWithWall(w4);
		h.collidesWithWall(w5);
		h.collidesWithWall(w6);
		h.collidesWithWall(w7);
		h.collidesWithWall(w8);
		h.collidesWithWall(w9);
		h.collidesWithWall(w10);
		h.collidesWithWall(w11);
		h.collidesWithWall(w12);
		h.collidesWithWall(w13);
		h.collidesWithWall(w14);
		h.collidesWithWall(w15);
		h.collidesWithWall(w16);
		h.collidesWithWall(w17);
		h.collidesWithWall(w18);
		h.collidesWithWall(w19);
		h.collidesWithWall(w20);
		h.collidesWithWall(w21);
		h.collidesWithWall(w22);
		h.collidesWithWall(w23);
		h.collidesWithWall(w24);
		h.collidesWithWall(w25);
		h.collidesWithWall(w26);
		h.collidesWithWall(w27);
		h.collidesWithWall(w28);
		h.collidesWithWall(w29);
		h.collidesWithWall(w30);
		h.collidesWithWall(w31);
		h.collidesWithWall(w32);
		h.collidesWithWall(w33);
		h.collidesWithWall(w34);
		h.collidesWithWall(w35);
		h.collidesWithWall(w36);
		h.collidesWithWall(w37);
		h.collidesWithWall(w38);
		h.collidesWithWall(w39);
		h.collidesWithWall(w40);
		
	}
	
	public void paint(Graphics g) {//��������
		this.draw(g);//��ǽ��
		g.drawString("����ǰ��Կ��������"+h.getKeynum(), 500, 60);
		g.drawString("����ǰ��ʣ��ʱ���ǣ�"+this.getTime()+"���룡", 500,540);
	
		if(d.isLive()){
			d.draw(g);
		}
		if(k.isLive()){
			k.draw(g);
		}
		
		if(h.isLive()) h.draw(g);
		if(h1.isLive())h1.draw(g);//��Ӣ��
		crash();//ײǽ���
		if(h.hit(h1)&&h1.isLive()){
			g.drawString("��ϲ���أ����������������Ҹ�����������һ��  ��F2�����¿�ʼ��", 60, 560);
		}
		if(h.hitDoor(d)){
			g.drawString("�ع������Ǵ��ˣ�", 100, 60);
		}
		if(h.hitKey(k)){
			g.drawString("���Կ�ף����Ͽ�ȥ�ȹ�����", 200, 60);
		}
		if(this.getTime()==0){
			g.drawString("ʱ�䵽����Ϸʧ�ܣ�������Ҳ���������������ˣ���F2�����¿�ʼ��", 60, 540);
			h1.setLive(false);
		}

	}
	
	private void draw(Graphics g) {//������ͼ
		Color c=g.getColor();
		g.setColor(Color.blue);
	
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		w4.draw(g);
		
		//��ǽ��
		w5.draw(g);
		w6.draw(g);
		w7.draw(g);
		w8.draw(g);
		w9.draw(g);
		w10.draw(g);
		w11.draw(g);
		w12.draw(g);
		w13.draw(g);
		w14.draw(g);
		w15.draw(g);
		w16.draw(g);
		w17.draw(g);
		w18.draw(g);
		w19.draw(g);
		w20.draw(g);
		w21.draw(g);
		w22.draw(g);
		w23.draw(g);
		w24.draw(g);
		w25.draw(g);
		w26.draw(g);
		w27.draw(g);
		w28.draw(g);
		w29.draw(g);
		w30.draw(g);
		w31.draw(g);
		w32.draw(g);
		w33.draw(g);
		w34.draw(g);
		w35.draw(g);
		w36.draw(g);
		w37.draw(g);
		w38.draw(g);
		w39.draw(g);
		w40.draw(g);
		
		g.setColor(c);
	}
	
	//˫��������˸����
	public void update(Graphics g){//�汾4.1--�ػ�ͼƬ--��������ͼƬ��Ȼ��ճ��
		if(offScreenImage==null){
			offScreenImage =this.createImage(800,600);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();//�õ�����ͼƬ�Ļ���
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0,0,800,600);//ˢ�±���ͼƬ
		gOffScreen.setColor(c);//�ظ�����ͼƬ���ʵ���ɫ
		
		paint(gOffScreen);//��ͼ���ڱ���ͼƬ��
		g.drawImage(offScreenImage,0,0,null);//��ͼƬ������Ļ
		
	}
	

	public  void launchFrame() {
			setLocation(200,100);
			setSize(800,600);
			this.setTitle("�������Ӿȹ���֮���Թ�");
			this.addWindowListener(new WindowAdapter(){

				@Override
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
			Maze m=new Maze();
			m.launchFrame();
		}

		private class PaintThread implements Runnable{

			public void run() {//�汾4.0--���������Ӷ���������ͣ���ػ�
				while(true){
					repaint();
					try {
						Thread.sleep(100);//ϵͳ����
						if(h.x == 680 && h.y==480||time==0){
							time=time;
						}else{
							time--;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		}
		
		private class KeyMonitor extends KeyAdapter{//�汾6.0--���Ӽ��̼���
			
			public void keyReleased(KeyEvent e) {
				h.keyReleased(e);
			}
		}
}
