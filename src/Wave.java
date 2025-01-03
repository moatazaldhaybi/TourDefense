import java.awt.Color;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wave {
  
  private Forme forme;
  Map<Temps, Ennemis> vague = new HashMap<>();  

  public Wave(Forme forme, String filePath){
    this.forme=forme; 
  }

  public void creerVague(String filePath) throws IOException {
    // Lecture du fichier
    List<String> lignes = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String ligne;
        while ((ligne = br.readLine()) != null) {
            lignes.add(ligne); // Ajoute chaque ligne à la liste
        }
    }

    // Validation des données
    if (lignes.isEmpty()) {
        throw new IllegalArgumentException("Le fichier est vide !");
    }

    // Parcourir les lignes et créer des objets Temps et Ennemis
    for (String ligne : lignes) {
        // Découpe la ligne en deux parties : temps et type d'ennemi
        String[] parts = ligne.split("\\|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Format de ligne invalide : " + ligne);
        }

        // Récupérer le temps
        double t = Double.parseDouble(parts[0]); // Convertir la partie gauche en double
        Temps temps = new Temps(t);

        // Récupérer le type d'ennemi
        String ennemiType = parts[1].trim();

        // Créer l'ennemi correspondant
        Ennemis ennemi = creerEnnemi(ennemiType);

        // Ajouter la paire (Temps, Ennemis) à la map
        vague.put(temps, ennemi);
    }
  }

  private Ennemis creerEnnemi(String ennemiType) {
    Position spawnPosition = forme.trouverSpawn().getPosition(); // Position du Spawn
    switch (ennemiType) {
        case "Minion":
            return new Minion("Minion", 10, 1, 1.0, 1, spawnPosition, Element.NEUTRE, 1.5, 5, Color.BLACK);

        case "Earth Brute":
            return new EarthBrute("Earth Brute", 30, 5, 1.0, 3, spawnPosition, Element.TERRE, 1.0, 10, Color.GREEN);

        case "Water Brute":
            return new WaterBrute("Water Brute", 30, 5, 1.0, 3, spawnPosition, Element.EAU, 1.0, 10, Color.BLUE);

        case "Fire Grognard":
            return new FireGrognard("Fire Grognard", 20, 7, 0.5, 2, spawnPosition, Element.FEU, 2.0, 15, Color.RED);

        case "Wind Grognard":
            return new WindGrognard("Wind Grognard", 20, 7, 0.5, 2, spawnPosition, Element.AIR, 2.0, 15, Color.YELLOW);

        case "Boss":
            return new Boss("Boss", 150, 50, 1.0, 5, spawnPosition, Element.NEUTRE, 0.5, 100, Color.MAGENTA);

        default:
            throw new IllegalArgumentException("Type d'ennemi inconnu : " + ennemiType);
    }
}



   
}
