package src.etud.jeux.dominos;
import src.etud.iia.jeux.modele.CoupJeu;

public class CoupDominos implements CoupJeu{

	/****** Attributs *******/ 

	private int ligne;

	private int colonne;


	/****** Clonstructeur *******/ 

	public CoupDominos(int l, int c) {
		ligne = l;
		colonne = c;
	}

	/****** Accesseurs *******/ 

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	/****** Accesseurs *******/ 

	public String toString() {
		return "("+ligne+","+colonne+")";
	}
}

