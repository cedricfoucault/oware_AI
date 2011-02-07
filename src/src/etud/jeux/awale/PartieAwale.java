/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.etud.jeux.awale;

import src.etud.iia.jeux.alg.AlgoJeu;
import src.etud.iia.jeux.alg.Alphabeta;
import src.etud.iia.jeux.alg.Minimax;
import src.etud.iia.jeux.modele.CoupJeu;
import src.etud.iia.jeux.modele.PlateauJeu;
import src.etud.iia.jeux.modele.joueur.Joueur;
import java.util.ArrayList;

/**
 *
 * @author Cedric
 */
public class PartieAwale {

    public static void main(String[] args) {

        Joueur j1 = new Joueur("1");
        Joueur j2 = new Joueur("2");

        Joueur[] lesJoueurs = new Joueur[2];

        lesJoueurs[0] = j1;
        lesJoueurs[1] = j2;

        int[][] tabScore = new int[2][13]; // Pour stocker les scores des joueurs à chaque profondeur
        double[] rapportNbNoeuds = new double[13]; // Pour stocker le rapport nbNoeuds moyen developpés par minimax / alphabeta à chaque profondeur

        System.out.println("TD IIA n.4 - Algorithmes pour les Jeux");

        AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
        AlgoJoueur[0] = new Minimax(HeuristiquesAwale.h1, j1, j2, 5);
        for (int profondeurMax = 1; profondeurMax < 12; profondeurMax++) {
            AlgoJoueur[1] = new Alphabeta(HeuristiquesAwale.h2, j2, j1, profondeurMax);
            // AlgoJoueur[1] = new Humain();
            System.out.println("PARTIE POUR UNE RECHERCHE ALPHABETA A PROFONDEUR "
                    + profondeurMax + " (joueur 2)\n");
            System.out.println("Etat Initial du plateau de jeu:");

            boolean jeufini = false;
            CoupJeu meilleurCoup = null;
            int jnum;
            int[] nbnoeudsTotal = {0, 0}; // Contient le nombre de noeuds développés au total par chaque joueur
            int[] nbCoups = {0, 0}; // Contient le nombre de coups joués par chaque joueur
            int nbnoeudsCoup = 0; // Nombre de noeuds développés à chaque coup

            PlateauAwale.setJoueurs(j1, j2); // Pour savoir qui joue "1" et qui joue "2"
            PlateauJeu plateauCourant = new PlateauAwale(lesJoueurs[0]); // On commence par le Joueur Blanc (arbitraire)

            // A chaque itération de la boucle, on fait jouer un des deux joueurs
            // tour a tour
            jnum = 0; // On commence par le joueur Blanc (arbitraire)

            // on itere sur les  joueurs, tant que le jeu n'est pas fini
            while (!jeufini) {
                System.out.println("" + plateauCourant + "\n");
                System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");
                // Vérifie qu'il y a bien des coups possibles
                // Ce n'est pas tres efficace, mais c'est plus rapide... à écrire...
                ArrayList<CoupJeu> lesCoupsPossibles = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
                System.out.println("Coups possibles pour " + lesJoueurs[jnum] + " : " + lesCoupsPossibles);
                if (lesCoupsPossibles.size() > 0) {
                    // On écrit le plateau

                    // Lancement de l'algo de recherche du meilleur coup
                    System.out.println("Recherche du meilleur coup avec l'algo " + AlgoJoueur[jnum]);
                    meilleurCoup = AlgoJoueur[jnum].meilleurCoup(plateauCourant);
                    nbnoeudsCoup = AlgoJoueur[jnum].getNbnoeuds();
                    nbnoeudsTotal[jnum] += nbnoeudsCoup;
                    System.out.println("Nombre de noeuds développés " + nbnoeudsCoup);
                    System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + lesJoueurs[jnum]);

                    plateauCourant.joue(lesJoueurs[jnum], meilleurCoup);
                    System.out.println("Le joueur " + (jnum + 1) + " a capturé " + ((PlateauAwale) plateauCourant).getScore(lesJoueurs[jnum]) + " graines");
                    nbCoups[jnum]++;
                    // Le coup est effectivement joué
                    jnum = 1 - jnum;

                } else {
                    System.out.println("Le joueur " + lesJoueurs[jnum] + " ne peut plus jouer !\n");
                    int[] score = new int[2];
                    score[jnum] = ((PlateauAwale) plateauCourant).getScore(lesJoueurs[jnum]);
                    score[1 - jnum] = ((PlateauAwale) plateauCourant).getScore(lesJoueurs[1 - jnum]);
                    System.out.println("Le joueur " + (jnum + 1) + " a capturé " + ((PlateauAwale) plateauCourant).getScore(lesJoueurs[jnum]) + " graines");
                    System.out.println("Le joueur " + (2 - jnum) + " a capturé " + ((PlateauAwale) plateauCourant).getScore(lesJoueurs[1 - jnum]) + " graines");
                    if (score[1 - jnum] > score[jnum]) {
                        System.out.println("Le joueur " + lesJoueurs[1 - jnum] + " a gagné cette partie !");
                    } else if (score[1 - jnum] == score[jnum]) {
                        System.out.println("Match nul !");
                    } else {
                        System.out.println("Le joueur " + lesJoueurs[jnum] + " a gagné cette partie !");
                    }
                    double[] nbNoeudsMoyen = new double[2];
                    nbNoeudsMoyen[0] = ((double)nbnoeudsTotal[0]) / nbCoups[0];
                    nbNoeudsMoyen[1] = ((double)nbnoeudsTotal[1]) / nbCoups[1];
                    System.out.printf("\nNombre de noeuds moyen développés par le joueur 1 : %.1f\n", nbNoeudsMoyen[0]);
                    System.out.printf("Nombre de noeuds moyen développés par le joueur 2 : %.1f\n", nbNoeudsMoyen[1]);

                    tabScore[jnum][profondeurMax - 1] = score [jnum];
                    tabScore[1 - jnum][profondeurMax - 1] = score[1 - jnum];
                    rapportNbNoeuds[profondeurMax - 1] = Math.log10(nbNoeudsMoyen[1] / nbNoeudsMoyen[0]);

                    jeufini = true;
                }
            }
        }
        /************* BILAN **********/
        System.out.println("\nBILAN DES MATCHS :\n");
        for (int profondeur = 1; profondeur < 12; profondeur++) {
            System.out.println("J1(Minimax horizon 5) - J2(Alphabeta horizon " + profondeur + ") : "
                    + tabScore[0][profondeur-1] + " - " + tabScore[1][profondeur-1]);
        }
            System.out.println("\nBILAN DES CALCULS");
            System.out.println("LOG10(Rapport du nombre moyen de noeuds développés Alphabeta / Minimax) : \n");
        for (int profondeur = 1; profondeur < 12; profondeur++) {
            System.out.printf("LOG10(NbNoeuds(Alphabeta horizon " + profondeur + ") / NbNoeuds(Minimax horizon 5) : %6.4f\n", rapportNbNoeuds[profondeur - 1]);
        }
    }
}
