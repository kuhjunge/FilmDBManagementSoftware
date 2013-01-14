package sort;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;


//Cooperation von Chris und Merlin
public class choose
{
	static JTextArea ta; // Text area
	static JButton btnQuit;
	public static long getDirSize(File dir) {
		
		long size = 0;
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					size += getDirSize(files[i]); // Gesamtgröße des Verzeichnisses aufaddieren
				}
				else {
					size += files[i].length(); // Größe der Datei aufaddieren
				}
			}
		}
		return size;
	}
	
	public static void analyse(File dir) {

		File[] files = dir.listFiles();
		if (files != null) 
		{ // Erforderliche Berechtigungen etc. sind vorhanden
			for (int i = 0; i < files.length; i++) 
			{
				if (files[i].isDirectory()) 
				{
					ta.append(files[i].getName());
					File m = new File (files[i].getAbsolutePath());
					ta.append(" " + (getDirSize(m)/1024/1024/1024)+ "GB \n");
					/*
					 * if((getDirSize(m)/1024/1024/1024)<=3)
					
					{
						System.out.println("Kleiner als 3 GB");
					}
					else if (((getDirSize(m)/1024/1024/1024)>3) && (getDirSize(m)/1024/1024/1024) <=6)
					{
						System.out.println("Größer als 3 und kleiner als 6 GB");
					}
					else if ((getDirSize(m)/1024/1024/1024)>6)
					{
						System.out.println("Größer als 6 GB");
					}
					 */
				}
			}
		}
	}
	
    public static void main(String[] args)
    {
    	

   	 
        JFrame frame = new JFrame("Ordnersort"); //Frame für Text Area
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Default Bedeutung "X"
        
    	 ta = new JTextArea("", 5, 50); //Text Area generieren
    	 ta.setLineWrap(true); //verhindert automatisches verbreitern nach rechts und links
    	 
    	 JScrollPane sbrText = new JScrollPane(ta); //Scrollbalken
    	 sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


    	 //Auswahlfenster Verzeichnis
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Nur Ordner auswählbar
        int returnVal = fc.showOpenDialog(null);
        File f;
        if (returnVal == JFileChooser.APPROVE_OPTION) 	//Wenn gewählt, dann
        {
            f = fc.getSelectedFile();	//Verzeichnis Holen
	        File f1 = new File(f.getPath());	//In File speichern
	        
	        //f1.renameTo(new File(f.getPath() + "\\Data"));	//f1 umbenennen

	        

	        //Texte in Textarea
	        ta.append("Pfad: \n" + f.getPath() + "\n");
	        ta.append("Gesamtgröße \n" +(getDirSize(f1)/1024/1024/1024)+ "GB \n");
	        ta.append("------------------------------------------------------------------------------ \n");
	        analyse(f1);	//analyse funktion
        }
        // Create Quit Button
        	  btnQuit = new JButton("Quit");
        	  btnQuit.addActionListener
        	  (
        			  new ActionListener()
        			  {
        				  public void actionPerformed(ActionEvent e)
        				  {
        					  System.exit(0);         
        				  }
        			  }
        	  );
        	  
       //Scrollbalken und Textarea in panel
   	 frame.getContentPane().add(sbrText);  
   	 frame.getContentPane().add(btnQuit);
   	 frame.pack(); // Adjusts frame to size of components
   	 frame.setVisible(true);
    }    
}