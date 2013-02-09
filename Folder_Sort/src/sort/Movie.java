package sort;

import java.io.File;
import java.io.IOException;

public class Movie {
	
	private String saveplace = "";
	private String foldername = "";
	private String oTitle = "";
	private String oQually = "";
	private String oTag = "";
	private String oSize ="";
	
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
	
	// Konstruktor
	public Movie()
	{
		
	}
	
	public void loadNamefromInsert(String name)
	{
		foldername = name;
		String[] splitFolder = foldername.split("_",4);
		// Schreiben der einzelnen Filme in die CSV
		//writer.append(foldername);

		
		// Ausgabe des Split STrings
		if (splitFolder.length > 0)
			oTitle = splitFolder[splitFolder.length - 1]; // Letzten Teil des Strings ausgeben
		if (splitFolder.length > 1)
			oTag = splitFolder[splitFolder.length - 2]; // Letzten Teil des Strings ausgeben
		if (splitFolder.length > 2)
			oQually = splitFolder[splitFolder.length - 3]; // Letzten Teil des Strings ausgeben
		if (splitFolder.length > 3)
			oQually = " " + splitFolder[splitFolder.length - 4]; // Letzten Teil des Strings ausgeben
		// Ausgabe der größe
		//File m = new File (files[i].getAbsolutePath());
		// Größenberechnung 
		//filesize(getDirSize(m)); // Funktion Filesize gibt einen String mit Einhait zurück
	}
	
	public void loadIMDB (String mImdbID)
	{
		xmlConnect getxml = new xmlConnect();
		String[] data =  getxml.getMovieObject(mImdbID);
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
	
	// Get Funktionen
	public String gettitle ()
	{
		if (mTitle != "")
			return mTitle;
		else
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
}
