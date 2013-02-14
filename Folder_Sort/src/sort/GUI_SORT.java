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
import javax.swing.JTextField;


public class GUI_SORT extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPaneTitel;
	final sort_class lib = new sort_class();
	private JTextField textField;

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
		setTitle("Komplexe Erstellung einer Filmliste!");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 354);
		contentPaneTitel = new JPanel();
		contentPaneTitel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneTitel);
		contentPaneTitel.setLayout(null);
		
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
		lblPic.setBounds(552, 13, 150, 150);
		contentPaneTitel.add(lblPic);
		
		// Label Titel
		JLabel lblPfad = new JLabel("Pfad: ");
		lblPfad.setBounds(300, 13, 221, 14);
		contentPaneTitel.add(lblPfad);
		
		// Scroll Panel
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 276, 278);
		contentPaneTitel.add(scrollPane);
		
		// Label Titel
		final JLabel lblTitel = new JLabel("Titel:");
		lblTitel.setBounds(300, 56, 239, 16);
		contentPaneTitel.add(lblTitel);
		
		// Label Genre
		final JLabel lblGenre = new JLabel("Genre:");
		lblGenre.setBounds(300, 77, 241, 19);
		contentPaneTitel.add(lblGenre);
		
		// Label Plot
		final JLabel lblPlot = new JLabel("Plot:");
		lblPlot.setBounds(300, 169, 402, 14);
		contentPaneTitel.add(lblPlot);
		
		final JLabel lblJahr = new JLabel("Jahr: ");
		lblJahr.setBounds(300, 94, 86, 16);
		contentPaneTitel.add(lblJahr);
		
		final JLabel lblLaufzeit = new JLabel("Laufzeit:");
		lblLaufzeit.setBounds(300, 112, 103, 16);
		contentPaneTitel.add(lblLaufzeit);
		
		final JLabel lblRating = new JLabel("Wertung: ");
		lblRating.setBounds(300, 196, 103, 16);
		contentPaneTitel.add(lblRating);
		
		final JLabel lblSchauspieler = new JLabel("Schauspieler:");
		lblSchauspieler.setBounds(300, 130, 240, 16);
		contentPaneTitel.add(lblSchauspieler);
		
		final JLabel lblDirector = new JLabel("Direktor:");
		lblDirector.setBounds(300, 147, 240, 16);
		contentPaneTitel.add(lblDirector);
		
		final JLabel lblGroesse = new JLabel("Gr\u00F6\u00DFe:");
		lblGroesse.setBounds(300, 220, 106, 16);
		contentPaneTitel.add(lblGroesse);
		
		JComboBox comboBoxTyp = new JComboBox();
		comboBoxTyp.setModel(new DefaultComboBoxModel(new String[] {"Film", "Serie"}));
		comboBoxTyp.setBounds(300, 239, 57, 22);
		contentPaneTitel.add(comboBoxTyp);
		
		JComboBox comboBoxQually = new JComboBox();
		comboBoxQually.setModel(new DefaultComboBoxModel(new String[] {"4K", "3D", "1080", "720", "SQ", "LQ"}));
		comboBoxQually.setBounds(369, 239, 57, 22);
		contentPaneTitel.add(comboBoxQually);
		
		textField = new JTextField();
		textField.setBounds(438, 239, 157, 22);
		contentPaneTitel.add(textField);
		textField.setColumns(10);
		
		// Label IMDB Datensatz
		JLabel lblImdbDatensatz = new JLabel("IMDB Datensatz:");
		lblImdbDatensatz.setBounds(436, 201, 103, 16);
		contentPaneTitel.add(lblImdbDatensatz);
		
		// Label Auswahl IMDB ID
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String data =(String) comboBox.getSelectedItem();
				setInfo(data,  filmMaster, lblTitel,  lblGenre,  lblPlot,	 lblJahr,  lblLaufzeit,
						lblRating, lblSchauspieler,  lblDirector,  lblGroesse, lblPic);
			}
		});
		comboBox.setBounds(539, 196, 103, 27);
		contentPaneTitel.add(comboBox);
		
		// Button Save
		JButton btnSave = new JButton("speichern");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filmMaster.save();
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
		btnSave.setBounds(492, 268, 103, 23);
		contentPaneTitel.add(btnSave);
		
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

		btnSaveToCsv.setBounds(300, 268, 103, 23);
		contentPaneTitel.add(btnSaveToCsv);
		
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
		btnRename.setBounds(110, 295, 86, 23);
		contentPaneTitel.add(btnRename);
		
		// Button GetXML
		final JButton btnGetXML = new JButton("Info");
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
					if (!data.isEmpty())
					setInfo(data.get(0),  filmMaster, lblTitel,  lblGenre,  lblPlot,	 lblJahr,  lblLaufzeit,
							lblRating, lblSchauspieler,  lblDirector,  lblGroesse, lblPic);
					else setInfoLite(filmMaster, lblTitel, lblGroesse);
				}
				//comboBox.add("test2", null);
				//JOptionPane.showMessageDialog(null,mes,"Information", JOptionPane.OK_CANCEL_OPTION);
				/*
				 * 		        String subvorhanden = null;
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
				 * 
				 * 
				 * 
				 * */
			}
		});
		btnGetXML.setBounds(599, 240, 103, 23);
		contentPaneTitel.add(btnGetXML);
		
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
		btnQuit.setBounds(599, 268, 103, 23);
		contentPaneTitel.add(btnQuit);
		
		// Eingabe des gesuchten Filmes
		JButton button = new JButton("?");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String input = JOptionPane.showInputDialog("Geben sie den Suchbegriff oder die IMDB ID ein: ", null);
				if (input != null)
				{
					//filmMaster.loadNamefromInsert(input);
					filmMaster.setQuery(input);
					xmlConnect getxml = new xmlConnect();
					ArrayList<String> data = getxml.getXml(filmMaster.gettitle());
					imdbListModel.removeAllElements();
						for (int i = 0; i < data.size(); i++) 
						{
								imdbListModel.addElement(data.get(i));
						}
					if (!data.isEmpty())
					setInfo(data.get(0),  filmMaster, lblTitel,  lblGenre,  lblPlot,	 lblJahr,  lblLaufzeit,
							lblRating, lblSchauspieler,  lblDirector,  lblGroesse, lblPic);
					//else setInfoLite(filmMaster, lblTitel, lblGroesse);
				}
			}
		});
		button.setBounds(654, 196, 48, 27);
		contentPaneTitel.add(button);
		
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
	
	public void setInfo(String imdbid, Movie filmMaster,
			JLabel lblTitel, JLabel lblGenre, JLabel lblPlot,
			JLabel lblJahr, JLabel lblLaufzeit, JLabel lblRating,
			JLabel lblSchauspieler, JLabel lblDirector, JLabel lblGroesse,
			JLabel lblPic)
	{
		try 
		{
				filmMaster.loadIMDB(imdbid);
				lblTitel.setText("Titel: " +  filmMaster.gettitle());
				lblGenre.setText("Genre: " +  filmMaster.getgenre());
				lblPlot.setText("Plot: " +  filmMaster.getplot());
				lblJahr.setText("Jahr: " + filmMaster.getyear());
				lblLaufzeit.setText("Laufzeit: " + filmMaster.getruntime());
				lblRating.setText("Wertung: " + filmMaster.getrating());
				lblSchauspieler.setText("Schauspieler: " + filmMaster.getactors());
				lblDirector.setText("Direktor: " + filmMaster.getdirector());
				lblGroesse.setText("Größe: " + filmMaster.getSize());
				lblPic.setIcon(filmMaster.getpic()); // zeige Bild an
			}
			catch (Exception e)
			{
				lblTitel.setText("Titel: (Wurde nicht erkannt)");
				lblGenre.setText("Genre: ?");
				lblPlot.setText("Plot: ?");
				lblJahr.setText("Jahr: ");
				lblLaufzeit.setText("Laufzeit: ");
				lblRating.setText("Wertung: ");
				lblSchauspieler.setText("Schauspieler: ");
				lblDirector.setText("Direktor: ");
				lblGroesse.setText("Größe: ");
				setInfoLite(filmMaster, lblTitel, lblGroesse);
			}
	}
	public void setInfoLite( Movie filmMaster,JLabel lblTitel, JLabel lblGroesse)
	{
				lblTitel.setText("Titel: " +  filmMaster.gettitle());
				lblGroesse.setText("Größe: " + filmMaster.getSize());
	}
}
