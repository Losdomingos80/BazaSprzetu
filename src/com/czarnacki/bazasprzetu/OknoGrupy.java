package com.czarnacki.bazasprzetu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


@SuppressWarnings("serial")
public class OknoGrupy extends JFrame implements ActionListener, KeyListener, FocusListener {

	public static JFrame oknGrup;
	private JPanel contentPane;
	
	private JTextField textField;
	private JTable table;
	private JButton dodaj;
	private JButton szukaj;
	ResultSet resultSet = null;
	private JCheckBox znacznik;
	
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
			      tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width + 10, tableColumn.getPreferredWidth()));
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
                  int s = Integer.valueOf(table.getModel().getValueAt(row, 2).toString());
                  dialog(2, id, n, s);
               }
            }
         });


		oknGrup.getContentPane().add( new JScrollPane(table));

		try {
			odczytaj();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		oknGrup.setPreferredSize(new Dimension(600, 800));
		oknGrup.pack();
		oknGrup.setVisible(true);
		
	}
	
	public void odczytaj() throws SQLException {
		
		String[] naglowek = { "id", "nazwa", "status" };
		try {
			resultSet = baza.Select("SELECT * FROM grupy");
			String[][] wiersz = baza.zapytanieGrupy(resultSet);
			((DefaultTableModel) table.getModel()).setDataVector(wiersz, naglowek);
			table.setAutoCreateRowSorter(true); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dialog(int rodzaj, int id, String nazw, int stat) {
		JTextField nazwa = new JTextField();
	    
	    nazwa.setColumns(20);
	    

	      JPanel myPanel = new JPanel();
	      //myPanel.setPreferredSize(new Dimension(390, 90));
	      myPanel.setLayout(new GridLayout(3, 2));
	      JLabel nazwaText = new JLabel("Nazwa", SwingConstants.RIGHT);
	      nazwaText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(nazwaText);
	      myPanel.add(nazwa);
	      nazwa.setText(nazw);
	      
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
	               "Dane Grupy", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	         System.out.println("nazwa: " + nazwa.getText());
	        
	         System.out.println("status: "  + status.getItemAt(status.getSelectedIndex()));
	         
			
	         if(status.getItemAt(status.getSelectedIndex()).equals("Aktywny")) {
	        	 stat = 1;
	         }
	         if(status.getItemAt(status.getSelectedIndex()).equals("Nie Aktywny")) {
	        	 stat = 0;
	         }
	         System.out.println(stat);
	         if(rodzaj == 1) {
	        	 zapiszNowy(nazwa.getText(), stat);
	         }
	         if(rodzaj == 2) {
	        	 popraw(id, nazwa.getText(), stat);
	         }
	      }
	}
	
	public void zapiszNowy(String nazwa, int status) {
		
		
		try {
			baza.insertGrupy(nazwa, status);
			odczytaj();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public void popraw(int id, String nazwa, int status) {
		try {
			baza.poprawGrupy(id, nazwa, status);
			odczytaj();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void wyszukaj() { 
		String ss;
		String[] naglowek = { "id", "nazwa", "status" };
		if(znacznik.isSelected()) {
			ss = "1";
		}
		else {
			ss = "0";
		}
		
		try {
			resultSet = baza.Select("SELECT * FROM grupy where nazwa like '%" + textField.getText() 
			+ "%' and status = " + ss);
			String[][] wiersz = baza.zapytanieGrupy(resultSet);
			((DefaultTableModel) table.getModel()).setDataVector(wiersz, naglowek);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == textField) {
			textField.setBackground(Color.CYAN);
		}
		
	}


	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
	                  int s = Integer.valueOf(table.getModel().getValueAt(row, 2).toString());
	                  dialog(2, id, n, s);
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
		// TODO Auto-generated method stub
		Object source = e.getSource();
		 
 		if(source == dodaj) {
 			try {
 				dialog(1, 0, "", 0);
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

}


