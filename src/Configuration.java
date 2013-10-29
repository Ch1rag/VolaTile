import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

public class Configuration {
	private JFrame frame;
	private JFileChooser selectFile;
	private JButton selectDump;
	private JButton volButton;
	private String fileName;
	private String vol;
	private JButton openGUI;
	private String os;
	private Group group;
	private JButton profileButton;
	private JComboBox osCombo;
	private List<String> osProfiles = new ArrayList<String>();
	private JTextArea text;;
	private String version;
	private String arch;
	private String profile;
	private String path;
	private JFileChooser selectVol;
	private String volPath;
	private JTextField box1;
	private JTextField box2;
	


	public Configuration() {
		os = System.getProperties().getProperty("os.name");
		version = System.getProperties().getProperty("os.version");
		arch = System.getProperties().getProperty("os.arch");
	}

	public void addProfiles() {
		osProfiles.add("WinXPSP2x86");
		osProfiles.add("Mac10_8_4_64bitx64");
	}

	public void selectFile() {

		frame = new JFrame();
		final Container cp = frame.getContentPane();

		cp.setLayout(new BorderLayout());
		final JPanel p1 = new JPanel(new GridLayout(2, 2));
		JPanel p2 = new JPanel(new GridLayout(1, 1));
		final JPanel p3 = new JPanel(new GridLayout(1, 1));

		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p2.setBorder(new TitledBorder(new EtchedBorder(), "Profile Info"));
		p1.setBorder(new TitledBorder(new EtchedBorder(), "Profile Selection"));
		p3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		p3.setBorder(new TitledBorder(new EtchedBorder(), ""));

		selectDump = new JButton("Select Image");
		volButton = new JButton("vol.py");
		openGUI = new JButton("Open VolaTile");
		profileButton = new JButton("Detect Profile");
		osCombo = new JComboBox();
		box1=new JTextField();
		box2=new JTextField();
		box1.setEditable(false);
		box2.setEditable(false);


		java.util.Iterator<String> iterCmd = osProfiles.iterator();
		while (iterCmd.hasNext()) {
			osCombo.addItem(iterCmd.next());
		}

		text = new JTextArea(10, 20);
		text.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		text.setBorder(new TitledBorder(new EtchedBorder(), ""));

		text.setEditable(false);
		text.setLineWrap(true);
		text.getCaretPosition();
		text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));

		p1.add(selectDump, FlowLayout.LEFT);
		p1.add(box2,FlowLayout.LEFT);
		p1.add(volButton, FlowLayout.LEFT);
		p1.add(box1,FlowLayout.LEFT);
		p1.add(osCombo, FlowLayout.RIGHT);
	
		p2.add(text, BorderLayout.NORTH);

		p3.add(openGUI, BorderLayout.EAST);
		p3.add(profileButton, BorderLayout.WEST);

		cp.add(p1, BorderLayout.NORTH);
		cp.add(p2, BorderLayout.CENTER);
		cp.add(p3, BorderLayout.SOUTH);

		openGUI.setVisible(false);

		text.setVisible(true);
		osCombo.setVisible(true);
		p3.setVisible(false);

		/*text.append("Detected OS:" + "\t" + os + "\n");
		text.append("Version    :" + "\t" + version + "\n");
		text.append("OS Arch    :" + "\t" + arch + "\n");*/

		text.setText("Select vol.py file");
		
		selectFile = new JFileChooser();
		selectVol = new JFileChooser();

		// selectFile.setCurrentDirectory(new File(user+"/volatility"));
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
						text.setText("volatility python filename:" + vol + "\n");
						selectDump.setVisible(true);
						
					} else {
						JOptionPane
						.showMessageDialog(frame,
								"File vol.py not found, please select the correct file");

					}

				}
			}
		});

		selectDump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = selectFile.showOpenDialog(selectDump);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					path = selectFile.getSelectedFile().getParent();
					box2.setText(path);

					// selectFile.setCurrentDirectory(new File(path));
					File file = selectFile.getSelectedFile();
                    
					fileName = file.getAbsolutePath();
					text.append("\n"+"Selected dump file is:" + file.getName());
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
				new ImageInfo(fileName, path, vol);
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
						System.out.println(profile);
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

		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				text.setVisible(true);
				text.setText("Profile detection may take some time,Please wait..");
				worker.execute();

			}
		});

		openGUI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profile = osCombo.getSelectedItem().toString();
                
				try{
					if(fileName!=null && profile!=null && vol!=null && volPath!=null){	
						GUI gui = new GUI(fileName, profile, vol, volPath);
						gui.storeProfile(profile);//last change
						gui.storeCmd_Mac();
						gui.storeCmd_Win();
						gui.makeFrame();		
						frame.dispose();
					}
				}
				catch(Exception e1){
					JOptionPane.showMessageDialog(frame,"Please select all attributes:"
				+"\n"+"Selected RAM dump:>"+fileName
				+"\n"+"Selected profile:>" +profile
				+"\n"+"Selected vol file:>"+vol
				+"\n"+"Selected volatility path:>"+volPath
				+"\n"+e1.getMessage());
	
				}

			}
		});

		// Set frame elements
		frame.setTitle("Configuration");
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
	}

	public void readFile() throws IOException {

		FileReader r = null;
		BufferedReader br = null;
		String s=File.separator;
		try {
			r = new FileReader(volPath+s+"imageinfo.txt");
			 br= new BufferedReader(r);
			String line;
			String found="Win";
            int i=0;
			while ((line = br.readLine()) != null) {
				if(i==0 && line.startsWith(found)==true)
				{
				  text.append("Found:"+line);
				}
				 text.append(line + "\n");
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();

		}
		finally{
			br.close();
		}

	}
	public void readPath(String volPath){
		box1.setText(volPath);
	}
	
	

}
