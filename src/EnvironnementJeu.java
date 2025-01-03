import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnvironnementJeu {
    private static final List<Unite> toutesLesUnites = new ArrayList<>();

    public static List<Unite> getAllUnites() {
        return Collections.unmodifiableList(toutesLesUnites);
    }

    public static void ajouterUnite(Unite unite) {
        if (unite != null && !toutesLesUnites.contains(unite)) {
            toutesLesUnites.add(unite);
        }
    }

    public static void retirerUnite(Unite unite) {
        toutesLesUnites.remove(unite);
    }

    public static Ennemis[] getEnnemis() {
        return toutesLesUnites.stream()
            .filter(unite -> unite instanceof Ennemis)
            .toArray(Ennemis[]::new);
    }
    public static void nettoyerUnitesMortes() {
        List<Unite> unitesMorts = new ArrayList<>();
        for (Unite unite : toutesLesUnites) {
            if (unite.getPv() <= 0) {
                System.out.println(unite.getName() + " est mort et est retiré de l'environnement.");
                unitesMorts.add(unite);
            }
        }
        toutesLesUnites.removeAll(unitesMorts);
    }
    
public static void retirerEnnemisArrivesFinChemin(List<Case> chemin) {
    // Parcourir toutes les unités dans l'environnement
    for (Unite unite : toutesLesUnites) {
        if (unite instanceof Ennemis) {
            Ennemis ennemi = (Ennemis) unite;

            // Vérifier si l'ennemi est arrivé à la fin du chemin
            if (estArriveAlaFinDuChemin(ennemi, chemin)) {
                System.out.println(ennemi.getName() + " est arrivé à la fin du chemin et est retiré de l'environnement.");
                retirerUnite(ennemi);  // Retirer l'ennemi de la liste
            }
        }
    }
}

public static boolean estArriveAlaFinDuChemin(Ennemis ennemi, List<Case> chemin) {
    // Comparer la position de l'ennemi avec la dernière case du chemin
    Case derniereCase = chemin.get(chemin.size() - 1);
    return ennemi.getPosition().equals(derniereCase.getPosition());
}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Environnement de Jeu ===\n");
        sb.append("Nombre total d'unités : ").append(toutesLesUnites.size()).append("\n");
        for (Unite unite : toutesLesUnites) {
            sb.append("- ").append(unite.getClass().getSimpleName())
              .append(" | Position : ").append(unite.getPosition())
              .append(" | PV : ").append(unite.getPv()).append("/")
              .append(unite.getMaxPv()).append("\n");
        }
        return sb.toString();
    }
}
