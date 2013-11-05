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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Class for display the Graphical User Interface
 * <p> This class is responsible for running the GUI front end for text based volatility
 * All the Buttons, Text Areas and Lists which are visible on the interface are defined in this class
 * 
 * @author 		Chirag Barot
 * @version		1.0
 */
public class GUI {
	private JButton confButton;
	
	// private JComboBox cmdList;
	private JComboBox proList;
	private JTextArea textArea;
	private JTextArea output;
	
	// table text areas
	private JTextArea p1Text;
	private JTextArea p2Text;
	private JTextArea p3Text;
	private JTextArea p4Text;
	private JSplitPane splitPane;
	private JTextField volPathText;
	private JScrollPane scrollTable;
	private JScrollPane scrollTablepsx;
	private JScrollPane scroll_p1Text;
	private JScrollPane scroll_p2Text;
	private JScrollPane scroll_p3Text;
	private JScrollPane scroll_p4Text;
	private JTabbedPane tabPaneTX;
    private JPanel p3;
    private JPanel p4;
	private JTable table;
	private JTable table1;
	private BufferedReader rf;
	private String command = null;
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

	// Array list to store strings of commands
	private ArrayList<String> macCommands = new ArrayList<String>();
	private ArrayList<String> winCommands = new ArrayList<String>();
	private ArrayList<String> profileList = new ArrayList<String>();
	private String dumpFile;
	private String volPath;
	private SwingWorker<ArrayList<Future<?>>, Void> worker;

	/**
     * The default constructor method. 
     * Used only the first time when the application is run.
	 * Used only when  the overloaded constructor is not used
     */
	public GUI() {
	}

	/**
     * The overloaded constructor for GUI Class.
     * This is the constructor used by configuration class.
	 * When the user wants to select the memory dump file, the profile and the volatility path
     * this method will be called. 
     * 
     * @param   dumpFile     The Memory dump file which selected by browsing the computer will be called by this string variable
	 * @param   profile      The Profile of the chosen dump file will be called by this string variable
	 * @param   path         The Path to the Memory dump file will be called by this string variable
	 * @param   vol          vol.py file will be called
     * @param   volPath      The Path to the vol.py will be called by this string variable
	 */
	public GUI(String dumpFile, String profile, String vol, String volPath) {
		this.dumpFile = dumpFile;
		this.profile = profile;
		this.volPath = volPath;
		this.vol = vol;
	}

	/**
     * Get Path method.  When the class instance is constructed with the overloaded constructor,
     * this method returns the path of the vol.py, from which the class was instantiated.
     * 
     * @return  volPath		The Path of the vol.py will be returned when this method is called
     */	
	public String getPath() {
		return volPath;
	}

	/**
     * This method particularly used by apple mac os.
     * In order to retrieve the pslist, psxview, notifiers, pstree, tasks and ifconfig,
	 * the following method will be used.
     * 
     * @param  pslist		The Process List of the Memory dump
	 * @param  psxview		Will enable to view the hidden processes of the memory dump
	 * @param  pstree		The Process List will be displayed in a tree form
	 * @param  ifconfig		The IP configuration of the system when the mem dump was taken
     */	
	public void storeCmd_Mac() {
		macCommands.add("mac_pslist");
		macCommands.add("mac_psxview");
		macCommands.add("mac_notifiers");
		macCommands.add("mac_pstree");
		macCommands.add("mac_tasks");
		macCommands.add("mac_ifconfig");

	}

	/**
     * This method particularly used by windows os.
     * In order to retrieve the pslist and psxview
	 * the following method will be used.
     * 
     * @param  pslist		The Process List of the Memory dump
	 * @param  psxview		Will enable to view the hidden processes of the memory dump
     */	
	public void storeCmd_Win() {
		winCommands.add("pslist");
		winCommands.add("psxview");
	}

