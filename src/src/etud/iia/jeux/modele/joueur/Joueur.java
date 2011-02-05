package src.etud.iia.jeux.modele.joueur;


public class Joueur  //A
{

    private String id;

    public Joueur(String s)
    {
        id = s;
    }

    public String getId()    // A
    {
        return id;
    }

    public void setId(String s)   //A(String)
    {
        id = s;
    }

    @Override
    public String toString()
    {
        return id;
    }

}
