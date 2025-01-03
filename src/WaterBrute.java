import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class WaterBrute extends Ennemis {
private long dernierTempsAttaque = 0; 
    
    public WaterBrute(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color) {
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
            List<Tour> toursDansPortee = getToursDansPortee();
            if (!toursDansPortee.isEmpty()) {
                Tour tourCible = this.trouverTourAvecMoinsDePv();

                double distance = this.distanceVers(tourCible.getPosition());

                if (distance <= this.getRange()) {
                    tourCible.recevoirDegats(this.getAtk());
                    System.out.println(getName() + " attaque " + tourCible.getClass().getSimpleName() + " et inflige " + getAtk() + " dégâts!");

                    List<Unite> toursProches = getUnitesProches(tourCible.getPosition(), 1.5);
                    for (Unite tour : toursProches) {
                        if (tour instanceof Tour && !tour.equals(tourCible)) {
                            tour.recevoirDegats(this.getAtk());
                            System.out.println(getName() + " inflige " + getAtk() + " dégâts à une tour proche: " + tour.getClass().getSimpleName());
                        }
                    }
                } else {
                    System.out.println(getName() + " ne peut pas atteindre " + tourCible.getClass().getSimpleName() + " car elle est hors de portée.");
                }
            } else {
                System.out.println(getName() + " ne trouve pas de tour dans sa portée.");
            }
        } else {
            System.out.println(getName() + " ne peut attaquer que des tours.");
        }
    }

    

   
    
     
}
}
