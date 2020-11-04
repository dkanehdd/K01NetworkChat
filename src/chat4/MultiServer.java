package chat4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	//멤버변수
	static ServerSocket serverSocket =null;
	static Socket socket = null;
	static PrintWriter out = null;
	static BufferedReader in =null;
	static String s ="";//클라이언트의 메세지를 저장
	
	//생성자
	public MultiServer() {
		//실행부없음
	}
	//서버의 초기화를 담당할 메소드
	public static void init() {

		String name = "";//클라이언트의 이름을 저장
		
		try {
			/*
			9999번으로 포트번호를 설정하여 서버객체를 생성하고
			클라이언트의 접속을 기다린다.
			 */
			serverSocket = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			
			//클라이언트의 접속을 허가
			socket = serverSocket.accept();
			System.out.println(socket.getInetAddress()+":"+socket.getPort());
			
			//메세지를 보낼준비(output스트림)
			out = new PrintWriter(socket.getOutputStream(), true);
			//메세지를 읽을(받을)준비(input스트림)
			in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			/*
			클라이언트가 서버로 전송하는 최초의 메세지는 "대화명"이므로
			메세지를 읽은후 변수에 저장하고 클라이언트 쪽으로 Echo해준다.
			 */
			if(in!=null) {
				name = in.readLine();//클라이언트의 이름을 읽어서 저장
				System.out.println(name+" 접속");//서버의 콘솔에 출력
				out.println(">"+name+"님이 접속했습니다.");//클라이언트에게Echo
			}
			/*
			두번째 메세지부터는 실제 대화내용이므로 읽어와서 콘솔에 출력하고
			동시에 클라이언트측으로 Echo해준다.
			 */
			while(in != null) {
				s=in.readLine();
				if(s==null) {
					break;
				}
				
				System.out.println(name + " ==> "+s);//메세지 출력
				sendAllMsg(name, s);//클라이언트에게 Echo해주는 메소드 호출
			}
			System.out.println("Bye...!!!");
		}
		catch (Exception e) {
			System.out.println("예외1:"+e);
		}
		finally {
			try {
				in.close();
				out.close();
				socket.close();
				serverSocket.close();
			}
			catch (Exception e) {
				System.out.println("예외2:"+e);
				//e.printStackTrace();
			}
		}
	}
	//서버가 클라이언트에게 메세지를 Echo해주는 메소드
	public static void sendAllMsg(String name, String msg) {
		try {
			out.println("> "+name+" ==> "+msg);
		}
		catch (Exception e) {
			System.out.println("예외:" +e);
		}
	}
	//main()은 프로그램의 출발점 역할만 담당한다.
	public static void main(String[] args) {
		init();
	}
}
