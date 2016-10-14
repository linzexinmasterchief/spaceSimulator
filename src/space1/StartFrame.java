package space1;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class StartFrame extends JFrame {

	private static JPanel startPanel;
	private static JPanel gamePanel;
	private static JPanel menuPanel;
	private static ImageIcon image;

	private boolean inGame;
	private boolean clear;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	//initialize the frame
	public StartFrame() {
		//make frame
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.UTILITY);
		setTitle("2DSpace");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
		//make first Panel(Start Panel)------------------------------------------
		startPanel = new JPanel();
		startPanel.setBackground(Color.BLACK);
		startPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		startPanel.setLayout(null);
		setContentPane(startPanel);
		
		//Title(2D SPACE)
		JLabel lblSpaced = new JLabel("SPACE 2D");
		lblSpaced.setBackground(Color.BLACK);
		lblSpaced.setForeground(Color.WHITE);
		lblSpaced.setFont(new Font("Bauhaus 93", Font.PLAIN, 40));
		lblSpaced.setBounds(35, 72, 208, 67);
		startPanel.add(lblSpaced);
		
		//start game button
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		btnStartGame.setForeground(Color.WHITE);
		btnStartGame.setBackground(new Color(0, 0, 0));
		btnStartGame.setBounds(35, 149, 176, 61);
		startPanel.add(btnStartGame);
		btnStartGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startPanel.setVisible(false);
				gamePanel.setVisible(true);
				setContentPane(gamePanel);
				
			}
			
		});
		
		//help button
		JButton button = new JButton("?");
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		button.setFont(new Font("Adobe Hebrew", Font.BOLD, 40));
		button.setBounds(35, 239, 60, 49);
		startPanel.add(button);
		
		//background
		JLabel label = new JLabel(new ImageIcon("back.jpg"));
		label.setBounds(0, 0, 984, 561);
		startPanel.add(label);
		//first panel end--------------------------------------------------------
		
		//make second Panel(Game Panel)------------------------------------------
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.BLACK);
		gamePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		gamePanel.setLayout(null);
		
		//menu button
		JButton btnMenu = new JButton("menu");
		btnMenu.setBounds(10, 10, 93, 23);
		btnMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		gamePanel.add(btnMenu);
		//first panel end--------------------------------------------------------
		
		//menu frame start-------------------------------------------------------
		menuPanel = new JPanel();
		menuPanel.setBackground(Color.BLACK);
		menuPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		menuPanel.setLayout(null);
	}
}
