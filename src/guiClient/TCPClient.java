package guiClient;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

class TestClient extends Thread{
	String testName;
	int testCount;
	TestPage page;
	
	String name;
	String stuNum;
	String[] tests;
	String[] userAnswer;
	String[][] answers;
	
	BufferedImage[] img;
	
	Socket testSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	public TestClient(TestPage _page, String hostIP, int port, String _name, String _stuNum) throws IOException {
		// TODO Auto-generated constructor stub
		page = _page;
		name = _name;
		stuNum = _stuNum;
		try {
			testSocket = new Socket(hostIP, port);
			out = new PrintWriter(testSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO: handle exception
			System.err.println("can't access host");
			throw e;
		} catch (IOException e) {
			System.err.println("File I/O Error");
			throw e;
		}
		
		out.println(name);
		out.println(stuNum);
		
		testName = in.readLine();
		testCount = Integer.parseInt(in.readLine());
		tests = new String[testCount];
		answers = new String[testCount][5];
		userAnswer = new String[testCount];
		
		img = new BufferedImage[testCount];
		
		for(int i = 0; i < testCount; i++) {
			tests[i] = in.readLine();
			
			for(int j = 0; j < 5; j++) {
				answers[i][j] = in.readLine();
			}
			
			if (!in.readLine().equals("null")) {
				img[i] = ImageIO.read(testSocket.getInputStream());
				@SuppressWarnings("unused")
				String tempS = in.readLine();
			}
			else img[i] = null;
			
		}
		page.setTestCount(testCount);
		page.setTestData(tests, answers, img);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.StartConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(page, "서버와 통신을 할 수 없어 데이터를 전송할 수 없습니다.\n프로그램을 종료합니다.", "에러", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	public void StartConnection() throws IOException{
		
		for(int i = 0; i < testCount; i++) {
			out.println(userAnswer[i]);
			
		}
		int score = Integer.parseInt(in.readLine());
		
		System.out.println("맞은 개수는 : " + score + "개 입니다.");
		in.close();
		out.close();
		testSocket.close();
		System.exit(0);
	}
	
	public String getTestName() {
		return testName;
	}
	
	public void setAnswer(String[] finalAnswers) {
		userAnswer = finalAnswers;
	}
	
	public String getTest(int i) {
		return tests[i];
	}
	
	public BufferedImage getPicture(int i) {
		return img[i];
	}
	
	public String getTestAnswer(int testNum, int answer) {
		return answers[testNum][answer];
	}
	public String getUserAnswer(int i) {
		return userAnswer[i];
	}
}

public class TCPClient {

	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//new TestClient("127.0.0.1", 5555);
	}*/

}
