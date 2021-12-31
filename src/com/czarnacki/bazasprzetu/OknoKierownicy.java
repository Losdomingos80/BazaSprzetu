package com.czarnacki.bazasprzetu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
//import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.*;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
//import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Font;



@SuppressWarnings("serial")
public class OknoKierownicy extends JFrame implements ActionListener, KeyListener, FocusListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JButton dodaj;
	private JButton szukaj;

	
	static MySql baza;
	ResultSet resultSet = null;
	private JCheckBox znacznik;

	

	/**
	 * Create the frame.
	 */
	public OknoKierownicy(MySql serwer) {
		baza = serwer;
		setTitle("Słownik - Kierownicy");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		dodaj = new JButton("Dodaj");
		dodaj.addActionListener(this);
		toolBar.add(dodaj);
		
		textField = new JTextField();
		textField.addKeyListener(this);
		textField.addFocusListener(this);
		toolBar.add(textField);
		textField.setColumns(10);
		
		szukaj = new JButton("Szukaj");
		szukaj.addActionListener(this);
		
		znacznik = new JCheckBox("Aktywny");
		znacznik.setSelected(true);
		znacznik.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		toolBar.add(znacznik);
		toolBar.add(szukaj);
		
		table = new JTable(){
			  @Override
			    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			      Component component = super.prepareRenderer(renderer, row, column);
			      int rendererWidth = component.getPreferredSize().width;
			      TableColumn tableColumn = getColumnModel().getColumn(column);
			      tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
			      Component returnComp = super.prepareRenderer(renderer, row, column);
			      Color alternateColor = new Color(252,242,206);
			      Color whiteColor = Color.WHITE;
			      if (!returnComp.getBackground().equals(getSelectionBackground())){
			        Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
			        returnComp .setBackground(bg);
			        bg = null;
			      }
			      return component;
			    }
			  	public boolean editCellAt(int row, int column, java.util.EventObject e) {
			  		return false;
			  	}
			  };
		table.addKeyListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFocusable(true);
		
		table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {

               if (me.getClickCount() == 2) {     
                  JTable target = (JTable)me.getSource();
                  int row = target.getSelectedRow(); 
              
                  int id = Integer.valueOf(table.getModel().getValueAt(row, 0).toString());
                  String n = (String) table.getModel().getValueAt(row, 1);
                  String i = (String) table.getModel().getValueAt(row, 2);
                  int s = Integer.valueOf(table.getModel().getValueAt(row, 3).toString());
                  dialog(2, id, n, i, s);
               }
            }
         });


		getContentPane().add(new JScrollPane(table));

		try {
			odczytaj();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		setPreferredSize(new Dimension(600, 800));
		pack();
		setVisible(true);
	}



	@Override
	public void focusGained(FocusEvent e) {
		Object source = e.getSource();
		if(source == textField) {
			textField.setBackground(Color.CYAN);
		}
		
	}



	@Override
	public void focusLost(FocusEvent e) {
		Object source = e.getSource();
		if(source == textField) {
			textField.setBackground(Color.WHITE);
		}
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		Object source = e.getSource();
		if (e.getKeyCode()==KeyEvent.VK_ENTER){
			if(source == textField) {
				try {
					System.out.println("szukanie");
					wyszukaj();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
        }
		if (e.getKeyCode()==KeyEvent.VK_ENTER){
			if(source == table) {
				try {
					 JTable target = (JTable)e.getSource();
	                  int row = target.getSelectedRow(); 
	              
	                  int id = Integer.valueOf(table.getModel().getValueAt(row, 0).toString());
	                  String n = (String) table.getModel().getValueAt(row, 1);
	                  String i = (String) table.getModel().getValueAt(row, 2);
	                  int s = Integer.valueOf(table.getModel().getValueAt(row, 3).toString());
	                  dialog(2, id, n, i, s);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
        }
		
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		 
 		if(source == dodaj) {
 			try {
 				dialog(1, 0, "", "", 0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 		}
 		
 		if(source == szukaj) {
 			try {
 				wyszukaj();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 		}
 			
		
	}
	
	public void odczytaj() throws SQLException {
		
		String[] naglowek = { "id", "nazwisko", "imie", "status" };
		try {
			resultSet = baza.Select("SELECT * FROM kierownicy");
			String[][] wiersz = baza.zapytanieKierownicy(resultSet);
			((DefaultTableModel) table.getModel()).setDataVector(wiersz, naglowek);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dialog(int rodzaj, int id, String nazw, String im, int stat) {
		JTextField nazwisko = new JTextField();
	    JTextField imie = new JTextField();
	    nazwisko.setColumns(20);
	    imie.setColumns(20);

	      JPanel myPanel = new JPanel();
	      //myPanel.setPreferredSize(new Dimension(390, 90));
	      myPanel.setLayout(new GridLayout(3, 2));
	      JLabel nazwiskoText = new JLabel("Nazwisko", SwingConstants.RIGHT);
	      nazwiskoText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(nazwiskoText);
	      myPanel.add(nazwisko);
	      nazwisko.setText(nazw);
	      JLabel imieText = new JLabel("Imie", SwingConstants.RIGHT);
	      imieText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(imieText);
	      myPanel.add(imie);
	      imie.setText(im);
	      JLabel statusText = new JLabel("Status", SwingConstants.RIGHT);
	      statusText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(statusText);
	      String statusy[]={"Aktywny","Nie Aktywny"};        
	      JComboBox status = new JComboBox(statusy);  
	      myPanel.add(status);
	      if(stat == 0) {
	    	  status.setSelectedIndex(1);
	      }
	      if(stat == 1) {
	    	  status.setSelectedIndex(0);
	      }

	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Dane kierownika", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	         System.out.println("nazwisko: " + nazwisko.getText());
	         System.out.println("imie: " + imie.getText());
	         System.out.println("status: "  + status.getItemAt(status.getSelectedIndex()));
	         
			
	         if(status.getItemAt(status.getSelectedIndex()).equals("Aktywny")) {
	        	 stat = 1;
	         }
	         if(status.getItemAt(status.getSelectedIndex()).equals("Nie Aktywny")) {
	        	 stat = 0;
	         }
	         System.out.println(stat);
	         if(rodzaj == 1) {
	        	 zapiszNowy(nazwisko.getText(), imie.getText(), stat);
	         }
	         if(rodzaj == 2) {
	        	 popraw(id, nazwisko.getText(), imie.getText(), stat);
	         }
	      }
	}
	
	public void zapiszNowy(String nazwisko, String imie, int status) {
		
		
		try {
			baza.insertKierownicy(nazwisko, imie, status);
			odczytaj();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public void popraw(int id, String nazwisko, String imie, int status) {
		try {
			baza.poprawKierownicy(id, nazwisko, imie, status);
			odczytaj();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	public void wyszukaj() { 
		String ss;
		String[] naglowek = { "id", "nazwisko", "imie", "status" };
		if(znacznik.isSelected()) {
			ss = "1";
		}
		else {
			ss = "0";
		}
		
		try {
			resultSet = baza.Select("SELECT * FROM kierownicy where (nazwisko like '%" + textField.getText() 
			+ "%' or imie like '%" + textField.getText() + "%') and status = " + ss);
			String[][] wiersz = baza.zapytanieKierownicy(resultSet);
			((DefaultTableModel) table.getModel()).setDataVector(wiersz, naglowek);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		  

}
