package src.etud.jeux.awale;

import src.etud.iia.jeux.modele.CoupJeu;

import src.etud.iia.jeux.modele.PlateauJeu;

import src.etud.iia.jeux.modele.joueur.Joueur;


import java.util.ArrayList;

public class PlateauAwalé implements PlateauJeu {

    /* Pour coder un nouveau jeu... il faut au minimum coder

     * - Une classe PlateauAwalé pour représenter l'état du "plateau"

     *  de ce jeu.

     *  Cette classe doit fournir le code des méthodes de l'interface PlateauJeu

     *  qui permettent de caractériser les règles du jeu

     *  Une classe CoupAwalé qui

     */

    /* *********** Paramètres de classe *********** */
    /** Le joueur que joue "1" */
    private static Joueur joueur1;
    /** Le joueur que joue "2" */
    private static Joueur joueur2;

    /* *********** Attributs  *********** */
    /** Le plateau à 2 rangées de trous */
    private int trous[][];
    /** Le nombre de graines capturées par chaque joueur */
    private int capture[];
    /** Le nombre de graines restantes en jeu */
    private int reste;

    /************* Constructeurs ****************/
    public PlateauAwalé() {
        reste = 48;
        trous = new int[2][6];
        capture = new int[2];
        for (int i = 0; i < 2; i++) {
            capture[i] = 0;
            for (int j = 0; j < 6; j++)
                trous[i][j] = 4;
        }
    }

    public PlateauAwalé(int[][] trou) {
        reste = 48;
        this.trous = new int[2][6];
        capture = new int[2];
        for (int i = 0; i < 2; i++) {
            capture[i] = 0;
            System.arraycopy(trou[i], 0, this.trous[i], 0, 6);
        }
    }

    public PlateauAwalé(int[][] trou, int[] capture, int reste) {
        this.reste = reste;
        this.trous = new int[2][6];
        this.capture = new int[2];
        System.arraycopy(capture, 0, this.capture, 0, 2);
        for (int i = 0; i < 2; i++) {
            System.arraycopy(trou[i], 0, this.trous[i], 0, 6);
        }
    }

    /************* Gestion des paramètres de classe ******************/
    public static void setJoueurs(Joueur j1, Joueur j2) {
        joueur1 = j1;
        joueur2 = j2;
    }

    public boolean isJoueur1(Joueur j1) {
        return joueur1.equals(j1);
    }

    public boolean isJoueur2(Joueur j2) {
        return joueur2.equals(j2);
    }

    public Joueur getJoueurAdverse(Joueur j) {
        if (this.isJoueur1(j)) // joueur2 : joueur adverse
            return joueur2;
        else  // joueur1 : joueur adverse
            return joueur1;
    }

    /************* Méthodes de l'interface PlateauJeu ****************/
    public ArrayList<CoupJeu> coupsPossibles(Joueur jo) {
        ArrayList<CoupJeu> lesCoupsPossibles = new ArrayList<CoupJeu>();
        if (finDePartie()) {
            return lesCoupsPossibles;
        }
        Joueur joueurAdverse = getJoueurAdverse(jo);
        int rangée = joueurToInt(jo); // rangée du tableau trous correspondant au joueur jo

        if (estAffamé(joueurAdverse)) { // l'adversaire n'a plus de graine
            for (int j = 0; j < 6; j++) {
                if (trous[rangée][j] != 0) {
                    CoupAwalé coup = new CoupAwalé(rangée, j);
                    if (estNourrissant(jo, coup)) // le coup doit nourrir l'adversaire
                    {
                        lesCoupsPossibles.add(coup);
                    }
                }
            }
        } else { // l'adversaire a des graines
            for (int j = 0; j < 6; j++) {
                if (trous[rangée][j] != 0) {
                    lesCoupsPossibles.add(new CoupAwalé(rangée, j));
                }
            }
        }
        return lesCoupsPossibles;
    }

    public void joue(Joueur j, CoupJeu c) {
        CoupAwalé ca = (CoupAwalé) c;
        int rangée = ca.getRangée();
        int trou = ca.getTrou();
        int nbGraines = trous[rangée][trou];
        int rangéeActuelle = rangée, trouActuel = trou;
        boolean coupAffamant = estAffamant(j, ca);

        trous[rangée][trou] = 0; // On prend toutes les graines du coup joué
        for (int i = 1; i <= nbGraines; i++) {  // On égraine
            int indice = trou + i;
            if (i % 12 == 0) {  // A VERIFIER !!!
                nbGraines++;
                continue;
            }
            int quotient = indice / 6;
            if (rangée == 0) {
                rangéeActuelle = rangée + quotient % 2;
            } else {
                rangéeActuelle = rangée - quotient % 2;
            }
            trouActuel = indice - quotient * 6; // Trou sur lequel on pose une graine
            trous[rangéeActuelle][trouActuel] += 1;
        }
        if ((!coupAffamant) && rangéeActuelle == joueurToInt(getJoueurAdverse(j))) { // Si on a posé la dernière graine chez l'adversaire
            for (; trouActuel >= 0; trouActuel--) {
                if (estCapturé(rangéeActuelle, trouActuel, j)) { // Condition de capture
                    capture[rangée] += trous[rangéeActuelle][trouActuel]; // On capture les graines
                    reste -= trous[rangéeActuelle][trouActuel];
                    trous[rangéeActuelle][trouActuel] = 0;
                } else {
                    break; // Si la condition de capture n'est pas remplie, on arrête là
                }
            }
        }
    }

