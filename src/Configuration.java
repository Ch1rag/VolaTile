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
	private String profile;

	public Configuration(){
		os=System.getProperties().getProperty("os.name");
		user=System.getProperties().getProperty("user.home");
		version=System.getProperties().getProperty("os.version");
		arch=System.getProperties().getProperty("os.arch");

	}
	public void addOs(){
		osProfiles.add("WinXPSP2x86");
		osProfiles.add("Mac10_8_4_64bitx64");
	}


	public void selectFile(){

		frame=new JFrame();
		final Container cp = frame.getContentPane();

		cp.setLayout(new BorderLayout());
		final JPanel p1=new JPanel(new GridLayout());
		JPanel p2=new JPanel(new GridLayout(1,1));
		final JPanel p3=new JPanel(new GridLayout(1,1));

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

		text = new JTextArea(10,20);
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
		p3.setVisible(false);

		text.append("Detected OS:"+"\t"+os+"\n");
		text.append("Version    :"+"\t"+version+"\n");
		text.append("OS Arch    :"+"\t"+arch+"\n");
		


		selectFile=new JFileChooser();

		selectFile.setCurrentDirectory(new File(user+"/volatility"));

		selectDump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = selectFile.showOpenDialog(selectDump);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = selectFile.getSelectedFile();
					fileName=file.getName();
					text.append("Selected dump file is:"+file.getName());
					p3.setVisible(true);
					profileButton.setVisible(true);
					openGUI.setVisible(true);
				}
			}
		});
		final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			private Void future;
			@Override
			public Void doInBackground() throws InterruptedException, IOException {
				new ImageInfo(fileName);
				text.setText("");
				text.setVisible(true);
				text.setBorder(new TitledBorder(new EtchedBorder(), "Detected Profile"));
                
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
		
		final SwingWorker<Future, Void> worker1 = new SwingWorker<Future, Void>() {
			private Future<?> future;
			@Override
			public Future<?> doInBackground() throws InterruptedException {
				p1.setVisible(false);
				profileButton.setVisible(false);
				text.setText("VolaTile is loading..Please wait...");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				selectDump.setVisible(false);
				profile=osCombo.getSelectedItem().toString();

				Connections con=new Connections(fileName,profile, user);
				Thread conThread = new Thread(con);
				
				Handles hnd=new Handles(fileName,profile, user);
				Thread hndThread = new Thread(hnd);
				
				Sockets soc=new Sockets(fileName,profile, user);
				Thread socThread = new Thread(soc);
				
				Threads thd=new Threads(fileName,profile, user);
				Thread thdThread = new Thread(thd);
				
				ThreadExecutor te=new ThreadExecutor(conThread,hndThread,socThread,thdThread);	
				
				try {
					future=te.executor();	
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return future;
			}

			@Override
			public void done() {

				try {
					if(future.get()==null){
						gui();
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
				
               worker1.execute();
			}	
		});
		
		// Set frame elements
		frame.setTitle("Configuration");
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void gui(){

		GUI gui=new GUI(fileName,profile,user);
		gui.storeOS(profile);
		gui.storeCmd_Mac();
		gui.storeCmd_Win();
		gui.makeFrame();	
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
