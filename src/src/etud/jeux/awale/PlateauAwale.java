package src.etud.jeux.awale;

import src.etud.iia.jeux.modele.CoupJeu;

import src.etud.iia.jeux.modele.PlateauJeu;

import src.etud.iia.jeux.modele.joueur.Joueur;


import java.util.ArrayList;

public class PlateauAwale implements PlateauJeu {

    /* *********** Paramètres de classe *********** */
    /** Le joueur que joue "1" */
    private static Joueur joueur1;
    /** Le joueur que joue "2" */
    private static Joueur joueur2;

    /* *********** Attributs  *********** */
    /** Le plateau à 2 rangées de trous */
    private Trou[] trous;
    /** Le nombre de graines capturées par chaque joueur (indice 0 : Joueur 1, indice 1 : Joueur 2) */
    private int[] score;
    /** Garde en memoire le joueur qui vient de jouer */
    private Joueur vientDeJouer;

    /* *********** Classe interne *********** */
    /** Un trou (case) du jeu */
    private class Trou {

        /* *********** Attributs  *********** */
        /** Le "propriétaire" du trou */
        private Joueur propriétaire;
        /** Le nombre de graines que contient le trou */
        private int nbGraines;
        /** Une référence vers le trou de gauche (on tourne dans le sens horaire) */
        private Trou voisinGauche;
        /** Une référence vers le trou de droite (on tourne dans le sens trigo) */
        private Trou voisinDroit;

        /* *********** Constructeurs  *********** */
        public Trou(Joueur j) {
            this(j, 4); // 4 graines pour chaque trou en début de partie
        }

        public Trou(Joueur j, int nbGraines) {
            propriétaire = j;
            this.nbGraines = nbGraines;
        }

//        public Trou(Joueur j, int nbGraines, Trou voisinGauche, Trou voisinDroit) {
//            propriétaire = j;
//            this.nbGraines = nbGraines;
//            this.voisinGauche = voisinGauche;
//            this.voisinDroit = voisinDroit;
//        }

        /* *********** Gestion des attributs  *********** */
        public int getNbGraines() {
            return nbGraines;
        }

        public void setVoisins(Trou voisinGauche, Trou voisinDroit) {
            setVoisinGauche(voisinGauche);
            setVoisinDroit(voisinDroit);
        }

        public void setVoisinGauche(Trou voisin) {
            voisinGauche = voisin;
        }

        public Trou getVoisinGauche() {
            return voisinGauche;
        }

        public void setVoisinDroit(Trou voisin) {
            voisinDroit = voisin;
        }

        public Trou getVoisinDroit() {
            return voisinDroit;
        }

        private boolean isPropriétaire(Joueur j) {
            return j.equals(propriétaire);
        }

        public Joueur getPropriétaire() {
            return propriétaire;
        }

        /* *********** Autres méthodes  *********** */
        /** Renvoie une copie du trou */
        public Trou copy() {
            return new Trou(propriétaire, nbGraines);
        }

        /** Déclencher un coup :
         *  Appeler cette méthode sur le trou joué pour ramasser, égrainer et
         * capturer les graines.
         */
        public void declencherCoup(Joueur joueurActuel) {
//            if (!isPropriétaire(joueurActuel) || nbGraines == 0) {
//                throw new IllegalArgumentException("Coup non valide");
//            }
            if (nbGraines / 12 != 0) { // Si on fait au moins un tour complet, on garde le trou de départ en mémoire
                getVoisinDroit().égrainer(nbGraines, joueurActuel, this);
            } else {
                getVoisinDroit().égrainer(nbGraines, joueurActuel);
            }
            nbGraines = 0;
        }

        /** Lorsque le coup joué devait affamer l'adversaire, on appelle cette méthode sur le trou joué */
        private void declencherCoupSansCapturer(Joueur joueurActuel) {
            if (nbGraines / 12 != 0) { // Si on fait au moins un tour complet, on garde le trou de départ en mémoire
                getVoisinDroit().égrainerSansCapturer(nbGraines, joueurActuel, this);
            } else {
                getVoisinDroit().égrainerSansCapturer(nbGraines, joueurActuel);
            }
            nbGraines = 0;
        }

