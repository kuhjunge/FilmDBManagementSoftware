package sort;
//import java.awt.FlowLayout;
//import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;


//Cooperation von Chris und Merlin!!!
public class sort_class
{
	static FileWriter writer;

	// Schreibt eine CSV mit einer Filmliste in den ausgewählten Ordner
	void writeCSV(File f)
	{
		try
			{
				final File f1 = new File(f.getPath());	//In File speichern
			    writer = new FileWriter(f.getPath() + "\\Filmliste.csv");
		
			    writer.append("Pfad: ;" + f.getPath() + "\n");
				writer.append("Gesamtgröße ;" +(Movie.getOtherDirSize(f1)) +" \n");
				writer.append("-----------------\n");
				
				// Auslesen der Unterordner
				
				File[] files = f1.listFiles();
				if (files != null) 
				{ // Erforderliche Berechtigungen etc. sind vorhanden
					for (int i = 0; i < files.length; i++) 
					{
						if (files[i].isDirectory()) 
						{
							String foldername = files[i].getName();
							String[] splitFolder = foldername.split("_",4);
							// Schreiben der einzelnen Filme in die CSV
							//writer.append(foldername);

							writer.append(splitFolder[splitFolder.length - 1]+ ";"); // Letzten Teil des Strings ausgeben
							
							// Vorne fehlende Daten leer setzen
							for (int ii = 0; ii < (4 - splitFolder.length);ii++)
							{
								writer.append(";");
							}
							// Ausgabe des Split STrings
							for (int ii = 0; ii < (splitFolder.length - 1); ii++) // Dann die restlichen Informationen im String ausgeben
							{
								writer.append(splitFolder[ii]+ ";");
							}
							// Ausgabe der größe
							File m = new File (files[i].getAbsolutePath());
							writer.append(";");
							// Größenberechnung 
							writer.append(Movie.getOtherDirSize(m)); // Funktion Filesize gibt einen String mit Einhait zurück
							writer.append("");
						}
					}
				}
			    writer.flush();
			    writer.close();
    			JOptionPane.showMessageDialog(null, "Speichern erfolgreich! \nDie CSV Datei liegt im Zielordner.");
			}
			catch (FileNotFoundException ex)
			{
				JOptionPane.showMessageDialog(null, "Der Zugriff auf die Datei ist gescheitert!");
			}
			catch(IOException e1)
			{
			     e1.printStackTrace();
    			JOptionPane.showMessageDialog(null, "Es ist ein Fehler aufgetreten!");
			} 	
	}
	
	// Auswahldialog für Pfadauswahl
	// File auswahl
	
	/**
	* Löst den Select Folder Dialog aus - zurzeit ungenutzt
	* @param ...
	* @return nichts
	*/
	void selectFolder(Movie filmMaster, DefaultListModel<String> lm, JList <String> list, DefaultComboBoxModel<String> imdbListModel,
			JList <String> imdbList,JComboBox<String> comboBox,JScrollPane scrollPane )
	{
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
							lm.addElement(files[i].getName());
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