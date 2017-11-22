import java.util.*;
//Class for finding means and standard deviations
public class Mean{
  private List<String> values=new ArrayList<>();
  private double mean;
  private double stnd;

  public Mean(List<String> everything, int rangeMin, int numOfValues){
    double sum=0;
    for(int i=rangeMin;i<rangeMin+numOfValues;i++){
      values.add(everything.get(i));
      sum+=Double.parseDouble(everything.get(i));
    }
    mean=sum/numOfValues;
    double sumA=0;
    for(int i=rangeMin;i<rangeMin+numOfValues;i++){
      sumA+=Math.pow(Double.parseDouble(everything.get(i))-mean,2);
    }
    stnd=Math.pow(sumA/(double)numOfValues,0.5);
  }

  //Getters
  public double getMean(){
    return mean;
  }
  public double getDev(){
    return stnd;
  }
  public List<String> getValues(){
    return values;
  }
  //Setters
}
