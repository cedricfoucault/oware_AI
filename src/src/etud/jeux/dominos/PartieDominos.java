package src.etud.jeux.dominos;

import src.etud.iia.jeux.alg.AlgoJeu;
import src.etud.iia.jeux.alg.Alphabeta;
import src.etud.iia.jeux.alg.Minimax;
import src.etud.iia.jeux.modele.CoupJeu;
import src.etud.iia.jeux.modele.PlateauJeu;
import src.etud.iia.jeux.modele.joueur.Joueur;
import java.util.ArrayList;

//public class PartieDominos {
//
//    public static void main(String[] args) {
//
//        Joueur jBlanc = new Joueur("Blanc");
//        Joueur jNoir = new Joueur("Noir");
//
//        Joueur[] lesJoueurs = new Joueur[2];
//
//        lesJoueurs[0] = jBlanc;
//        lesJoueurs[1] = jNoir;
//
//        System.out.println("TD IIA n.3 - Algorithmes pour les Jeux\n");
//
//        AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
//        AlgoJoueur[1] = new Minimax(HeuristiquesDominos.hnoir, jNoir, jBlanc);
//        for (int profondeurMax = 1; profondeurMax < 10; profondeurMax++) {
//            AlgoJoueur[0] = new Minimax(HeuristiquesDominos.hblanc, jBlanc, jNoir, profondeurMax);
//
//            // AlgoJoueur[1] = new Humain();
//            System.out.println("PARTIE POUR UNE RECHERCHE MINIMAX A PROFONDEUR "
//                               + profondeurMax + " (joueur Blanc)\n");
//            System.out.println("Etat Initial du plateau de jeu:");
//
//            boolean jeufini = false;
//            CoupJeu meilleurCoup = null;
//            int jnum;
//            int[] nbnoeudsTotal = {0, 0}; // Contient le nombre de noeuds développés au total par chaque joueur
//            int[] nbCoups = {0, 0}; // Contient le nombre de coups joués par chaque joueur
//            int nbnoeudsCoup = 0; // Nombre de noeuds développés à chaque coup
//
//            PlateauJeu plateauCourant = new PlateauDominos();
//            PlateauDominos.setJoueurs(jBlanc, jNoir);
//            // Pour savoir qui joue "noir" et qui joue "blanc"
//
//
//            // A chaque itération de la boucle, on fait jouer un des deux joueurs
//            // tour a tour
//            jnum = 0; // On commence par le joueur Blanc (arbitraire)
//
//            // on itere sur les  joueurs, tant que le jeu n'est pas fini
//            while (!jeufini) {
//                System.out.println("" + plateauCourant);
//                System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");
//                // Vérifie qu'il y a bien des coups possibles
//                // Ce n'est pas tres efficace, mais c'est plus rapide... à écrire...
//                ArrayList<CoupJeu> lesCoupsPossibles = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
//                System.out.println("Coups possibles pour " + lesJoueurs[jnum] + " : " + lesCoupsPossibles);
//                if (lesCoupsPossibles.size() > 0) {
//                    // On écrit le plateau
//
//                    // Lancement de l'algo de recherche du meilleur coup
//                    System.out.println("Recherche du meilleur coup avec l'algo " + AlgoJoueur[jnum]);
//                    meilleurCoup = AlgoJoueur[jnum].meilleurCoup(plateauCourant);
//                    nbnoeudsCoup = AlgoJoueur[jnum].getNbnoeuds();
//                    nbnoeudsTotal[jnum] += nbnoeudsCoup;
//                    System.out.println("Nombre de noeuds développés : " + nbnoeudsCoup);
//                    System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + lesJoueurs[jnum]);
//
//                    plateauCourant.joue(lesJoueurs[jnum], meilleurCoup);
//                    nbCoups[jnum]++;
//                    // Le coup est effectivement joué
//                    jnum = 1 - jnum;
//
//                } else {
//                    System.out.println("Le joueur " + lesJoueurs[jnum] + " ne peut plus jouer et abandone !");
//                    System.out.println("Le joueur " + lesJoueurs[1 - jnum] + " a gagné cette partie !");
//                    int[] nbMoyen = {0,0}; // Contient le nombre moyen de noeuds développés par chaque joueur
//                    nbMoyen[0] = nbnoeudsTotal[0] / nbCoups[0];
//                    nbMoyen[1] = nbnoeudsTotal[1] / nbCoups[1];
//                    System.out.println("Nombre moyen de noeuds développés par le joueur Blanc : " + nbMoyen[0]);
//                    System.out.println("Nombre moyen de noeuds développés par le joueur Noir : " + nbMoyen[1]);
//                    System.out.printf("A un horizon de " + profondeurMax +
//                                       ", Minimax a développé en moyenne %.3f"
//                                       + " fois plus de noeuds que MiniMax à un horizon de 4", (double)nbMoyen[0] / (double)nbMoyen[1]);
//                    jeufini = true;
//
//                }
//            }
//            System.out.println("\n--------------------------------------------"
//                               + "-------------------------------------------\n");
//        }
//    }
//}
public class PartieDominos {

