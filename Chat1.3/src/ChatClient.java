import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {//�ͻ���
	Socket s=null;
	DataOutputStream dos=null;
	DataInputStream dis=null;
	private boolean bConnected =false;
	
	TextField tfTxt = new TextField();//�汾2.0--���ݔ���
	TextArea taContent = new TextArea();
	
	Thread tRecv=new Thread(new RecvThread());

	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}

	public void launchFrame() {//�汾1.0--��ӿ��
		setLocation(400, 300);
		this.setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();// �Զ��������������ʴ�С
		this.addWindowListener(new WindowAdapter(){////�汾3.0--��Ӵ����P�]���¼�푑�������

			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();//���һ�򿪣��Ϳ�ʼ����
		
		tRecv.start();//�������ܷ��������߳�
	}
	
	public void connect(){//�汾6.0--��ӿͻ�����������˵�����
			try {
				s=new Socket("127.0.0.1",8880);//�ͻ��ˣ����ӷ�ʽ
				dos=new DataOutputStream(s.getOutputStream());//�õ��ܵ�
				dis=new DataInputStream(s.getInputStream());
System.out.println("connected!");//�����Ƿ����Ϸ�����
				bConnected=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void disconnect(){
		
		try {
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}//�汾12--bug�޸�
		
	}
	
	private class TFListener implements ActionListener{//�汾4.0--����������������ӵ������

		@Override
		public void actionPerformed(ActionEvent e) {
			String str=tfTxt.getText().trim();//ȥ�����߶���Ŀո�
			//taContent.setText(str);
			tfTxt.setText("");
			
			try {
				//System.out.println(s);//�汾7.0--�ͻ�����������˽�������
				dos.writeUTF(str);//�Ѷ���д��ͻ���
				dos.flush();
				//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}//�ڲ���
		
	}

	private class RecvThread implements Runnable{

		@Override
		public void run() {
				try {
					while(bConnected){
						String str=dis.readUTF();
						taContent.setText(taContent.getText()+str+'\n');//�汾12--���ݽ�������
					}	
				}catch(SocketException e){
					System.out.println("�ټ��ˣ�bye");
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
	}
	
}
