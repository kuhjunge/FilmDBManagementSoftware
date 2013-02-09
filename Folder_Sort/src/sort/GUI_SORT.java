package sort;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class GUI_SORT extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final sort_class lib = new sort_class();

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
	//Filter Anzahl MKV Dateien
	public class FilterFilesmkv implements FileFilter { 
		  public boolean accept(File file) { 
		     return !file.isDirectory() && file.getName().endsWith (".mkv"); 
		  } 
		}
	//Filter Anzahl nfo Dateien
	public class FilterFilesnfo implements FileFilter { 
		  public boolean accept(File file) { 
		     return !file.isDirectory() && file.getName().endsWith (".nfo"); 
		  } 
		}
	//Filter Subs vorhanden
	public class FilterFilessub implements FileFilter { 
		  public boolean accept(File file) { 
		     return !file.isDirectory() && (file.getName().endsWith (".srt") || file.getName().endsWith (".sub") || file.getName().endsWith (".idx") || file.getName().endsWith (".ssa") || file.getName().endsWith (".ass")); 
		  } 
		}

	/**
	 * Create the frame.
	 * @return 
	 */
	public GUI_SORT() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblKomplexeErstellungEiner = new JLabel("Komplexe Erstellung einer Filmliste!");
		lblKomplexeErstellungEiner.setBounds(130, 15, 221, 14);
		contentPane.add(lblKomplexeErstellungEiner);
		
		
		JButton btnSaveToCsv = new JButton("Save to CSV");
		JButton btnRename = new JButton("Rename");
		JButton btnInformation = new JButton("TechInfo");
		JButton btnGetXML = new JButton("Info");
		JButton btnQuit = new JButton("Quit");
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(99, 50, 276, 278);
		contentPane.add(scrollPane);
		
		final JLabel lblTitel = new JLabel("Titel:");
		lblTitel.setBounds(22, 332, 402, 16);
		contentPane.add(lblTitel);
		
		final JLabel lblGenre = new JLabel("Genre:");
		lblGenre.setBounds(22, 350, 402, 19);
		contentPane.add(lblGenre);
		
		final JLabel lblPlot = new JLabel("Plot:");
		lblPlot.setBounds(22, 372, 548, 16);
		contentPane.add(lblPlot);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String data =(String) comboBox.getSelectedItem();
				Movie Film = new Movie();
				try 
				{
					if (data != null)
					{
						Film.loadIMDB(data);
						lblTitel.setText("Titel: " +  Film.gettitle());
						lblGenre.setText("Genre: " +  Film.getgenre());
						lblPlot.setText("Plot: " +  Film.getplot());
					}
					else {
						lblTitel.setText("Titel: (Wurde nicht erkannt)");
						lblGenre.setText("Genre: ?");
						lblPlot.setText("Plot: ?");
					}
				}
				catch (Exception e)
				{
					lblTitel.setText("Titel: (Wurde nicht erkannt)");
					lblGenre.setText("Genre: ?");
					lblPlot.setText("Plot: ?");
				}
			}
		});
		comboBox.setBounds(469, 329, 90, 22);
		contentPane.add(comboBox);
		
		final DefaultListModel<String> listModel = new DefaultListModel<String>();
		final DefaultComboBoxModel<String> imdbListModel = new DefaultComboBoxModel<String>();
		//final DefaultListModel<String> imdbListModel = new DefaultListModel<String>();
		final JList <String> imdbList = new JList <String>(imdbListModel);
		final JList <String> list = new JList <String>(listModel);
		
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("C:\\"));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Nur Ordner auswählbar
        int returnVal = fc.showOpenDialog(null);
        final File f;
        if (returnVal == JFileChooser.APPROVE_OPTION) 	//Wenn gewählt, dann
        {
			f = fc.getSelectedFile();	//Verzeichnis Holen
			final File f1 = new File(f.getPath());	//In File speichern
			
			
				//SaveToCsv Button
				btnSaveToCsv.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						lib.writeCSV(f); // Schreibt die CSV Datei
					}
				});
		
				btnSaveToCsv.setBounds(471, 30, 103, 23);
				contentPane.add(btnSaveToCsv);
		
				
				//Rename Button
				btnRename.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						//Angeklickte Datei mit PFad auslesen
				        File f2 = new File(f.getPath() + "\\" + list.getSelectedValue());
				        //Eingabefeld
				        String titel = JOptionPane.showInputDialog("Geben sie den neuen Dateinamen ein: ", list.getSelectedValue());
				        //Rename Eingegebener neuer Titel
						f2.renameTo(new File(f.getPath() + "\\" + titel));
						//JList leeren
						listModel.clear();
						
						//JList neu generieren
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
					}
				});
				btnRename.setBounds(471, 210, 103, 23);
				contentPane.add(btnRename);
		
					
				btnInformation.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
				        String subvorhanden = null;
						//Angeklickte Datei auslesen und in Message Dialog
						list.getSelectedValue();
						// Filter Anzahl mkv Dateien
						int anzmkvfiles = new File(f.getPath() + "\\" + list.getSelectedValue()).listFiles(new FilterFilesmkv ()).length;
						//Filter Anzahl nfo Dateien
						int anznfofiles = new File(f.getPath() + "\\" + list.getSelectedValue()).listFiles(new FilterFilesnfo ()).length;
						//Filter Subs vorhanden
						int anzsubfiles = new File(f.getPath() + "\\" + list.getSelectedValue()).listFiles(new FilterFilessub ()).length;
						if(anzsubfiles > 0) 
						{
							subvorhanden = "ja";
						}
						else
						{
							subvorhanden = "nein";
						}
						JOptionPane.showMessageDialog(null, list.getSelectedValue() + "\n" + "Anzahl mkv Dateien: " + anzmkvfiles + "\n" + "Anzahl nfo Dateien: " + anznfofiles + "\n" + "Untertitel vorhanden: " + subvorhanden + "\n" + "Größe: " + lib.filesize(lib.getDirSize(new File(f.getPath() + "\\" + list.getSelectedValue()))), "Information", JOptionPane.OK_CANCEL_OPTION);
					}
				});
				btnInformation.setBounds(471, 120, 103, 23);
				contentPane.add(btnInformation);
				
				btnGetXML.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						Movie Film = new Movie();
						if (list.getSelectedValue() != null)
						{
							Film.loadNamefromInsert(list.getSelectedValue());
							String auswahl = Film.gettitle();
							xmlConnect getxml = new xmlConnect();
							ArrayList<String> data = getxml.getXml(auswahl);
							imdbListModel.removeAllElements();
							for (int i = 0; i < data.size(); i++) 
							{
									imdbListModel.addElement(data.get(i));
							}
							try 
							{
								if (data.get(0) != null)
								{
									Film.loadIMDB(data.get(0));
									lblTitel.setText("Titel: " +  Film.gettitle());
									lblGenre.setText("Genre: " +  Film.getgenre());
									lblPlot.setText("Plot: " +  Film.getplot());
								}
								else {
									lblTitel.setText("Titel: (Wurde nicht erkannt)");
									lblGenre.setText("Genre: ?");
									lblPlot.setText("Plot: ?");
								}
							}
							catch (Exception e)
							{
								lblTitel.setText("Titel: (Wurde nicht erkannt)");
								lblGenre.setText("Genre: ?");
								lblPlot.setText("Plot: ?");
							}
						}
						//comboBox.add("test2", null);
						//JOptionPane.showMessageDialog(null,mes,"Information", JOptionPane.OK_CANCEL_OPTION);
					}
				});
				btnGetXML.setBounds(471, 90, 103, 23);
				contentPane.add(btnGetXML);
				
				//Quit Button
				btnQuit.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						System.exit(0); 
					}
				});
				btnQuit.setBounds(471, 305, 103, 23);
				contentPane.add(btnQuit);
		
				//Jlist
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
				scrollPane.setViewportView(list);
				//scrollPane.setViewportView(imdbList);
				// add items to listModel...
				imdbList.setModel(imdbListModel);
				comboBox.setModel(imdbListModel);
        }
	}
}
