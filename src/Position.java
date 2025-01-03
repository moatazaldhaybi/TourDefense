public class Position {
    private double x; // Coordonnée X (en pixels)
    private double y; // Coordonnée Y (en pixels)

    // Constructeur
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Setters
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Calcul de la distance entre deux positions
    public double distanceTo(Position other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

    // Conversion d'une PositionCase en pixels
    public static Position fromCase(PositionCase p,double halfLengthCase) {
        double x = 350.0 - 350.0 + halfLengthCase * (2 * p.getCol() + 1);
        double y = 350.0 + 350.0 - halfLengthCase * (2 * p.getRow() + 1);
        return new Position(x, y);
    }

    // Conversion d'une position réelle (pixels) en PositionCase
    public static PositionCase toCase(Position position, double halfLengthCase) {
        double caseSize = 2 * halfLengthCase;
        int row = (int) (position.getY() / caseSize);
        int col = (int) (position.getX() / caseSize);
        return new PositionCase(row, col);
    }
}
