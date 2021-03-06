import java.io.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
<<<<<<< HEAD
<<<<<<< HEAD
 * Class for displaying open handles
 * <p>
 * This class is resposible for running the volatility command to list open
 * handles in the RAM image. Because volatility takes considerable time to run
 * each command, this command is run only once. The output is stored in a Text
 * file which is recalled every time a user requests the list of opened handles,
 * using the provided tab in the GUI
 * 
 * @author Chirag Barot
 * @version 1.0
 */
public class Configuration {
=======
 * Class for Configuration
 * <p> 
 * This class manages variables used by Volatility. 
 * 
 * @author      Chirag Barot, Anthony Nowlan
 * @version     1.1
 * 20131107 Added save state to variables. -AN
 * 20131106 Original Release.
 *
 */
 public class Configuration implements Serializable {
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
=======
 * Class for Configuration
 * <p> 
 * This class manages variables used by Volatility. 
 * 
 * @author      Chirag Barot, Anthony Nowlan
 * @version     1.1
 * 20131107 Added save state to variables. -AN
 * 20131106 Original Release.
 *
 */
 public class Configuration implements Serializable {
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
	private JFrame frame;
	private JFileChooser selectFile;
	private JButton selectDump;
	private JButton volButton;
	private String fileName;
	private String vol;
	private JButton openGUI;
	private JButton profileButton;
	private JComboBox osCombo;
	private List<String> osProfiles = new ArrayList<String>();
	private JTextArea text;;
<<<<<<< HEAD
<<<<<<< HEAD
	private String profile;
=======
	private String profile; //
	private String path;	//
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
=======
	private String profile; //
	private String path;	//
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
	private JFileChooser selectVol;
	private String volPath; // 
	private JTextField box1;
	private JTextField box2;
<<<<<<< HEAD
<<<<<<< HEAD

	/**
	 * Adding the OS (profile) of the memory dumps into an array named
	 * addProfile
	 */
=======
=======
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
	private File file;
	private JPanel p3;
	
	 /**
      * Adding the OS (profile) of the memory dumps into an array named addProfile
      */
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
	public void addProfiles() {
		osProfiles.add("WinXPSP2x86");
		// osProfiles.add("Mac10_8_4_64bitx64");
	}

