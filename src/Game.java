import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private boolean gameRunning;
    private Wave wave;
    private Chrono chrono;
    private Forme forme;
    private InterfaceGraphique interfaceGraphique;
    private List<Ennemis> ennemisActifs;

    public Game() throws IOException {
        gameRunning = true;
        chrono = new Chrono();
        forme = new Forme(".\\resources\\resources\\maps\\10-10.mtp"); // Initialise la carte
        ennemisActifs = new ArrayList<>(); // Liste des ennemis actuellement sur le chemin
        interfaceGraphique = new InterfaceGraphique(forme, new UniteGraphique(ennemisActifs));
        wave = new Wave(forme); // Initialise les vagues
    }

    public void launch() {
        try {
            init();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'initialisation : " + e.getMessage());
            return;
        }

        long startTime = System.currentTimeMillis();//1000

        while (gameRunning) {
            double elapsedTimeSec = (System.currentTimeMillis() - startTime) / 1000.0;

            update(elapsedTimeSec);
            render(elapsedTimeSec);
        }

        //endGame();
    }

    private void init() throws IOException {
        wave.creerVague(".\\resources\\resources\\waves\\waveMinion.wve"); // Charge un fichier de vague
        interfaceGraphique.fenetre();
        System.out.println("Vague chargée avec succès.");
    }
private void update(double elapsedTimeSec) {
    wave.getVague().entrySet().removeIf(entry -> {
        double spawnTime = entry.getKey().getSecondes();
        if (Math.round(spawnTime * 10) / 10.0 == Math.round(elapsedTimeSec * 10) / 10.0) {
            ennemisActifs.add(entry.getValue());
            interfaceGraphique.afficherEnemisAuSpawn(wave, elapsedTimeSec);
            System.out.println("Ennemi ajouté : " + entry.getValue().getName());
            return true;
        }
        return false;
    });

    System.out.println(ennemisActifs);

    for (Ennemis ennemi : new ArrayList<>(ennemisActifs)) {
        System.out.println("Position actuelle de l'ennemi : "+ennemi.getPosition());
        ennemi.avancer(elapsedTimeSec, forme.calculerChemin(), ennemi.getSpeed());
        System.out.println("Position apr de l'ennemi : "+ennemi.getPosition());

        if (ennemi.getPosition().equals(forme.trouverBase().getPosition())) {
            ennemisActifs.remove(ennemi);
            System.out.println("Un ennemi a atteint la base !");
        }
    }
}
private void render(double elapsedTimeSec) {
    interfaceGraphique.afficherZones(wave);
    interfaceGraphique.afficherCheminEnnemis(wave, ennemisActifs);
}
}



    /*private vo
    ifie les ennemis à faire apparaître
        wave.getVague().entrySet().removeIf(entry -> {
            double spawnTime = entry.getKey().getSecondes();
            if (Math.round(spawnTime * 10) / 10.0 == Math.round(elapsedTimeSec * 10) / 10.0) {
                ennemisActifs.add(entry.getValue());
                interfaceGraphique.afficherEnemisAuSpawn(wave, elapsedTimeSec);
                System.out.println("Ennemi ajouté : " + entry.getValue().getName());
                return true; // Retirer l'entrée une fois l'ennemi ajouté
            }
            return false;
        });

        // Fait avancer les ennemis
        for (Ennemis ennemi : new ArrayList<>(ennemisActifs)) {
            ennemi.avancer(elapsedTimeSec, forme.calculerChemin(), ennemi.getSpeed());

            // Si l'ennemi atteint la base, le retirer
            if (ennemi.getPosition().equals(forme.trouverBase().getPosition())) {
                ennemisActifs.remove(ennemi);
                System.out.println("Un ennemi a atteint la base !");
            }
        }
    }

    private void render(double elapsedTimeSec) {
        // Affiche les zones et les ennemis actifs
        interfaceGraphique.afficherZones(wave);
        interfaceGraphique.afficherCheminEnnemis(wave, ennemisActifs);
    }

    private void endGame() {
        System.out.println("Jeu terminé !");
        if (ennemisActifs.isEmpty() && wave.getVague().isEmpty()) {
            System.out.println("Victoire : Toutes les vagues ont été complétées !");
        } else {
            System.out.println("Défaite : Les ennemis ont atteint votre base !");
        }
    }
}*/
