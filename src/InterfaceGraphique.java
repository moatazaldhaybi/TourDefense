import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import libraries.StdDraw;

public class InterfaceGraphique {

  private Forme forme;
  UniteGraphique affichageUnite;
  private List<Case> caseTour;
  private Joueur joueur;
  private double x0 = 0;
  private double y0 = 0;
  private Tour tour;
  private boolean m_estStoreSelectionne = false;

  public void setAffichageUnite(UniteGraphique affichageUnite) {
    this.affichageUnite = affichageUnite;
  }

  public InterfaceGraphique(Forme forme, UniteGraphique affichageUnite, Joueur joueur, Tour tour) {
    this.tour = tour;
    this.forme = forme;
    this.affichageUnite = affichageUnite;
    this.joueur = joueur;
    this.caseTour = forme.calculerCasesTour();

  }

  public void afficher(Wave wave) {
    fenetre(); // Configure la fenêtre
    afficherZones(wave); // Appelle l'affichage des zones dès l'instanciation
  }

  public void disparus() {
    fenetre();
    afficherZoneMap();
    afficherZoneLevel();
    afficherZonePlayer();
    afficherZoneStore();
  }

  public void fenetre() {
    StdDraw.setCanvasSize(1024, 720);
    StdDraw.setXscale(-12, 1012);
    StdDraw.setYscale(-10, 710);
    StdDraw.enableDoubleBuffering();
  }

  public void afficherZones(Wave wave) {
    // afficherZoneMap();
    // afficherZoneLevel();

    if (StdDraw.isMousePressed()) {
      // Récupère les coordonnées de la souris
      double x = StdDraw.mouseX();
      double y = StdDraw.mouseY();
      caseDeselectionne(x0, y0);
      x0 = x;
      y0 = y;
      caseSelectionne(x, y);

    }

    afficherZonePlayer();
    afficherZoneStore();
    // testAff();
    afficherCheminEnnemis(wave, affichageUnite.getEnnemisActif());
    StdDraw.show();
  }

  private boolean estDansZoneStore(double x, double y) {
    // Définir le centre et les demi-dimensions de la zone Store
    double centerX = 856;
    double centerY = 303;
    double halfWidth = 144;
    double halfHeight = 303;

    // Vérifier si le point (x, y) est dans les limites du rectangle
    return (x >= centerX - halfWidth && x <= centerX + halfWidth &&
        y >= centerY - halfHeight && y <= centerY + halfHeight);
  }

  public void caseSelectionne(double x, double y) {
    if (!estDansZoneStore(x, y)) {
      double a = 350.0;
      int rows = forme.getRows();
      int cols = forme.getCols();
      double maxHalfLength = Math.min(a / cols, a / rows);
      int row = (int) ((a + a - y) / (2 * maxHalfLength));
      int col = (int) ((x - (a - a)) / (2 * maxHalfLength));
      double caseCenterX = a - a + maxHalfLength * (2 * col + 1);
      double caseCenterY = a + a - maxHalfLength * (2 * row + 1);
      StdDraw.setPenColor(Color.YELLOW);
      StdDraw.square(caseCenterX, caseCenterY, maxHalfLength);
    }

  }

  public void caseDeselectionne(double x, double y) {
    // System.out.println(x);
    // System.out.println(y);
    if (!estDansZoneStore(x, y)) {
      if (x != 0 && y != 0) {
        double a = 350.0;
        int rows = forme.getRows();
        int cols = forme.getCols();
        double maxHalfLength = Math.min(a / cols, a / rows);
        int row = (int) ((a + a - y) / (2 * maxHalfLength));
        int col = (int) ((x - (a - a)) / (2 * maxHalfLength));
        double caseCenterX = a - a + maxHalfLength * (2 * col + 1);
        double caseCenterY = a + a - maxHalfLength * (2 * row + 1);
        StdDraw.setPenRadius(0.003);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.square(caseCenterX, caseCenterY, maxHalfLength);
      }
    }
  }

  public void testAff() {
    StdDraw.setPenColor(Color.BLACK);
    StdDraw.filledCircle(595.00, 105.00, 15);
  }

  // Méthode pour dessiner la grille
  private void dessinerGrille(int rows, int cols, double centerX, double centerY, double halfWidth, double halfHeight,
      double maxHalfLength) {
    StdDraw.setPenColor(Color.BLACK);

    // Lignes horizontales
    for (int i = 0; i <= rows; i++) {
      double y = centerY + halfHeight - i * (2 * maxHalfLength);
      StdDraw.line(centerX - halfWidth, y, cols * 2 * maxHalfLength, y);
    }

    // Lignes verticales
    for (int j = 0; j <= cols; j++) {
      double x = centerX - halfWidth + j * (2 * maxHalfLength);
      StdDraw.line(x, centerY + halfHeight, x, centerY + halfHeight - rows * 2 * maxHalfLength);
    }
  }