	/**
     * This method stores the information of the current working OS.
     * 
     * @param  profile		The Profile of the OS where the VolaTile runs
     */	
	public void storeProfile(String profile) {
		profileList.add(profile);
	}

	/**
     * This method is used for creating the Frame. Any essential changes 
	 * to the display of the frame should be done by overriding this method.
     * <p>
	 * This is the method which is used to display the face of the GUI
	 * <p>
	 * Inside the Java Frame a panel is used to set the grid layouts
	 * also setting the border is done using this method.
	 * <p>
	 * Run button uses an action listener in order to handle its events.
     */	
	public void makeFrame() {
		
		/** 
		 * Create a JFrame with content pane and setting the
		 * layout to display the pslist information in a table.
		 * in the VolaTile window.
		 */
		final JFrame frame = new JFrame();
		final Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());

		/** 
		 *	Create new JTable and DefaultTableModel
		 */
		createTable();
		

		/** 
		 *	Create the Panels inside the JFrame
		 */
		JPanel panelBL = new JPanel(new BorderLayout());
		final JPanel panelTX = new JPanel(new GridLayout());
		final JPanel panelFL1 = new JPanel(new GridLayout(1, 1));
		
		panelFL1.setBorder(new TitledBorder(new EtchedBorder(),
				"Profile Selection"));

		/** 
		 *	Create the tab panes
		 */
		tabPane = new JTabbedPane();
		tabPane.setPreferredSize(new Dimension(300, 300));

		tabPaneTX = new JTabbedPane();

