import java.awt.Color;

public class Unite {
    private int pv;            // Points de vie actuels
    private int maxPv;         // Points de vie maximum
    private int atk;           // Valeur d'attaque
    private double atkSpeed;   // Vitesse d'attaque (en secondes)
    private int range;         // Portée d'attaque (en cases)
    private Element element;   // Élément (Neutre, Feu, Terre, Air, Eau)
    private Position position; // Position actuelle sur la carte
    private Color color;       // Couleur associée à l'unité

    // Constructeur
    public Unite(int maxPv, int atk, double atkSpeed, int range, Element element, Position position, Color color) {
      if (maxPv <= 0 || atk < 0 || atkSpeed <= 0 || range < 0) {
        throw new IllegalArgumentException("Les valeurs des attributs doivent être positives.");
      }
      this.pv = maxPv;
      this.maxPv = maxPv;
      this.atk = atk;
      this.atkSpeed = atkSpeed;
      this.range = range;
      this.element = element;
      this.position = position;
      this.color = color;
    }


    public int getPv() { return pv; }
    public int getMaxPv() { return maxPv; }
    public int getAtk() { return atk; }
    public double getAtkSpeed() { return atkSpeed; }
    public int getRange() { return range; }
    public Element getElement() { return element; }
    public Position getPosition() { return position; }
    public Color getColor() { return color; }

  
    public void setPosition(Position position) { this.position = position; }

    // Méthodes communes
    public boolean estMort() {
        return pv <= 0;
    }

    public void recevoirDegats(int degats) {
        this.pv -= degats;
        if (this.pv < 0) {
            this.pv = 0;
        }
    }

    public double distanceVers(Position cible) {
        double dx = position.getX() - cible.getX();
        double dy = position.getY() - cible.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double calculerVulnerabilite(Element elementDefendeur, Element elementAttaquant) {
      if (elementDefendeur == Element.FEU && elementAttaquant == Element.TERRE) return 1.5;
      if (elementDefendeur == Element.EAU && elementAttaquant == Element.FEU) return 1.5;
      if (elementDefendeur == Element.AIR && elementAttaquant == Element.EAU) return 1.5;
      if (elementDefendeur == Element.TERRE && elementAttaquant == Element.AIR) return 1.5;
  
      if (elementDefendeur == Element.TERRE && elementAttaquant == Element.FEU) return 0.5;
      if (elementDefendeur == Element.FEU && elementAttaquant == Element.EAU) return 0.5;
      if (elementDefendeur == Element.EAU && elementAttaquant == Element.AIR) return 0.5;
      if (elementDefendeur == Element.AIR && elementAttaquant == Element.TERRE) return 0.5;
  
      return 1.0;
  }

  public void subitDegat(Unite source) {
    double vuln = calculerVulnerabilite(this.getElement(), source.getElement());
    int degats = (int) (source.getAtk() * vuln);
    recevoirDegats(degats);
  }
  
}
