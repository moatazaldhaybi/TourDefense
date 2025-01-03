import java.awt.Color;
import java.util.List;

public class Minion extends Ennemis {

    // Constructeur spécifique à Minion
    public Minion(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color) {
        super(name, maxPv, atk, atkSpeed, range, position, element, speed, reward, color);
        EnvironnementJeu.ajouterUnite(this); // Ajout au système centralisé
    }

    @Override
    public void avancer(double deltaTime, List<Case> chemin, double vitesse){
      super.avancer(deltaTime, chemin, vitesse);
    }

    public void setPosition(Position position){
      super.setPosition(position);
    }

    public Position getPosition(){
      return super.getPosition();
    }

    public double getSpeed(){
      return super.getSpeed();
    }

    @Override
    public void attaquer(Unite cible) {
        System.out.println(getName() + " ne peut pas attaquer.");
    }


}
