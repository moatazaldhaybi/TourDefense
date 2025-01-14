import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class FireGrognard extends Ennemis {
     private long dernierTempsAttaque = 0; 

 
    public FireGrognard(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color) {
        super(name, maxPv, atk, atkSpeed, range, position, element, speed, reward, color);
        EnvironnementJeu.ajouterUnite(this);
    }

    @Override
    public void attaquer(Unite cible) {
        long tempsActuel = System.currentTimeMillis();

        // Convertir atkSpeed de secondes en millisecondes
        long tempsRechargement = (long) (getAtkSpeed() * 1000);

        if (tempsActuel - dernierTempsAttaque >= tempsRechargement) {

        if (cible instanceof Tour) {
            
            Tour tourCible = this.trouverTourLaPlusProche();
            if (tourCible != null) {
                double distance = this.distanceVers(tourCible.getPosition());

                if (distance <= this.getRange()) {
                    
                    this.subitDegat(tourCible);
                    System.out.println(getName() + " attaque la tour et inflige " + getAtk() + " dégâts!");

                    
                    List<Unite> toursProches = getUnitesProches(tourCible.getPosition(), 1.5);
                    for (Unite tour : toursProches) {
                        if (tour instanceof Tour && !tour.equals(tourCible)) {
                            this.subitDegat(tour);
                            System.out.println(getName() + " inflige " + getAtk() + " dégâts à une tour proche: " + tour.getClass().getSimpleName());
                        }
                    }
                } else {
                    System.out.println(getName() + " ne peut pas atteindre la tour car elle est hors de portée.");
                }
            } else {
                System.out.println(getName() + " n'a pas trouvé de tour à attaquer.");
            }
        } else {
            System.out.println(getName() + " ne peut attaquer que des tours.");
        }
        
        dernierTempsAttaque = tempsActuel;
        } else {
            System.out.println(getName() + " est en rechargement et ne peut pas attaquer pour l'instant.");
        }
    }

    
    
    
}


