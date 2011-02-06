package src.etud.jeux.dominos;

import src.etud.iia.jeux.alg.Heuristique;
import src.etud.iia.jeux.modele.PlateauJeu;
import src.etud.iia.jeux.modele.joueur.Joueur;

public class HeuristiquesDominos {

	public static Heuristique hblanc = new Heuristique() {
		public int eval(PlateauJeu p, Joueur j) {
			PlateauDominos pdom = (PlateauDominos) p;
			int h = pdom.avantageBlanc();
			if (pdom.isJoueurBlanc(j))
				return h;
			else // joueur noir
				return -h;
		}
	};

	public static Heuristique hnoir = new Heuristique() {
		public int eval(PlateauJeu p, Joueur j) {
			PlateauDominos pdom = (PlateauDominos) p;
			int h = pdom.avantageBlanc();
			if (pdom.isJoueurBlanc(j))
				return h;
			else
				return -h;
		}
	};

}
