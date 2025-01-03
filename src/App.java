import java.awt.Color;
import java.io.IOException;
import libraries.StdDraw; 

/*public class App {
    public static void main(String[] args) throws IOException {
        // Initialiser les objets nécessaires
        Forme forme = new Forme(".\\resources\\resources\\maps\\10-10.mtp");
        UniteGraphique uniteGraphique = new UniteGraphique();
        InterfaceGraphique interfaceGraphique = new InterfaceGraphique(forme, uniteGraphique);
        Wave wave = new Wave(forme);
        
        // Charger une vague
        wave.creerVague(".\\resources\\resources\\waves\\waveMinion.wve");
        System.out.println(wave.getVague());

        // Configurer la fenêtre
        interfaceGraphique.fenetre();

        // Variables pour le chrono
        long startTime = System.currentTimeMillis();

        while (true) {
            // Calculer le temps écoulé
            double elapsedTimeSec = (System.currentTimeMillis() - startTime) / 1000.0;
            System.out.println(elapsedTimeSec);
            //previousTime = currentTime;

            // Mettre à jour et afficher les zones
            interfaceGraphique.afficherZones(wave, elapsedTimeSec);

            // Pause pour limiter les mises à jour (facultatif, environ 60 FPS)
            StdDraw.pause(16);
        }
    }
}*/



public class App{
    public static void main ( String [] args ) throws IOException{
        Game g = new Game () ;
        g. launch () ;
    }
}

/*public class App{
    public static void main ( String [] args ) throws IOException{
        Forme f = new Forme(".\\resources\\resources\\maps\\10-10.mtp");
        Wave w = new Wave(f);
        w.creerVague(".\\resources\\resources\\waves\\waveMinion.wve");
        System.out.println(w);
        System.out.println(f.calculerChemin());
        PositionCase start = f.trouverSpawn().getPosition();
        PositionCase fin = f.trouverBase().getPosition();
        PositionCase deuxieme = new PositionCase(2, 1);
        Position deux = Position.fromCase(deuxieme, f.getHalfLenghtCase());
        Position startPosition = Position.fromCase(start,f.getHalfLenghtCase());
        System.out.println(start);
        Position finPosition = Position.fromCase(fin,f.getHalfLenghtCase());
        System.out.println(startPosition);
        System.out.println(finPosition);
        Minion min = new Minion(
            "min",           // Nom
            100,             // Points de vie maximum
            20,              // Attaque
            1.5,             // Vitesse d'attaque
            5,               // Portée
            startPosition,   // Position initiale
            Element.NEUTRE,  // Élément
            22.0,             // Vitesse de déplacement
            50,              // Récompense
            Color.RED        // Couleur
        );
        System.out.println("debut : "+min.getPosition());
        min.avancer(0.001,f.calculerChemin(),min.getSpeed());
        System.out.println("fin : "+min.getPosition());
        InterfaceGraphique interfaceGraphique = new InterfaceGraphique(f, new UniteGraphique());
        //interfaceGraphique.afficherMinionAuSpawn(min);
        interfaceGraphique.afficher(min);
       

        //Game g = new Game () ;
        //g. launch () ;
    }
    
}*/

/*public class App {
    public static void main(String[] args) throws IOException {
        // Configuration de la fenêtre graphique
        StdDraw.setCanvasSize(1024, 720);
        StdDraw.setXscale(-12, 1012);
        StdDraw.setYscale(-10, 710);
        StdDraw.enableDoubleBuffering();

        // Chargement de la carte et de la vague
        Forme f = new Forme(".\\resources\\resources\\maps\\5-8.mtp");
        Wave w = new Wave(f);
        w.creerVague(".\\resources\\resources\\waves\\waveMinion.wve");

        // Récupération de la position de spawn
        PositionCase spawnCase = f.trouverSpawn().getPosition();
        double halfLengthCase = f.getHalfLenghtCase();

        // Calcul des coordonnées du centre de la case de spawn
        Position spawnCenter = Position.fromCase(spawnCase, halfLengthCase);

        // Création d'un minion
        Minion min = new Minion(
            "min",           // Nom
            100,             // Points de vie maximum
            20,              // Attaque
            1.5,             // Vitesse d'attaque
            5,               // Portée
            spawnCenter,     // Position initiale (centre du spawn)
            Element.NEUTRE,  // Élément
            2.0,             // Vitesse de déplacement
            50,              // Récompense
            Color.RED        // Couleur
        );

        // Affichage graphique
        StdDraw.clear();
        UniteGraphique.dessinerEnnemi(spawnCenter.getX(), spawnCenter.getY(), halfLengthCase / 2, Color.RED);
        StdDraw.show();

        // Pause pour observer l'affichage
        StdDraw.pause(2000);
    }
}*/
