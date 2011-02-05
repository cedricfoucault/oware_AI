package src.etud.iia.jeux.alg;

import src.etud.iia.jeux.modele.CoupJeu;
import src.etud.iia.jeux.modele.PlateauJeu;

public interface AlgoJeu {

    /** Renvoie le meilleur
     * @param p
     * @return
     */
	public CoupJeu meilleurCoup(PlateauJeu p);
        public int getNbnoeuds();

}
 