		/** 
		 *	Create a split pane with the two scroll panes in it.
		 */
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTX, panelBL);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(350);

		/** 
		 *	Provide minimum sizes for the two components in the split pane
		 */		
		Dimension minimumSize = new Dimension(100, 50);
		panelBL.setMinimumSize(minimumSize);
		panelTX.setMinimumSize(minimumSize);

		
		/** 
		 *	Panels for four tabs on the bottom of the window
		 */			
		final JPanel p1 = new JPanel(new GridLayout());
		final JPanel p2 = new JPanel(new GridLayout());
		p3 = new JPanel(new GridLayout());
		p4 = new JPanel(new GridLayout());
		final JPanel p5 = new JPanel(new GridLayout());
		final JPanel p6 = new JPanel(new GridLayout());
		

		/** 
		 *	Add os to combobox list
		 */			
		proList = new JComboBox();
		proList.setEditable(false);
		proList.setToolTipText("Selected OS profile");
		java.util.Iterator<String> iterOs = profileList.iterator();
		while (iterOs.hasNext()) {
			proList.addItem(iterOs.next());
		}

		/** 
		 *	Define Components and initialize them.
		 * <p>
		 * Config (confButton) will lead to the main configuration window.
		 * Also when the user hover the mouse over this button a tool tip 
		 * will be displayed. Set tool tips
		 */			
		confButton = new JButton("Config");
		confButton.setToolTipText("Open configuration window");


		// Textfields
		volPathText = new JTextField(dumpFile);

		/** 
		 *	The path for the vol.py file is displayed inside this 
		 *  text box.
		 */	
		volPathText = new JTextField(volPath);

		volPathText.setEditable(false);
		volPathText.setToolTipText("Selected RAM dump path");

		/** 
		 *	Text area to add to tab panels
		 */			
		p1Text = new JTextArea(50, 50);
		p1Text.setBackground(bg);
		p1Text.setEditable(false);
		p1Text.setLineWrap(true);
		p1Text.getCaretPosition();
		p1Text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		/** 
		 *	Text area to add to tab panels
		 */	
		p2Text = new JTextArea(50, 50);
		p2Text.setBackground(bg);
		p2Text.setEditable(false);
		p2Text.setLineWrap(true);
		p2Text.getCaretPosition();
		p2Text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		/** 
		 *	Text area to add to tab panels
		 */	
		p3Text = new JTextArea(10, 40);
		p3Text.setBackground(bg);
		p3Text.setEditable(false);
		p3Text.setLineWrap(true);
		p3Text.getCaretPosition();
		p3Text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		/** 
		 *	Text area to add to tab panels
		 */	
		p4Text = new JTextArea(10, 40);
		p4Text.setBackground(bg);
		p4Text.setEditable(false);
		p4Text.setLineWrap(true);
		p4Text.getCaretPosition();
		p4Text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		textArea = new JTextArea(30, 30);
		textArea.setVisible(false);
		p5.add(table);
		p6.setVisible(false);

		output = new JTextArea(30, 30);
		output.setText("text area");
		output.setLineWrap(true);
		output.setEditable(false);
		output.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		output.setForeground(Color.BLACK);
		output.setBackground(bg);

		/** 
		 *	Adding a scroll bar to the tab panels
		 */	
		scrollTable = new JScrollPane(table);// last change
		scrollTablepsx = new JScrollPane(table1);
		scroll_p1Text = new JScrollPane(p1Text);
		scroll_p2Text = new JScrollPane(p2Text);
		scroll_p3Text = new JScrollPane(p3Text);
		scroll_p4Text = new JScrollPane(p4Text);

		confButton.setVisible(true);

		cp.add(panelFL1, BorderLayout.NORTH);
		cp.add(splitPane);

		/** 
		 *	Adding the components to the Java Panel
		 */	
		panelTX.add(output);
		panelBL.add(scroll_p1Text);
		panelBL.add(scroll_p2Text);
		panelBL.add(scroll_p3Text);
		panelBL.add(scroll_p4Text);
		panelBL.add(tabPane, BorderLayout.CENTER);
		panelFL1.add(confButton, FlowLayout.LEFT);
		panelFL1.add(proList, FlowLayout.LEFT);
		panelFL1.add(volPathText,FlowLayout.LEFT);
		panelFL1.setVisible(false);

		/** 
		 *	Adding the components inside the table panel
		 */	
		p1.add(scroll_p1Text);
		p2.add(scroll_p2Text);
		p3.add(scroll_p3Text);
		p4.add(scroll_p4Text);
		p5.add(scrollTable);

		/** 
		 *	Setting the frame elements of the VolaTile window
		 */	
		frame.setTitle("VolaTile");
		frame.setSize(700, 900);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/** 
		 *	Swing worker for loading tabs
		 */	
		worker = new SwingWorker<ArrayList<Future<?>>, Void>() {

			private ArrayList<Future<?>> futures = new ArrayList<Future<?>>();

			@Override
			public ArrayList<Future<?>> doInBackground()
					throws InterruptedException, ExecutionException {
				// command = cmdList.getSelectedItem().toString();
				command = winCommands.get(0);
				// Instantiating all processes objects
				Connections con = new Connections(dumpFile, profile, vol,
						volPath);
				Handles hnd = new Handles(dumpFile, profile, vol, volPath);
				Sockets soc = new Sockets(dumpFile, profile, vol, volPath);
				Threads thd = new Threads(dumpFile, profile, vol, volPath);
				ProcessBuilderClass pb = new ProcessBuilderClass(command,
						dumpFile, profile, vol, volPath);
				// Passing processes objects to ThreadExecutors to manage
				ThreadExecutor te = new ThreadExecutor(con, hnd, soc, thd, pb);

				/** 
				 * Executing the processes in the background 
				 * while checking for exceptions. Once each process is complete
				 * a tab will appear on the bottom of the window
				 * 
				 * @Exception	InterruptedException
				 * @Exception	IOException
				 */				
				try {
					futures = te.executor();
					output.setText("Executing processes(Connections, Sockets, Threads, Handles).");

					/**
					 * After the Connection Process is complete,
					 * connections tab will appear and display a message
					 * "Connections are available"
					 */					
					if (futures.get(4).get() == null || futures.get(4).isDone()) {
						tabPane.addTab("Connections", p1);
						tabPane.setSelectedComponent(p1);
						p1Text.append("Connections are available!");
						output.append("\n" + "Connections are available");

					}
					
					/**
					 * After the Sockets Process is complete,
					 * sockets tab will appear and display a message
					 * "Sockets are available"
					 */					
					if (futures.get(2).get() == null || futures.get(2).isDone()) {
						tabPane.addTab("Sockets", p2);
						tabPane.setSelectedComponent(p2);
						p2Text.append("Sockets are available!");
						output.append("\n" + "Sockets are available");
					}
					
				    /**
					 * After the Threads Process is complete,
					 * threads tab will appear and display a message
					 * "Threads are available"
					 */
					if (futures.get(0).get() == null || futures.get(0).isDone()) {
						tabPane.addTab("Threads", p3);
						tabPane.setSelectedComponent(p3);
						p3Text.append("Threads are available!");
						output.append("\n" + "Threads are available");
					}
					
					/**
					 * After the Handles Process is complete,
					 * handles tab will appear and display a message
					 * "Handles are available"
					 */					
					if (futures.get(1).get() == null || futures.get(1).isDone()) {
						tabPane.addTab("Handles", p4);
						tabPane.setSelectedComponent(p4);
						p4Text.append("Handles are available!");
						output.append("\n" + "Handles are available");
					}
					
					
					if (futures.get(3).get() == null || futures.get(3).isDone()) {
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

				if (futures.get(2).isDone() == true) {
					panelTX.remove(output);
					panelTX.add(tabPaneTX, BorderLayout.CENTER);
					tabPaneTX.addTab(command, p5);
					//tabPaneTX.addTab("psxview", p6);
					panelTX.add(tabPaneTX);
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

		/**
		 * Table Selection
		 */
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
						int modelClm=table.convertColumnIndexToModel(clm);
						int modelRow = table
								.convertRowIndexToModel(selectedRow);
						table.isCellEditable(modelRow, modelClm);
                        //Call method and pass row, Column
						
							selectCell(modelRow, modelClm);
						
					}

				}

			});

		} else {
			table.setRowSelectionAllowed(false);
		}

		/**
		 * Once the user clicks the Config button a message box will
		 * be displayed asking to close the VolaTile window to go back to the 
		 * configuration window.
		 */
		confButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "Yes", "No" };
				int i = JOptionPane
						.showOptionDialog(
								frame,
								"Are you sure? This will close the current window and open the configuration window.",
								"", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (i == 0) {
					frame.dispose();
					Configuration config = new Configuration();
					config.addProfiles();
					config.selectFile();
					// String s=File.separator;
					// config.readPath(volPath+s+vol);
				}
			}
		});

	}
	public void selectCell(int row,int column){
		
		switch (column) {
		case 0:
		case 1: {
			column= 2;
		}
		case 2: {
			tabPane.getSelectedComponent();
			data = table.getModel().getValueAt(row,column);
			String PID = data.toString();
			callProcesses(PID);

			break;
		}
		case 3: {
			tabPane.getSelectedComponent();
			data = table.getModel().getValueAt(row,column);
			String PID = data.toString();
			callProcesses(PID);
			break;
		}
		
		/**
		 * Threads
		 */
		case 4: {
			tabPane.setSelectedComponent(p3);
			column= 2;

			data = table.getModel().getValueAt(row, column);
			String PID = data.toString();
			callProcesses(PID);
			break;
		}
		
		/**
		 * Handles
		 */		
		case 5: {
			tabPane.setSelectedComponent(p4);
			column= 2;
			data = table.getModel().getValueAt(row, column);
			String PID = data.toString();
			callProcesses(PID);
			break;
		}
		case 6:{
			column= 2;
			tabPane.getSelectedComponent();
			data = table.getModel().getValueAt(row,column);
			String PID = data.toString();
			callProcesses(PID);
			break;
		}
		case 7:{
			column= 2;
			tabPane.getSelectedComponent();
			data = table.getModel().getValueAt(row,column);
			String PID = data.toString();
			callProcesses(PID);
			break;
		}
		case 8:{
			column= 2;
			tabPane.getSelectedComponent();
			data = table.getModel().getValueAt(row,column);
			String PID = data.toString();
			callProcesses(PID);
			break;
		}
		case 9:{
			column= 2;
			tabPane.getSelectedComponent();
			data = table.getModel().getValueAt(row,column);
			String PID = data.toString();
			callProcesses(PID);
			break;
		}
		case 10:{
			column= 2;
			tabPane.getSelectedComponent();
			data = table.getModel().getValueAt(row,column);
			String PID = data.toString();
			callProcesses(PID);
			break;
		}
		default: {
			System.out.println("No rows are selected");
		}

		}
		
	}
	
	public void callProcesses(String PID){
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
			
		} 
		
		catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(
					confButton,
					"File not found"
							+ e1.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					confButton,
					"General I/O Exception"
							+ e.getMessage());
			e.printStackTrace();
		}
	}

	/** 
	 * Create Table Default Rows and Columns Format specified Format table
	 * with Set attributes
	 */	
	public void createTable() {
		
		/** 
	     * Creating Tables and modeling them
		 */
		tModel = new DefaultTableModel(){
			
			/**
			 * The following method is used to make cells non-editable for the given table model
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};
		TableRowSorter<TableModel> trs = new TableRowSorter<TableModel>(tModel);
		trs.addRowSorterListener(table);
		table = new JTable(tModel);
		table.setRowSorter(trs);
		table.setBackground(Color.DARK_GRAY);
		trs.setSortsOnUpdates(true);

		/**
		 * Table Row Settings
		 */
		table.setRowHeight(22);
		table.setModel(tModel);
		table.setAutoCreateRowSorter(true);
		tModel.setColumnIdentifiers(columnTitles);
		
	}

	/**
	 * Read file and return read file object
	 */	
	public BufferedReader readFile(String volPath) {
		FileReader r = null;
		try {
			String s = File.separator;
			r = new FileReader(volPath + s + command + ".txt");
		} catch (FileNotFoundException e1) {
			Component frame = null;
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frame, "File not found");
			e1.printStackTrace();
		}
		return rf = new BufferedReader(r);
	}

	/** 
	 * Default function call for switch statement..
	 */
	public void readRows(BufferedReader rf) {
		String line = null;
		String[] splits;
		String[] clm = null;

		int i = 0;
		int j = 0;

		try {
			while ((line = rf.readLine()) != null) {
				i++;
				if (i > 2) {
					splits = line.split("\\s+");
					if (splits.length > 8) {
						StringBuilder builder = new StringBuilder();
						builder.append(splits[8]);
						builder.append(" ");
						builder.append(splits[9]);
						builder.append(" ");
						builder.append(splits[10]);
						splits[8] = builder.toString();
					}
					tModel.addRow(splits);
				} else if (!line.contains("--") && i<2) {
					clm = line.split("\\s+");
					for (j = 0; j < (clm.length - 1); j++) {
		               
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

	/** 
	 * Inner class used for cell renderring of the table
	 */
	public class MyTableCellRenderer extends DefaultTableCellRenderer implements
			TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final Color alt2 = new Color(249, 246, 244);
		// private final Color alt2 = new Color(235, 244, 250);
		private final Color alt1 = Color.WHITE;
		private final Color invalidStatus = Color.RED;
		private final Color foreGround = new Color(61, 38, 57);
		private final Color backGround = new Color(161, 138, 157);

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
				
				table.setSelectionForeground(foreGround);
				table.setSelectionBackground(backGround);
				
			} else {

				table.setSelectionBackground(backGround);
			}
			setFont(table.getFont());
			setValue(value);

			if (o != null) {
				s = o.toString();
				if ((s.equalsIgnoreCase("UNKNOWN") || s
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
