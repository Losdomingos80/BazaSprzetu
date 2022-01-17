package com.czarnacki.bazasprzetu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class OknoSprzet extends JFrame {

	public static JFrame oknSprz;
	private JPanel contentPane;
	
	static MySql baza;


	/**
	 * Create the frame.
	 */
	public OknoSprzet(MySql serwer) {
		baza = serwer;
		oknSprz = new JFrame();
		oknSprz.setTitle("Słownik - Sprzęt");
		oknSprz.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oknSprz.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		oknSprz.setContentPane(contentPane);
		
		oknSprz.setPreferredSize(new Dimension(600, 800));
		oknSprz.pack();
		oknSprz.setVisible(true);
	}

}
