/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.etud.jeux.awale;
import src.etud.iia.jeux.alg.Heuristique;
import src.etud.iia.jeux.modele.PlateauJeu;
import src.etud.iia.jeux.modele.joueur.Joueur;

/**
 *
 * @author Cedric
 */
public class HeuristiquesAwale {

	public static Heuristique h1 = new Heuristique() {
		public int eval(PlateauJeu p, Joueur j) {
			PlateauAwale pAwal = (PlateauAwale) p;
			int h = pAwal.avantageJ1();
			if (PlateauAwale.isJoueur1(j))
				return h;
			else
				return -h;
		}
	};

	public static Heuristique h2 = new Heuristique() {
		public int eval(PlateauJeu p, Joueur j) {
			PlateauAwale pAwal = (PlateauAwale) p;
			int h = pAwal.avantageJ1();
			if (PlateauAwale.isJoueur1(j))
				return h;
			else
				return -h;
		}
	};


}
