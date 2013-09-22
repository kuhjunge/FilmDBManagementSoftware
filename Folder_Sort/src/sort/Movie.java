package sort;

import java.awt.Image;
import java.io.File;
import java.net.URL;
//import java.io.IOException;

import javax.swing.ImageIcon;

public class Movie {
	
	// Datei Variablen
	private File movieFile = null;
	private String saveplace = "";
	private String foldername = "";
	private String oTitle = "";
	private String oQually = "SQ";
	private String oSize ="";
	private String oType ="F";
	
	// IMDB Variablen
	private String mTitle = "";
	private String mYear = "";
	private String mRated = "";
	private String mReleased = "";
	private String mRuntime = "";
	private String mGenre = "";
	private String mDirector = "";
	private String mWriter = "";
	private String mActors = "";
	private String mPlot = "";
	private String mPoster = "";
	private String mImdbRating = "";
	private String mImdbVotes = "";
	private String mImdbID = "";
	private String mImdbURL="";
	
	// Config Variablen
	private boolean lockID = false;
	
	// Konstruktor
	public Movie()
	{
		
	}
	
	private void clear()
	{
		 oTitle = "";
		 oQually = "";
		 oSize ="";
		 oType ="F";
		
		// IMDB Variablen
		 mTitle = "";
		 mYear = "";
		 mRated = "";
		 mReleased = "";
		 mRuntime = "";
		 mGenre = "";
		 mDirector = "";
		 mWriter = "";
		 mActors = "";
		 mPlot = "";
		 mPoster = "";
		 mImdbRating = "";
		 mImdbVotes = "";
		 mImdbID = "";
		 mImdbURL="";
		 lockID = false;
	}
	
	// Läd Filmdaten aus dem Ordernamen
	public void loadNamefromInsert(String name)
	{
		clear(); // Leert das Filmobjekt
		foldername = name;
		String[] splitFolder = foldername.split("_",4);
		// Titel extrahieren
		if (splitFolder.length > 0)
			oTitle = splitFolder[splitFolder.length - 1]; // Letzten Teil des Strings ausgeben
		// rest extrahieren
		if (splitFolder.length == 2)
		{
			String split = splitFolder[splitFolder.length - 2];
			if (split.startsWith("S")) oType = "S";
			else oType = "F";
			switch (split.substring(1))
			{
				case "4k":
					oQually= "4k";
					break;
				case "3D":
					oQually= "3D";
					break;
				case "1080":
					oQually= "1080";
					break;
				case "720":
					oQually= "720";
					break;
				case "SQ":
					oQually= "SQ";
					break;
				case "LQ":
					oQually= "LQ";
					break;
			}
		}
		
		// Alte analyse
		else if (splitFolder.length > 2)
		{
			if (splitFolder.length > 1)
			{
				String oTag = splitFolder[splitFolder.length - 2]; // Letzten Teil des Strings ausgeben
				taganalyse(oTag);
			}
			if (splitFolder.length > 2)
			{
				String split = splitFolder[splitFolder.length - 3];
				if(split.contains("SQ")) oQually = "SQ";
				else if(split.contains("720p")) oQually = "720";
				else if(split.contains("1080p")) oQually = "1080";
				else if(split.contains("LQ")) oQually = "LQ";
			}
		/*	if (splitFolder.length > 3)
			{
				String split = splitFolder[splitFolder.length - 4];
				if (split == "Serie") ;
			} */
		}
		File moviexml = new File(getpath() + "\\movie.xml");
	    if(moviexml.exists()){
	    	xmlConnect xml = new xmlConnect();
	    	String tag = xml.readXML( getpath() +"\\movie.xml");
	    	taganalyse( tag);
	    }
		oSize = getDirSize();
	}
	
	// Läd Filmdaten von der IMDB
	public void loadIMDB (String imdbID)
	{
		if (!lockID)
		{
			xmlConnect getxml = new xmlConnect();
			String[] data =  getxml.getMovieObject(imdbID);
			 mTitle = data[0];
			 mYear = data[1];
			 mRated = data[2];
			 mReleased = data[3];
			 mRuntime = data[4];
			 mGenre = data[5];
			 mDirector = data[6];
			 mWriter = data[7];
			 mActors = data[8];
			 mPlot = data[9];
			 mPoster = data[10];
			 mImdbRating = data[11];
			 mImdbVotes = data[12];
			 mImdbID = data[13];	
			 mImdbURL = "http://www.imdb.de/title/" + data[13] + "/";
		}
	}
	