    public boolean finDePartie() {
        return (reste <= 6 || capture[0] > 24 || capture[1] > 24);
    }

    public PlateauJeu copy() {
        return new PlateauAwalé(trous, capture, reste);
    }

    public boolean coupValide(Joueur joueur, CoupJeu c) {
        CoupAwalé ca = (CoupAwalé) c;
        Joueur joueurAdverse = getJoueurAdverse(joueur);
        int rangée = ca.getRangée();
        int trou = ca.getTrou();

        if (estAffamé(joueurAdverse)) { // l'adversaire n'a plus de graine
            if (trous[rangée][trou] != 0) {
                return (estNourrissant(joueur, ca)); // le coup doit nourrir l'adversaire
            } else
                return false;
        } else // l'adversaire a au moins une graine
            return (trous[rangée][trou] != 0);
    }
    
    /* ********************* Autres méthodes ***************** */

    public int avantageJ1() {
        if (finDePartie()) {
            if (capture[0] > capture[1]) {
                return Integer.MAX_VALUE;
            } else if (capture[0] == capture[1]) {
                return 0;
            } else {
                return Integer.MIN_VALUE;
            }
        }
        return (capture[0] - capture[1]);
    }

    @Override
    public String toString() {
        String retstr = "";
        for (int j = 5; j >= 0; j--) {
            if (j == 0) {
                retstr += trous[1][j];
            } else {
                retstr += trous[1][j] + " - ";
            }
        }
        retstr += "\n";
        for (int j = 0; j < 6; j++) {
            if (j == 5) {
                retstr += trous[0][j];
            } else {
                retstr += trous[0][j] + " - ";
            }
        }
        retstr += "\n";
        return retstr;
    }

    /** Associe à un joueur une rangée du tableau trous */
    private int joueurToInt(Joueur j){
        if (isJoueur1(j))
            return 0;
        else // Joueur 2
            return 1;
    }

    /* Détermine si un joueur n'a plus de graine */
    private boolean estAffamé(Joueur j) {
        for (int nbGraines : trous[joueurToInt(j)]) {
            if (nbGraines != 0)
                return false;
        }
        return true;
    }

    /* Détermine si un coup est apte à nourrir l'adversaire (si celui-ci est affamé) */
    private boolean estNourrissant(Joueur j, CoupAwalé c) {
        Joueur joueurAdverse = getJoueurAdverse(j);
        PlateauAwalé p = (PlateauAwalé) this.copy();
        p.joue(j, c); // joue le coup c sur une copie du plateau courant
        /* Le joueur adverse est-il affamé après qu'on ait joué le coup j ? */
        return !p.estAffamé(joueurAdverse);
    }

    /* Détermine si un coup affame l'adversaire */
    private boolean estAffamant(Joueur j, CoupAwalé c) {
        Joueur joueurAdverse = getJoueurAdverse(j);
        PlateauAwalé p = (PlateauAwalé) this.copy();
        p.joueFictive(j, c); // joue le coup c sur une copie du plateau courant
        /* Le joueur adverse est-il affamé après qu'on ait joué le coup j ? */
        return p.estAffamé(joueurAdverse);
    }

    private boolean estCapturé(int rangée, int trou, Joueur j) {
        return (trous[rangée][trou] == 2 || trous[rangée][trou] == 3);
    }

    public int getCapture(Joueur j) {
        return capture[joueurToInt(j)];
    }

    /* Joue "pour de faux" : pour déterminer si un coup donné va affamer l'adversaire */
    private void joueFictive(Joueur j, CoupJeu c) {
        CoupAwalé ca = (CoupAwalé) c;
        int rangée = ca.getRangée();
        int trou = ca.getTrou();
        int nbGraines = trous[rangée][trou];
        int rangéeActuelle = rangée, trouActuel = trou;

        trous[rangée][trou] = 0; // On prend toutes les graines du coup joué
        for (int i=1; i <= nbGraines; i++) {  // On égraine
            int indice = trou + i;
            if (i % 12 == 0) {  // A VERIFIER !!!
                nbGraines++;
                continue;
            }
            int quotient = indice / 6;
            if (rangée == 0) {
                rangéeActuelle = rangée + quotient % 2;
            }
            else {
                rangéeActuelle = rangée - quotient % 2;
            }
            trouActuel = indice - quotient * 6; // Trou sur lequel on pose une graine
            trous[rangéeActuelle][trouActuel] += 1;
        }
        if (rangéeActuelle == joueurToInt(getJoueurAdverse(j))) { // Si on a posé la dernière graine chez l'adversaire
            for (; trouActuel >= 0; trouActuel--) {
                if (estCapturé(rangéeActuelle, trouActuel, j)) { // Condition de capture
                    capture[rangée] += trous[rangéeActuelle][trouActuel]; // On capture les graines
                    reste -= trous[rangéeActuelle][trouActuel];
                    trous[rangéeActuelle][trouActuel] = 0;
                } else {
                    break; // Si la condition de capture n'est pas remplie, on arrête là
                }
            }
        }
    }

}



