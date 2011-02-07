package src.etud.jeux.awale;

import src.etud.iia.jeux.modele.CoupJeu;
import src.etud.iia.jeux.modele.joueur.Joueur;

public class CoupAwale implements CoupJeu {

    /****** Attributs *******/
    /** L'indice du trou (dans le tableau trous) à jouer */
    private int indiceTrou;

    /****** Clonstructeur *******/
    public CoupAwale(int indiceTrou) {
        this.indiceTrou = indiceTrou;
    }

    /****** Accesseurs *******/
    public int getIndiceTrou() {
        return indiceTrou;
    }

    /****** Accesseurs *******/
    @Override
    public String toString() {
        return "(" + (indiceTrou+1) + ")";
    }
}