	/**
	 * This method is used to select the vol.py file and memory dump file. Also
	 * retrieving the path of the vol.py file and memory dump file is being done
	 * here.
	 * <p>
	 * Setting up a JFrame in order, for the user to select the vol.py file and
	 * the memory dump file.
	 * <p>
	 * Setting up an action listener for the volButton and the selectDump, this
	 * is done to handle the events of the volButton and the selectDump. Such as
	 * opening up a dialog box to browse into vol.py file and memory dump file.
	 */
	public void selectFile() {

		/**
		 * Creating a new JFrame object to setting up the borders of the layout.
		 */
		frame = new JFrame();
		final Container cp = frame.getContentPane();

		/**
		 * Creating the JPanels and its objects
		 */
		cp.setLayout(new BorderLayout());
		final JPanel p1 = new JPanel(new GridLayout(2, 2));
		JPanel p2 = new JPanel(new GridLayout(1, 1));
		// final JPanel p3 = new JPanel(new GridLayout(1, 1));
		p3 = new JPanel(new GridLayout(1, 1));

		/**
		 * Setting up the borders of the JPanels.
		 */
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p2.setBorder(new TitledBorder(new EtchedBorder(), "Profile Info"));
		p1.setBorder(new TitledBorder(new EtchedBorder(), "Profile Selection"));
		p3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		p3.setBorder(new TitledBorder(new EtchedBorder(), ""));

		/**
		 * Implementation of JButtons for the JPanel.
		 */
		selectDump = new JButton("Select RAM Dump");
		volButton = new JButton("Volatility Script(vol.py)");
		openGUI = new JButton("Open VolaTile");
		profileButton = new JButton("Detect Profile");
		osCombo = new JComboBox();
		box1 = new JTextField();
		box2 = new JTextField();
		box1.setEditable(false);
		box2.setEditable(false);

		/**
		 * Read from the osProfile array and repeat until end of array.
		 */
		java.util.Iterator<String> iterCmd = osProfiles.iterator();
		while (iterCmd.hasNext()) {
			osCombo.addItem(iterCmd.next());
		}

		/**
		 * Implementation of a new Text area for the system information to be
		 * diaplayed.
		 */
		text = new JTextArea(10, 20);
		text.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		text.setBorder(new TitledBorder(new EtchedBorder(), ""));

		/**
		 * Disabling the text area from editing, setting the allignment and
		 * setting the font.
		 */
		text.setEditable(false);
		text.setLineWrap(true);
		text.getCaretPosition();
		text.setFont(new Font("Courier", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		/**
		 * Adding the Text boxes, Buttons and the Combo Box inside the p1
		 * (Profile Selection) JPanel.
		 */
		p1.add(selectDump, FlowLayout.LEFT);
		p1.add(box2, FlowLayout.LEFT);
		p1.add(volButton, FlowLayout.LEFT);
		p1.add(box1, FlowLayout.LEFT);
		p1.add(osCombo, FlowLayout.RIGHT);

		/**
		 * JPanel2 (Profile Info) is added. In GUI the user can see the selected
		 * profile information in this area. Also it displays messages when the
		 * vol.py and memory dump files are selected.
		 */
		p2.add(text, BorderLayout.NORTH);

		/**
		 * JPanel3, Two buttons Open Volatile and Detect Profile is added into
		 * this JPanel.
		 */
		p3.add(openGUI, BorderLayout.EAST);
		p3.add(profileButton, BorderLayout.WEST);

		/**
		 * Putting the Profile Selection (p1) on top, Profile Info(p2) on the
		 * middle and the third panel on the bottom of the configuration window.
		 */
		cp.add(p1, BorderLayout.NORTH);
		cp.add(p2, BorderLayout.CENTER);
		cp.add(p3, BorderLayout.SOUTH);

		/**
		 * Disable the Open Volatile Button.
		 */
		openGUI.setVisible(false);
		text.setVisible(true);
		osCombo.setVisible(true);
		p3.setVisible(false);

		text.setText("Select vol.py file");
		selectFile = new JFileChooser();
		selectVol = new JFileChooser();

		/**
		 * Setting up an Action Listener for the Volatility Script(vol.py).
		 * <p>
		 * Check whether the correct vol.py file has been chosen or not
		 */
		volButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = selectVol.showOpenDialog(volButton);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					volPath = selectVol.getSelectedFile().getParent();
					box1.setText(volPath);
					selectFile.setCurrentDirectory(new File(volPath));

					File volFile = selectVol.getSelectedFile();
					vol = volFile.getName();

					if (vol.matches("vol.py") == true) {
						text.setText("Selected Volatility script:" + vol + "\n");
						selectDump.setVisible(true);

					} else {
						JOptionPane
								.showMessageDialog(frame,
										"File vol.py not found, please select the correct file");

					}

				}
			}
		});

		/**
		 * Setting up an Action Listener for the Select RAM Dump button.
		 * <p>
		 * Check whether the correct memory dump file has been chosen or not
		 */
		selectDump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = selectFile.showOpenDialog(selectDump);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
<<<<<<< HEAD
					File file = selectFile.getSelectedFile();
=======
					path = selectFile.getSelectedFile().getParent();
					
					file = selectFile.getSelectedFile();
                    
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
					fileName = file.getAbsolutePath();
					box2.setText(fileName);
					text.setText("");
					text.setText("\n" + "Selected Volatility script:" + vol
							+ "\n" + "Selected RAM dump:" + file.getName());
					p3.setVisible(true);
					profileButton.setVisible(true);
					openGUI.setVisible(true);
				}
			}
		});

		final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			private Void future;

			@Override
			public Void doInBackground() throws InterruptedException,
					IOException {
				new ImageInfo(fileName, volPath, vol);
				text.setText("");
				text.setVisible(true);
				text.setBorder(new TitledBorder(new EtchedBorder(),
						"Detected Profile"));

				try {
					readFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return future;
			}

			@Override
			public void done() {

				try {
					if (get() == null) {
						osCombo.setVisible(true);
						openGUI.setVisible(true);
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};

		/**
		 * Setting up an Action Listener for the Detect Profile Button
		 * <p>
		 * Clicking on Detect Profile button will display the imageinfo of the
		 * chosen memory dump file
		 */
		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				text.setVisible(true);
				text.setText("Profile detection may take some time,Please wait..");
				worker.execute();

			}
		});

		/**
		 * Setting up an Action Listener for the Open VolaTile Button
		 * <p>
		 * Clicking on Open VolTile button will display the pslist information
		 * on a seperate window.
		 */
		openGUI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profile = osCombo.getSelectedItem().toString();