        private void égrainer(int graines, Joueur joueurActuel) {
            switch (graines) {
                case 0:
                    throw new IllegalArgumentException("Aucune graine à distribuer");
                case 1:
                    nbGraines++;
                    capturer(joueurActuel);
                    break;
                default:
                    nbGraines++;
                    getVoisinDroit().égrainer(graines - 1, joueurActuel);
                    break;
            }
        }

        private void égrainer(int graines, Joueur joueurActuel, Trou trouJoué) {
            if (this.equals(trouJoué)) { // On saute le trou où l'on a pris les graines au départ
                getVoisinDroit().égrainer(graines, joueurActuel, trouJoué);
            }
            switch (graines) {
                case 0:
                    throw new IllegalArgumentException("Aucune graine à distribuer");
                case 1:
                    nbGraines++;
                    capturer(joueurActuel);
                    break;
                default:
                    nbGraines++;
                    getVoisinDroit().égrainer(graines - 1, joueurActuel, trouJoué);
                    break;
            }
        }

        private void égrainerSansCapturer(int graines, Joueur joueurActuel) {
            switch (graines) {
                case 0:
                    throw new IllegalArgumentException("Aucune graine à distribuer");
                case 1:
                    nbGraines++;
                    break;
                default:
                    nbGraines++;
                    getVoisinDroit().égrainerSansCapturer(graines - 1, joueurActuel);
                    break;
            }
        }

        private void égrainerSansCapturer(int graines, Joueur joueurActuel, Trou trouJoué) {
            if (this.equals(trouJoué)) { // On saute le trou où l'on a pris les graines au départ
                getVoisinDroit().égrainer(graines, joueurActuel, trouJoué);
            }
            switch (graines) {
                case 0:
                    throw new IllegalArgumentException("Aucune graine à distribuer");
                case 1:
                    nbGraines++;
                    break;
                default:
                    nbGraines++;
                    getVoisinDroit().égrainer(graines - 1, joueurActuel, trouJoué);
            }
        }

        private void capturer(Joueur joueurActuel) {
            if ((!isPropriétaire(joueurActuel)) && (nbGraines == 2 || nbGraines == 3)) {
                addToScore(nbGraines, joueurActuel);
                nbGraines = 0;
                getVoisinGauche().capturer(joueurActuel);
            }
        }

        private void capturerLeReste(Joueur joueur){
            if (isPropriétaire(joueur)) {
                addToScore(nbGraines, joueur);
                nbGraines = 0;
                getVoisinDroit().capturerLeReste(joueur);
            }
        }
    }// FIN DE LA CLASSE Trou

    /************* Constructeurs ****************/

    public PlateauAwale(Joueur joueurQuiCommence) {
        vientDeJouer = getJoueurAdverse(joueurQuiCommence);
        score = new int[2];
        score[0] = 0;
        score[1] = 0;
        trous = new Trou[12];
        for (int i = 0; i < 6; i++) {
            trous[i] = new Trou(PlateauAwale.joueur1);
        }
        for (int i = 6; i < 12; i++) {
            trous[i] = new Trou(PlateauAwale.joueur2);
        }
        assignerVoisins();
    }

    private PlateauAwale(Joueur joueurQuiCommence, Trou[] trous, int[] score) {
        vientDeJouer = getJoueurAdverse(joueurQuiCommence);
        this.score = new int[2];
        this.score[0] = score[0];
        this.score[1] = score[1];
        this.trous = new Trou[12];
        for (int i = 0; i < 12; i++) {
            int ng = trous[i].getNbGraines();
            Joueur prop = trous[i].getPropriétaire();
            this.trous[i] = new Trou(prop, ng);
            //this.trous[i] = trous[i].copy();
        }
        assignerVoisins();
//        System.arraycopy(score, 0, this.score, 0, 2);
    }

