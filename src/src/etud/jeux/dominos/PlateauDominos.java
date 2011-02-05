package src.etud.jeux.dominos;

import src.etud.iia.jeux.modele.CoupJeu;
import src.etud.iia.jeux.modele.PlateauJeu;
import src.etud.iia.jeux.modele.joueur.Joueur;

import java.io.PrintStream;
import java.util.ArrayList;

public class PlateauDominos implements PlateauJeu {

    /* *********** constantes *********** */
    /** Taille de la grille */
    public final static int TAILLE = 7;

    /* *********** Paramètres de classe *********** */
    private final static int VIDE = 0;
    private final static int BLANC = 1;
    private final static int NOIR = 2;
    /** Le joueur que joue "Blanc" */
    private static Joueur joueurBlanc;
    /** Le joueur que joue "noir" */
    private static Joueur joueurNoir;

    /* *********** Attributs  *********** */
    /** le damier */
    private int damier[][];

    /************* Constructeurs ****************/
    public PlateauDominos() {
        damier = new int[TAILLE][TAILLE];
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                damier[i][j] = VIDE;
            }
        }
    }

    public PlateauDominos(int depuis[][]) {
        damier = new int[TAILLE][TAILLE];
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                damier[i][j] = depuis[i][j];
            }
        }
    }

    /************* Gestion des paramètres de classe ******************/
    public static void setJoueurs(Joueur jb, Joueur jn) {
        joueurBlanc = jb;
        joueurNoir = jn;
    }

    public boolean isJoueurBlanc(Joueur jb) {
        return joueurBlanc.equals(jb);
    }

    public boolean isJoueurNoir(Joueur jn) {
        return joueurNoir.equals(jn);
    }

    /************* Méthodes de l'interface PlateauJeu ****************/
    public PlateauJeu copy() {
        return new PlateauDominos(this.damier);
    }

    public boolean coupValide(Joueur joueur, CoupJeu c) {
        CoupDominos cd = (CoupDominos) c;
        int ligne = cd.getLigne();
        int colonne = cd.getColonne();
        return coupValide(joueur, ligne, colonne);
    }

    public ArrayList<CoupJeu> coupsPossibles(Joueur joueur) {
        ArrayList<CoupJeu> lesCoupsPossibles = new ArrayList<CoupJeu>();
        if (joueur.equals(joueurBlanc)) {
            for (int i = 0; i < TAILLE; i++) { // toutes les lignes
                for (int j = 0; j < TAILLE - 1; j++) { // regarde sur une colonne
                    if ((damier[i][j] == VIDE) && (damier[i][j + 1] == VIDE)) // on peut jouer
                    {
                        lesCoupsPossibles.add(new CoupDominos(i, j));
                    }
                }
            }
        } else { // Noir
            for (int i = 0; i < TAILLE - 1; i++) { // toutes les lignes qui passent
                for (int j = 0; j < TAILLE; j++) { // regarde sur toute colonne
                    if ((damier[i][j] == VIDE) && (damier[i + 1][j] == VIDE)) // on peut jouer
                    {
                        lesCoupsPossibles.add(new CoupDominos(i, j));
                    }
                }
            }
        }
        return lesCoupsPossibles;
    }

    public boolean finDePartie() {
        int nbCoupsBlanc = this.coupsPossibles(joueurBlanc).size();
        int nbCoupsNoir = this.coupsPossibles(joueurNoir).size();
        return (nbCoupsBlanc == 0 || nbCoupsNoir == 0);
    }

    public void joue(Joueur joueur, CoupJeu c) {
        CoupDominos cd = (CoupDominos) c;
        int ligne = cd.getLigne();
        int colonne = cd.getColonne();
        if (joueur.equals(joueurBlanc)) {
            damier[ligne][colonne] = BLANC;
            damier[ligne][colonne + 1] = BLANC;
        } else {
            damier[ligne][colonne] = NOIR;
            damier[ligne + 1][colonne] = NOIR;
        }
    }

    /* ********************* Autres méthodes ***************** */
    private boolean coupValide(Joueur joueur, int l, int c) {
        if (joueur.equals(joueurBlanc)) {
            return (damier[l][c] == VIDE && damier[l][c + 1] == VIDE);
        } else {
            return (damier[l][c] == VIDE && damier[l + 1][c] == VIDE);
        }
    }

    @Override
    public String toString() {
        String retstr = new String("");
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (damier[i][j] == VIDE) {
                    retstr += "-";
                } else if (damier[i][j] == BLANC) {
                    retstr += "O";
                } else // damier[i][j] == NOIR
                {
                    retstr += "#";
                }
            }
            retstr += "\n";
        }
        return retstr;
    }

    public void printPlateau(PrintStream out) {
        out.println(this.toString());
    }

    public int avantageBlanc() {
        int nbCoupsBlanc = 0;
        int nbCoupsNoir = 0;
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE - 1; j++) {
                if (damier[i][j] == VIDE && damier[i][j + 1] == VIDE) {
                    nbCoupsBlanc++;
                }
                if (damier[j][i] == VIDE && damier[j + 1][i] == VIDE) {
                    nbCoupsNoir++;
                }
            }
        }
        if (nbCoupsBlanc == 0) {
            return -java.lang.Integer.MAX_VALUE; // perdu
        } else if (nbCoupsNoir == 0) {
            return java.lang.Integer.MAX_VALUE; // gagné
        } else {
            return (nbCoupsBlanc - nbCoupsNoir);
        }

    }
}
