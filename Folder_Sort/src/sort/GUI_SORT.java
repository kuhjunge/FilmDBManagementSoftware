package sort;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class GUI_SORT extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final choose lib = new choose();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
{
		EventQueue.invokeLater(new Runnable() 
	{
			public void run()
			{
				try 
				{
					GUI_SORT frame = new GUI_SORT();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
	});
}

	/**
	 * Create the frame.
	 */
	public GUI_SORT() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblKomplexeErstellungEiner = new JLabel("Komplexe Erstellung einer Filmliste!");
		lblKomplexeErstellungEiner.setBounds(130, 15, 221, 14);
		contentPane.add(lblKomplexeErstellungEiner);
		
		JButton btnSaveToCsv = new JButton("Save to CSV");
		JButton btnRename = new JButton("Rename");
		JButton btnInformation = new JButton("Information");
		JButton btnQuit = new JButton("Quit");
				
		
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Nur Ordner auswählbar
        int returnVal = fc.showOpenDialog(null);
        final File f;
        if (returnVal == JFileChooser.APPROVE_OPTION) 	//Wenn gewählt, dann
        {
			f = fc.getSelectedFile();	//Verzeichnis Holen
			final File f1 = new File(f.getPath());	//In File speichern
			
			
				//SaveToCsv Button
				btnSaveToCsv.addMouseListener(new MouseAdapter() 
				{
					@Override
					public void mouseClicked(MouseEvent arg0) 
					{
						lib.writeCSV(f); // Schreibt die CSV Datei
					}
				});
		
				btnSaveToCsv.setBounds(471, 30, 103, 23);
				contentPane.add(btnSaveToCsv);
		
				
				//Rename Button
				btnRename.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						
					}
				});
				btnRename.setBounds(471, 210, 103, 23);
				contentPane.add(btnRename);
		
		
				//Information Button
				btnInformation.addMouseListener(new MouseAdapter() 
				{
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						
					}
				});
				btnInformation.setBounds(471, 120, 103, 23);
				contentPane.add(btnInformation);
		
		
				//Quit Button
				btnQuit.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						System.exit(0); 
					}
				});
				btnQuit.setBounds(471, 305, 103, 23);
				contentPane.add(btnQuit);
		

				//JList
				DefaultListModel<String> listModel = new DefaultListModel<String>();
				JList <String> list = new JList <String>(listModel);
				list.setBounds(30, 50, 410, 278);
				
				
				File[] files = f1.listFiles();
				if (files != null) 
				{ // Erforderliche Berechtigungen etc. sind vorhanden
					for (int i = 0; i < files.length; i++) 
					{
						if (files[i].isDirectory()) 
						{
							listModel.addElement(files[i].getName());
						}
					}
				}
				contentPane.add(list);
        }
	}
}
