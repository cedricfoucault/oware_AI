package src.etud.jeux.awale;

import src.etud.iia.jeux.modele.CoupJeu;

public class CoupAwalé implements CoupJeu{

       /****** Attributs *******/

       private int rangée;

       private int trou;


       /****** Clonstructeur *******/

       public CoupAwalé(int l, int c) {
               rangée = l;
               trou = c;
       }

       /****** Accesseurs *******/

       public int getRangée() {
               return rangée;
       }

       public int getTrou() {
               return trou;
       }

       /****** Accesseurs *******/

       public String toString() {
               return "("+rangée+","+trou+")";
       }
}
