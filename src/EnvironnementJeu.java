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
    
}
