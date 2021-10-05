package com.czarnacki.bazasprzetu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class OknoLogin extends JFrame implements ActionListener, KeyListener, FocusListener{

	private JPanel contentPane;
	private JTextField inputLogin;
	private JLabel textHaslo;
	private JPasswordField inputHaslo;
	private JSeparator separator;
	private JButton buttonLogowanie;



	/**
	 * Create the frame.
	 */
	public OknoLogin() {
		setTitle("BazaSprz\u0119tu - Logowanie");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 257, 208);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, 
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(0, 2, 6, 2));
		setContentPane(contentPane);
		
		JLabel textLogin = new JLabel("Login", SwingConstants.RIGHT);
		textLogin.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(textLogin);
		
		inputLogin = new JTextField();
		inputLogin.addKeyListener(this);
		inputLogin.addFocusListener(this);
		inputLogin.setColumns(15);
		contentPane.add(inputLogin);
		
		
		textHaslo = new JLabel("Has\u0142o", SwingConstants.RIGHT);
		textHaslo.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(textHaslo);
		
		inputHaslo = new JPasswordField();
		inputHaslo.setColumns(15);
		inputHaslo.addKeyListener(this);
		inputHaslo.addFocusListener(this);
		contentPane.add(inputHaslo);
		
		separator = new JSeparator();
		contentPane.add(separator);
		
		buttonLogowanie = new JButton("Logowanie");
		buttonLogowanie.addActionListener(this);
		buttonLogowanie.addKeyListener(this);
		contentPane.add(buttonLogowanie);
		pack();
		setVisible(true);
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (e.getKeyCode()==KeyEvent.VK_ENTER){
			if(source == buttonLogowanie) {
				try {
					System.out.println("logowanie");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(source == inputLogin) {
				inputHaslo.requestFocus();
			}
			if(source == inputHaslo) {
				buttonLogowanie.requestFocus();
			}
			
			
        }
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		 
 		if(source == buttonLogowanie) {}
 			try {
 				System.out.println("logowanie");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}
	
	public void focusGained(FocusEvent e) {
		Object source = e.getSource();
		if(source == inputLogin) {
			inputLogin.setBackground(Color.CYAN);
		}
		if(source == inputHaslo) {
			inputHaslo.setBackground(Color.CYAN);
		}
    }

    public void focusLost(FocusEvent e) {
    	Object source = e.getSource();
		if(source == inputLogin) {
			inputLogin.setBackground(Color.WHITE);
		}
		if(source == inputHaslo) {
			inputHaslo.setBackground(Color.WHITE);
		}
    }

}