	private void taganalyse(String tag)
	{
		if (tag != null && tag.startsWith("tt"))
		{
			loadIMDB (tag);
			lockID = true;
		}
		else if (tag != null )
		{
			mTitle = tag;
		}
	}
	
	
	// Gibt die Größe des ausgewählten Filmobjektes zurück
	private String getDirSize() {
		// lib.getDirSize(new File(f.getPath() + "\\" + list.getSelectedValue())))
		//f = fc.getSelectedFile();	//Verzeichnis Holen
		//filmMaster.setFile(f);
		//final File f1 = new File(f.getPath());	//In File speichern
		if (saveplace != "" && foldername != "")
		{
			File dir = new File(saveplace + "\\" + foldername);
			return filesize(getSubDirSize(dir));
		}
		else return "";
	}
	
	// Gibt die Größe des ausgewählten Filmobjektes zurück
	public static String getOtherDirSize(File dir) {
			return filesize(getSubDirSize(dir));
	}
	
	// Größe aller Objekte in diesem Ordner auslesen 
	private static long getSubDirSize(File dir) {
		
		long size = 0;
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					size += getSubDirSize(files[i]); // Gesamtgröße des Verzeichnisses aufaddieren
				}
				else {
					size += files[i].length(); // Größe der Datei aufaddieren
				}
			}
		}
		return size;
	}
	
	// Set Funktion
	public void setFile(File file)
	{
		movieFile = file;
		saveplace = movieFile.getPath();
	}
	
	public boolean setQuery(String input)
	{
		taganalyse(input);
		return lockID;
	}
	
	// Get Funktionen
	public String getpath()
	{
			return saveplace + "\\" + foldername;
	}
	public String gettitle ()
	{
		if (mTitle != null && mTitle != "")
		{
			return mTitle;
		}
		else
			return oTitle;
	}
	public String getotitle ()
	{
			return oTitle;
	}
	public String getyear ()
	{
		return mYear;
	}
	public String getrated ()
	{
		return mRated;
	}
	public String getreleased ()
	{
		return mReleased;
	}
	public String getruntime ()
	{
		return mRuntime;
	}
	public String getgenre ()
	{
		return mGenre;
	}
	public String getdirector ()
	{
		return mDirector;
	}
	public String getwriter ()
	{
		return mWriter;
	}
	public String getactors ()
	{
		return mActors;
	}
	public String getplot ()
	{
		return mPlot;
	}
	public String getposters ()
	{
		return mPoster;
	}
	public ImageIcon getpic()
	{
		try 
		{
			ImageIcon ico = new ImageIcon(new URL(mPoster)); // Lade Bild aus Inet
			if (ico.getIconWidth() <  ico.getIconHeight())
			{
				float factor = (float)ico.getIconWidth() / (float)ico.getIconHeight();
				ico.setImage(ico.getImage().getScaledInstance((int)(150 * factor),150,Image.SCALE_DEFAULT)); // Skaliere
			}
			else 
			{
				float factor = ico.getIconHeight() / ico.getIconWidth();
				ico.setImage(ico.getImage().getScaledInstance(150,(int)(150*factor),Image.SCALE_DEFAULT)); // Skaliere
			}
			 return ico;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	public String getrating ()
	{
		return mImdbRating;
	}
	public String getvotes ()
	{
		return mImdbVotes;
	}
	public String getid ()
	{
		return mImdbID;
	}
	public String getImdbURL ()
	{
		return mImdbURL;
	}
	public String getFiledir()
	{
		return saveplace;
	}
	public File getFile()
	{
		return movieFile;
	}
	public String getSize()
	{
		return oSize;
	}
	public String getQually()
	{
		return  oQually;
	}
	public String getType()
	{
		if (oType == "S")
		return "Serie";
		else return "Film";
	}
	
	/**
	* Gibt die Dateigröße zurück
	* @param long groesse - Größenangabe
	* @return String mit Größenangabe
	*/
	private static String filesize(long groesse)
	{
		String erg = "";
		if (groesse < 1024)
		{
			erg = " " + (groesse)+ " Byte \n";
		}
		else if (groesse < 1024 * 1024)
		{
			erg = " " + (groesse/1024)+ " KB \n";
		}
		else if (groesse < 1024 * 1024 * 1024)
		{
			erg = " " + (groesse/1024/1024)+ " MB \n";
		}
		else erg = " " + (groesse/1024/1024/1024)+ "GB \n";
		return erg;
	}
	
	//
	public void save(String name,String typ,String qually)
	{
		if (oTitle != null)
		{
			String titel  =  name;
			oType = typ;
			oTitle = name;
			oQually = qually;
			//Angeklickte Datei mit PFad auslesen
	        File f = new File(saveplace + "\\" + foldername);
	        //Rename Eingegebener neuer Titel
			f.renameTo(new File(saveplace + "\\" + titel));
			xmlConnect xml = new xmlConnect();
			xml.saveToXML(saveplace + "\\" + titel + "\\" + "movie.xml" ,mImdbID );
			
		}
	}
}
