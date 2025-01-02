public class Temps {
  private double secondes;

  public Temps(double secondes){
    this.secondes=secondes;
  }

  public double getSecondes(){
    return this.secondes;
  }

  public void setSecondes(double sec){
    this.secondes=sec;
  }

  @Override
    public String toString() {
        return secondes + "s";
    }
}
