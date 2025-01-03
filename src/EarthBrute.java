import java.awt.Color;
import java.util.List;

public class EarthBrute extends Ennemis {
    private long dernierTempsAttaque = 0;

    public EarthBrute(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color) {
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
                    Tour tourCible = this.trouverTourLaPlusProche();

                    double distance = this.distanceVers(tourCible.getPosition());

                    if (distance <= this.getRange()) {
                        tourCible.recevoirDegats(this.getAtk());
                        System.out.println(getName() + " attaque " + tourCible.getClass().getSimpleName() + " et inflige " + getAtk() + " dégâts!");
                    }
                }
            }
            // Mise à jour du dernier temps d'attaque après une attaque réussie
            dernierTempsAttaque = tempsActuel;
        } else {
            System.out.println(getName() + " est en rechargement et ne peut pas attaquer pour l'instant.");
        }
    }
}
