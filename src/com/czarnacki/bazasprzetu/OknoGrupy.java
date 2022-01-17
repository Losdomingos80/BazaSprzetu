package com.czarnacki.bazasprzetu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class OknoGrupy extends JFrame {

	public static JFrame oknGrup;
	private JPanel contentPane;
	
	static MySql baza;


	/**
	 * Create the frame.
	 */
	public OknoGrupy(MySql serwer) {
		baza = serwer;
		oknGrup = new JFrame();
		oknGrup.setTitle("Słownik - Grupy");
		oknGrup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oknGrup.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		oknGrup.setContentPane(contentPane);
		
		oknGrup.setPreferredSize(new Dimension(600, 800));
		oknGrup.pack();
		oknGrup.setVisible(true);
		
	}

}
