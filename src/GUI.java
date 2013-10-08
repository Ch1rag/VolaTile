import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
	//table text areas
	private JTextArea p1Text;
	private JTextArea p2Text;
	private JTextArea p3Text;
	private JTextArea p4Text;
	
	private JButton clrButton;
	private JScrollPane scrollTable;
	private JScrollPane scroll_p1Text;
	private JTable table;
	private BufferedReader rf;
	private ProcessBuilderClass pb = new ProcessBuilderClass();
	private Connections connection;
	private Sockets socket;
	private DefaultTableModel tModel;
	private String[] columnTitles = { "", "", "", "", "", "", "", "", "", "",
			"", "" };
	private JTabbedPane tabPane;
	private JButton inspectButton;
	private Object data;
	//private String OS=null;

	// Array list to store strings of commands
	private ArrayList<String> macCommands = new ArrayList<String>();
	private ArrayList<String> winCommands = new ArrayList<String>();
	private ArrayList<String> os = new ArrayList<String>();

	public GUI() {
	}
	

	public void storeCmd_Mac() {
		macCommands.add("pslist");
		macCommands.add("psxview");
		/*macCommands.add("mac_notifiers");
		macCommands.add("mac_pstree");
		macCommands.add("mac_tasks");
		macCommands.add("mac_ifconfig");*/
	}

	public void storeCmd_Win() {
		winCommands.add("netstat");
		winCommands.add("pstree");
	}

	public void storeOS(String OS) {
		os.add(OS);
		/*os.add("Windows");
		os.add("Linux");*/
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
		JPanel panelFL1 = new JPanel(new GridLayout(4, 4));
		JPanel panelFL2 = new JPanel(new FlowLayout());
		panelTX.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		panelTX.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));
		panelFL1.setBorder(new TitledBorder(new EtchedBorder(), "North"));

		// create tab pane
		tabPane = new JTabbedPane();

		// panels for tabs
		final JPanel p1 = new JPanel();
		tabPane.addTab("Connections", p1);
		tabPane.setMnemonicAt(0, KeyEvent.VK_1);

		final JPanel p2 = new JPanel();
		tabPane.addTab("Sockets", p2);
		tabPane.setMnemonicAt(1, KeyEvent.VK_2);

		final JPanel p3 = new JPanel();
		tabPane.addTab("Threads", p3);

		final JPanel p4 = new JPanel();
		tabPane.addTab("none", p4);
		tabPane.setPreferredSize(new Dimension(300, 300));

		// Add commands to combobox list
		cmdList = new JComboBox();
		java.util.Iterator<String> iterCmd = macCommands.iterator();
		while (iterCmd.hasNext()) {
			cmdList.addItem(iterCmd.next());
		}

		// Add os to combobox list
		osList = new JComboBox();
		osList.setEditable(false);
		java.util.Iterator<String> iterOs = os.iterator();
		while (iterOs.hasNext()) {
			osList.addItem(iterOs.next());
		}

		// Define Components and initialize them
		
		
		runButton = new JButton("Run");
		clrButton = new JButton("Clear");
		loadButton = new JButton("Load");
		inspectButton =new JButton("Inspect");

		// Text area to add to tab panels
		p1Text = new JTextArea(50,50);
		p1Text.setEditable(false);
		p1Text.setLineWrap(true);
		
		// Text area to add to tab panels
	    p2Text = new JTextArea(50,50);
	    p2Text.setEditable(false);
		p2Text.setLineWrap(true);
		

		textArea = new JTextArea(10, 10);
		textArea.setText("Click 'Run'");
		textArea.add(table);

		
		scrollTable = new JScrollPane(table);
		scroll_p1Text = new JScrollPane(p1Text);
		//loadButton.setVisible(false);
		clrButton.setVisible(false);
		runButton.setVisible(true);
		inspectButton.setVisible(false);

		// panelFL2.add(panelBL);
		cp.add(panelBL, BorderLayout.SOUTH);
		cp.add(panelFL1, BorderLayout.NORTH);
		cp.add(panelFL2, BorderLayout.WEST);
		cp.add(panelTX);

		// Add components here
		panelTX.add(scrollTable);
		panelBL.add(scroll_p1Text);
		// panelFL1.add(l1,FlowLayout.LEFT);
		panelFL1.add(runButton, FlowLayout.LEFT);
		panelFL1.add(cmdList, FlowLayout.LEFT);
		panelFL1.add(osList, FlowLayout.LEFT);
		panelFL1.add(clrButton, FlowLayout.LEADING);
		panelFL1.add(loadButton, FlowLayout.LEADING);
		panelFL1.add(inspectButton, FlowLayout.LEADING);
	
		panelBL.add(tabPane, BorderLayout.CENTER);
		

		// table panel add components
		p1.add(p1Text);
		p2.add(p2Text);
		/*p3.add(p3Text);
		p4.add(p4Text);*/

		// Set frame elements
		frame.setTitle("VolaTile");
		frame.setSize(700, 900);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Populate available commands for selected OS
		osList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(osList.getSelectedItem().toString().contains("Mac")) {
					cmdList.removeAllItems();
					java.util.Iterator<String> iterCmd = macCommands.iterator();
					while (iterCmd.hasNext()) {
						cmdList.addItem(iterCmd.next());
					}
				} else if (osList.getSelectedItem().toString().contains("Win")) {
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
				
				pb.List();
				pb.editList(cmdList.getSelectedItem().toString());
				pb.run();
				
				inspectButton.setVisible(false);
				cmdList.setVisible(false);
				osList.setVisible(false);
				loadButton.setVisible(true);
				runButton.setVisible(false);
				clrButton.setVisible(false);
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

			}
		});

		// * Load Button Event handler switch Function call using Switch JRE 1.7
		// * support for String switch parameter
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clrButton.setVisible(true);
				inspectButton.setVisible(false);
				tModel.setRowCount(0);
				readFile();
				int choice = cmdList.getSelectedIndex();
				switch (choice) {

				case 0:// pstree
				{
					readPslist(rf);
					break;
				}
				case 1: // psxview
				{
					loadButton.setVisible(true);
					try {
						readPsxview();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					break;
				}

				default:
					readRows(rf);

				}
			}
		});

		// Table selection
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		boolean ROW_SELECTED = true;
		if (ROW_SELECTED) {
			ListSelectionModel rowSL = table.getSelectionModel();
			rowSL.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {
					if (e.getValueIsAdjusting())
						return;

					ListSelectionModel lsm = (ListSelectionModel) e.getSource();
					if (lsm.isSelectionEmpty()) {
						System.out.println("No rows are selected.");
					} else {
						p1Text.setText("");
						p2Text.setText("");
						int selectedRow = lsm.getMinSelectionIndex();
						int clm = table.getSelectedColumn();
						
						switch(clm){
						case 0:{
							data = table.getModel().getValueAt(selectedRow,
									clm);
							System.out.println("Selected value is:"+data.toString());
							break;
						}
						case 1:{
							data = table.getModel().getValueAt(selectedRow,
									clm);
							System.out.println("Selected value is:"+data.toString());
							break;
						}
						case 2:{
							
							data = table.getModel().getValueAt(selectedRow,
									clm);
							String PID=data.toString();
							connection = new Connections(PID);
							socket=new Sockets(PID);
							//inspectButton.setVisible(true);
							try {
								connection.readFile();
								p1Text.append(connection.toString());
								socket.readFile();
								p2Text.append(socket.toString());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							break;
						}
						default:{
							System.out.println("No rows are selected");
						}
							
						
						
						
						}
						
						
						
					}

				}

			});
        
		} else {
			table.setRowSelectionAllowed(false);
		}
		
		/*//Connection or lsof
		inspectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					obj.readFile();
					
					p1Text.append(obj.toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
 
			}	
		});*/

	}

	// * Create Table Default Rows and Columns Format specified Format table
	// with
	// * Set attributes
	//
	public void createTable() {
		// Create Table and table model
		table = new JTable();
		table.setBackground(Color.white);
		tModel = new DefaultTableModel(0, 0);

		// Table row settings
		table.setRowHeight(22);
		table.setModel(tModel);
		table.setAutoCreateRowSorter(true);
		table.setCellSelectionEnabled(true);
		tModel.setColumnIdentifiers(columnTitles);
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();

		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new MyTableCellRenderer());
		}
	}

	// Read file and return read file object
	public BufferedReader readFile() {
		FileReader r = null;
		try {
			int ch = osList.getSelectedIndex();
			switch (ch) {
			case 0: {
				r = new FileReader("/Users/chiragbarot/volatility/output.txt");
				break;
			}
			case 1: {
				r = new FileReader("C:\\os.txt");
				break;
			}

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

	// Default function call for switch statement..
	public void readRows(BufferedReader rf) {
		String line = null;
		String[] splits;
		String[] clm;
		int i = 0;

		try {
			while ((line = rf.readLine()) != null) {
				i++;
				if (i >= 2 && !line.contains("--")) {
					splits = line.split("\\s+");
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
			e.printStackTrace();
		}
	}

	// psxview
	public void readPsxview() throws FileNotFoundException {
		FileReader r = new FileReader("/Users/chiragbarot/volatility/psxview.txt");
		BufferedReader br=new BufferedReader(r);
		String line = null;
		String[] splits;
		String[] clm;
		int i = 0;

		try {
			while ((line = br.readLine()) != null) {
				i++;
				if (i >= 2 && !line.contains("--")) {
					splits = line.split("\\s+");
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

	// pstree
	public void readPslist(BufferedReader rf) {
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
			e.printStackTrace();
		}
	}

	// Inner class for table cell renderer
	public class MyTableCellRenderer extends DefaultTableCellRenderer implements
			TableCellRenderer {
		private static final long serialVersionUID = 1L;
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
			if (isSelected) {
				super.setForeground(Color.BLACK);
				super.setBackground(table.getSelectionBackground());
			} else {
				super.setBackground(colorAlternator(row));
			}
			setFont(table.getFont());
			setValue(value);

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
