import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import libraries.StdDraw;

public class InterfaceGraphique {
  
  private Forme forme;
  UniteGraphique affichageUnite;

  public void setAffichageUnite(UniteGraphique affichageUnite) {
    this.affichageUnite = affichageUnite;
  }

  public InterfaceGraphique(Forme forme, UniteGraphique affichageUnite) {
    this.forme = forme;
    this.affichageUnite = affichageUnite;

  }

  public void afficher(Wave wave) {
    fenetre(); // Configure la fenêtre
    afficherZones(wave); // Appelle l'affichage des zones dès l'instanciation
    interagirAvecLaSouris();
  }

  public void fenetre() {
    StdDraw.setCanvasSize(1024, 720);
    StdDraw.setXscale(-12, 1012);
    StdDraw.setYscale(-10, 710);
    StdDraw.enableDoubleBuffering();
  }

  public void afficherZones(Wave wave) {
    afficherZoneMap();
    afficherZoneLevel();
    afficherZonePlayer();
    afficherZoneStore();
    // testAff();
    afficherCheminEnnemis(wave, affichageUnite.getEnnemisActif());
    StdDraw.show();
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

  private void afficherZoneMap() {

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

  private void afficherZoneLevel() {
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

  private void afficherZonePlayer() {
    double centerX = 856;
    double centerY = 641;
    double halfWidth = 144;
    double halfHeight = 25;

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
    for (Ennemis ennemi : ennemisActifs) {
      // Obtenir la position actuelle de l'ennemi
      Position currentPosition = ennemi.getPosition();

      // Dessiner l'ennemi sur sa position actuelle
      affichageUnite.dessinerEnnemi(
          currentPosition.getX(),
          currentPosition.getY(),
          forme.getHalfLenghtCase() / 2,
          ennemi.getColor());
    }

    // Mettre à jour l'affichage
    StdDraw.show();
  }

  /*
   * public void afficherEnemisAuSpawn(Wave wave, double elapsedTimeMs) {
   * Map<Temps, Ennemis> ennemisMap = wave.getVague();
   * 
   * for (Map.Entry<Temps, Ennemis> entry : ennemisMap.entrySet()) {
   * Temps temps = entry.getKey();
   * Ennemis ennemi = entry.getValue();
   * 
   * if (temps.getTempsMs() == elapsedTimeMs) {
   * Position spawnPosition =
   * Position.fromCase(forme.trouverSpawn().getPosition(),
   * forme.getHalfLenghtCase());
   * ennemi.setPosition(spawnPosition);
   * 
   * affichageUnite.dessinerEnnemi(
   * spawnPosition.getX(),
   * spawnPosition.getY(),
   * forme.getHalfLenghtCase() / 2,
   * ennemi.getColor());
   * StdDraw.show();
   * }
   * }
   * }
   */
  /*
   * public void afficherCheminEnnemis(Wave wave, List<Ennemis> ennemisActifs) {
   * // Afficher tous les ennemis actifs sur leur position actuelle
   * for (Ennemis ennemi : ennemisActifs) {
   * // Obtenir la position actuelle de l'ennemi
   * Position currentPosition = ennemi.getPosition();
   * 
   * // Dessiner l'ennemi sur sa position actuelle
   * affichageUnite.dessinerEnnemi(
   * currentPosition.getX(),
   * currentPosition.getY(),
   * forme.getHalfLenghtCase() / 2,
   * ennemi.getColor()
   * );
   * }
   * 
   * // Mettre à jour l'affichage
   * StdDraw.show();
   * }
   */

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

  /*
   * public void afficherEnemisAuSpawn(Wave wave, double elapsedTimeMs) {
   * Map<Temps, Ennemis> ennemisMap = wave.getVague();
   * 
   * for (Map.Entry<Temps, Ennemis> entry : ennemisMap.entrySet()) {
   * Temps temps = entry.getKey();
   * Ennemis ennemi = entry.getValue();
   * 
   * // Comparer les temps arrondis à une décimale
   * if (Math.round(temps.getSecondes() * 10) / 10.0 == Math.round(elapsedTimeMs *
   * 10) / 10.0) {
   * // Calculer la position de spawn
   * Position spawnPosition =
   * Position.fromCase(forme.trouverSpawn().getPosition(),
   * forme.getHalfLenghtCase());
   * ennemi.setPosition(spawnPosition);
   * 
   * // Dessiner l'ennemi à la position de spawn
   * affichageUnite.dessinerEnnemi(
   * spawnPosition.getX(),
   * spawnPosition.getY(),
   * forme.getHalfLenghtCase() / 2,
   * ennemi.getColor());
   * }
   * }
   * 
   * // Mettre à jour l'affichage après les dessins
   * StdDraw.show();
   * }
   */
}
