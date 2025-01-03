public class Case {
  private String type;
  private PositionCase position;
  private boolean occupe;

  public Case(String type, PositionCase position, boolean occupe) {
    this.type = type;
    this.position = position;
    this.occupe = occupe;
  }

  public String getType() {
    return type;
  }

  public PositionCase getPosition() {
    return position;
  }

  public boolean isOccupe() {
    return occupe;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setPosition(PositionCase position) {
    this.position = position;
  }

  public void setOccupe(boolean occupe) {
    this.occupe = occupe;
  }

  @Override
  public String toString() {
      return "Case{" +
            "type='" + type + '\'' +
            ", position=" + position +
            ", occupe=" + occupe +
            '}';
  }

}