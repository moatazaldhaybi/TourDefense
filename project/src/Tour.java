import java.awt.Color;

public class Tour extends Unite  {
  private String nom;
  private int cost;

  public Tour(String nom, int atk, int maxPv, double atkSpeed, int range, Element element, Position position, int cost, 
  Color color) {
    super(maxPv, atk, atkSpeed, range, element, position, color);
    this.nom = nom;
    this.cost=cost;
  }

  public String getNom() {
    return nom;
  }

  public int getCost() {
    return cost;
  }
  
  
}

