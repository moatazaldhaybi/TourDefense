import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public abstract class Ennemis extends Unite {
    
    private double deltaTime;
    private String name;       
    private double speed;     
    private int reward;       
  
    public Ennemis(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color, double deltaTime){

      super(maxPv, atk, atkSpeed, range, element, position, color);
      this.deltaTime=deltaTime;
      this.name=name;
      this.speed = speed;
      this.reward = reward;

    }

    public String getName() {
      return name;
    }

    public double getSpeed() {
      return speed;
    }

    public int getReward() {
      return reward;
    }

    public void avancer(List<Case> chemin) {
      if (chemin == null || chemin.isEmpty()) {
          throw new IllegalArgumentException("Le chemin est invalide ou vide !");
      }
  
      // Si l'ennemi est déjà arrivé à la dernière case
      if (chemin.get(chemin.size() - 1).getPosition().equals(super.getPosition())) {
          return; // L'ennemi a atteint la base
      }

      

      
    }
  
  // Méthode pour trouver la case actuelle de l'ennemi
  private Case trouverCaseActuelle(List<Case> chemin) {
      for (Case c : chemin) {
          if (c.getPosition().equals(super.getPosition())) {
              return c;
          }
      }
      throw new IllegalStateException("L'ennemi est en dehors du chemin !");
  }
  
  // Méthode pour obtenir la prochaine case dans le chemin
  private Case obtenirProchaineCase(Case currentCase, List<Case> chemin) {
      int index = chemin.indexOf(currentCase);
      if (index >= 0 && index < chemin.size() - 1) {
          return chemin.get(index + 1);
      }
      return null; // Pas de prochaine case (fin du chemin)
  }
   

    public List<Unite> getUnitesProches(Position ciblePosition, double rayon) {
        List<Unite> unitesProches = new ArrayList<>();
        for (Unite unite : EnvironnementJeu.getAllUnites()) {
            if (unite != null && this.distanceVers(unite.getPosition()) <= rayon) {
                unitesProches.add(unite);
            }
        }
        return unitesProches;
    }

    public List<Tour> getToursDansPortee() {
        List<Tour> toursDansPortee = new ArrayList<>();
        for (Unite unite : EnvironnementJeu.getAllUnites()) {
            if (unite instanceof Tour) {
                double distance = this.distanceVers(unite.getPosition());
                if (distance <= this.getRange()) {
                    toursDansPortee.add((Tour) unite);
                }
            }
        }
        return toursDansPortee;
    }
    
    public Tour trouverTourAvecMoinsDePv() {
        List<Tour> toursDansPortee = this.getToursDansPortee();
        Tour tourAvecMoinsDePv = toursDansPortee.get(0); 
        for (Tour tour : toursDansPortee) {
            if (tour.getPv() < tourAvecMoinsDePv.getPv()) {
                tourAvecMoinsDePv = tour; 
            }
        }
        return tourAvecMoinsDePv;
    }

    public Tour trouverTourLaPlusProche() {
        Tour tourLaPlusProche = null;
        double distanceMin = Double.MAX_VALUE;

        for (Unite unite : EnvironnementJeu.getAllUnites()) {
            if (unite instanceof Tour) {
                double distance = this.distanceVers(unite.getPosition());
                if (distance < distanceMin) {
                    distanceMin = distance;
                    tourLaPlusProche = (Tour) unite;
                }
            }
        }

        return tourLaPlusProche;
    }
    public Ennemis trouverEnnemiLePlusAvance(List<Ennemis> ennemisDansPortee) {
        Ennemis ennemiAvance = null;
        double maxDistanceParcourue = -Double.MAX_VALUE;

        for (Ennemis ennemi : ennemisDansPortee) {
            double distanceParcourue = calculerDistanceParcourue(ennemi);
            if (distanceParcourue > maxDistanceParcourue) {
                maxDistanceParcourue = distanceParcourue;
                ennemiAvance = ennemi;
            }
        }

        return ennemiAvance;
    }

       //!!!!  faire calculer distance parcoure !!!
    

    public abstract void attaquer(Unite cible);
    
 
}
