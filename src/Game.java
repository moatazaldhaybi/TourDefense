import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private boolean gameRunning;
    private Wave wave;
    private double m_elapsedTimeSec=0.0;
    private Forme forme;
    private InterfaceGraphique interfaceGraphique;
    private List<Ennemis> ennemisActifs;
    private List<Case> m_chemin;
    private Joueur joueur;
    private Tour tour;
    private Position position;

    public Game() throws IOException {
        gameRunning = true;
        forme = new Forme(".\\resources\\resources\\maps\\10-10.mtp"); // Initialise la carte
        position = Position.fromCase(new PositionCase(2, 3), forme.getHalfLenghtCase());
        System.out.println(position);
        tour = new Tour("BossMoataz", 100, 10, 2, 4, Element.FEU, position, 25, Color.BLUE);
        joueur = new Joueur(forme);
        ennemisActifs = new ArrayList<>(); // Liste des ennemis actuellement sur le chemin
        interfaceGraphique = new InterfaceGraphique(forme, new UniteGraphique(ennemisActifs), joueur, tour);
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
        long previousTime = System . currentTimeMillis () ;
        while (gameRunning) {
            long currentTime = System.currentTimeMillis();
            m_elapsedTimeSec  = (currentTime - startTime);
            double deltaTimeSec = ( double )( currentTime - previousTime ) /1000;
            previousTime = currentTime ;
            //System.out.println("TC : " + deltaTimeSec);
            update(deltaTimeSec);
        }

        //endGame();
    }

    private void init() throws IOException {
        m_chemin = forme.calculerChemin();
        //System.out.println(m_chemin);
        wave.creerVague(".\\resources\\resources\\waves\\waveBoss.wve"); // Charge `  un fichier de vague
        interfaceGraphique.fenetre();
        interfaceGraphique.afficherZoneMap();
        interfaceGraphique.afficherZoneLevel();
        //interfaceGraphique.afficherTour(tour);
        //System.out.println("Vague chargée avec succès.");
    }
private void update(double iDeltaTimeSec) {
    long timeBeforeEN = System.currentTimeMillis();
    wave.getVague().entrySet().removeIf(entry -> {
        double spawnTime = entry.getKey().getSecondes()*1000;
        //System.out.println("a : "+(int)(spawnTime));
        //System.out.println("b : "+(int)(m_elapsedTimeSec));
        if ((int)(m_elapsedTimeSec)>=(int)(spawnTime)) {
            ennemisActifs.add(entry.getValue());
            interfaceGraphique.setAffichageUnite(new UniteGraphique(ennemisActifs));
            //interfaceGraphique.afficherEnemisAuSpawn(wave, m_elapsedTimeSec);
            /*System.out.println("Ennemi ajouté : " + m_elapsedTimeSec + " " + spawnTime);
            for (Ennemis en : ennemisActifs) {
                System.out.println("Pose E: " + en.getPosition().getX() + " " + en.getPosition().getY());
            }*/
            return true;
        }
        return false;
    });

    //System.out.println(ennemisActifs.size());
    //if(!ennemisActifs.isEmpty()){
    //}

    // Update Enemie positions
    for (int i = ennemisActifs.size() - 1; i >= 0; i--) {
        Ennemis ennemi = ennemisActifs.get(i);
    
        //System.out.println("M : " + Position.toCase(ennemi.getPosition(), forme.getHalfLenghtCase()));
        //System.out.println("B : " + forme.trouverBase().getPosition());
    
        // Vérifie si l'ennemi a atteint la base
        if (Position.toCase(ennemi.getPosition(), forme.getHalfLenghtCase()).getRow() == forme.trouverBase().getPosition().getRow() &&
            Position.toCase(ennemi.getPosition(), forme.getHalfLenghtCase()).getCol() == forme.trouverBase().getPosition().getCol()) {
            
            ennemisActifs.remove(i); // Supprime l'ennemi
            joueur.ennemisBase(ennemi);
            //System.out.println("Un ennemi a atteint la base !");
        }
    
        // Met à jour la position de l'ennemi
        ennemi.avancer(iDeltaTimeSec, m_chemin, ennemi.getSpeed());
    }
    long timeAfterEN = System.currentTimeMillis();
    long elapsedTime = timeAfterEN - timeBeforeEN;
    //System.out.println("ET EN " + elapsedTime);
    // Update graphics
    timeBeforeEN = System.currentTimeMillis();
    render(m_elapsedTimeSec);
    timeAfterEN = System.currentTimeMillis();
    elapsedTime = timeAfterEN - timeBeforeEN;
    //System.out.println("ET RENDER " + elapsedTime);
}
private void render(double iElapsedTimeSec) {
    interfaceGraphique.afficherZones(wave);
    //interfaceGraphique.afficherCheminEnnemis(wave, ennemisActifs);
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
