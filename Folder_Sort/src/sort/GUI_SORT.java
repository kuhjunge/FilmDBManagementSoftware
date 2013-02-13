package sort;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/* Film Objekt */
		final Movie filmMaster = new Movie();
		// Liste für die IMDB Filmauswahl
		final DefaultComboBoxModel<String> imdbListModel = new DefaultComboBoxModel<String>();
		final JList <String> imdbList = new JList <String>(imdbListModel);
		// Liste für die Filme auf der Festplatte
		final DefaultListModel<String> listModel = new DefaultListModel<String>();
		final JList <String> list = new JList <String>(listModel);
		
		//LabelPic
		final JLabel lblPic = new JLabel(" ");
		lblPic.setBounds(553, 47, 150, 150);
		contentPane.add(lblPic);
		
		// Label Titel
		JLabel lblKomplexeErstellungEiner = new JLabel("Komplexe Erstellung einer Filmliste!");
		lblKomplexeErstellungEiner.setBounds(12, 13, 221, 14);
		contentPane.add(lblKomplexeErstellungEiner);
		
		// Scroll Panel
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 42, 276, 278);
		contentPane.add(scrollPane);
		
		// Label Titel
		final JLabel lblTitel = new JLabel("Titel:");
		lblTitel.setBounds(302, 47, 239, 16);
		contentPane.add(lblTitel);
		
		// Label Genre
		final JLabel lblGenre = new JLabel("Genre:");
		lblGenre.setBounds(300, 70, 241, 19);
		contentPane.add(lblGenre);
		
		// Label Plot
		final JLabel lblPlot = new JLabel("Plot:");
		lblPlot.setBounds(300, 266, 402, 14);
		contentPane.add(lblPlot);
		
		// Label Auswahl IMDB ID
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String data =(String) comboBox.getSelectedItem();
				try 
				{
					if (data != null)
					{
						filmMaster.loadIMDB(data);
						lblTitel.setText("Titel: " +  filmMaster.gettitle());
						lblGenre.setText("Genre: " +  filmMaster.getgenre());
						lblPlot.setText("Plot: " +  filmMaster.getplot());
						lblPic.setIcon(filmMaster.getpic()); // zeige Bild an
					}
					else {
						lblTitel.setText("Titel: (Wurde nicht gefunden)");
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
		comboBox.setBounds(409, 293, 103, 27);
		contentPane.add(comboBox);
		
		// Button Save
		JButton btnSave = new JButton("speichern");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filmMaster.save();
			}
		});
		btnSave.setBounds(578, 293, 103, 27);
		contentPane.add(btnSave);
		
		//SaveToCsv Button
		JButton btnSaveToCsv = new JButton("Save to CSV");
		btnSaveToCsv.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				lib.writeCSV(filmMaster.getFile()); // Schreibt die CSV Datei
			}
		});

		btnSaveToCsv.setBounds(230, 328, 103, 23);
		contentPane.add(btnSaveToCsv);
		
		//Rename Button
		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				//Angeklickte Datei mit PFad auslesen
		        File f2 = new File(filmMaster.getFiledir() + "\\" + list.getSelectedValue());
		        //Eingabefeld
		        String titel = JOptionPane.showInputDialog("Geben sie den neuen Dateinamen ein: ", list.getSelectedValue());
		        //Rename Eingegebener neuer Titel
				f2.renameTo(new File(filmMaster.getFiledir() + "\\" + titel));
				//JList leeren
				listModel.clear();
				
				//JList neu generieren
				File f1 = new File(filmMaster.getFiledir());
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
		btnRename.setBounds(300, 293, 103, 27);
		contentPane.add(btnRename);
		
		// TechInfo Button
		JButton btnInformation = new JButton("TechInfo");
		btnInformation.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
		        String subvorhanden = null;
				//Angeklickte Datei auslesen und in Message Dialog
				list.getSelectedValue();
				// Filter Anzahl mkv Dateien
				int anzmkvfiles = new File(filmMaster.getFiledir() + "\\" + list.getSelectedValue()).listFiles(new FilterFilesmkv ()).length;
				//Filter Anzahl nfo Dateien
				int anznfofiles = new File(filmMaster.getFiledir() + "\\" + list.getSelectedValue()).listFiles(new FilterFilesnfo ()).length;
				//Filter Subs vorhanden
				int anzsubfiles = new File(filmMaster.getFiledir() + "\\" + list.getSelectedValue()).listFiles(new FilterFilessub ()).length;
				if(anzsubfiles > 0) 
				{
					subvorhanden = "ja";
				}
				else
				{
					subvorhanden = "nein";
				}
				filmMaster.loadNamefromInsert(list.getSelectedValue());
				JOptionPane.showMessageDialog(null,
					list.getSelectedValue() + "\n" + "Anzahl mkv Dateien: " + anzmkvfiles + "\n" + "Anzahl nfo Dateien: "
					+ anznfofiles + "\n" + "Untertitel vorhanden: " + subvorhanden + "\n" + "Größe: " + 
					filmMaster.getSize(),
					"Information", JOptionPane.OK_CANCEL_OPTION);
			}
		});
		btnInformation.setBounds(116, 328, 103, 23);
		contentPane.add(btnInformation);
		
		// Button GetXML
		JButton btnGetXML = new JButton("Info");
		btnGetXML.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if (list.getSelectedValue() != null)
				{
					filmMaster.loadNamefromInsert(list.getSelectedValue());
					String auswahl = filmMaster.gettitle();
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
							filmMaster.loadIMDB(data.get(0));
							lblTitel.setText("Titel: " +  filmMaster.gettitle());
							lblGenre.setText("Genre: " +  filmMaster.getgenre());
							lblPlot.setText("Plot: " +  filmMaster.getplot());
							lblPic.setIcon(filmMaster.getpic()); // zeige Bild an
						}
						else {
							lblTitel.setText("Titel: (Wurde nicht gefunden)");
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
		btnGetXML.setBounds(12, 328, 103, 23);
		contentPane.add(btnGetXML);
		
		//Quit Button
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0); 
			}
		});
		btnQuit.setBounds(600, 328, 103, 23);
		contentPane.add(btnQuit);
		
		JButton button = new JButton("?");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String input = JOptionPane.showInputDialog("Geben sie den Suchbegriff oder die IMDB ID ein: ", null);
				filmMaster.setQuery(input);
			}
		});
		button.setBounds(524, 293, 48, 27);
		contentPane.add(button);
		
		// File auswahl
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("C:\\"));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Nur Ordner auswählbar
        int returnVal = fc.showOpenDialog(null);
        final File f;
        if (returnVal == JFileChooser.APPROVE_OPTION) 	//Wenn gewählt, dann
        {
			f = fc.getSelectedFile();	//Verzeichnis Holen
			filmMaster.setFile(f);
			final File f1 = new File(filmMaster.getFiledir());	//In File speichern

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
				// add items to listModel...
				imdbList.setModel(imdbListModel);
				comboBox.setModel(imdbListModel);
        }
	}
}
