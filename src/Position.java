public class Position {
  private int x;
  private int y;

  // Constructeur
  public Position(int x, int y) {
      this.x = x;
      this.y = y;
  }

  // Getters
  public int getX() { return x; }
  public int getY() { return y; }

  // Setters
  public void setX(int x) { this.x = x; }
  public void setY(int y) { this.y = y; }

  // Méthode pour comparer deux positions
  public boolean equals(Position other) {
      return this.x == other.x && this.y == other.y;
  }

  // Calcul de la distance entre deux positions
  public double distanceTo(Position other) {
      return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
  }

  @Override
  public String toString() {
      return String.format("(%d, %d)", x, y);
  }
}
