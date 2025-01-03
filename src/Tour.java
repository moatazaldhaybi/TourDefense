import java.awt.Color;
import java.util.List;

public class Tour extends Unite {
  private String nom; // Nom de la tour
  private int cost; // Coût de la tour

  // Constructeur de la classe Tour
  public Tour(String nom, int maxPv, int atk, double atkSpeed, int range, Element element, Position position, int cost,
      Color color) {
    super(nom, maxPv, atk, atkSpeed, range, element, position, color);
    this.nom = nom;
    this.cost = cost;
  }

  public String getNom() {
    return nom;
  }

  public int getCost() {
    return cost;
  }

  public static Ennemis trouverEnnemiLePlusAvanceDansListe(List<Case> chemin) {
    // Obtenir tous les ennemis dans l'environnement
    Ennemis[] ennemisArray = EnvironnementJeu.getEnnemis();

    // Convertir le tableau en une liste pour faciliter le traitement
    List<Ennemis> ennemis = List.of(ennemisArray);

    if (ennemis.isEmpty()) {
      return null; // Aucun ennemi à traiter
    }

    if (chemin == null || chemin.isEmpty()) {
      throw new IllegalArgumentException("Le chemin est invalide ou vide !");
    }

    Ennemis ennemiLePlusAvance = null;
    int positionMax = -1; // Indice le plus avancé sur le chemin

    for (Ennemis ennemi : ennemis) {
      int positionActuelle = trouverPositionSurChemin(ennemi, chemin);
      if (positionActuelle > positionMax) {
        positionMax = positionActuelle;
        ennemiLePlusAvance = ennemi;
      }
    }

    return ennemiLePlusAvance;
  }

  public static int trouverPositionSurChemin(Ennemis ennemi, List<Case> chemin) {
    for (int i = 0; i < chemin.size(); i++) {
      if (chemin.get(i).getPosition().equals(ennemi.getPosition())) {
        return i;
      }
    }
    return -1; // L'ennemi n'est pas sur le chemin
  }

  public void attaquer(double deltaTimeSec) {
   
    throw new UnsupportedOperationException("Unimplemented method 'attaquer'");
  }
}
