/**    
 * A concise description of the class (including invariants if any)
 *    
 * @
 * PUBLIC FEATURES:
 * // Constructors
 *    each constructor should be listed here
 * // Methods
 *    The signature and a brief comment (if needed)
 *    In alphabetic order
 *
 * COLLABORATORS:
 *    Names of classes (other than System and java.lang)
 *
 * MODIFIED:
 * @version number, date last changed and author’s initials &/or what changed (very brief)
 * @author    (can have multiple authors)
 * http://swinbrain.ict.swin.edu.au/wiki/Swinburne_Java_Coding_Standard
 */
 
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
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	// table text areas
	private JTextArea p1Text;
	private JTextArea p2Text;
	private JTextArea p3Text;
	private JTextArea p4Text;
	private String done = "Process one is completed!";

	private JSplitPane splitPane;

	private JScrollPane scrollTable;
	private JScrollPane scroll_p1Text;
	private JScrollPane scroll_p2Text;
	private JScrollPane scroll_p3Text;
	private JScrollPane scroll_p4Text;

	private JTable table;
	private BufferedReader rf;
	private String command = null;
	// private ProcessBuilderClass pb = new ProcessBuilderClass();
	private Connections connection;
	private Sockets socket;
	private Threads thread;
	private Handles handle;
	private DefaultTableModel tModel;
	private String[] columnTitles = { "", "", "", "", "", "", "", "", "", "",
			"", "" };
	private JTabbedPane tabPane;
	private JButton inspectButton;
	private String profile;
	private Object data;
	private Color bg = new Color(249, 246, 244);
	// private String OS=null;

	// Array list to store strings of commands
	private ArrayList<String> macCommands = new ArrayList<String>();
	private ArrayList<String> winCommands = new ArrayList<String>();
	private ArrayList<String> os = new ArrayList<String>();
	private String dumpFile;
	private String user;

	public GUI(){}
	public GUI(String dumpFile,String profile,String user) {
		this.dumpFile=dumpFile;
		this.profile=profile;
		this.user=user;
	}

	public void storeCmd_Mac() {
		macCommands.add("pslist");
		macCommands.add("psxview");
		/*
		 * macCommands.add("mac_notifiers"); macCommands.add("mac_pstree");
		 * macCommands.add("mac_tasks"); macCommands.add("mac_ifconfig");
		 */
	}

	public void storeCmd_Win() {
		winCommands.add("netstat");
		winCommands.add("pstree");
	}

	public void storeOS(String OS) {
		os.add(OS);
		/*
		 * os.add("Windows"); os.add("Linux");
		 */
	}

	public void makeFrame() {
		// Create JFrame with content pane and set layout
		JFrame frame = new JFrame();

		final Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());

		// create table
		createTable();

		// Create Panels
		JPanel panelBL = new JPanel(new BorderLayout());
		JPanel panelTX = new JPanel(new GridLayout());
		JPanel panelFL1 = new JPanel(new GridLayout(1, 1));
		// JPanel panelFL2 = new JPanel(new FlowLayout());

		// set border
		panelTX.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panelBL.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panelBL.setBorder(new TitledBorder(new EtchedBorder(), "Tabs Area"));
		panelTX.setBorder(new TitledBorder(new EtchedBorder(), command));
		panelFL1.setBorder(new TitledBorder(new EtchedBorder(),
				"Profile Selection"));

		// create tab pane
		tabPane = new JTabbedPane();
		

		// Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTX, panelBL);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(500);

		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		panelBL.setMinimumSize(minimumSize);
		panelTX.setMinimumSize(minimumSize);

		// panels for tabs
		final JPanel p1 = new JPanel(new GridLayout());
		tabPane.addTab("Connections", p1);

		final JPanel p2 = new JPanel(new GridLayout());
		tabPane.addTab("Sockets", p2);

		final JPanel p3 = new JPanel(new GridLayout());
		tabPane.addTab("Threads", p3);

		final JPanel p4 = new JPanel(new GridLayout());
		tabPane.addTab("Handles", p4);
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
		loadButton = new JButton("Load");
		inspectButton = new JButton("Inspect");

		// Text area to add to tab panels
		p1Text = new JTextArea(50, 50);
		p1Text.setBackground(bg);
		p1Text.setEditable(false);
		p1Text.setLineWrap(true);
		p1Text.getCaretPosition();
		p1Text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		// Text area to add to tab panels
		p2Text = new JTextArea(50, 50);
		p2Text.setBackground(bg);
		p2Text.setEditable(false);
		p2Text.setLineWrap(true);
		p2Text.getCaretPosition();
		p2Text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		// Text area to add to tab panels
		p3Text = new JTextArea(10, 40);
		p3Text.setBackground(bg);
		p3Text.setEditable(false);
		p3Text.setLineWrap(true);
		p3Text.getCaretPosition();
		p3Text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		// Text area to add to tab panels
		p4Text = new JTextArea(10, 40);
		p4Text.setBackground(bg);
		p4Text.setEditable(false);
		p4Text.setLineWrap(true);
		p4Text.getCaretPosition();
		p4Text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		textArea = new JTextArea(30, 30);
		textArea.setBackground(bg);
		textArea.add(table);

		// Add scroll bar to tab panels
		scrollTable = new JScrollPane(table);
		scroll_p1Text = new JScrollPane(p1Text);
		scroll_p2Text = new JScrollPane(p2Text);
		scroll_p3Text = new JScrollPane(p3Text);
		scroll_p4Text = new JScrollPane(p4Text);

		loadButton.setVisible(false);
		runButton.setVisible(true);
		inspectButton.setVisible(false);

		// panelFL2.add(panelBL);
		// cp.add(panelBL, BorderLayout.SOUTH);
		cp.add(panelFL1, BorderLayout.NORTH);
		// cp.add(panelTX);
		cp.add(splitPane);

		// Add components here
		panelTX.add(scrollTable);
		panelBL.add(scroll_p1Text);
		panelBL.add(scroll_p2Text);
		panelBL.add(scroll_p3Text);
		panelBL.add(scroll_p4Text);

		// panelFL1.add(l1,FlowLayout.LEFT);
		panelFL1.add(runButton, FlowLayout.LEFT);
		panelFL1.add(cmdList, FlowLayout.LEFT);
		panelFL1.add(osList, FlowLayout.LEFT);
		panelFL1.add(loadButton, FlowLayout.LEADING);
		panelFL1.add(inspectButton, FlowLayout.LEADING);

		panelBL.add(tabPane, BorderLayout.CENTER);

		// table panel add components
		p1.add(scroll_p1Text);
		p2.add(scroll_p2Text);
		p3.add(scroll_p3Text);
		p4.add(scroll_p4Text);

		// Set frame elements
		frame.setTitle("VolaTile");
		frame.setSize(700, 900);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Populate available commands for selected OS
		osList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (osList.getSelectedItem().toString().contains("Mac")) {
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

		final SwingWorker worker = new SwingWorker<String, Void>() {
			@Override
			public String doInBackground() throws InterruptedException {
				
				ProcessBuilderClass pb = new ProcessBuilderClass(command,dumpFile,profile,user);
				Thread td = new Thread(pb);
				td.start();
				Thread.sleep(10000);
				new ThreadExecutor(td);
				return done;
			}

			@Override
			public void done() {
				try {
					textArea.setText(get());
					loadButton.setVisible(true);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};

		// Button event handler
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command = cmdList.getSelectedItem().toString();
				cmdList.setVisible(false);
				osList.setVisible(false);
				runButton.setVisible(false);
				loadButton.setVisible(false);
				textArea.setText("");

				worker.execute();
				// new Thread (new ProcessBuilderClass(profile)).start();

				
			}
		});

		/*
		 * // Clear button events clrButton.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) {
		 * 
		 * tModel.setRowCount(0); loadButton.setVisible(false);
		 * runButton.setVisible(true); clrButton.setVisible(false);
		 * cmdList.setVisible(true); osList.setVisible(true);
		 * tModel.setColumnIdentifiers(columnTitles);
		 * 
		 * } });
		 */

		// * Load Button Event handler switch Function call using Switch JRE 1.7
		// support for String switch parameter
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tModel.setRowCount(0);
				readFile();
				int choice = cmdList.getSelectedIndex();
				switch (choice) {

				case 0:// pslist
				{
					readPslist(rf);
					break;
				}
				case 1: // psxview
				{
					loadButton.setVisible(true);
					try {
						readPsxview(rf);
						tabPane.addTab("pslist", p1);
						tabPane.addTab("psscan", p2);
						tabPane.remove(p3);
						tabPane.remove(p4);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					break;
				}

				default:
					readRows(rf);

				}
				runButton.setVisible(true);
				osList.setVisible(true);
				cmdList.setVisible(true);
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
						p3Text.setText("");
						p4Text.setText("");
						int selectedRow = lsm.getMinSelectionIndex();
						int clm = table.getSelectedColumn();

						switch (clm) {
						case 0: 
						case 1: {
							clm=2;
						}
						case 2: {
							tabPane.setSelectedComponent(p1);
							data = table.getModel()
									.getValueAt(selectedRow, clm);

							String PID = data.toString();

							thread = new Threads(PID);
							connection = new Connections(PID);
							socket = new Sockets(PID);
							handle = new Handles(PID);

							// inspectButton.setVisible(true);
							try {

								ArrayList<String> connections = new ArrayList<String>();
								connections = connection.readFile();
								Iterator<String> con = connections.iterator();

								while (con.hasNext()) {
									p1Text.append(con.next());
								}

								ArrayList<String> sockets = new ArrayList<String>();
								sockets = socket.readFile();
								Iterator<String> soc = sockets.iterator();

								while (soc.hasNext()) {
									p2Text.append(soc.next());
								}

								ArrayList<String> threads = new ArrayList<String>();
								threads = thread.readFile();
								Iterator<String> thd = threads.iterator();

								while (thd.hasNext()) {
									p3Text.append(thd.next());
								}

								ArrayList<String> handles = new ArrayList<String>();
								handles = handle.readFile();
								Iterator<String> hnd = handles.iterator();

								while (hnd.hasNext()) {
									p4Text.append(hnd.next());
								}

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                          
							break;
						}
						case 3:
						case 4:{
							tabPane.setSelectedComponent(p3);
							clm=2;
							data = table.getModel()
									.getValueAt(selectedRow, clm);
							String PID = data.toString();
							thread = new Threads(PID);
							ArrayList<String> threads = new ArrayList<String>();
							try {
								threads = thread.readFile();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Iterator<String> thd = threads.iterator();

							while (thd.hasNext()) {
								p3Text.append(thd.next());
							}
							break;
						}
						case 5:{
							tabPane.setSelectedComponent(p4);
							clm=2;
							data = table.getModel()
									.getValueAt(selectedRow, clm);
							String PID = data.toString();
							handle = new Handles(PID);
							ArrayList<String> handles = new ArrayList<String>();
							try {
								handles = handle.readFile();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Iterator<String> hnd = handles.iterator();

							while (hnd.hasNext()) {
								p4Text.append(hnd.next());
							}
							break;
						}
						default: {
							System.out.println("No rows are selected");
						}

						}

					}

				}

			});

		} else {
			table.setRowSelectionAllowed(false);
		}

	}

	// * Create Table Default Rows and Columns Format specified Format table
	// with
	// * Set attributes
	public void createTable() {
		// Create Table and table model
		table = new JTable();
		table.setBackground(Color.DARK_GRAY);
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
			r = new FileReader("/Users/chiragbarot/volatility/"+command+".txt");
		} catch (FileNotFoundException e1) {
			Component frame = null;
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frame,
					"File not found");
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
	public void readPsxview(BufferedReader rf) throws FileNotFoundException {
		/*
		 * FileReader r = new
		 * FileReader("/Users/chiragbarot/volatility/output.txt");
		 * BufferedReader br=new BufferedReader(r);
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

		String s = "\\s+";
		Pattern pattern = Pattern.compile("\\s+");
		Matcher matcher = pattern.matcher(s);
		boolean found = matcher.find();

		try {
			while ((line = rf.readLine()) != null) {
				i++;
				/*
				 * if(i==1 && found){ while ((line = rf.readLine()) != null){
				 * splits = line.split("\\s+"); tModel.addRow(splits);
				 */
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
		private final Color alt2 = new Color(249, 246, 244);
		// private final Color alt2 = new Color(235, 244, 250);
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
