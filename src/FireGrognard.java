import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class FireGrognard extends Ennemis {

 
    public FireGrognard(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color, double deltaTime) {
        super(name, maxPv, atk, atkSpeed, range, position, element, speed, reward, color, deltaTime);
    }

    @Override
    public void attaquer(Unite cible) {
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
    }

    
    
    
}