<<<<<<< HEAD

				try {
					if (fileName != null && profile != null && vol != null
							&& volPath != null) {
=======
                
				try{
					if(fileName!=null && profile!=null && vol!=null && volPath!=null){	
						
						saveConfig(fileName, profile, vol, volPath);
						
<<<<<<< HEAD
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
=======
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
						GUI gui = new GUI(fileName, profile, vol, volPath);
						gui.storeProfile(profile);
						gui.storeCmd_Mac();
						gui.storeCmd_Win();
						gui.makeFrame();
						frame.dispose();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame,
							"Please select all attributes:" + "\n"
									+ "Selected RAM dump:>" + fileName + "\n"
									+ "Selected profile:>" + profile + "\n"
									+ "Selected vol file:>" + vol + "\n"
									+ "Selected volatility path:>" + volPath
									+ "\n" + e1.getMessage());

				}

			}
		});

		loadConfig(); 
		/**
		 * Setting up the Frame elements for the Configuration Window.
		 */
		frame.setTitle("Configuration");
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * This method is used to read from file.
	 * 
	 * @exception IOException
	 *                if the file doesn't exist it will throw an exception
	 */
	public void readFile() throws IOException {

		FileReader r = null;
		BufferedReader br = null;
		String s = File.separator;
		try {
			r = new FileReader(volPath + s + "imageinfo.txt");
			br = new BufferedReader(r);
			String line;

			while ((line = br.readLine()) != null) {

				text.append(line + "\n");
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showConfirmDialog(frame, "File not found");

		} finally {
			br.close();
		}

	}
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
	
	/**
	 * saveConfig
	 * <p>
	 * @param fn filename we are using
	 * @param prof profile we are using
	 * @param vola is the executable name of volatility
	 * @param volpath is the folder where volatility is installed.
	 *
	 * @author Anthony Nowlan
	 * 20131107
	 */
		
	private void saveConfig(String fn, String prof, String vola, String volpath)
	{
		// save the important parameters to file.
		try (
		  OutputStream save = new FileOutputStream("config.ser");
		  OutputStream buffer = new BufferedOutputStream(save);
		  ObjectOutput output = new ObjectOutputStream(buffer);
		){
			System.out.println("Saving parameters to config.ser... ");
			output.writeObject(fn);
			output.writeObject(prof);
			output.writeObject(vola);
			output.writeObject(volpath);
		}  
		catch(IOException ex)
		{
			// expect the unexpected.
			System.err.println("saveConfig generated an exception "+ex.getMessage());
		}
	}
	
	
	/**
	 * loadConfig
	 * <p>
	 * loads config details which were saved previously to config.ser
	 *
	 * @author Anthony Nowlan
	 * 20131107
	 */
	private void loadConfig()
	{
	//deserialize the save file
		try(
		  InputStream save = new FileInputStream("config.ser");
		  InputStream buffer = new BufferedInputStream(save);
		  ObjectInput input = new ObjectInputStream (buffer);
		){
		  //deserialize from file
		  fileName = (String)input.readObject();
		  profile = (String)input.readObject();
		  vol = (String)input.readObject();
		  volPath = (String)input.readObject();
		  
		  //display data in log window
		  System.out.println("Filename:"+fileName);
		  System.out.println("Profile:"+profile);
		  System.out.println("Volatility:"+vol);
		  System.out.println("VolPath:"+volPath);
		  
		  // show details in main window. Gets over written once buttons enable.
		  text.append("\nLoaded Config\n");
		  text.append(fileName+"\n");
		  text.append(profile+"\n");
		  text.append(vol+"\n");
		  text.append(volPath+"\n");
		  
			// show the loaded volatility path in text field.
			box1.setText(volPath);
			selectFile.setCurrentDirectory(new File(volPath));
			text.setText("Selected Volatility script:" + vol + "\n");
			selectDump.setVisible(true);
			
			// show the loaded memory file name in text field.
			box2.setText(fileName);
			text.setText("");
			text.setText("\n"+"Selected Volatility script:" + vol +"\n"+"Selected RAM dump:" + fileName);
			
			// ENABLE BOXES
			p3.setVisible(true);
			profileButton.setVisible(true);
			openGUI.setVisible(true);

		}
		catch(FileNotFoundException ex)
		{
			System.out.println("No config save found");	// if no save found, continue without a fuss.
		}
		catch(ClassNotFoundException ex){
		  System.out.println("loadConfig Class not found " + ex.getMessage());	// will trigger if save is wrong format.
		}
		catch(IOException ex){
		  System.out.println("loadConfig generated an IOException " + ex.getMessage());	// something bad happened.
		}
		
	
	}
		
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185

}
