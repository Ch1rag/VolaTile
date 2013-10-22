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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
	private JTextArea output;
	// table text areas
	private JTextArea p1Text;
	private JTextArea p2Text;
	private JTextArea p3Text;
	private JTextArea p4Text;
    private JRadioButton psxview;
    private JRadioButton pslist;
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
	private String profile;
	private String vol;
	private Object data;
	private Color bg = new Color(249, 246, 244);
	// private String OS=null;

	// Array list to store strings of commands
	private ArrayList<String> macCommands = new ArrayList<String>();
	private ArrayList<String> winCommands = new ArrayList<String>();
	private ArrayList<String> os = new ArrayList<String>();
	private String dumpFile;
	private String volPath;
	private SwingWorker<ArrayList<Future<?>>, Void> worker;

	public GUI(){
	}
	public GUI(String dumpFile,String profile,String vol,String volPath) {
		this.dumpFile=dumpFile;
		this.profile=profile;
		this.volPath=volPath;
		this.vol=vol;	
	}
	public String getPath(){
			return volPath;
		 	}

	public void storeCmd_Mac() {
		macCommands.add("mac_pslist");
		macCommands.add("mac_psxview");	
		 macCommands.add("mac_notifiers"); 
		 macCommands.add("mac_pstree");
		 macCommands.add("mac_tasks");
		 macCommands.add("mac_ifconfig");
		 
	}

	public void storeCmd_Win() {
		winCommands.add("pslist");
		winCommands.add("psxview");
	}

	public void storeOS(String profile) {
		os.add(profile);
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
		final JPanel panelTX = new JPanel(new GridLayout());
		final JPanel panelFL1 = new JPanel(new GridLayout(1, 1));
		
		
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
		final JPanel p2 = new JPanel(new GridLayout());
		final JPanel p3 = new JPanel(new GridLayout());
		final JPanel p4 = new JPanel(new GridLayout());
		tabPane.setPreferredSize(new Dimension(300, 300));

		// Add commands to combobox list
		cmdList = new JComboBox();
		java.util.Iterator<String> iterCmd = winCommands.iterator();
		while (iterCmd.hasNext()) {
			cmdList.addItem(iterCmd.next());	
		}
		
		if (profile.contains("Mac")==true) {
			cmdList.removeAllItems();
			java.util.Iterator<String> Cmd = macCommands.iterator();
			while (Cmd.hasNext()) {
				cmdList.addItem(Cmd.next());
			}
		} else if (profile.contains("Win")==true) {
			cmdList.removeAllItems();
			java.util.Iterator<String> iterOs = winCommands.iterator();
			while (iterOs.hasNext()) {
				cmdList.addItem(iterOs.next());
			}
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
		/*
		//radio button
		psxview=new JRadioButton("psxview");
		pslist=new JRadioButton("pslist");
		pslist.setSelected(true);
	    
		ButtonGroup group=new ButtonGroup();
		group.add(psxview);
		group.add(pslist);*/

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
		textArea.append("text area");
		textArea.add(table);
		textArea.setVisible(false);
		
		output = new JTextArea(30, 30);
		output.setText("text area");
		
		// Add scroll bar to tab panels
		scrollTable = new JScrollPane(table);
		scroll_p1Text = new JScrollPane(p1Text);
		scroll_p2Text = new JScrollPane(p2Text);
		scroll_p3Text = new JScrollPane(p3Text);
		scroll_p4Text = new JScrollPane(p4Text);

		loadButton.setVisible(false);
		runButton.setVisible(true);
		//configButton.setVisible(false);

		// panelFL2.add(panelBL);
		// cp.add(panelBL, BorderLayout.SOUTH);
		cp.add(panelFL1, BorderLayout.NORTH);
		// cp.add(panelTX);
		cp.add(splitPane);

		// Add components here
		panelTX.add(scrollTable);
		panelTX.add(output);
		panelBL.add(scroll_p1Text);
		panelBL.add(scroll_p2Text);
		panelBL.add(scroll_p3Text);
		panelBL.add(scroll_p4Text);

		// panelFL1.add(l1,FlowLayout.LEFT);
		
		panelFL1.add(runButton, FlowLayout.LEFT);
		panelFL1.add(cmdList, FlowLayout.LEFT);
		panelFL1.add(osList, FlowLayout.LEFT);
		panelFL1.add(loadButton, FlowLayout.LEADING);
		panelFL1.setVisible(false);

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

		
		//Swing worker for loading tabs
		worker = new SwingWorker<ArrayList<Future<?>>, Void>() {
			
			private ArrayList<Future<?>> futures=new ArrayList<Future<?>>();
			@Override
			public ArrayList<Future<?>> doInBackground() throws InterruptedException, ExecutionException {
				command = cmdList.getSelectedItem().toString();
				
				//Instantiating all processes objects
				Connections con=new Connections(dumpFile,profile,vol,volPath);
				Handles hnd=new Handles(dumpFile,profile,vol,volPath);
				Sockets soc=new Sockets(dumpFile,profile,vol,volPath);
				Threads thd=new Threads(dumpFile,profile,vol,volPath);
				ProcessBuilderClass pb = new ProcessBuilderClass(command,dumpFile,profile,vol,volPath);
				//Passing processes objects to ThreadExecutors to manage 
				ThreadExecutor te=new ThreadExecutor(con,hnd,soc,thd,pb);	
				
				try {
					futures=te.executor();
					output.setText("Executing processes.");
					
					if(futures.get(0).get()==null || futures.get(0).isDone()){
						tabPane.addTab("Connections", p1);
						tabPane.setSelectedComponent(p1);
						p1Text.append("Connections are available!");
						output.setText("Executing processes...");
					}
					if(futures.get(1).get()==null || futures.get(1).isDone()){
						tabPane.addTab("Sockets", p2);	
						tabPane.setSelectedComponent(p2);
						p2Text.append("Sockets are available!");
						output.append("..");
					}
					
				   if(futures.get(2).get()==null || futures.get(2).isDone()){
						
						tabPane.addTab("Threads", p3);
						tabPane.setSelectedComponent(p3);
						p3Text.append("Threads are available!");
						output.append("..");
					}
				   if(futures.get(3).get()==null || futures.get(3).isDone()){
						
						tabPane.addTab("Handles", p4);
						tabPane.setSelectedComponent(p4);
						p4Text.append("Handles are available!");
						output.append("..");
					}
				   if(futures.get(4).get()==null || futures.get(4).isDone()){
						
					   output.setVisible(false);
					  
					}
						
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return futures;
			}

			@Override
			public void done() {

				if(futures.get(4).isDone()==true){
					panelTX.remove(output);
					textArea.setVisible(true);
					tModel.setRowCount(0);
					readFile(volPath);
					readRows(rf);
					panelFL1.setVisible(true);
					panelTX.setVisible(true);
						
				}

			}

		};
		worker.execute();
		

		// Button event handler
		/*runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   
				command = cmdList.getSelectedItem().toString();
				ProcessBuilderClass pb = new ProcessBuilderClass(command,dumpFile,profile,vol,volPath);
                pb.call();
				
				try {
					tModel.setRowCount(0);
					readFile(volPath);
					readPsxview(rf);
					tabPane.addTab("pslist", p1);
					tabPane.addTab("psscan", p2);
					tabPane.remove(p3);
					tabPane.remove(p4);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				cmdList.setVisible(false);
				osList.setVisible(false);
				runButton.setVisible(false);
				textArea.setText("");
				tModel.setRowCount(0);
				tModel.setColumnIdentifiers(columnTitles);
				
				
				// new Thread (new ProcessBuilderClass(profile)).start();

				
			}
		});
*/
		

		// * Load Button Event handler switch Function call using Switch JRE 1.7
		// support for String switch parameter
		/*loadButton.addActionListener(new ActionListener() {
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
*/
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
							tabPane.getSelectedComponent();
							
							data = table.getModel()
									.getValueAt(selectedRow, clm);

							String PID = data.toString();

							thread = new Threads(PID);
							connection = new Connections(PID);
							socket = new Sockets(PID);
							handle = new Handles(PID);


							try {

								ArrayList<String> connections = new ArrayList<String>();
								connections = connection.readFile(volPath);
								Iterator<String> con = connections.iterator();

								while (con.hasNext()) {
									p1Text.append(con.next());
								}

								ArrayList<String> sockets = new ArrayList<String>();
								sockets = socket.readFile(volPath);
								Iterator<String> soc = sockets.iterator();

								while (soc.hasNext()) {
									p2Text.append(soc.next());
								}

								ArrayList<String> threads = new ArrayList<String>();
								threads = thread.readFile(volPath);
								Iterator<String> thd = threads.iterator();

								while (thd.hasNext()) {
									p3Text.append(thd.next());
								}

								ArrayList<String> handles = new ArrayList<String>();
								handles = handle.readFile(volPath);
								Iterator<String> hnd = handles.iterator();

								while (hnd.hasNext()) {
									p4Text.append(hnd.next());
								}

							} catch (IOException e1) {
								JOptionPane.showMessageDialog(runButton,"File not found"+e1.getMessage());
							}
                          
							break;
						}
						case 3:{
							tabPane.getSelectedComponent();
						}
						case 4:{
							tabPane.setSelectedComponent(p3);
							clm=2;
							data = table.getModel()
									.getValueAt(selectedRow, clm);
							String PID = data.toString();
							thread = new Threads(PID);
							ArrayList<String> threads = new ArrayList<String>();
							try {
								threads = thread.readFile(volPath);
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
								handles = handle.readFile(volPath);
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
	public BufferedReader readFile(String volPath) {
		FileReader r = null;
		try {
			File file=null;
			String s=file.separator;
			r = new FileReader(volPath+s+command+".txt");
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
