import java.io.*;
import java.util.*;

import libraries.StdDraw;

public class Forme {
    private Case[][] cases;
    private int rows;
    private int cols;
    private static double halfLenghtCase;
    
        public Forme(String filePath) throws IOException {
            creerForme(filePath);
        }
    
        public Case getCase(int i, int j) {
            return cases[i][j];
        }
    
        public int getRows() {
            return rows;
        }
    
        public void setRows(int rows) {
            this.rows = rows;
        }
    
        public int getCols() {
            return cols;
        }
        
    
        public static double getHalfLenghtCase() {
            return halfLenghtCase;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    private void creerForme(String filePath) throws IOException {
        List<String> lignes = new ArrayList<>();

        // Lecture du fichier
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                lignes.add(ligne);
            }
        }

        // Validation des données
        if (lignes.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide !");
        }

        this.rows = lignes.size();
        this.cols = lignes.get(0).length();

        for (String ligne : lignes) {
            if (ligne.length() != cols) {
                throw new IllegalArgumentException("Toutes les lignes du fichier doivent avoir la même longueur !");
            }
        }

        double halfWidth = 350.0;
        double halfHeight = 350.0;
        halfLenghtCase = Math.min(halfWidth / cols, halfHeight / rows); // Largeur max pour une case

        this.cases = new Case[rows][cols];

        // Remplissage de la matrice
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = lignes.get(i).charAt(j);

                // Validation des caractères
                if ("SBRCX".indexOf(c) == -1) {
                    throw new IllegalArgumentException("Caractère invalide détecté : " + c);
                }

                this.cases[i][j] = new Case(String.valueOf(c), new PositionCase(i, j), false);
            }
        }
    }

    public void displayMap() {
        for (Case[] row : cases) {
            for (Case tile : row) {
                System.out.print(tile.getType() + " ");
            }
            System.out.println();
        }
    }

    public int getLevel() {
        if (rows == 5 && cols == 10) {
            return 1; // Level 1
        } else if (rows == 8 && cols == 5) {
            return 2; // Level 2
        } else if (rows == 10 && cols == 10) {
            return 3; // Level 3
        } else {
            throw new IllegalArgumentException("Dimensions inconnues pour déterminer le niveau : " + rows + "x" + cols);
        }
    }

    public List<Case> getVoisins(Case c) {
        List<Case> voisins = new ArrayList<>();
        int i = c.getPosition().getRow();
        int j = c.getPosition().getCol();

        if (i > 0)
            voisins.add(cases[i - 1][j]);
        if (i < rows - 1)
            voisins.add(cases[i + 1][j]);
        if (j > 0)
            voisins.add(cases[i][j - 1]);
        if (j < cols - 1)
            voisins.add(cases[i][j + 1]);

        return voisins;

    }

    public Case trouverSpawn() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (cases[i][j].getType().equals("S")) {
                    return cases[i][j];
                }
            }
        }
        throw new IllegalArgumentException("Le point de Spawn est introuvable !");
    }

    public Case trouverBase() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (cases[i][j].getType().equals("B")) {
                    return cases[i][j];
                }
            }
        }
        throw new IllegalArgumentException("Le point de Base est introuvable !");
    }

    public List<Case> calculerChemin() {
        List<Case> chemin = new ArrayList<>();
        Set<Case> visites = new HashSet<>();
        Stack<Case> pile = new Stack<>();

        Case spawn = trouverSpawn();
        Case base = trouverBase();

        // Initialisation
        pile.push(spawn);
        visites.add(spawn);

        while (!pile.isEmpty()) {
            Case current = pile.pop();
            chemin.add(current);

            if (current.equals(base)) {
                return chemin;
            }

            boolean voisinAjoute = false;
            for (Case voisin : getVoisins(current)) {
                if (!visites.contains(voisin) && estAccessible(voisin)) {
                    pile.push(voisin);
                    visites.add(voisin);
                    voisinAjoute = true;
                }
            }

            if (!voisinAjoute) {
                chemin.remove(chemin.size() - 1);
            }
        }

        throw new IllegalArgumentException("Aucun chemin n'existe entre le Spawn et la Base !");
    }

    private boolean estAccessible(Case c) {
        return c.getType().equals("R") || c.getType().equals("S") || c.getType().equals("B");
    }

}
