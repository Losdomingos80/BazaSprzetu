package com.czarnacki.bazasprzetu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class OknoLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel textHaslo;
	private JPasswordField passwordField;
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
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 6, 2));
		
		JLabel textLogin = new JLabel("Login", SwingConstants.RIGHT);
		textLogin.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(textLogin);
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(15);
		
		textHaslo = new JLabel("Has\u0142o", SwingConstants.RIGHT);
		textHaslo.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(textHaslo);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(15);
		contentPane.add(passwordField);
		
		separator = new JSeparator();
		contentPane.add(separator);
		
		buttonLogowanie = new JButton("Logowanie");
		contentPane.add(buttonLogowanie);
		pack();
		setVisible(true);
	}

}
