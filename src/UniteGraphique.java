import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import libraries.StdDraw;

public class UniteGraphique {
    private List<Ennemis> ennemisActif;

    public UniteGraphique(List<Ennemis> ennemisActif){
        this.ennemisActif = new ArrayList<>(ennemisActif);
    }
    

    // Méthode pour dessiner un ennemi
    public static void dessinerEnnemi(double x, double y, double rayon, Color couleur) {
      StdDraw . setPenColor ( couleur );
      StdDraw . filledCircle ( x , y , rayon );
      StdDraw . setPenColor ( new Color (192 , 192 ,192) );
      StdDraw . filledCircle ( x , y , rayon );
  }

    // Méthode pour dessiner une tour
    public static void dessinerTour(double x, double y, double largeur, double hauteur, Color couleur) {
        StdDraw.setPenColor(couleur);
        StdDraw.filledRectangle(x, y, largeur / 2, hauteur / 2);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(x, y, largeur / 2, hauteur / 2); // Contour
    }

    // Méthode pour afficher toutes les unités sur la carte
    public static void afficherUnites(Forme forme, Map<Temps, Ennemis> ennemis, List<Tour> tours) {
        double halfCase = forme.getHalfLenghtCase();
        for (Ennemis ennemi : ennemis.values()) {
            Position pos = ennemi.getPosition();
            dessinerEnnemi(pos.getX(), pos.getY(), halfCase / 2, ennemi.getElement().getColor());
        }

        for (Tour tour : tours) {
            Position pos = tour.getPosition();
            dessinerTour(pos.getX(), pos.getY(), halfCase, halfCase, tour.getElement().getColor());
        }
    }


    public List<Ennemis> getEnnemisActif() {
        return ennemisActif;
    }


    public void setEnnemisActif(List<Ennemis> ennemisActif) {
        this.ennemisActif = ennemisActif;
    }
}
