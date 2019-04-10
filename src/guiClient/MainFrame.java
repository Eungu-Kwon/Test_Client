package guiClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
class MyFrameInClient extends JFrame {
	
	JPanel panel;
	
	JTextField ipField;
	JTextField portField;
	JTextField nameField;
	JTextField stuNumField;
	JButton endButton;
	
	public MyFrameInClient() {
		// TODO Auto-generated constructor stub
		//setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("시험 프로그램");
		setResizable(false);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel ipPanel = new JPanel();
		ipPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		ipPanel.setPreferredSize(new Dimension(600, 60));
		
		JLabel ipLabel = new JLabel("IP 주소 : ");
		ipField = new JTextField("localhost", 15);
		
		JPanel portPanel = new JPanel();
		portPanel.setPreferredSize(new Dimension(400,  80));
		portPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		
		JLabel portInfo = new JLabel("Port : ");
		portField = new JTextField("5555", 6);
		
		JPanel studentInfo = new JPanel();
		studentInfo.setLayout(new BoxLayout(studentInfo, BoxLayout.Y_AXIS));
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		JLabel nameLabel = new JLabel("이름 : ");
		nameField = new JTextField(6);
		namePanel.setPreferredSize(new Dimension(400, 40));
		
		JPanel stuNumPanel = new JPanel();
		stuNumPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		JLabel stuNumLabel = new JLabel("학번 : ");
		stuNumField = new JTextField(13);
		stuNumPanel.setPreferredSize(new Dimension(400, 150));
		
		if(HasFont("나눔바른고딕")) {
			ipLabel.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
			portInfo.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
			nameLabel.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
			stuNumLabel.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
		}
		
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		stuNumPanel.add(stuNumLabel);
		stuNumPanel.add(stuNumField);
		studentInfo.add(namePanel);
		studentInfo.add(stuNumPanel);
		
		JPanel buttonPanel = new JPanel();
		JButton startB = new JButton("시험 시작");
		startB.addActionListener(new ButtonListener(this));
		
		ipPanel.add(ipLabel);
		ipPanel.add(ipField);
		portPanel.add(portInfo);
		portPanel.add(portField);
		buttonPanel.add(startB);
		
		panel.add(Box.createRigidArea(new Dimension(0, 30)));
		panel.add(ipPanel);
		panel.add(portPanel);
		panel.add(studentInfo);
		panel.add(buttonPanel);
		add(panel);
		pack();
		setVisible(true);
	}
	
	TestClient tc;
	TestPage page;
	
	class ButtonListener implements ActionListener {
		
		JFrame frameCtrl;
		
		public ButtonListener(JFrame _frameCtrl) {
			// TODO Auto-generated constructor stub
			frameCtrl = _frameCtrl;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			page = new TestPage(nameField.getText(), stuNumField.getText());
			try {
				tc = new TestClient(page, ipField.getText(), Integer.parseInt(portField.getText()), nameField.getText(), stuNumField.getText());
				
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(frameCtrl, "IP주소와 포트를 제대로 입력했는지 확인해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
				return;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frameCtrl, "해당 주소와 포트로 열려있는 서버가 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				return;
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
				JOptionPane.showMessageDialog(frameCtrl, "서버에 접속할 수 없습니다.", "접속 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			panel.setVisible(false);
			setLayout(new BorderLayout());
			page.setClientInPage(tc);
			page.MakeTestPage();
			
			setSize(500, 700);
			JPanel buttonLabel = new JPanel();
			endButton = new JButton("제출");
			endButton.addActionListener(new MyButtonListener());
			
			page.setPreferredSize(new Dimension(page.getMywidth(), page.getMyHeigh()));
			add(page.getScrollPane(), BorderLayout.NORTH);
			buttonLabel.add(endButton);
			add(buttonLabel, BorderLayout.SOUTH);
			pack();
		}
	}
	
	class MyButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == endButton) {
				JLabel text = new JLabel("<html>제출하시겠습니까?<br/>제출하면 창은 자동종료됩니다.</html>");
				text.setPreferredSize(new Dimension(250, 50));
				if(HasFont("나눔바른고딕"))	text.setFont(new Font("나눔바른고딕", Font.PLAIN, 15));
				int option = JOptionPane.showConfirmDialog(panel, text, "확인", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					tc.setAnswer(page.getAnswers());
					tc.start();
				}
			}
		}
	}
	
	boolean HasFont(String FontName) {
		String[] fontList= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i = 0; i < fontList.length; i++) {
			if(fontList[i].equals(FontName)) {
				return true;
			}
		}
		return false;
	}
}

public class MainFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyFrameInClient();
	}
}
