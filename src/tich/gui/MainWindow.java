package tich.gui;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.io.File;
import java.io.FileReader;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tich.utils.FileController;
import tich.utils.OSGenericUtils;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private final int width = 400;
	private final int height = 400;
	private int fontSize = 11;
	private boolean isHtml = false;
	private boolean isList = false;
	private String title;
	private JEditorPane editorPane;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JList list;
	private Font font; 
	
	public MainWindow(){
		initialSetup();
	}
	
	public void initialSetup(){
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setAutoscrolls(true);
		textArea.addKeyListener(keyListener);
		setTextAreaFont();
		
		list = new JList();
		list.addKeyListener(keyListener);
		
		editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		editorPane.setEditable(false);
		editorPane.addKeyListener(keyListener);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
        
		additionalInfo();
		this.setTitle(title);
		this.getContentPane().add(scrollPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(keyListener);
		this.setSize(width, height);
		this.setVisible(true);
	}
	
	private void additionalInfo(){
		title = "Tich Editor v" + serialVersionUID;
		title += " Ctrl + H To show the help";
	}
	
	private String toUrl(String url){
		String http = "http://";
		if(url.substring(0, 7) != http) url = http + url;
		return url;
	}
	
	private void setTextAreaFont(){
		font = new Font(null, 0, fontSize);
		textArea.setFont(font);
	}
	
	private void showHelp(){
		String control = "Ctrl ";
		String message = "";
		message += control + "A: Select All\n";
		message += control + "B: Open a default brower\n";
		message += control + "C: Copy\n";
		message += control + "H: Show this message\n";
		message += control + "K: Convert text to list or list to text\n";
		message += control + "L: Convert text to HTML\n";
		message += control + "O: Open file\n";
		message += control + "S: Save file\n";
		message += control + "V: Paste\n";
		message += control + "W: Set or unset wrap\n";
		message += control + "+: Increases font size\n";
		message += control + "-: Decreases font size\n";
		message += "\nCreated by Julian Alexander Murillo";
		JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.NO_OPTION);
	}
	
	private void saveFile(){
		JFileChooser chooser = new JFileChooser();
		int selected = chooser.showSaveDialog(this);
		if(selected == 1) return;
		
		File file = chooser.getSelectedFile();
		String fileName = file.getAbsolutePath();
		
		FileController controller = new FileController();
		controller.setFileName(fileName);
		controller.saveFile(textArea.getText());
	}
	
	private void openFile(){
		JFileChooser chooser = new JFileChooser();
		int selected = chooser.showOpenDialog(this);
		if(selected == 1) return;
		
		File file = chooser.getSelectedFile();
		String fileName = file.getAbsolutePath();
		
		FileController controller = new FileController();
		controller.setFileName(fileName);
		textArea.setText(controller.loadFile());
	}
	
	public void convertToList(){
		String[] lines = textArea.getText().split("\n");
		if (isList == false){
			list.setListData(lines);
			scrollPane.setViewportView(list);
			isList = true;
			System.out.println("List");
		}
		else {
			scrollPane.setViewportView(textArea);
			isList = false;
		}
	}
	
	public void convertToHtml(){
		String text = textArea.getText();
		if (isHtml == false){
			editorPane.setText(text);
			scrollPane.setViewportView(editorPane);
			isHtml = true;
		}
		else {
			scrollPane.setViewportView(textArea);
			isHtml = false;
		}
	}
	
	KeyListener keyListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			boolean keyPressedB = arg0.getKeyCode() == KeyEvent.VK_B;
			boolean keyPressedH = arg0.getKeyCode() == KeyEvent.VK_H;
			boolean keyPressedK = arg0.getKeyCode() == KeyEvent.VK_K;
			boolean keyPressedL = arg0.getKeyCode() == KeyEvent.VK_L;
			boolean keyPressedO = arg0.getKeyCode() == KeyEvent.VK_O;
			boolean keyPressedS = arg0.getKeyCode() == KeyEvent.VK_S;
			boolean keyPressedW = arg0.getKeyCode() == KeyEvent.VK_W;
			boolean keyPressedPlus = arg0.getKeyCode() == KeyEvent.VK_PLUS;
			boolean keyPressedMinus = arg0.getKeyCode() == KeyEvent.VK_MINUS;
			boolean modificators = arg0.getModifiers() == InputEvent.CTRL_MASK;
			
			if(keyPressedB && modificators){
				String url = textArea.getText();
				url = toUrl(url);
				OSGenericUtils.openBrowser(url);
			}
			if(keyPressedH && modificators) showHelp();
			if(keyPressedK && modificators) convertToList();
			if(keyPressedL && modificators) convertToHtml();
			if(keyPressedO && modificators) openFile();
			if(keyPressedS && modificators) saveFile();
			if(keyPressedW && modificators) 
				textArea.setLineWrap(!textArea.getLineWrap());
			if(keyPressedPlus && modificators){
				setTextAreaFont();
				fontSize++;
			}
			if(keyPressedMinus && modificators){
				setTextAreaFont();
				fontSize--;
			}
		}
	};
}
