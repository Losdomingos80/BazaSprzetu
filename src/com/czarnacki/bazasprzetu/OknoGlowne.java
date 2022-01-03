package com.czarnacki.bazasprzetu;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
//import javax.swing.Box;
//import javax.swing.JToolBar;
//import javax.swing.border.Border;
//import javax.swing.border.CompoundBorder;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class OknoGlowne extends JFrame implements ActionListener, KeyListener, FocusListener{

	static MySql baza;
	static String user;
	private JFrame frmBazaSprztu;
	private JPanel statusBar;
	private JLabel status;
	private final JPanel panel = new JPanel();
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem_2;

	
	/**
	 * Create the application.
	 */
	public OknoGlowne(MySql serwer, String login) {
		baza = serwer;
		user = login;
		System.out.println("uruchomiono okno glowne");
		statusBar = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEADING));
		
	    status = new JLabel();
	    statusBar.add(status);
	 	initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		frmBazaSprztu = new JFrame();
		frmBazaSprztu.setTitle("Baza Sprzętu");
		frmBazaSprztu.setBounds(100, 100, 257, 208);
		frmBazaSprztu.setSize(800, 600);
		frmBazaSprztu.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - frmBazaSprztu.getSize().width) / 2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - frmBazaSprztu.getSize().height) / 2);
		frmBazaSprztu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JMenuBar menuBar = new JMenuBar();
	
		frmBazaSprztu.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Plik");
		
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Logowanie");
		mnNewMenu.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(this);
		
		JMenu mnNewMenu_1 = new JMenu("Słowniki");
		menuBar.add(mnNewMenu_1);
		
		mntmNewMenuItem_1 = new JMenuItem("Działy");
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem_2 = new JMenuItem("Kierownicy");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		mntmNewMenuItem_2.addActionListener(this);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Zestawy");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Sprzęt");
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Grupy");
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		frmBazaSprztu.getContentPane().add(panel, BorderLayout.NORTH);
		frmBazaSprztu.getContentPane().add(statusBar, BorderLayout.SOUTH);
		status.setText(" Zalogowany : " + user);
		frmBazaSprztu.setPreferredSize(new Dimension(800, 600));
		frmBazaSprztu.pack();
		frmBazaSprztu.setVisible(true);
		
		
	}
	
	public void logowanieMenu() {
		new OknoLogin(baza);
		frmBazaSprztu.dispose();
	}
	
	public void kierownicyMenu() {
		new OknoKierownicy(baza);
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		 
 		if(source == mntmNewMenuItem) {
 			try {
 				logowanieMenu();
 				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 		}
 			
 			
 		if(source == mntmNewMenuItem_2) {
 			try {
 				kierownicyMenu();
 				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 		}
 			
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
