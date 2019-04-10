package guiClient;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class TestPage extends JLabel{
	int testCount;
	int heigh = 0;
	int width = 0;
	int fontCheck;
	String testName;
	String[] stuInfo;
	String[] tests;
	String[][] answers;
	String[] myAnswer;
	BufferedImage[] img;
	JScrollPane scroll;
	TestClient tc;
	
	public TestPage(String stuName, String stuNum) {
		// TODO Auto-generated constructor stub
		stuInfo = new String[2];
		stuInfo[0] = stuName + "";
		stuInfo[1] = stuNum + "";
		
		fontCheck = -1;
	}
	
	public void MakeTestPage() {
		// TODO Auto-generated constructor stub
		scroll = new JScrollPane(this);
		scroll.setPreferredSize(new Dimension(500, 600));
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		JPanel[] panels = new JPanel[testCount];
		JLabel[] testLabels = new JLabel[testCount];
		JLabel[] stuLabel = new JLabel[2];
		JRadioButton[][] answerButton = new JRadioButton[testCount][5];
		ButtonGroup[] answerGroup = new ButtonGroup[testCount];
		
		JLabel testNameLabel = new JLabel(tc.getTestName());
		stuLabel[0] = new JLabel("ÀÌ¸§ : " + stuInfo[0]);
		stuLabel[1] = new JLabel("ÇÐ¹ø : " + stuInfo[1]);
		
		heigh += testNameLabel.getMaximumSize().height;
		heigh += stuLabel[0].getMaximumSize().height;
		heigh += stuLabel[1].getMaximumSize().height;
		heigh += 30;
		add(testNameLabel);
		add(stuLabel[0]);
		add(stuLabel[1]);
		add(Box.createRigidArea(new Dimension(0, 30)));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		myAnswer = new String[testCount];
		
		for(int i = 0; i < testCount; i++) {
			
			myAnswer[i] = 0 + "";
			
			panels[i] = new JPanel();
			panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
			panels[i].setEnabled(true);
			testLabels[i] = new JLabel(tests[i]);
			if (HasFont("³ª´®¹Ù¸¥°íµñ")) testLabels[i].setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 18));
			panels[i].add(testLabels[i]);
			answerGroup[i] = new ButtonGroup();
			
			if(img[i] != null) panels[i].add(new JLabel(new ImageIcon(img[i])));
			
			panels[i].add(Box.createRigidArea(new Dimension(0, 10)));
			
			for(int j = 0; j < answers[i].length; j++) {
				if (answers[i][j].equals("NONEANSWER")) continue;
				answerButton[i][j] = new JRadioButton(answers[i][j]);
				if (HasFont("³ª´®¹Ù¸¥°íµñ")) answerButton[i][j].setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 15));
				answerButton[i][j].addActionListener(new RadioListener(i, j));
				answerGroup[i].add(answerButton[i][j]);
				panels[i].add(answerButton[i][j]);
				
			}
			
			add(panels[i]);
			add(Box.createRigidArea(new Dimension(0, 20)));
			
			if(width < panels[i].getMaximumSize().width) width = panels[i].getMaximumSize().width;
			heigh += panels[i].getMaximumSize().height;
			heigh += 20;
		}
		
		if (HasFont("³ª´®¹Ù¸¥°íµñ")) {
			testNameLabel.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 20));
			stuLabel[0].setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 18));
			stuLabel[1].setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 18));
		}
		
		setVisible(true);
	}
	
	class RadioListener implements ActionListener{
		int num, ans;
		
		public RadioListener(int _num, int _ans) {
			num = _num;
			ans = _ans + 1;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			myAnswer[num] = ans + "";
			System.out.println(num + "¹ø ´ä : " + ans);
		}
		
	}

	class MyButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			tc.setAnswer(myAnswer);
			tc.start();
		}
	}
	
	public void setTestName(String _testName) {
		testName = _testName + "";
	}
	
	public String[] getAnswers() {
		return myAnswer;
	}
	
	public void setAnswer(int num, int ans) {
		myAnswer[2] = ans + "";
		return;
	}
	
	public JScrollPane getScrollPane() {
		return scroll;
	}
	
	public int getMywidth() {
		return width;
	}
	
	public int getMyHeigh() {
		return heigh;
	}
	
	public void setClientInPage(TestClient _tc) {
		tc = _tc;
	}
	
	public void setTestCount(int count) {
		testCount = count;
	}
	public void setTestData(String[] _tests, String[][] _answers, BufferedImage[] _img) {
		tests = _tests;
		for(int i = 0; i < tests.length; i++) {
			tests[i] = (i+1) + ". " + tests[i];
			if(tests[i].contains("-*nr-")) {
				tests[i] = tests[i].replace("-*nr-", "<br/>");
				tests[i] = "<html>" + tests[i] + "</html>";
			}
		}
		answers = _answers;
		img = _img;
	}
	
	boolean HasFont(String FontName) {
		if(fontCheck == 1) return true;
		else if (fontCheck == 0) return false;
		String[] fontList= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i = 0; i < fontList.length; i++) {
			if(fontList[i].equals(FontName)) {
				fontCheck = 1;
				return true;
			}
		}
		fontCheck = 0;
		return false;
	}
}
