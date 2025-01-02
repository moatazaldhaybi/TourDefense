import java.awt.Color;

public class Minion extends Ennemis {

    // Constructeur spécifique à Minion
    public Minion(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color, double deltaTime) {
      super(name, maxPv, atk, atkSpeed, range, position, element, speed, reward, color, deltaTime);
    }

    @Override
    public void attaquer(Unite cible) {
      System.out.println(getName() + " ne peut pas attaquer.");
    }
}
