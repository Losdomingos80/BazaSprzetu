package com.czarnacki.bazasprzetu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class OknoZestaw extends JFrame {

	public static JFrame oknZest;
	private JPanel contentPane;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel_1;
	private JTable table;

	

	/**
	 * Create the frame.
	 */
	public OknoZestaw() {
		oknZest = new JFrame();
		System.out.println("uruchomiono zestaw");
		oknZest.setTitle("Zestaw");
		oknZest.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oknZest.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		oknZest.setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Zestaw :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(30);
		
		lblNewLabel_1 = new JLabel("Sprzęt");
		contentPane.add(lblNewLabel_1, BorderLayout.WEST);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		table = new JTable();
		scrollPane.add(table);
		oknZest.pack();
		oknZest.setVisible(true);
	}

}