    /************* Gestion des paramètres de classe ******************/
    public static void setJoueurs(Joueur j1, Joueur j2) {
        joueur1 = j1;
        joueur2 = j2;
    }

    public static boolean isJoueur1(Joueur j1) {
        return joueur1.equals(j1);
    }

    public static boolean isJoueur2(Joueur j2) {
        return joueur2.equals(j2);
    }

    public static Joueur getJoueurAdverse(Joueur j) {
        if (isJoueur1(j)) // joueur2 : joueur adverse
        {
            return joueur2;
        } else // joueur1 : joueur adverse
        {
            return joueur1;
        }
    }

    /** Retourne l'indice du tableau score correspondant à un joueur */
    private int joueurToInt(Joueur j) {
        if (isJoueur1(j)) { // Joueur 1, indice 0
            return 0;
        } else // Joueur 2, indice 1
        {
            return 1;
        }
    }

    /************* Accesseurs ****************/
    public int getScore(Joueur j) {
        return score[joueurToInt(j)];
    }

    /************* Méthodes de l'interface PlateauJeu ****************/
    public ArrayList<CoupJeu> coupsPossibles(Joueur jo) {
        ArrayList<CoupJeu> lesCoupsPossibles = new ArrayList<CoupJeu>();
        if (finDePartie()) {
            return lesCoupsPossibles;
        }

        for (int i = 0; i < 12; i++) {
            CoupAwale coup = new CoupAwale(i);
            if (coupValide(jo, coup)) {
                lesCoupsPossibles.add(coup);
            }
        }
        return lesCoupsPossibles;
    }

    public void joue(Joueur j, CoupJeu c) {
        CoupAwale ca = (CoupAwale) c;
        int indiceTrou = ca.getIndiceTrou();

        if (estAffamant(j, ca)) { // si le coup devait affamer l'adversaire, on égraine sans capturer
            trous[indiceTrou].declencherCoupSansCapturer(j);
        } else {
            trous[indiceTrou].declencherCoup(j);
        }

        vientDeJouer = j;
    }

    public boolean finDePartie() {
        Joueur doitJouer = getJoueurAdverse(vientDeJouer);
        if (nePeutPasNourrir(doitJouer)) { // Cas où le joueur qui doit jouer ne peut pas nourrir le joueur adverse
            // Le joueur qui devait jouer capture les graines restantes...
            if (isJoueur1(doitJouer)) {
                trous[0].capturerLeReste(doitJouer);
            } else {
                trous[6].capturerLeReste(doitJouer);
            }
            return true; //... et la partie s'arrête
        }

        return (score[0] > 24 || score[1] > 24 || nbGrainesRestantes() <= 6);
    }

    public PlateauJeu copy() {
        return new PlateauAwale(getJoueurAdverse(vientDeJouer), trous, score);
    }

    public boolean coupValide(Joueur joueur, CoupJeu c) {
        CoupAwale ca = (CoupAwale) c;
        Joueur joueurAdverse = getJoueurAdverse(joueur);
        int indiceTrou = ca.getIndiceTrou();

        if (estAffamé(joueurAdverse)) { // si l'adversaire n'a plus de graine...
            if (trous[indiceTrou].isPropriétaire(joueur) && trous[indiceTrou].getNbGraines() != 0) {
                return (estNourrissant(joueur, ca)); // ...le coup doit nourrir l'adversaire
            } else {
                return false;
            }
        } else // l'adversaire a au moins une graine
        {
            return (trous[indiceTrou].isPropriétaire(joueur) && trous[indiceTrou].getNbGraines() != 0);
        }
    }

    /* ********************* Autres méthodes ***************** */
    public void addToScore(int points, Joueur j) {
        score[joueurToInt(j)] += points;
    }

    public int nbGrainesRestantes() {
        int reste = 0;
        for (int i = 0; i < 12; i++) {
            reste += trous[i].getNbGraines();
        }
        return reste;
    }

