import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {//客户端
	Socket s=null;
	DataOutputStream dos=null;
	DataInputStream dis=null;
	private boolean bConnected =false;
	
	TextField tfTxt = new TextField();//版本2.0--添加輸入框
	TextArea taContent = new TextArea();
	
	Thread tRecv=new Thread(new RecvThread());

	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}

	public void launchFrame() {//版本1.0--添加框架
		setLocation(400, 300);
		this.setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();// 自动调整窗口至合适大小
		this.addWindowListener(new WindowAdapter(){////版本3.0--添加窗口關閉的事件響應匿名类

			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();//框架一打开，就开始链接
		
		tRecv.start();//启动接受服务器的线程
	}
	
	public void connect(){//版本6.0--添加客户端与服务器端的链接
			try {
				s=new Socket("127.0.0.1",8880);//客户端，链接方式
				dos=new DataOutputStream(s.getOutputStream());//得到管道
				dis=new DataInputStream(s.getInputStream());
System.out.println("connected!");//测试是否连上服务器
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
		}//版本12--bug修改
		
	}
	
	private class TFListener implements ActionListener{//版本4.0--动作监听，必须添加到框架里

		@Override
		public void actionPerformed(ActionEvent e) {
			String str=tfTxt.getText().trim();//去掉两边多余的空格
			//taContent.setText(str);
			tfTxt.setText("");
			
			try {
				//System.out.println(s);//版本7.0--客户端与服务器端交换数据
				dos.writeUTF(str);//把东西写入客户端
				dos.flush();
				//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}//内部类
		
	}

	private class RecvThread implements Runnable{

		@Override
		public void run() {
				try {
					while(bConnected){
						String str=dis.readUTF();
						taContent.setText(taContent.getText()+str+'\n');//版本12--数据接收排列
					}	
				}catch(SocketException e){
					System.out.println("再见了！bye");
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
	}
	
}