  public void afficherZoneMap() {

    double centerX = 350.0;
    double centerY = 350.0;
    double halfWidth = 350.0;
    double halfHeight = 350.0;

    // Dessiner le contour de la zone carte
    StdDraw.setPenColor(Color.BLACK);
    StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

    // Récupérer les dimensions de la carte
    int rows = forme.getRows(); // Nombre de lignes
    int cols = forme.getCols(); // Nombre de colonnes

    // Calculer la taille des cases
    double maxHalfLength = forme.getHalfLenghtCase();

    // Dessiner les cases de la carte
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {

        // Calculer les coordonnées du centre de la case
        double caseCenterX = centerX - halfWidth + maxHalfLength * (2 * j + 1);
        double caseCenterY = centerY + halfHeight - maxHalfLength * (2 * i + 1);

        Case c = forme.getCase(i, j);
        StdDraw.setPenColor(getCouleur(c.getType()));
        StdDraw.filledSquare(caseCenterX, caseCenterY, maxHalfLength);

        // Dessiner la grille pour délimiter les cases
        dessinerGrille(rows, cols, centerX, centerY, halfWidth, halfHeight, maxHalfLength);

      }
    }

  }

  // Méthode pour obtenir la couleur d'une case
  private Color getCouleur(String type) {
    switch (type) {
      case "S":
        return Color.RED;
      case "B":
        return Color.ORANGE;
      case "R":
        return new Color(194, 178, 128);
      case "C":
        return Color.LIGHT_GRAY;
      case "X":
        return new Color(11, 102, 35);
      default:
        return Color.WHITE;
    }
  }

  public void afficherZoneLevel() {
    double centerX = 856;
    double centerY = 688;
    double halfWidth = 144;
    double halfHeight = 12;

    StdDraw.setPenColor(Color.BLACK); // Couleur des bordures
    StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

    StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
    StdDraw.textRight(centerX + halfWidth - 10, centerY, "Wave: 5/10");
    StdDraw.textLeft(centerX - halfWidth + 10, centerY, "Level: " + forme.getLevel() + "/3");
  }

  public void afficherZonePlayer() {
    double centerX = 856;
    double centerY = 641;
    double halfWidth = 144;
    double halfHeight = 25;

    StdDraw.setPenColor(Color.WHITE);
    StdDraw.filledRectangle(centerX, centerY, halfWidth, halfHeight);
    // Dessiner le rectangle représentant la Zone Player
    StdDraw.setPenColor(Color.BLACK);
    StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

    // Dessiner une pièce (cercle doré) plus à gauche
    double pieceRadius = 20; // Rayon de la pièce
    double pieceX = centerX - halfWidth + 30; // Ajuster pour placer plus à gauche
    StdDraw.setPenColor(new Color(212, 175, 55)); // Or
    StdDraw.filledCircle(pieceX, centerY, pieceRadius);
    StdDraw.setPenColor(new Color(192, 192, 192)); // Argent
    StdDraw.filledCircle(pieceX, centerY, 0.7 * pieceRadius);
    StdDraw.textRight(centerX + halfWidth - 10, centerY, "50");
    StdDraw.setPenColor(Color.BLACK);
    StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
    StdDraw.textRight(centerX + halfWidth - 50, centerY, "" + joueur.getPointsDeVie());
    StdDraw.textLeft(centerX - halfWidth + 50, centerY, "" + joueur.getArgent());

    // Dessiner un cœur (rouge) plus à droite
    double heartX = centerX + halfWidth - 30; // Ajuster pour placer plus à droite
    double heartHalfHeight = 20; // Demi-hauteur du cœur
    StdDraw.setPenColor(new Color(223, 75, 95)); // Rouge
    double[] listX = {
        heartX,
        heartX - heartHalfHeight,
        heartX - heartHalfHeight,
        heartX - 0.66 * heartHalfHeight,
        heartX - 0.33 * heartHalfHeight,
        heartX,
        heartX + 0.33 * heartHalfHeight,
        heartX + 0.66 * heartHalfHeight,
        heartX + heartHalfHeight,
        heartX + heartHalfHeight
    };
    double[] listY = {
        centerY - heartHalfHeight,
        centerY,
        centerY + 0.5 * heartHalfHeight,
        centerY + heartHalfHeight,
        centerY + heartHalfHeight,
        centerY + 0.5 * heartHalfHeight,
        centerY + heartHalfHeight,
        centerY + heartHalfHeight,
        centerY + 0.5 * heartHalfHeight,
        centerY
    };
    StdDraw.filledPolygon(listX, listY);

    // Configurer la police (non gras)
    StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14)); // Police Arial, normal, taille 14

    // Texte pour la pièce (Gold) aligné à droite de la pièce
    StdDraw.textLeft(pieceX + 20, centerY, "");

    // Texte pour le cœur (Life) aligné à droite du cœur
    StdDraw.textLeft(heartX + 20, centerY, "");
  }

  private void afficherZoneStore() {
    // Définir le centre et les demi-dimensions
    double centerX = 856;
    double centerY = 303;
    double halfWidth = 144;
    double halfHeight = 303;

    // Dessiner le rectangle représentant la Zone Store
    StdDraw.setPenColor(Color.BLACK);
    StdDraw.rectangle(centerX, centerY, halfWidth, halfHeight);

    // Ajouter du texte (optionnel)
    StdDraw.setPenColor(Color.BLACK);
    StdDraw.text(centerX, centerY + 280, "Store"); // Texte au-dessus de la zone
    if (StdDraw.isMousePressed()) {
      // Récupère les coordonnées de la souris
      double x = StdDraw.mouseX();
      double y = StdDraw.mouseY();
      if (estDansZoneStore(x, y)) {
        // System.out.println("Boss : "+estDansZoneStore(x, y));
        m_estStoreSelectionne = true;
      }
      if (m_estStoreSelectionne) {
        if (joueur.verifierAcheterTour(tour)) {
          StdDraw.setPenColor(Color.WHITE);
          StdDraw.filledRectangle(centerX, centerY - 150, 135, 20);
          afficherTourSelec(tour);

        } else {
          // Afficher un message sur la zone graphique pour indiquer que l'argent est
          // insuffisant
          StdDraw.setPenColor(Color.RED);
          StdDraw.text(centerX, centerY - 150, "Argent insuffisant pour acheter la tour!");

        }
        // //if(m_estStoreSelectionne){
        // if(StdDraw.isMousePressed()){
        // double a = StdDraw.mouseX();
        // double b = StdDraw.mouseY();
        // if (estDansZoneStore(a, b)){
        // //System.out.println("Boss : "+estDansZoneStore(x, y));
        // m_estStoreSelectionne = false;
        // }
        // }

      }

    }

  }

  public void afficherTourSelec(Tour t) {
    if (StdDraw.isMousePressed()) {
      double a = StdDraw.mouseX();
      double b = StdDraw.mouseY();
      Position positionSouris = new Position(a, b);
      PositionCase posCaseSouris = Position.toCase(positionSouris, forme.getHalfLenghtCase());
      for (Case p : caseTour) {
        if (posCaseSouris.getRow() == p.getPosition().getRow() && posCaseSouris.getCol() == p.getPosition().getCol()) {
          if (!p.isOccupe()) {
            afficherTour(t, p.getPosition().getRow(), p.getPosition().getCol());
            joueur.acheterTour(t);
            m_estStoreSelectionne = false;
            p.setOccupe(true);
          }
        }
      }
    }
  }

  public List<Position> caseTourPixel() {
    List<Position> p = new ArrayList<>();
    for (Case c : caseTour) {
      Position pCase = Position.fromCase(c.getPosition(), forme.getHalfLenghtCase());
      p.add(pCase);
    }
    return p;
  }

  /*
   * private boolean isCaseSelected(int row, int col){
   * double centerX = 350.0;
   * double centerY = 350.0;
   * double halfWidth = 350.0;
   * double halfHeight = 350.0;
   * 
   * int lignes = forme.getRows();
   * int colonnes = forme.getCols();
   * double maxHalfLength = Math.min(halfWidth / colonnes, halfHeight / lignes);
   * double caseCenterX = maxHalfLength * (2*row+1);
   * double caseCenterY = centerY + halfHeight - maxHalfLength * (2*col+1);
   * 
   * double caseDroite = caseCenterX + maxHalfLength;
   * double caseGauche = caseCenterX - maxHalfLength;
   * double caseHaut = caseCenterY + maxHalfLength;
   * double caseBas = caseCenterY - maxHalfLength;
   * 
   * double mouseX = StdDraw.mouseX();
   * double mouseY = StdDraw.mouseY();
   * 
   * return (mouseX<=caseDroite && mouseX>=caseGauche && mouseY<=caseHaut &&
   * mouseY>=caseBas);
   * }
   */

  private void interagirAvecLaSouris() {
    double centerX = 350.0;
    double centerY = 350.0;
    double halfWidth = 350.0;
    double halfHeight = 350.0;

    int rows = forme.getRows();
    int cols = forme.getCols();
    double maxHalfLength = Math.min(halfWidth / cols, halfHeight / rows);

    while (true) {
      // Vérifie si la souris est pressée
      if (StdDraw.isMousePressed()) {
        // Récupère les coordonnées de la souris
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();

        // Convertir les coordonnées en indices de case
        int row = (int) ((centerY + halfHeight - y) / (2 * maxHalfLength));
        int col = (int) ((x - (centerX - halfWidth)) / (2 * maxHalfLength));

        // Vérifie si les indices sont valides
        if (col >= 0 && col < cols && row >= 0 && row < rows) {
          // Mettre à jour l'apparence de la case sélectionnée
          double caseCenterX = centerX - halfWidth + maxHalfLength * (2 * col + 1);
          double caseCenterY = centerY + halfHeight - maxHalfLength * (2 * row + 1);

          // Redessine la carte
          afficherZoneMap();

          // Ajoute une bordure jaune autour de la case sélectionnée
          StdDraw.setPenColor(Color.YELLOW);
          StdDraw.square(caseCenterX, caseCenterY, maxHalfLength);
          StdDraw.show();
        }

        // Pause pour éviter des clics multiples rapides
        StdDraw.pause(200);
      }
    }
  }

  /*
   * public void afficherMinionAuSpawn(Wave wave) {
   * double centerX = 350.0;
   * double centerY = 350.0;
   * double halfWidth = 350.0;
   * double halfHeight = 350.0;
   * // Récupérer la position centrale de la case de spawn
   * PositionCase spawnCase = forme.trouverSpawn().getPosition();
   * double halfLengthCase = forme.getHalfLenghtCase();
   * Position spawnCenter = Position.fromCase(spawnCase, halfLengthCase);
   * 
   * // Mettre à jour la position du minion
   * minion.setPosition(spawnCenter);
   * 
   * // Dessiner le minion à la position centrale
   * affichageUnite.dessinerEnnemi(spawnCenter.getX(), spawnCenter.getY(),
   * halfLengthCase / 2, minion.getColor());
   * StdDraw.show();
   * }
   */
  public void afficherCheminEnnemis(Wave wave, List<Ennemis> ennemisActifs) {
    List<Case> chemin = forme.getChemin();
    for (Case c : chemin) {
      StdDraw.setPenColor(getCouleur(c.getType()));
      StdDraw.filledSquare(Position.fromCase(c.getPosition(), forme.getHalfLenghtCase()).getX(),
          Position.fromCase(c.getPosition(), forme.getHalfLenghtCase()).getY(), forme.getHalfLenghtCase());
      // StdDraw.setPenColor(Color.BLACK);
      // StdDraw.square(Position.fromCase(c.getPosition(),
      // forme.getHalfLenghtCase()).getX(), Position.fromCase(c.getPosition(),
      // forme.getHalfLenghtCase()).getY(), forme.getHalfLenghtCase());
    }
    for (Ennemis ennemi : ennemisActifs) {
      // Obtenir la position actuelle de l'ennemi
      Position currentPosition = ennemi.getPosition();

      // Dessiner l'ennemi sur sa position actuelle

      if (!Position.toCase(ennemi.getPosition(), forme.getHalfLenghtCase())
          .comparePosition(forme.trouverBase().getPosition())) {
        affichageUnite.dessinerEnnemi(
            currentPosition.getX(),
            currentPosition.getY(),
            forme.getHalfLenghtCase() / 5,
            ennemi.getColor());
      }
    }

    // Mettre à jour l'affichage
    StdDraw.show();
  }

  public void afficherTour(Tour t, int x, int y) {
    Position p = Position.fromCase(new PositionCase(x, y), forme.getHalfLenghtCase());
    System.out.println(p);
    affichageUnite.dessinerTour(p.getX(), p.getY(), forme.getHalfLenghtCase(), forme.getHalfLenghtCase(), t.getColor());
  }

  public void afficherEnemisAuSpawn(Wave wave, double elapsedTimeMs) {
    Map<Temps, Ennemis> ennemisMap = wave.getVague();

    for (Map.Entry<Temps, Ennemis> entry : ennemisMap.entrySet()) {
      Temps temps = entry.getKey();
      Ennemis ennemi = entry.getValue();

      if (Math.round(temps.getSecondes() * 10) / 10.0 == Math.round(elapsedTimeMs * 10) / 10.0) {
        Position spawnPosition = Position.fromCase(forme.trouverSpawn().getPosition(), forme.getHalfLenghtCase());
        ennemi.setPosition(spawnPosition);

        affichageUnite.dessinerEnnemi(
            spawnPosition.getX(),
            spawnPosition.getY(),
            forme.getHalfLenghtCase() / 2,
            ennemi.getColor());
      }
    }

    StdDraw.show();
  }
}