    /** Utilisée pour le calcul de l'heuristique :
     * Calcule la différence entre le nombre de graines capturées par le joueur Ami
     * et le nombre de graines capturées par l’adversaire.
     * Si la partie se termine, elle retourne MAX_VALUE si l’on a gagné,
     * - MAX_VALUE si on a perdu, et 0 en cas de match nul.     *
     */
    public int avantageJ1() {
        if (finDePartie()) {
            if (score[0] > score[1]) { // gagné
                return Integer.MAX_VALUE;
            } else if (score[0] == score[1]) { // match nul
                return 0;
            } else { // perdu
                return -Integer.MAX_VALUE;
            }
        }
        return (score[0] - score[1]);
    }

    /** Relie les trous du tableau trous entre eux en assignant leurs voisins gauche et droite */
    private void assignerVoisins() {
        trous[0].setVoisinGauche(trous[11]);
        trous[0].setVoisinDroit(trous[1]);
        for (int i = 1; i < 11; i++) {
            trous[i].setVoisinGauche(trous[i - 1]);
            trous[i].setVoisinDroit(trous[i + 1]);
        }
        trous[11].setVoisinGauche(trous[10]);
        trous[11].setVoisinDroit(trous[0]);
    }

    @Override
    public String toString() {
        String retstr = "";
        for (int i = 5; i >= 0; i--) {
            retstr += " " + trous[i].getNbGraines();
        }
        retstr += "\n";
        for (int i = 6; i < 12; i++) {
                retstr += " " + trous[i].getNbGraines();
        }
        return retstr;
    }

    /* Détermine si un joueur n'a plus de graine */
    private boolean estAffamé(Joueur j) {
        if (isJoueur1(j)) { // Joueur 1
            for (int i = 0; i < 6; i ++) {
                if (trous[i].getNbGraines() != 0) {
                    return false;
                }
            }
        } else { // Joueur 2
            for (int i = 6; i < 12 ; i++) {
                if (trous[i].getNbGraines() != 0) {
                    return false;
                }
            }
        }
        // Si on a trouvé aucun trou du joueur i ayant un nombre de graines non nul, le joueur est affamé :
        return true;
    }

    /* Détermine si un coup est apte à nourrir l'adversaire (si celui-ci est affamé) */
    private boolean estNourrissant(Joueur j, CoupAwale c) {
        Joueur joueurAdverse = getJoueurAdverse(j);
        PlateauAwale p = (PlateauAwale) this.copy();
        p.joue(j, c); // joue le coup c sur une copie du plateau courant
        /* Le joueur adverse est-il affamé après qu'on ait joué le coup c ? */
        return (!p.estAffamé(joueurAdverse));
    }

    /* Détermine si un coup affame l'adversaire */
    private boolean estAffamant(Joueur j, CoupAwale c) {
        Joueur joueurAdverse = getJoueurAdverse(j);
        PlateauAwale p = (PlateauAwale) copy();
        p.joueBeurre(j, c); // joue pour du beurre le coup c sur une copie du plateau courant
        /* Le joueur adverse est-il affamé après qu'on ait joué le coup i ? */
        return p.estAffamé(joueurAdverse);
    }

    private boolean nePeutPasNourrir(Joueur j) {
        for (int i = 0; i < 12; i++) {
            CoupAwale coup = new CoupAwale(i);
            if (coupValide(j, coup)) { // Il existe un coup que j peut jouer
                return false;
            }
        }
        /* Le joueur j ne peut pas jouer (cas où son adversaire est affamé et qu'il ne peut le nourrir) */
        return true;
    }

    /** Joue "pour du beurre" le coup c, c'est-à-dire que ce coup peut affamer l'adversaire.
     * C'est un intermédiaire pour déterminer si le coup c devrait affamer l'adversaire
     */
    private void joueBeurre(Joueur j, CoupJeu c) {
        CoupAwale ca = (CoupAwale) c;
        int indiceTrou = ca.getIndiceTrou();
        trous[indiceTrou].declencherCoup(j);
    }

}
