package com.bjsxt.tank;
import  java.awt.*;

public class Explode {//�汾17--���챬ը��
	int x,y;
	private boolean live=true;
	private TankClient tc;
	
	//�汾28����ӱ�ը��ͼƬ
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();//���߰��࣬�ܹ���ȡӲ���ϵ����ݺ�ͼƬ
	
	private static Image[] imgs={
		tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),//ͨ��װ������ȡ·���µ����ݺ�ͼƬ
		tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};//��̬��ʼ��,�ö��Ÿ���
	int step=0;
	private boolean init=false;
	
	public Explode(int x,int y,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	
	public void draw(Graphics g){
		if(!init){//���Ƚ� ͼƬװ�ص��ڴ���
			for (int i= 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init=true;
		}
		
		if(!live){
			tc.explodes.remove(this);
			return ;
		}
		
		if(step==imgs.length){
			live=false;
			step=0;
			return ;
		}
		
		//����������첽��IO���µ�1��ͼƬ��������
		g.drawImage(imgs[step], x, y, null);
		
		step++;
	}
	
	
	
	
	
}
