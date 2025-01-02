import java.awt.Color;

public enum Element {
    NEUTRE(Color.BLACK),
    FEU(new Color(184, 22, 1)),
    TERRE(new Color(0, 167, 15)),
    AIR(new Color(242, 211, 0)),
    EAU(new Color(6, 0, 160));

    private final Color color;

    Element(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return this.color;
    }
}
