package com.czarnacki.bazasprzetu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
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
public class OknoDzialy extends JFrame implements ActionListener, KeyListener, FocusListener {

	public static JFrame oknDzia;
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
	 * przy dodawaniu do słownika od razu wybieramy kierownika i uzupełnia się data od
	 * po modyfikacji danych kierownika zmienia się w edytowanym wpisie status i data koncowa
	 * oraz jest dodawany nowy wpis z nowymi danymi kierownika oraz data startowa
	 * 
	 * ale to trzeba założyć osobną tablę na przypisanie kierownika do działy a w tabeli działu nie 
	 * przechowywać przypisania i daty od do
	 * 
	 * 
	 * 
	 */
	public OknoDzialy(MySql serwer) {
		
		//System.out.println(System.getProperty("dominik"));
		
		baza = serwer;
		oknDzia = new JFrame();
		oknDzia.setTitle("Słownik - Działy");
		oknDzia.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oknDzia.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		oknDzia.setContentPane(contentPane);
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
                  int ik = Integer.valueOf(table.getModel().getValueAt(row, 2).toString());
                  String dod = (String) table.getModel().getValueAt(row, 4);
                  String ddo = (String) table.getModel().getValueAt(row, 5);
                  int s = Integer.valueOf(table.getModel().getValueAt(row, 6).toString());
                  dialog(2, id, n, ik, dod, ddo, s);
               }
            }
         });


		oknDzia.getContentPane().add( new JScrollPane(table));

		try {
			odczytaj();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		oknDzia.setPreferredSize(new Dimension(600, 800));
		oknDzia.pack();
		oknDzia.setVisible(true);
		
	}
	
	public void odczytaj() throws SQLException {
		
		String[] naglowek = {"id", "nazwa", "idKierownika", "kierownik", "dataOd", "dataDo", "status"};
		try {
			resultSet = baza.Select("SELECT *, (select CONCAT_WS(' ', nazwisko, imie) from kierownicy where kierownicy.id = dzialy.id) as kierownik FROM dzialy");
			String[][] wiersz = baza.zapytanieDzialy(resultSet);
			((DefaultTableModel) table.getModel()).setDataVector(wiersz, naglowek);
			table.setAutoCreateRowSorter(true); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "null" })
	public void dialog(int rodzaj, int id, String nazw, int idk, String dod, String ddo, int stat) {
		
  		  JTextField nazwa = new JTextField();
		  nazwa.setColumns(20);
		  JPanel myPanel = new JPanel();
	      //myPanel.setPreferredSize(new Dimension(390, 90));
	      myPanel.setLayout(new GridLayout(5, 2));
	      JLabel nazwaText = new JLabel("Nazwa", SwingConstants.RIGHT);
	      nazwaText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(nazwaText);
	      myPanel.add(nazwa);
	      nazwa.setText(nazw);
	      
	      JLabel kierText = new JLabel("Kierownik", SwingConstants.RIGHT);
	      kierText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(kierText);
	      //lista aktywnych kierowników
	      String[][] kierownicy = null;
	      try {
			resultSet = baza.Select("SELECT id, CONCAT_WS(' ', nazwisko, imie) as kierownik FROM kierownicy where status = 1");
			kierownicy = baza.zapytanieListaKierownicy(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      int wybrkier = 0;
	      String[] l = new String[kierownicy.length];
	      for(int i = 0; i < kierownicy.length; i++) {
	          l[i] = kierownicy[i][0] + " " + kierownicy[i][1];
	          if(idk == Integer.valueOf(kierownicy[i][0])) {
	        	  wybrkier = i;
	          }
	         
	      }
	      
	         
	      @SuppressWarnings("unchecked")
		  JComboBox kier = new JComboBox(l);  
	      myPanel.add(kier);
	      if(rodzaj == 2) {
	    	  kier.setSelectedIndex(wybrkier);
	      }
	     
	      
	      
	      JTextField dataod = new JTextField();
		  dataod.setColumns(20);
		  
	      JLabel dataodText = new JLabel("Data od", SwingConstants.RIGHT);
	      dataodText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(dataodText);
	      myPanel.add(dataod);
	      dataod.setText(dod);
	      
	      JTextField datado = new JTextField();
		  datado.setColumns(20);
		  
	      JLabel datadoText = new JLabel("Data do", SwingConstants.RIGHT);
	      datadoText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(datadoText);
	      myPanel.add(datado);
	      datado.setText(ddo);
	      
	      JLabel statusText = new JLabel("Status", SwingConstants.RIGHT);
	      statusText.setVerticalAlignment(SwingConstants.TOP);
	      myPanel.add(statusText);
	      String statusy[]={"Aktywny","Nie Aktywny"};        
	      @SuppressWarnings("unchecked")
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
	         
	         int idkk = Integer.valueOf(kierownicy[kier.getSelectedIndex()][0]);
	    	  
			
	         if(status.getItemAt(status.getSelectedIndex()).equals("Aktywny")) {
	        	 stat = 1;
	         }
	         if(status.getItemAt(status.getSelectedIndex()).equals("Nie Aktywny")) {
	        	 stat = 0;
	         }
	         
	         if(rodzaj == 1) {
	        	// zapiszNowy(nazwa.getText(), stat);
	         }
	         if(rodzaj == 2) {
	        	 
	        	popraw(id, nazwa.getText(), idkk, dataod.getText(), datado.getText(), stat);
	         }
	      }
	}
	
	public void popraw(int id, String nazwa, int idk, String dod, String ddo, int status) {
		try {
			baza.poprawDzialy(id, nazwa, idk, dod, ddo, status);
			odczytaj();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void wyszukaj() { 
		String ss;
		String[] naglowek = {"id", "nazwa", "idKierownika", "kierownik", "dataOd", "dataDo", "status"};
		if(znacznik.isSelected()) {
			ss = "1";
		}
		else {
			ss = "0";
		}
		
		try {
			resultSet = baza.Select("SELECT *, (select CONCAT_WS(' ', nazwisko, imie) from kierownicy where kierownicy.id = dzialy.id) as kierownik FROM dzialy where"
					+ "(nazwa like '%" + textField.getText() + "%' or (select CONCAT_WS(' ', nazwisko, imie) from kierownicy where kierownicy.id = dzialy.id) like '%"
					+ textField.getText() + "%') and status = " + ss);
			
			
			String[][] wiersz = baza.zapytanieDzialy(resultSet);
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
	                  int ik = Integer.valueOf(table.getModel().getValueAt(row, 2).toString());
	                  String dod = (String) table.getModel().getValueAt(row, 4);
	                  String ddo = (String) table.getModel().getValueAt(row, 5);
	                  int s = Integer.valueOf(table.getModel().getValueAt(row, 6).toString());
	                  dialog(2, id, n, ik, dod, ddo, s);
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
 				
 				dialog(1, 0, "", 0, "", "", 1);
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
