public class Case {
  private String type;
  private Position position;
  private boolean occupe;

  public Case(String type, Position position, boolean occupe) {
    this.type = type;
    this.position = position;
    this.occupe = occupe;
  }

  public String getType() {
    return type;
  }

  public Position getPosition() {
    return position;
  }

  public boolean isOccupe() {
    return occupe;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public void setOccupe(boolean occupe) {
    this.occupe = occupe;
  }

  
}