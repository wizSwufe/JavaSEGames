import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {//版本5.0---网络版---服务器端
	boolean started=false;
	ServerSocket ss=null;
	
	List<Client> clients=new ArrayList<Client>();//存储收到的客户端
	
	public static void main(String[] args) {
		new ChatServer().start();//主函数要清爽
	}
	
	public void start(){
		//内部类只能封装在动态方法中--版本10，对于接受多个客户端数据
				
		
		try {
			ss=new ServerSocket(8880);//接受端口后才能是true
			started=true;
		}catch(BindException e){
			System.out.println("端口使用中。。。");
			System.out.println("请关掉相关程序，并重新运行服务器！");
			System.exit(0);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			while(started){
				Socket	s=ss.accept();//accepted也是阻塞函数
				Client c=new Client(s);//接受客户端请求后，生成线程
System.out.println("a client connected!");//测试语句，放到最左边
				new Thread(c).start();
				clients.add(c);
				//dis.close();
			}
		}catch (IOException e) {//版本9.0--处理异常的特点
			e.printStackTrace();
		}finally{
			try{
				ss.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	
	}
	
	class Client implements Runnable{//线程客户类
		private Socket s=null;
		private DataInputStream dis=null;
		private DataOutputStream dos=null;
		private boolean bConnected=false;
		
		public Client(Socket s){//构造方法
			this.s=s;
			try{
				dis=new DataInputStream(s.getInputStream());
				dos=new DataOutputStream(s.getOutputStream());
				bConnected=true;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		public void send(String str){//发送消息
			try{
				dos.writeUTF(str);
			} catch (IOException e) {//版本13--修复bug实时更新客户数量
				clients.remove(this);
				System.out.println("对方退出了，我从列表去除了！");
				//e.printStackTrace();
			}
		}
		
		public void run(){
			
			try{
				while(bConnected){
					String str=dis.readUTF();
System.out.println(str);
					for(int i=0;i<clients.size();i++){//读取到一个，就发给所有的客户端
					Client	c=clients.get(i);
						c.send(str);
					}//版本11--服务器端发送消息给所有客户端
					
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