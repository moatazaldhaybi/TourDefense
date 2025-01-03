public class Temps {
  private double secondes;

  // Constructeur
  public Temps(double secondes) {
      this.secondes = secondes;
  }

  // Getter pour les secondes
  public double getSecondes() {
      return this.secondes;
  }

  // Setter pour les secondes
  public void setSecondes(double sec) {
      this.secondes = sec;
  }

  // Convertir en millisecondes
  public long getTempsMs() {
      return (long) (this.secondes * 1000);
  }

  // Méthode toString pour afficher
  @Override
  public String toString() {
      return this.secondes + "s";
  }

  // Comparaison (utile si vous souhaitez trier ou comparer des objets Temps)
  @Override
  public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Temps temps = (Temps) obj;
      return Double.compare(temps.secondes, secondes) == 0;
  }

  @Override
  public int hashCode() {
      return Double.hashCode(secondes);
  }
}
