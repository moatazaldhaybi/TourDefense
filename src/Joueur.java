public class Joueur {
  private int pointsDeVie; // Points de vie du joueur
  private int argent;      // Pièces d'or du joueur
  private Forme forme;

  public Joueur(Forme forme) {
      this.pointsDeVie = 100; // Commence avec 100 points de vie
      this.argent = 50;       // Commence avec 50 pièces d'or
      this.forme=forme;
  }

  // Getter et Setter pour les points de vie
  public int getPointsDeVie() {
      return pointsDeVie;
  }

  public void setPointsDeVie(int pointsDeVie) {
      this.pointsDeVie = Math.max(0, pointsDeVie); // Pas de points de vie négatifs
  }

  // Getter et Setter pour l'argent
  public int getArgent() {
      return argent;
  }

  public void setArgent(int argent) {
      this.argent = Math.max(0, argent); // Pas d'argent négatif
  }

  // Réduit les points de vie du joueur
  public void recevoirDegats(int degats) {
      setPointsDeVie(pointsDeVie - degats);
      if (pointsDeVie <= 0) {
          System.out.println("Vous avez perdu la partie !");
      }
  }

  // Ajoute de l'argent
  public void gagnerArgent(int montant) {
      this.argent += montant;
      System.out.println("Vous avez gagné " + montant + " pièces d'or !");
  }

  // Déduit de l'argent pour un achat
  public boolean acheter(int cout) {
      if (argent >= cout) {
          this.argent -= cout;
          System.out.println("Achat effectué pour " + cout + " pièces d'or.");
          return true;
      } else {
          System.out.println("Vous n'avez pas assez d'argent pour cet achat !");
          return false;
      }
  }

  // Vérifie si le joueur a perdu
  public boolean estPerdu() {
      return pointsDeVie <= 0;
  }

  public void ennemisBase(Ennemis enemi){
    if (Position.toCase(enemi.getPosition(), forme.getHalfLenghtCase()).comparePosition(forme.trouverBase().getPosition())){
        pointsDeVie-=enemi.getAtk();
        //setPointsDeVie(pointsDeVie);
    }
  }

  @Override
  public String toString() {
      return "Joueur{" +
              "pointsDeVie=" + pointsDeVie +
              ", argent=" + argent +
              '}';
  }
}
