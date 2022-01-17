package com.czarnacki.bazasprzetu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class OknoDzialy extends JFrame {

	public static JFrame oknDzia;
	private JPanel contentPane;
	
	static MySql baza;

	/**
	 * Create the frame.
	 */
	public OknoDzialy(MySql serwer) {
		baza = serwer;
		oknDzia = new JFrame();
		oknDzia.setTitle("Słownik - Działy");
		oknDzia.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oknDzia.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		oknDzia.setContentPane(contentPane);
		
		oknDzia.setPreferredSize(new Dimension(600, 800));
		oknDzia.pack();
		oknDzia.setVisible(true);
		
	}

}
