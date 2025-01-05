import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.awt.Color;

public class Wave {
    private Map<Temps, Ennemis> vague; // Utiliser LinkedHashMap pour préserver l'ordre d'insertion
    private Forme forme;

    public Wave(Forme forme) {
        this.forme = forme;
        this.vague = new LinkedHashMap<>(); // Utilisation de LinkedHashMap
    }

    public void creerVague(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split("\\|");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Format de ligne invalide : " + ligne);
                }

                double t = Double.parseDouble(parts[0].trim());
                String type = parts[1].trim();

                Ennemis ennemi = creerEnnemi(type);
                Temps t0 = new Temps(t);
                //System.out.println(t0);
                vague.put(t0, ennemi); // L'ordre d'insertion est conservé
            }
        }
    }

    public Map<Temps, Ennemis> getVague() {
        return vague;
    }

    private Ennemis creerEnnemi(String type) {
        Position spawnPosition = Position.fromCase(forme.trouverSpawn().getPosition(), forme.getHalfLenghtCase());
        switch (type) {
            case "Minion":
                return new Minion("Minion", 10, 3, 1.0, 1, spawnPosition, Element.NEUTRE, 1*2*forme.getHalfLenghtCase(), 5, Color.BLACK);
            case "Earth Brute":
                return new EarthBrute("Earth Brute", 30, 5, 1.0, 3, spawnPosition, Element.TERRE, 1*2*forme.getHalfLenghtCase(), 10,
                        new Color(0, 167, 15));
            case "Water Brute":
                return new WaterBrute("Water Brute", 30, 5, 1.0, 3, spawnPosition, Element.EAU, 1*2*forme.getHalfLenghtCase(), 10, Color.BLUE);
            case "Fire Grognard":
                return new FireGrognard("Fire Grognard", 20, 7, 0.5, 2, spawnPosition, Element.FEU, 2*2*forme.getHalfLenghtCase(), 15,
                        new Color(184, 22, 1));
            case "Wind Grognard":
                return new WindGrognard("Wind Grognard", 20, 7, 0.5, 2, spawnPosition, Element.AIR, 2*2*forme.getHalfLenghtCase(), 15,
                        Color.YELLOW);
            case "Boss":
                return new Boss("Boss", 150, 50, 1.0, 5, spawnPosition, Element.NEUTRE, 0.5*2*forme.getHalfLenghtCase(), 100,
                        new Color(128, 0, 128));
            default:
                throw new IllegalArgumentException("Type d'ennemi inconnu : " + type);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Wave {\n");

        for (Map.Entry<Temps, Ennemis> entry : vague.entrySet()) {
            sb.append("  Temps: ").append(entry.getKey().toString())
                    .append(", Ennemi: ").append(entry.getValue().toString()).append("\n");
        }

        sb.append("}");
        return sb.toString();
    }

}
