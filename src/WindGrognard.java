import java.awt.Color;
import java.util.List;

public class WindGrognard extends Ennemis {

    public WindGrognard(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color, double deltaTime){
      super(name, maxPv, atk, atkSpeed, range, position, element, speed, reward, color, deltaTime);
    }

    @Override
    public void attaquer(Unite cible){
        if (cible instanceof Tour) {
            List<Tour> toursDansPortee = getToursDansPortee();
            if (!toursDansPortee.isEmpty()) {
                Tour tourCible = this.trouverTourAvecMoinsDePv();

                double distance = this.distanceVers(tourCible.getPosition());

                if (distance <= this.getRange()) {
                    tourCible.recevoirDegats(this.getAtk());
                    System.out.println(getName() + " attaque " + tourCible.getClass().getSimpleName() + " et inflige " + getAtk() + " dégâts!");
                }
            }
        }
    }
}
