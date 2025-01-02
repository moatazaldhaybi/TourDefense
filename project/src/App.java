import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            // Charger une carte exemple
            Forme forme = new Forme(".\\resources\\resources\\maps\\5-8.mtp");
            Case spawn = forme.trouverSpawn();
            Position positionSpawn = spawn.getPosition();
            System.out.println("Position actuelle : " + positionSpawn);

            new InterfaceGraphique(forme);

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        }
        List<Case> chemin = new ArrayList<>();
        chemin.add(new Case("S", new Position(0, 0), false));
        chemin.add(new Case("R", new Position(1, 0), false));
        chemin.add(new Case("R", new Position(2, 0), false));
        chemin.add(new Case("B", new Position(3, 0), false));

        // Création d'un ennemi spécifique (Minion)
        Minion ennemi = new Minion("Minion", 10, 1, 1.0, 1, new Position(0, 0), Element.NEUTRE, 1.5, 5, Color.BLACK,0.4);
        

        // Simulation d'avancée
        double deltaTime = 0.5; // Intervalle de temps (en secondes)
        //ennemi.avancer(chemin);
        
    }
}

/*public class App {
    public static void main(String[] args) throws Exception {
        try {
            
            // Charger une carte exemple
            Forme forme = new Forme(".\\resources\\resources\\maps\\10-10.mtp");

            // Initialisation de l'interface graphique
            //new InterfaceGraphique(forme);

            // Trouver la position du spawn
            Case spawn = forme.trouverSpawn();
            Position positionSpawn = spawn.getPosition();

            // Exemple de chemin
            List<Case> chemin = forme.calculerChemin();

            // Création d'un ennemi (Minion)
            Minion ennemi = new Minion("Minion", 10, 1, 1.0, 1, positionSpawn, Element.NEUTRE, 1.5, 5, Color.BLACK);

            // Simulation d'avancée
            double deltaTime = 0.5; // Intervalle de temps (en secondes)
            System.out.println(positionSpawn.toString());
            ennemi.avancer(deltaTime, chemin);
            System.out.println(ennemi.getPosition().toString());
            

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}*/
