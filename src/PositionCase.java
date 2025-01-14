import javax.swing.text.Position;

public class PositionCase {
  private int row; // Ligne
  private int col; // Colonne

  // Constructeur
  public PositionCase(int row, int col) {
      this.row = row;
      this.col = col;
  }

  // Getters
  public int getRow() {
      return row;
  }

  public int getCol() {
      return col;
  }

  public double distanceTo(PositionCase other) {
    double dx = this.row - other.row;
    double dy = this.col - other.col;
    return Math.sqrt(dx * dx + dy * dy);
}

public boolean comparePosition(PositionCase p){
    return this.getRow()==p.getRow() && this.getCol()==p.getCol();
}


  @Override
  public String toString() {
      return String.format("(%d, %d)", row, col);
  }
}
