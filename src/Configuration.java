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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;


public class Configuration{
	private JFrame frame;
	private JFileChooser selectFile;
	private JButton selectDump;
	private String fileName;
	private JButton openGUI;
	private String os;
	private JButton profileButton;
	private JComboBox osCombo;
	private List<String> osProfiles=new ArrayList<String>(); 
	private JTextArea text;
	private String user;
	private String version;
	private String arch;

	public Configuration(){
		os=System.getProperties().getProperty("os.name");
		user=System.getProperties().getProperty("user.home");
		version=System.getProperties().getProperty("os.version");
		arch=System.getProperties().getProperty("os.arch");
			
	}
	public void addOs(){
		osProfiles.add("WinXPSP2x86");
		osProfiles.add("Mac");
	}
	
	
	public void selectFile(){
		
		frame=new JFrame();
		final Container cp = frame.getContentPane();
		
		cp.setLayout(new BorderLayout());
		JPanel p1=new JPanel(new GridLayout(1,1));
		JPanel p2=new JPanel(new GridLayout(1,1));
		JPanel p3=new JPanel(new GridLayout(1,1));
		
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p2.setBorder(new TitledBorder(new EtchedBorder(), "Profile Info"));
		p1.setBorder(new TitledBorder(new EtchedBorder(),"Profile Selection"));
		p3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		p3.setBorder(new TitledBorder(new EtchedBorder(),""));

		selectDump=new JButton("Select Image");
		openGUI=new JButton("Open VolaTile");
		profileButton=new JButton("Detect Profile");
		osCombo=new JComboBox();
		
		
		java.util.Iterator<String> iterCmd = osProfiles.iterator();
		while (iterCmd.hasNext()) {
			osCombo.addItem(iterCmd.next());
		}
		
		text = new JTextArea(5,5);
		text.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		text.setBorder(new TitledBorder(new EtchedBorder(), "System Info"));
		
		text.setEditable(false);
		text.setLineWrap(true);
		text.getCaretPosition();
		text.setFont(new Font("Monaco", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		
		
		p1.add(selectDump,FlowLayout.LEFT);
		p1.add(osCombo,FlowLayout.LEFT);
		
		p2.add(text,BorderLayout.NORTH);
		
		p3.add(openGUI,BorderLayout.EAST);
		p3.add(profileButton,BorderLayout.WEST);
		
		cp.add(p1,BorderLayout.NORTH);
		cp.add(p2,BorderLayout.CENTER);
		cp.add(p3,BorderLayout.SOUTH);
        
		openGUI.setVisible(false);
		profileButton.setVisible(false);
		text.setVisible(true);
		osCombo.setVisible(true);

		text.append("Detected OS:"+os+"\n");
		text.append("Version:"+version+"\n");
		text.append("OS Arch:"+arch+"\n");
		text.append("Current User:"+user+"\n");
		
		
		selectFile=new JFileChooser();
	
		selectFile.setCurrentDirectory(new File(user+"/volatility"));

		selectDump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = selectFile.showOpenDialog(selectDump);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = selectFile.getSelectedFile();
					fileName=file.getName();
					text.setText("Selected dump file is:"+file.getName());
					profileButton.setVisible(true);
					openGUI.setVisible(true);
				}
			}
		});
		final SwingWorker worker = new SwingWorker<Void, Void>() {
			   private Void future;
				@Override
				public Void doInBackground() throws InterruptedException {
					new ImageInfo(fileName);
					text.setText("");
					text.setVisible(true);
					text.setBorder(new TitledBorder(new EtchedBorder(), "Detected Profile"));
					text = new JTextArea(20,50);
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
						if(get()==null){
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

		
		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				text.setVisible(true);
				worker.execute();
				}
		});
		
		openGUI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDump.setVisible(false);
				String profile=osCombo.getSelectedItem().toString();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				GUI frame=new GUI(fileName,profile,user);
				frame.storeOS(os);
				frame.storeCmd_Mac();
				frame.storeCmd_Win();
				frame.makeFrame();	
				
			}	
		});
		// Set frame elements
		frame.setTitle("Configuration");
		frame.setSize(100, 100);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void readFile() throws IOException {

		FileReader r = null;
		try {
			r = new FileReader("/Users/chiragbarot/volatility/imageinfo.txt");
			BufferedReader br = new BufferedReader(r);
			String line;
			
			while ((line = br.readLine()) != null) {
				text.append(line+"\n");
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();

		}
     

	}

	
}
