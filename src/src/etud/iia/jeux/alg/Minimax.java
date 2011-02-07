/**
 * 
 */
package src.etud.iia.jeux.alg;

import java.util.ArrayList;

import src.etud.iia.jeux.modele.CoupJeu;
import src.etud.iia.jeux.modele.PlateauJeu;
import src.etud.iia.jeux.modele.joueur.Joueur;


import java.lang.Math;

public class Minimax implements AlgoJeu {

    /** La profondeur de recherche par défaut
     */
    private final static int PROFMAXDEFAUT = 4;
    /**  La profondeur de recherche utilisée pour l'algorithme
     */
    private int profMax;
    /**  L'heuristique utilisée par l'algorithme
     */
    private Heuristique h;
    /** Le joueur Min
     *  (l'adversaire) */
    private Joueur joueurMin;
    /** Le joueur Max
     * (celui dont l'algorithme de recherche adopte le point de vue) */
    private Joueur joueurMax;
    /**  Le nombre de noeuds développé par l'algorithme
     * (facultatif... mais peut être intéressant pour se faire
     * une idée du nombre de noeuds développés) */
    private int nbnoeuds;
    /** Le nombre de feuilles évaluées par l'algorithme
     * (facultatif) */
    private int nbfeuilles;

    private CoupJeu meilleurCoup;

    // -------------------------------------------
    // Constructeurs
    // -------------------------------------------
    public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
    }

    public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
        nbnoeuds = 0;
//		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
    }

    // -------------------------------------------
    // Méthodes de l'interface AlgoJeu
    // -------------------------------------------
    //TODO: eviter NULL exception
    public CoupJeu meilleurCoup(PlateauJeu p) {
        meilleurCoup = null;
        nbnoeuds = 0;
        ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMax);

        int meilleur = Integer.MIN_VALUE;

        for (CoupJeu c : coupsPossibles) {
            nbnoeuds++;
            PlateauJeu s = jouerBeurre(p, joueurMax, c);
            int minMax = minMax(s, profMax - 1);
            if (meilleur < minMax) {
                meilleur = minMax;
                meilleurCoup = c;
            }
        }
        return meilleurCoup;

    }

    public int getNbnoeuds() {
        return nbnoeuds;
    }

    // -------------------------------------------
    // Méthodes publiques
    // -------------------------------------------
    @Override
    public String toString() {
        return "MiniMax(ProfMax=" + profMax + ")";
    }

    // -------------------------------------------
    // Méthodes internes
    // -------------------------------------------
    //A vous de jouer pour implanter Minimax
    private int maxMin(PlateauJeu p, int profondeur) {
        ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMax);

        if (profondeur == 0 || coupsPossibles.isEmpty()) {
            return h.eval(p, joueurMax);
        }

        int meilleur = Integer.MIN_VALUE;

        for (CoupJeu c : coupsPossibles) {
            nbnoeuds++;
            PlateauJeu s = jouerBeurre(p, joueurMax, c);
            meilleur = Math.max(meilleur, minMax(s, profondeur - 1));
        }
        return meilleur;
    }

    private int minMax(PlateauJeu p, int profondeur) {
        ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMin);
        if (profondeur == 0 || coupsPossibles.isEmpty()) {
            return h.eval(p, joueurMax);
        }

        int pire = Integer.MAX_VALUE;

        for (CoupJeu c : coupsPossibles) {
            nbnoeuds++;
            PlateauJeu s = jouerBeurre(p, joueurMin, c);
            pire = Math.min(pire, maxMin(s, profondeur - 1));
        }

        return pire;
    }

    private PlateauJeu jouerBeurre(PlateauJeu p, Joueur j, CoupJeu c) {
        PlateauJeu s = p.copy();
        s.joue(j, c);
        return s;
    }
}
