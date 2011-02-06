/**
 * 
 */
package src.etud.iia.jeux.alg;

import java.util.ArrayList;

import src.etud.iia.jeux.modele.CoupJeu;
import src.etud.iia.jeux.modele.PlateauJeu;
import src.etud.iia.jeux.modele.joueur.Joueur;

public class Alphabeta implements AlgoJeu {

    /** La profondeur de recherche par défaut
     */
    private final static int PROFMAXDEFAUT = 4;
    /**  La profondeur de recherche utilisée pour l'algorithme
     */
    private static int profMax = PROFMAXDEFAUT;
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
    public Alphabeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
    }

    public Alphabeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
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
    public CoupJeu meilleurCoup(PlateauJeu p) {
        meilleurCoup = null;
        nbnoeuds = 0;
        ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMax);

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (CoupJeu c : coupsPossibles) {
            nbnoeuds++;
            PlateauJeu s = p.copy();
            s.joue(joueurMax, c);
            int minValue = minValue(s, profMax - 1, alpha, beta);
            if (alpha < minValue) {
                alpha = minValue;
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
        return "AlphaBeta(ProfMax=" + profMax + ")";
    }

    // -------------------------------------------
    // Méthodes internes
    // -------------------------------------------
    //A vous de jouer pour implanter Minimax
    private int maxValue(PlateauJeu p, int profondeur, int alpha, int beta) {
        ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMax);
        if (profondeur == 0 || coupsPossibles.isEmpty()) {
            return h.eval(p, joueurMax);
        }

        for (CoupJeu c : coupsPossibles) {
            nbnoeuds++;
            PlateauJeu s = jouerBeurre(p, joueurMax, c);
            alpha = Math.max(alpha, minValue(s, profondeur - 1, alpha, beta));
            if (alpha >= beta) {
                return beta;
            }
        }
        return alpha;
    }

    private int minValue(PlateauJeu p, int profondeur, int alpha, int beta) {
        ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(joueurMin);
        if (profondeur == 0 || coupsPossibles.isEmpty()) {
            return h.eval(p, joueurMax);
        }

        for (CoupJeu c : coupsPossibles) {
            nbnoeuds++;
            PlateauJeu s = jouerBeurre(p, joueurMin, c);
            beta = Math.min(beta, maxValue(s, profondeur - 1, alpha, beta));
            if (alpha >= beta) {
                return alpha;
            }
        }
        return beta;
    }

    private PlateauJeu jouerBeurre(PlateauJeu p, Joueur j, CoupJeu c) {
        PlateauJeu s = p.copy();
        s.joue(j, c);
        return s;
    }
}
