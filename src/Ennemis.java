import java.awt.Color;
import java.util.List;
import java.util.ArrayList;


public abstract class Ennemis extends Unite {
      
    private double speed;     
    private int reward;
    private double distanceParcourue;       
  
    public Ennemis(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element, double speed, int reward, Color color){

      super(name,maxPv, atk, atkSpeed, range, element, position, color);
      this.speed = speed;
      this.reward = reward;
      this.distanceParcourue=0;

    }

    public String getName(){
        return super.getName();
    }

    public double getSpeed() {
      return speed;
    }

    public int getReward() {
      return reward;
    }

    public Position getPosition(){
        return super.getPosition();
    }

    public void setPosition(Position position ){
        super.setPosition(position);
    }

    public void setPosition1(PositionCase position ){
        super.getPosition();
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

    /*public void avancer(double deltaTime, List<Case> chemin, double vitesse){
        for(int i=0; i<chemin.size()-1; i++){
            PositionCase current = chemin.get(i).getPosition();
            PositionCase prochain = chemin.get(i+1).getPosition();
            double distanceTotal = current.distanceTo(prochain);
            if(current.getRow()==prochain.getRow()){
                double x = this.getPosition().getX();
                for(double j=0;j<distanceTotal;j+=deltaTime){
                    if(current.getCol()<prochain.getCol()){
                        x+=vitesse*deltaTime;
                    }
                    else{
                        x-=vitesse*deltaTime;                 
                    }
                    this.getPosition().setX(x);
                }
            }
            else{
                double y = this.getPosition().getY();
                while (Math.abs(y - 481.25) > 0.01) {

                    for(double j=0;j<1;j+=deltaTime){
                        if(current.getRow()<prochain.getRow()){
                            
                            y-=vitesse*deltaTime; 
                            if (y ==481.25){
                                System.out.println("cacaMoataz");
                            }
                        }
                        else{
                            y+=vitesse*deltaTime;
                        }
                        this.getPosition().setY(y);
                    }
                }
            }
        }
    }*/






    public void avancer(double deltaTime, List<Case> chemin, double vitesse) {
        for (int i = 0; i < chemin.size() - 1; i++) {
            PositionCase currentCase = chemin.get(i).getPosition();
            PositionCase nextCase = chemin.get(i + 1).getPosition();
    
            // Convertir les positions des cases en pixels
            Position currentPosition = Position.fromCase(currentCase, Forme.getHalfLenghtCase());
            Position nextPosition = Position.fromCase(nextCase, Forme.getHalfLenghtCase());
    
            // Calculer la différence en X et Y entre les positions
            double dx_total = nextPosition.getX() - currentPosition.getX();
            double dy_total = nextPosition.getY() - currentPosition.getY();
    
            // Calculer la distance totale entre les positions
            double distance_total = Math.sqrt(dx_total * dx_total + dy_total * dy_total);
    
            // Normaliser les vecteurs de direction
            double dx_normalized = dx_total / distance_total;
            double dy_normalized = dy_total / distance_total;
    
            // Initialiser la distance parcourue
            double distance_parcourue = 0.0;
    
            // Initialiser la position du minion
            double x = currentPosition.getX();
            double y = currentPosition.getY();
    
            // Boucle pour déplacer le minion vers la prochaine case
            while (distance_parcourue < distance_total) {
                // Calculer la distance du pas
                double step_distance = vitesse * deltaTime;
    
                // Éviter de dépasser la distance totale
                if (distance_parcourue + step_distance > distance_total) {
                    step_distance = distance_total - distance_parcourue;
                }
    
                // Mettre à jour les positions X et Y
                x += dx_normalized * step_distance;
                y += dy_normalized * step_distance;
    
                // Mettre à jour la position du minion
                this.getPosition().setX(x);
                this.getPosition().setY(y);
    
                // Mettre à jour la distance parcourue
                distance_parcourue += step_distance;
            }
    
            // S'assurer que le minion est exactement à la position de la prochaine case
            this.getPosition().setX(nextPosition.getX());
            this.getPosition().setY(nextPosition.getY());
        }
    }

    
    
    

    public abstract void attaquer(Unite cible);
    
 
}
