import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public abstract class Ennemis extends Unite {

    private double speed;
    private int reward;
    private double m_distanceParcourue;
    protected int m_indexPath;

    public Ennemis(String name, int maxPv, int atk, double atkSpeed, int range, Position position, Element element,
            double speed, int reward, Color color) {

        super(name, maxPv, atk, atkSpeed, range, element, position, color);
        this.speed = speed;
        this.reward = reward;
        this.m_distanceParcourue = 0.0;
        this.m_indexPath = 0;
    }

    public String getName() {
        return super.getName();
    }

    public double getSpeed() {
        return speed;
    }

    public int getReward() {
        return reward;
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public void setPosition(Position position) {
        super.setPosition(position);
    }

    public void setPosition1(PositionCase position) {
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

    public void avancer(double deltaTime, List<Case> chemin, double vitesse) {
        if (m_indexPath < chemin.size()-1) {
            PositionCase currentCase = chemin.get(m_indexPath).getPosition();
            PositionCase nextCase = chemin.get(m_indexPath + 1).getPosition();

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

            // Initialiser la position du minion
            double x = this.getPosition().getX();
            double y = this.getPosition().getY();

            // Boucle pour déplacer le minion vers la prochaine case
            // Calculer la distance du pas
            double step_distance = vitesse * deltaTime;
            if(currentCase.getCol()== 7 && currentCase.getRow()==1 && m_distanceParcourue == distance_total){
                System.out.println("1");
            }

            if(nextCase!=chemin.get(chemin.size()-1).getPosition()){
                PositionCase nextNextCase = chemin.get(m_indexPath + 2).getPosition();
                if((currentCase.getRow()==nextCase.getRow()&&nextCase.getCol()==nextNextCase.getCol())||(currentCase.getCol()==nextCase.getCol()&&nextCase.getRow()==nextNextCase.getRow())){
                    // Mettre à jour la distance parcourue
                    if (m_distanceParcourue + step_distance > distance_total) {
                        step_distance = distance_total - m_distanceParcourue;
                    }
                    //System.out.println("CD: " + " " + m_distanceParcourue + " " +
                    //currentCase.getCol()+ " " + nextCase.getRow() + " " + nextCase.getCol());
                }
            }

            // Mettre à jour les positions X et Y
            x += dx_normalized * step_distance;
            y += dy_normalized * step_distance;

            // Mettre à jour la position du minion
            this.getPosition().setX(x);
            this.getPosition().setY(y);

            m_distanceParcourue += step_distance;
            // Éviter de dépasser la distance totale
            if (m_distanceParcourue >= distance_total) {

                m_indexPath++;
                m_distanceParcourue = m_distanceParcourue - distance_total;
                // currentCase = chemin.get(m_indexPath).getPosition();
                // nextCase = chemin.get(m_indexPath + 1).getPosition();
                // System.out.println("CC: " + this + " " + currentCase.getRow() + " " +
                // currentCase.getCol()+ " " + nextCase.getRow() + " " + nextCase.getCol());
            }
        }
        // System.out.println("DXY: " + this + " " + dx_normalized + " " +
        // dy_normalized);
        // System.out.println("PE: " + this + " " + x + " " + y);
        // System.out.println("PPT: " + this + " " + m_distanceParcourue + " " +
        // distance_total);
    }

    public abstract void attaquer(Unite cible);

}