    public static void main(String[] args) {

        Joueur jBlanc = new Joueur("Blanc");
        Joueur jNoir = new Joueur("Noir");

        Joueur[] lesJoueurs = new Joueur[2];

        lesJoueurs[0] = jBlanc;
        lesJoueurs[1] = jNoir;

        System.out.println("TD IIA n.3 - Algorithmes pour les Jeux\n");

        AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
        AlgoJoueur[1] = new Minimax(HeuristiquesDominos.hnoir, jNoir, jBlanc);
        for (int profondeurMax = 1; profondeurMax < 10; profondeurMax++) {
            AlgoJoueur[0] = new Alphabeta(HeuristiquesDominos.hblanc, jBlanc, jNoir, profondeurMax);

            // AlgoJoueur[1] = new Humain();
            System.out.println("PARTIE POUR UNE RECHERCHE ALPHABETA A PROFONDEUR "
                               + profondeurMax + " (joueur Blanc)\n");
            System.out.println("Etat Initial du plateau de jeu:");

            boolean jeufini = false;
            CoupJeu meilleurCoup = null;
            int jnum;
            int[] nbnoeudsTotal = {0, 0}; // Contient le nombre de noeuds développés au total par chaque joueur
            int[] nbCoups = {0, 0}; // Contient le nombre de coups joués par chaque joueur
            int nbnoeudsCoup = 0; // Nombre de noeuds développés à chaque coup

            PlateauJeu plateauCourant = new PlateauDominos();
            PlateauDominos.setJoueurs(jBlanc, jNoir);
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
                    System.out.println("Nombre de noeuds développés : " + nbnoeudsCoup);
                    System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + lesJoueurs[jnum]);

                    plateauCourant.joue(lesJoueurs[jnum], meilleurCoup);
                    nbCoups[jnum]++;
                    // Le coup est effectivement joué
                    jnum = 1 - jnum;

                } else {
                    System.out.println("Le joueur " + lesJoueurs[jnum] + " ne peut plus jouer et abandone !");
                    System.out.println("Le joueur " + lesJoueurs[1 - jnum] + " a gagné cette partie !");
                    int[] nbMoyen = {0,0}; // Contient le nombre moyen de noeuds développés par chaque joueur
                    nbMoyen[0] = nbnoeudsTotal[0] / nbCoups[0];
                    nbMoyen[1] = nbnoeudsTotal[1] / nbCoups[1];
                    System.out.println("Nombre moyen de noeuds développés par le joueur Blanc : " + nbMoyen[0]);
                    System.out.println("Nombre moyen de noeuds développés par le joueur Noir : " + nbMoyen[1]);
                    System.out.printf("A un horizon de " + profondeurMax +
                                       ", AlphaBeta a développé en moyenne %.3f"
                                       + " fois plus de noeuds que MiniMax à un horizon de 4", (double)nbMoyen[0] / (double)nbMoyen[1]);
                    jeufini = true;

                }
            }
            System.out.println("\n--------------------------------------------"
                               + "-------------------------------------------\n");
        }
    }
}
//package src.etud.jeux.dominos;
//
//import src.etud.iia.jeux.alg.AlgoJeu;
//import src.etud.iia.jeux.alg.Alphabeta;
//import src.etud.iia.jeux.alg.Minimax;
//import src.etud.iia.jeux.modele.CoupJeu;
//import src.etud.iia.jeux.modele.PlateauJeu;
//import src.etud.iia.jeux.modele.joueur.Joueur;
//import java.util.ArrayList;
//
//public class PartieDominos {
//
//    public static void main(String[] args) {
//
//        Joueur jBlanc = new Joueur("Blanc");
//        Joueur jNoir = new Joueur("Noir");
//
//        Joueur[] lesJoueurs = new Joueur[2];
//
//        lesJoueurs[0] = jBlanc;
//        lesJoueurs[1] = jNoir;
//
//
//
//        AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
//        AlgoJoueur[0] = new Alphabeta(HeuristiquesDominos.hblanc, jBlanc, jNoir); // Il faut remplir la méthode !!!
//        AlgoJoueur[1] = new Minimax(HeuristiquesDominos.hnoir, jNoir, jBlanc); // Il faut remplir la méthode !!!
//        // AlgoJoueur[1] = new Humain();
//
//        System.out.println("TD IIA n.3 - Algorithmes pour les Jeux");
//        System.out.println("Etat Initial du plateau de jeu:");
//
//        boolean jeufini = false;
//        CoupJeu meilleurCoup = null;
//        int jnum;
//        int[] nbnoeudsTotal = {0,0}; // Contient le nombre de noeuds développés au total par chaque joueur
//        int[] nbCoups = {0,0}; // Contient le nombre de coups joués par chaque joueur
//        int nbnoeudsCoup = 0; // Nombre de noeuds développés à chaque coup
//
//        PlateauJeu plateauCourant = new PlateauDominos();
//        PlateauDominos.setJoueurs(jBlanc, jNoir);
//        // Pour savoir qui joue "noir" et qui joue "blanc"
//
//
//        // A chaque itération de la boucle, on fait jouer un des deux joueurs
//        // tour a tour
//        jnum = 0; // On commence par le joueur Blanc (arbitraire)
//
//        // on itere sur les  joueurs, tant que le jeu n'est pas fini
//        while (!jeufini) {
//            System.out.println("" + plateauCourant);
//            System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");
//            // Vérifie qu'il y a bien des coups possibles
//            // Ce n'est pas tres efficace, mais c'est plus rapide... à écrire...
//            ArrayList<CoupJeu> lesCoupsPossibles = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
//            System.out.println("Coups possibles pour " + lesJoueurs[jnum] + " : " + lesCoupsPossibles);
//            if (lesCoupsPossibles.size() > 0) {
//                // On écrit le plateau
//
//                // Lancement de l'algo de recherche du meilleur coup
//                System.out.println("Recherche du meilleur coup avec l'algo " + AlgoJoueur[jnum]);
//                meilleurCoup = AlgoJoueur[jnum].meilleurCoup(plateauCourant);
//                nbnoeudsCoup = AlgoJoueur[jnum].getNbnoeuds();
//                nbnoeudsTotal[jnum] += nbnoeudsCoup;
//                System.out.println("Nombre de noeuds développés " + nbnoeudsCoup);
//                System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + lesJoueurs[jnum]);
//
//                plateauCourant.joue(lesJoueurs[jnum], meilleurCoup);
//                nbCoups[jnum]++;
//                // Le coup est effectivement joué
//                jnum = 1 - jnum;
//
//            } else {
//                System.out.println("Le joueur " + lesJoueurs[jnum] + " ne peut plus jouer et abandone !");
//                System.out.println("Le joueur " + lesJoueurs[1 - jnum] + " a gagné cette partie !");
//                System.out.println("Nombre de noeuds moyen développés par le joueur Blanc : " + nbnoeudsTotal[0] / nbCoups[0]);
//                System.out.println("Nombre de noeuds moyen développés par le joueur Noir : " + nbnoeudsTotal[1] / nbCoups[1]);
//                jeufini = true;
//
//            }
//        }
//    }
//}
