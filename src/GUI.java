import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author chirag Barot
 * 
 */
public class GUI {
	private JButton runButton;
	private JButton loadButton;
	private JComboBox cmdList;
	private JComboBox osList;
	private JTextArea textArea;
	private JButton clrButton;
	private JScrollPane scroll;
	private JLabel l1;
	private JLabel l2;
	private JTable table;
	private BufferedReader rf;
	private DoProcessBuilder pb = new DoProcessBuilder();
	private DefaultTableModel tModel;
	private String[] columnTitles = { "", "", "", "", "", "", "", "", "", "",
			"", "" };
	private JTabbedPane tabPane;

	// Array list to store strings of commands
	private ArrayList<String> macCommands = new ArrayList<String>();
	private ArrayList<String> winCommands = new ArrayList<String>();
	private ArrayList<String> os = new ArrayList<String>();

	// Default Constructor
	public GUI() {
	}

	public void storeCmd_Mac() {
		macCommands.add("mac_netstat");
		macCommands.add("mac_pstree");
		macCommands.add("mac_psxview");
		macCommands.add("mac_dead_procs");
		macCommands.add("mac_notifiers");
		macCommands.add("mac_check_sysctl");
		macCommands.add("mac_tasks");
		macCommands.add("mac_ifconfig");
	}

	public void storeCmd_Win() {
		winCommands.add("netstat");
		winCommands.add("pstree");
	}

	public void storeOs() {
		os.add("Mac");
		os.add("Windows");
		os.add("Linux");
	}

	public void makeFrame() {
		// Create JFrame with content pane and set layout
		JFrame frame = new JFrame();

		Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());

		// create table
		createTable();

