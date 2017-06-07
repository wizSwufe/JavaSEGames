import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {//�汾5.0---�����---��������
	boolean started=false;
	ServerSocket ss=null;
	
	List<Client> clients=new ArrayList<Client>();//�洢�յ��Ŀͻ���
	
	public static void main(String[] args) {
		new ChatServer().start();//������Ҫ��ˬ
	}
	
	public void start(){
		//�ڲ���ֻ�ܷ�װ�ڶ�̬������--�汾10�����ڽ��ܶ���ͻ�������
				
		
		try {
			ss=new ServerSocket(8880);//���ܶ˿ں������true
			started=true;
		}catch(BindException e){
			System.out.println("�˿�ʹ���С�����");
			System.out.println("��ص���س��򣬲��������з�������");
			System.exit(0);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			while(started){
				Socket	s=ss.accept();//acceptedҲ����������
				Client c=new Client(s);//���ܿͻ�������������߳�
System.out.println("a client connected!");//������䣬�ŵ������
				new Thread(c).start();
				clients.add(c);
				//dis.close();
			}
		}catch (IOException e) {//�汾9.0--�����쳣���ص�
			e.printStackTrace();
		}finally{
			try{
				ss.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	
	}
	
	class Client implements Runnable{//�߳̿ͻ���
		private Socket s=null;
		private DataInputStream dis=null;
		private DataOutputStream dos=null;
		private boolean bConnected=false;
		
		public Client(Socket s){//���췽��
			this.s=s;
			try{
				dis=new DataInputStream(s.getInputStream());
				dos=new DataOutputStream(s.getOutputStream());
				bConnected=true;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		public void send(String str){//������Ϣ
			try{
				dos.writeUTF(str);
			} catch (IOException e) {//�汾13--�޸�bugʵʱ���¿ͻ�����
				clients.remove(this);
				System.out.println("�Է��˳��ˣ��Ҵ��б�ȥ���ˣ�");
				//e.printStackTrace();
			}
		}
		
		public void run(){
			
			try{
				while(bConnected){
					String str=dis.readUTF();
System.out.println(str);
					for(int i=0;i<clients.size();i++){//��ȡ��һ�����ͷ������еĿͻ���
					Client	c=clients.get(i);
						c.send(str);
					}//�汾11--�������˷�����Ϣ�����пͻ���
					
				}
			}catch(EOFException e){
				System.out.println("Client closed!");
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				
				try{
					if(dis!=null) dis.close();
					if(dos!=null) dos.close();
					if(s!=null) s.close();
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}
	}
		
}
}