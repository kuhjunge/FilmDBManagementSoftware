package sort;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class FilmManagerGUI {

	public JFrame frmFilmManager;
	private JPanel panel;
	private JButton btnChangeDir;
	private JLabel labelAbout;
	private JScrollPane scrollFilmlist;
	/* Film Objekt */
	private Movie filmMaster = new Movie();
	// Sortierfunktion
	private sort_class lib = new sort_class();
	// Liste für die IMDB Filmauswahl
	private DefaultComboBoxModel<String> imdbListModel = new DefaultComboBoxModel<String>();
	private JList <String> imdbList = new JList <String>(imdbListModel);
	// Liste für die Filme auf der Festplatte
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList <String> list = new JList <String>(listModel);
	private JComboBox<String> comboBoxIMDBID; // Combo Box mit IMDB ID
	private JLabel lblTitel;
	private JLabel lblGenre;
	private JLabel lblPlot;
	private JLabel lblJahr;
	private JLabel lblLaufzeit;
	private JLabel lblRating;
	private JLabel lblSchauspieler;
	private JLabel lblDirector;
	private JLabel lblGroesse;
	private JComboBox<String> comboBoxTyp;
	private JComboBox<String> comboBoxQually;
	private JLabel lblPic;
	private JLabel lblPfad;
	private JTextField textFieldTitle;
	private JLabel lblImdbDatensatz;
	private JButton btnSave;
	private JButton btnSaveToCsv;
	private JButton btnGetXML;
	private JButton btnQuit;
	private JButton buttonq;
	
	
	/**
	 * Create the application.
	 */
	public FilmManagerGUI() {
		initialize();
		loadlist(targetFolder()); // Dialog öffnen mit Pfadauswahl, ergebnis in die Filmliste laden
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFilmManager = new JFrame();
		frmFilmManager.setTitle("Film Manager");
		frmFilmManager.setResizable(false);
		frmFilmManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFilmManager.setBounds(100, 100, 720, 348);
		
		// Das JPanel welches die gesammte Oberfläche umrahmt 
		panel = new JPanel();
		frmFilmManager.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		// Scroll Panel mit Filmen
		scrollFilmlist = new JScrollPane();
		scrollFilmlist.setBounds(0, 0, 276, 313);
		panel.add(scrollFilmlist);
		
		// Label Auswahl IMDB ID
		comboBoxIMDBID = new JComboBox<String>();
		comboBoxIMDBID.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String data =(String) comboBoxIMDBID.getSelectedItem();
				setInfo(data,  filmMaster, lblTitel,  lblGenre,  lblPlot,	 lblJahr,  lblLaufzeit,	lblRating, lblSchauspieler,  
						lblDirector,  lblGroesse, lblPic,  comboBoxTyp,  comboBoxQually, textFieldTitle,lblPfad);
			}
		});
		comboBoxIMDBID.setBounds(541, 221, 103, 27);
		panel.add(comboBoxIMDBID);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setBounds(448, 255, 157, 22);
		panel.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		//LabelPic
		lblPic = new JLabel(" ");
		lblPic.setBounds(551, 27, 150, 150);
		panel.add(lblPic);
		
		// Label Titel
		lblPfad = new JLabel("Pfad: ");
		lblPfad.setBounds(300, 13, 401, 14);
		panel.add(lblPfad);
		
		// Label Titel
		lblTitel = new JLabel("Titel:");
		lblTitel.setBounds(300, 56, 239, 16);
		panel.add(lblTitel);
		
		// Label Genre
		lblGenre = new JLabel("Genre:");
		lblGenre.setBounds(300, 77, 241, 19);
		panel.add(lblGenre);
		
		// Label Plot
		lblPlot = new JLabel("Plot:");
		lblPlot.setBounds(300, 190, 402, 14);
		panel.add(lblPlot);
		
		// label Jahr
		lblJahr = new JLabel("Jahr: ");
		lblJahr.setBounds(300, 94, 140, 16);
		panel.add(lblJahr);
		
		// Label laufzeit
		lblLaufzeit = new JLabel("Laufzeit:");
		lblLaufzeit.setBounds(300, 112, 202, 16);
		panel.add(lblLaufzeit);
		
		// Label Wertung
		lblRating = new JLabel("Wertung: ");
		lblRating.setBounds(300, 163, 126, 16);
		panel.add(lblRating);
		
		// Label Schauspieler
		lblSchauspieler = new JLabel("Schauspieler:");
		lblSchauspieler.setBounds(300, 130, 240, 16);
		panel.add(lblSchauspieler);
		
		// label Director
		lblDirector = new JLabel("Direktor:");
		lblDirector.setBounds(300, 147, 240, 16);
		panel.add(lblDirector);
		
		// Label Größe
		lblGroesse = new JLabel("Gr\u00F6\u00DFe:");
		lblGroesse.setBounds(288, 226, 126, 16);
		panel.add(lblGroesse);
		
		// label Film / Serie
		comboBoxTyp = new JComboBox<String>();
		comboBoxTyp.setModel(new DefaultComboBoxModel<String>(new String[] {"Film", "Serie"}));
		comboBoxTyp.setBounds(288, 255, 57, 22);
		panel.add(comboBoxTyp);
		
		// label Qually
		comboBoxQually = new JComboBox<String>();
		comboBoxQually.setModel(new DefaultComboBoxModel<String>(new String[] {"4K", "3D", "1080", "720", "SQ", "LQ"}));
		comboBoxQually.setBounds(379, 255, 57, 22);
		panel.add(comboBoxQually);

		// About Label
		labelAbout = new JLabel("V.0.2 \u00A9 2013 by SirGotcha & Kuhjunge");
		labelAbout.setBounds(488, 0, 226, 16);
		panel.add(labelAbout);
		
		// Listener für Mausklick auf Liste
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
					setInfo(data.get(0),  filmMaster, lblTitel,  lblGenre,  lblPlot,	 lblJahr,  lblLaufzeit,	lblRating, 
							lblSchauspieler,  lblDirector,  lblGroesse, lblPic,  comboBoxTyp,  comboBoxQually, textFieldTitle,lblPfad);
					else setInfoLite(filmMaster, lblTitel, lblGroesse,  comboBoxTyp,  comboBoxQually, textFieldTitle,lblPfad);
				}
			}
		});
		
		// --------------------  Buttons -----------------------
		// Verzeichniswechsel
		btnChangeDir = new JButton("Ordner w\u00E4hlen");
		btnChangeDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadlist(targetFolder()); // Dialog öffnen mit Pfadauswahl, ergebnis in die Filmliste laden
			}
		});
		btnChangeDir.setBounds(382, 290, 124, 23);
		panel.add(btnChangeDir);
		
		// Label IMDB Datensatz
		lblImdbDatensatz = new JLabel("IMDB Datensatz:");
		lblImdbDatensatz.setBounds(426, 226, 103, 16);
		panel.add(lblImdbDatensatz);
		
		// Button Save
		btnSave = new JButton("speichern");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textFieldTitle.getText();
				String typ = comboBoxTyp.getSelectedItem().toString();
				String qually = comboBoxQually.getSelectedItem().toString();
				filmMaster.save(name, typ, qually);
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
		btnSave.setBounds(611, 254, 103, 23);
		panel.add(btnSave);
		
		//SaveToCsv Button
		btnSaveToCsv = new JButton("Save to CSV");
		btnSaveToCsv.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				lib.writeCSV(filmMaster.getFile()); // Schreibt die CSV Datei
			}
		});

		btnSaveToCsv.setBounds(508, 290, 103, 23);
		panel.add(btnSaveToCsv);
		
		// Button GetXML
		btnGetXML = new JButton("Info");
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
					setInfo(data.get(0),  filmMaster, lblTitel,  lblGenre,  lblPlot,	 lblJahr,  lblLaufzeit,	lblRating, 
							lblSchauspieler,  lblDirector,  lblGroesse, lblPic,  comboBoxTyp,  comboBoxQually, textFieldTitle,lblPfad);
					else setInfoLite(filmMaster, lblTitel, lblGroesse,  comboBoxTyp,  comboBoxQually, textFieldTitle,lblPfad);
				}
			}
		});
		btnGetXML.setBounds(277, 290, 103, 23);
		panel.add(btnGetXML);
		
		//Quit Button
		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0); 
			}
		});
		btnQuit.setBounds(611, 290, 103, 23);
		panel.add(btnQuit);
		
		// Eingabe des gesuchten Filmes
		buttonq = new JButton("?");
		buttonq.addMouseListener(new MouseAdapter() {
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
					setInfo(data.get(0),  filmMaster, lblTitel,  lblGenre,  lblPlot,	 lblJahr,  lblLaufzeit,	lblRating,
							lblSchauspieler,  lblDirector,  lblGroesse, lblPic,  comboBoxTyp,  comboBoxQually, textFieldTitle,lblPfad);
					//else setInfoLite(filmMaster, lblTitel, lblGroesse);
				}
			}
		});
		buttonq.setBounds(666, 221, 48, 27);
		panel.add(buttonq);
		
	}
	
	private void loadlist(File f)
	{
		if (f != null)
		{
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
			scrollFilmlist.setViewportView(list);
			// add items to listModel...
			imdbList.setModel(imdbListModel);
			comboBoxIMDBID.setModel(imdbListModel);	
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "Kein Verzeichnis ausgewählt!", "Fehler!", JOptionPane.OK_CANCEL_OPTION);
		}
	}
	
	/**
	* Wählt das Zielverzeichnis aus
	* @return Das ausgewählte Zielverzeichnis oder Null
	*/
	public File targetFolder()
	{
		JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("C:\\"));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Nur Ordner auswählbar
        int returnVal = fc.showOpenDialog(null);
        File f = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) 	//Wenn gewählt, dann
        {
			f = fc.getSelectedFile();	//Verzeichnis Holen
        }
        return f;
	}
	
	public void setInfo(String imdbid, Movie filmMaster,
			JLabel lblTitel, JLabel lblGenre, JLabel lblPlot,
			JLabel lblJahr, JLabel lblLaufzeit, JLabel lblRating,
			JLabel lblSchauspieler, JLabel lblDirector, JLabel lblGroesse,
			JLabel lblPic, JComboBox<String> comboBoxTyp, JComboBox<String> comboBoxQually,
			JTextField textFieldTitle, JLabel lblPfad)
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
				comboBoxTyp.setSelectedItem(filmMaster.getType());
				comboBoxQually.setSelectedItem(filmMaster.getQually());
				lblPic.setIcon(filmMaster.getpic()); // zeige Bild an
				textFieldTitle.setText(filmMaster.getotitle());
				lblPfad.setText(filmMaster.getpath());
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
				setInfoLite(filmMaster, lblTitel, lblGroesse,  comboBoxTyp,  comboBoxQually, textFieldTitle,lblPfad);
			}
	}
	public void setInfoLite( Movie filmMaster,JLabel lblTitel, JLabel lblGroesse, JComboBox<String> comboBoxTyp,
			JComboBox<String> comboBoxQually, JTextField textFieldTitle, JLabel lblPfad)
	{
				lblTitel.setText("Titel: " +  filmMaster.gettitle());
				lblGroesse.setText("Größe: " + filmMaster.getSize());
				comboBoxTyp.setSelectedItem(filmMaster.getType());
				comboBoxQually.setSelectedItem(filmMaster.getQually());
				textFieldTitle.setText(filmMaster.getotitle());
				lblPfad.setText(filmMaster.getpath());
	}
}
