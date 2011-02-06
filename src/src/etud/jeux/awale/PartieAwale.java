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



        AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
        AlgoJoueur[0] = new Minimax(HeuristiquesAwale.h1, j1, j2, 4); // Il faut remplir la méthode !!!
        AlgoJoueur[1] = new Alphabeta(HeuristiquesAwale.h2, j2, j1, 4); // Il faut remplir la méthode !!!
        // AlgoJoueur[1] = new Humain();

        System.out.println("TD IIA n.4 - Algorithmes pour les Jeux");
        System.out.println("Etat Initial du plateau de jeu:");

        boolean jeufini = false;
        CoupJeu meilleurCoup = null;
        int jnum;
        int[] nbnoeudsTotal = {0,0}; // Contient le nombre de noeuds développés au total par chaque joueur
        int[] nbCoups = {0,0}; // Contient le nombre de coups joués par chaque joueur
        int nbnoeudsCoup = 0; // Nombre de noeuds développés à chaque coup

        PlateauJeu plateauCourant = new PlateauAwale();
        PlateauAwale.setJoueurs(j1, j2);
        // Pour savoir qui joue "noir" et qui joue "blanc"


        // A chaque itération de la boucle, on fait jouer un des deux joueurs
        // tour a tour
        jnum = 0; // On commence par le joueur Blanc (arbitraire)

        // on itere sur les  joueurs, tant que le jeu n'est pas fini
        while (!jeufini) {
            System.out.println("" + plateauCourant);
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
                System.out.println("Le joueur " + (jnum+1) + " a capturé " + ((PlateauAwale)plateauCourant).getScore(lesJoueurs[jnum]) + " graines");
                nbCoups[jnum]++;
                // Le coup est effectivement joué
                jnum = 1 - jnum;

            } else {
                System.out.println("Le joueur " + lesJoueurs[jnum] + " ne peut plus jouer et abandone !");
                System.out.println("Le joueur " + lesJoueurs[1 - jnum] + " a gagné cette partie !");
                System.out.println("Nombre de noeuds moyen développés par le joueur Blanc : " + nbnoeudsTotal[0] / nbCoups[0]);
                System.out.println("Nombre de noeuds moyen développés par le joueur Noir : " + nbnoeudsTotal[1] / nbCoups[1]);
                jeufini = true;

            }
        }
    }
}
