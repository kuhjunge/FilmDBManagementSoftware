package sort;

import java.awt.EventQueue;

public class FilmManagerMain {

	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final FilmManagerGUI fmgui = new FilmManagerGUI();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fmgui.frmFilmManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}


/**
* Gibt das Objekt der ausgewählten Spielfigur zurück
* @param id - Die ID der Figur
* @return Das Objekt der Spielfigur
*/