		// Create Panels
		JPanel panelBL = new JPanel(new BorderLayout());
		JPanel panelTX = new JPanel(new GridLayout());
		JPanel panelFL1 = new JPanel(new GridLayout(2,2));
		JPanel panelFL2 = new JPanel(new FlowLayout());
		panelTX.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		panelTX.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));
		panelFL1.setBorder(new TitledBorder(new EtchedBorder(), "North"));

		// create tab pane
		tabPane = new JTabbedPane();

		// panels for tabs
		final JPanel p1 = new JPanel();
		tabPane.addTab("tab 1", p1);
		tabPane.setMnemonicAt(0, KeyEvent.VK_1);

		final JPanel p2 = new JPanel();
		tabPane.addTab("tab 2", p2);
		tabPane.setMnemonicAt(1, KeyEvent.VK_2);

		final JPanel p3 = new JPanel();
		tabPane.addTab("tab 3", p3);

		final JPanel p4 = new JPanel();
		tabPane.addTab("tab 4", p4);
		tabPane.setPreferredSize(new Dimension(300, 300));

		// Add commands to combobox list
		cmdList = new JComboBox();
		java.util.Iterator<String> iterCmd = macCommands.iterator();
		while (iterCmd.hasNext()) {
			cmdList.addItem(iterCmd.next());
		}
		// Add os to combobox list
		osList = new JComboBox();
		java.util.Iterator<String> iterOs = os.iterator();
		while (iterOs.hasNext()) {
			osList.addItem(iterOs.next());
		}

		// Define Components and initialize them
		l1 = new JLabel("Select OS: ");
		runButton = new JButton("Run");
		clrButton = new JButton("Clear");
		loadButton = new JButton("Load");
		textArea = new JTextArea(10, 10);
		textArea.setText("Click 'Run'");
		textArea.add(table);

		scroll = new JScrollPane(table);
		loadButton.setVisible(false);
		clrButton.setVisible(false);
		runButton.setVisible(true);

		// panelFL2.add(panelBL);
		cp.add(panelBL, BorderLayout.SOUTH);
		cp.add(panelFL1, BorderLayout.NORTH);
		cp.add(panelFL2, BorderLayout.WEST);
		cp.add(panelTX);

		// Add components here
		panelTX.add(scroll);
		//panelFL1.add(l1,FlowLayout.LEFT);
		panelFL1.add(runButton,FlowLayout.LEFT);
		panelFL1.add(cmdList,FlowLayout.LEFT);
		panelFL1.add(osList,FlowLayout.LEFT);
		panelFL1.add(clrButton,FlowLayout.LEADING);
		panelFL1.add(loadButton,FlowLayout.LEADING);
		
		panelBL.add(tabPane, BorderLayout.CENTER);

		// Set frame elements
		frame.setTitle("VolaTile");
		frame.setSize(700, 900);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Populate available commands for selected OS
		osList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (osList.getSelectedIndex() == 0) {
					// cmdList.removeAllItems();
					java.util.Iterator<String> iterCmd = macCommands.iterator();
					while (iterCmd.hasNext()) {
						cmdList.addItem(iterCmd.next());
					}
				} else {
					cmdList.removeAllItems();
					java.util.Iterator<String> iterOs = winCommands.iterator();
					while (iterOs.hasNext()) {
						cmdList.addItem(iterOs.next());
					}
				}
			}
		});

		// Button event handler
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					pb.List();
					pb.editList(cmdList.getSelectedItem().toString());

					// Call ProcessBuilder Method
					pb.processBuilder();

					cmdList.setVisible(false);
					osList.setVisible(false);
					l1.setVisible(false);
					loadButton.setVisible(true);
					runButton.setVisible(false);
					clrButton.setVisible(false);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Clear button events
		clrButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tModel.setRowCount(0);
				loadButton.setVisible(false);
				runButton.setVisible(true);
				clrButton.setVisible(false);
				cmdList.setVisible(true);
				osList.setVisible(true);
				tModel.setColumnIdentifiers(columnTitles);

				try {
					pb.manageFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Load Button events
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clrButton.setVisible(true);
				l1.setVisible(false);
				tModel.setRowCount(0);
				readFile();
				// Using switch
				int choice = cmdList.getSelectedIndex();
				switch (choice) {
				case 0: // Netstat
				{
					readNetstat(rf);
					tabPane.add("TCP", p2);
					tabPane.add("UDP", p3);
					tabPane.remove(p4);
					tabPane.remove(p1);
					break;
				}
				case 1:// pstree
				{
					readPstree(rf);
					tabPane.add("Pid", p1);
					tabPane.add("Uid", p2);
					tabPane.remove(p3);
					tabPane.remove(p4);
					break;

				}
				case 2: //psxview
				{
					readPsxview(rf);
					tabPane.add("TCP", p2);
					tabPane.add("UDP", p3);
					tabPane.remove(p4);
					tabPane.remove(p1);
					break;
				}
				case 3: {
					readNetstat(rf);
					tabPane.add("TCP", p2);
					tabPane.add("UDP", p3);
					tabPane.remove(p4);
					tabPane.remove(p1);
					break;
				}
				case 4: {
					readNotifiers(rf);
					tabPane.add("TCP", p2);
					tabPane.add("UDP", p3);
					tabPane.remove(p4);
					tabPane.remove(p1);
					break;
				}
				default:
					readRows(rf);
				}

			}
		});
	}

	// Create Table
	public void createTable() {
		// Create Table and table model
		table = new JTable();
		table.setBackground(Color.white);
		tModel = new DefaultTableModel(0, 0);

		// Table row settings
		table.setRowHeight(22);
		table.setModel(tModel);
		table.setAutoCreateRowSorter(true);

		tModel.setColumnIdentifiers(columnTitles);

		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new MyTableCellRenderer());
		}

	}
    
	// File Reader
			public BufferedReader readFile() {
				FileReader r = null;
				try {
					int ch = osList.getSelectedIndex();
					switch (ch) {
					case 0: {
						r = new FileReader(
								"/Users/chiragbarot/volatilityFinal/os.txt");
						break;
					}
					case 1: {
						r = new FileReader("C:\\os.txt");
						break;
					}
					// For linux:
					// case 2:{r = new
					// FileReader("/Users/chiragbarot/volatilityFinal/os.txt");}
					}

				} catch (FileNotFoundException e1) {
					Component frame = null;
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame,
							"File not found,Please select correct OS");
					loadButton.setVisible(false);
					e1.printStackTrace();
				}
				return rf = new BufferedReader(r);

			}

	// Read file to table
	public void readRows(BufferedReader rf) {
		/*
		 * FileReader r = null; try { int ch=osList.getSelectedIndex();
		 * switch(ch){ case 0:{r = new
		 * FileReader("/Users/chiragbarot/volatilityFinal/os.txt");} case 1:{r =
		 * new FileReader("C:\\os.txt");} //For linux: //case 2:{r = new
		 * FileReader("/Users/chiragbarot/volatilityFinal/os.txt");} }
		 * 
		 * } catch (FileNotFoundException e1) { Component frame = null; // TODO
		 * Auto-generated catch block JOptionPane.showMessageDialog(frame,
		 * "File not found,Please select correct OS"); e1.printStackTrace(); }
		 * BufferedReader rf = new BufferedReader(r);
		 */
		String line = null;
		String[] splits;
		String[] clm;
		int i = 0;

		try {
			while ((line = rf.readLine()) != null) {
				i++;
				if (i >= 2 && !line.contains("--")) {
					splits = line.split("\\s+");
					// Add rows here

					tModel.addRow(splits);
				} else if (!line.contains("--")) {

					clm = line.split("\\s+");
					for (int j = 0; j < clm.length; j++) {
						tModel.addColumn(clm[j]);

						tModel.setColumnIdentifiers(clm);

						Enumeration<TableColumn> en = table.getColumnModel()
								.getColumns();
						while (en.hasMoreElements()) {
							TableColumn tc = en.nextElement();
							tc.setCellRenderer(new MyTableCellRenderer());
						}

					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readPsxview(BufferedReader rf) {
		/*
		 * FileReader r = null; try { r = new FileReader(
		 * "/Users/chiragbarot/volatilityFinal/output/psxview.txt"); } catch
		 * (FileNotFoundException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } BufferedReader rf = new BufferedReader(r);
		 */
		String line = null;
		String[] splits;
		String[] clm;
		int i = 0;

		try {
			while ((line = rf.readLine()) != null) {
				i++;
				if (i >= 2 && !line.contains("--")) {
					splits = line.split("\\s+");
					// Add rows here
					tModel.addRow(splits);
				} else if (!line.contains("--")) {
					clm = line.split("\\s+");
					for (int j = 0; j < clm.length; j++) {
						tModel.addColumn(clm[j]);
						tModel.setColumnIdentifiers(clm);

						Enumeration<TableColumn> en = table.getColumnModel()
								.getColumns();
						while (en.hasMoreElements()) {
							TableColumn tc = en.nextElement();
							tc.setCellRenderer(new MyTableCellRenderer());
						}

					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// netstat
	public void readNetstat(BufferedReader rf) {
		/*
		 * FileReader r = null; try { r = new FileReader(
		 * "/Users/chiragbarot/volatilityFinal/output/netstat.txt"); } catch
		 * (FileNotFoundException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } BufferedReader rf = new BufferedReader(r);
		 */
		String line = null;
		String[] splits;
		try {
			while ((line = rf.readLine()) != null) {
				splits = line.split("\\s+");
				// Add rows here
				tModel.addRow(splits);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// pstree
	public void readPstree(BufferedReader rf) {
		/*
		 * FileReader r = null; try { r = new FileReader(
		 * "/Users/chiragbarot/volatilityFinal/output/pstree.txt"); } catch
		 * (FileNotFoundException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } BufferedReader rf = new BufferedReader(r);
		 */
		String line = null;
		String[] splits;
		String[] clm;
		int i = 0;

		try {
			while ((line = rf.readLine()) != null) {
				i++;
				if (i >= 2 && !line.contains("--")) {
					splits = line.split("\\s+");
					// Add rows here
					tModel.addRow(splits);
				} else if (!line.contains("--")) {
					clm = line.split("\\s+");
					for (int j = 0; j < clm.length; j++) {
						tModel.addColumn(clm[j]);
						tModel.setColumnIdentifiers(clm);

						Enumeration<TableColumn> en = table.getColumnModel()
								.getColumns();
						while (en.hasMoreElements()) {
							TableColumn tc = en.nextElement();
							tc.setCellRenderer(new MyTableCellRenderer());
						}

					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Notifier
	// Read file to table
	public void readNotifiers(BufferedReader rf) {
		/*
		 * FileReader r = null; try { r = new
		 * FileReader("/Users/chiragbarot/volatilityFinal/os.txt"); } catch
		 * (FileNotFoundException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } BufferedReader rf = new BufferedReader(r);
		 */
		String line = null;
		String[] splits;
		String[] clm;

		int i = 0;

		try {
			while ((line = rf.readLine()) != null) {
				i++;
				if (i >= 2 && !line.contains("--")) {
					splits = line.split("\\s+");
					// Add rows here
					tModel.addRow(splits);
				} else if (!line.contains("--")) {
					clm = line.split("\\s+");
					for (int j = 0; j < clm.length; j++) {
						tModel.addColumn(clm[j]);
						tModel.setColumnIdentifiers(clm);

						Enumeration<TableColumn> en = table.getColumnModel()
								.getColumns();
						while (en.hasMoreElements()) {
							TableColumn tc = en.nextElement();
							tc.setCellRenderer(new MyTableCellRenderer());

						}
					}

				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class MyTableCellRenderer extends DefaultTableCellRenderer implements
			TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// Color currColor = Color.white;
		private final Color alt2 = new Color(235, 244, 250);
		private final Color alt1 = Color.WHITE;
		private final Color invalidStatus = Color.RED;

		public void AlternateTableRowColorRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component cell = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			Object o = table.getModel().getValueAt(row, column);
			String s = "";

			if (o != null) {
				s = o.toString();
				if ((s.equalsIgnoreCase("UNKNOWN") || s.isEmpty() || s
						.equalsIgnoreCase("FALSE")) == true) {
					setBackground(invalidStatus);
				} else {
					setBackground(colorAlternator(row));
				}
			} else {
				setBackground(colorAlternator(row));
			}
			/*if (isSelected) {
				super.setForeground(table.getSelectionForeground());
				super.setBackground(table.getSelectionBackground());
			} else {
				super.setForeground(Color.black);
				super.setBackground(colorAlternator(row));
			}
			setFont(table.getFont());
			setValue(value);*/

			return cell;
		}

		private Color colorAlternator(int row) {
			if ((row % 2) == 0) {
				return alt1;
			} else {
				return alt2;
			}
		}
	}

}
