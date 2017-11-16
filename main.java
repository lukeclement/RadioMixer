import java.util.*;
import java.io.*;

public class main{
  public static void main(String[] args){
    String fileName="data.csv";
    String fNO="pure(no redactions).txt";
    String line=null;
    List<String> IF=new ArrayList<>();
    List<String> input=new ArrayList<>();
    List<String> std=new ArrayList<>();
    //Reading file
    try{
      FileReader fileReader = new FileReader(fileName);
      BufferedReader br=new BufferedReader(fileReader);
      while((line=br.readLine())!=null){
        String[] values=line.split(",");
        input.add(values[1]);
        IF.add(values[2]);
      }
      br.close();
    }catch(Exception ex){}

    List<String> IFValues=new ArrayList<>();
    List<String> inputValues=new ArrayList<>();

    for(int i=0;i<21;i++){
      double sumA=0;
      double sumB=0;
      for(int j=0;j<10;j++){
        sumA+=Double.parseDouble(IF.get(j+i*10));
        sumB+=Double.parseDouble(input.get(j+i*10));
      }
      //A is IF, B is input
      double meanA=sumA/10;
      double x=0;
      for(int j=0;j<10;j++){
        x+=Math.pow(Double.parseDouble(IF.get(j+i*10))-meanA,2);
      }
      double meanB=sumB/10;
      double stdA=Math.pow(x/9,0.5);
      std.add(Double.toString(stdA));
      IFValues.add(Double.toString(meanA));
      inputValues.add(Double.toString(meanB));
    }


    //Writing file(pure)
    try{
      FileWriter write = new FileWriter(fNO, false);
      PrintWriter print_line = new PrintWriter(write);
      for(int i=0;i<std.size();i++){
        print_line.printf( "%s" + "%n" , inputValues.get(i)+" "+IFValues.get(i)+" "+std.get(i)+" ");
      }
      print_line.close();
    }catch(Exception ex){}


    //Finding anomolies and printing (largest sigmas first)
    List<Integer> anomolies=new ArrayList<>();
    anomolies.add(0);
    int p=0;
    //while(anomolies.size()>0){
      anomolies.clear();
      for(int i=0;i<21;i++){
        for(int j=0;j<10;j++){
          double reading=Double.parseDouble(IF.get(i*10+j));
          double mean=Double.parseDouble(IFValues.get(i));
          double sigma=Double.parseDouble(std.get(i));
          if((mean-reading)/sigma>2||(mean-reading)/sigma<-2){
            anomolies.add(i*10+j);
            System.out.println(i*10+j);
          }
        }
      }
      for(int a:anomolies){
        int caught=0;
        double q=Math.floor(a/10);
        double target=a-q*10;
        double sumA=0;
        double sumB=0;
        for(double j=0;j<10-(double)caught;j++){
          if(j!=target){
            sumA+=Double.parseDouble(IF.get((int)(j+q*10)));
            sumB+=Double.parseDouble(input.get((int)(j+q*10)));
          }
        }
        //IF.remove((int)(target+q*10));
        //input.remove((int)(target+q*10));
        //System.out.println("Removed "+(target+q*10)+" for attempt "+p);
        //A is IF, B is input
        double meanA=sumA/(9-caught);
        double x=0;
        for(double j=0;j<10-(double)caught;j++){
          if(j!=target){
            x+=Math.pow(Double.parseDouble(IF.get((int)(j+q*10)))-meanA,2);
          }
        }
        double meanB=sumB/(9-caught);
        double stdA=Math.pow(x/9,0.5);
        std.set((int)q,Double.toString(stdA));
        IFValues.set((int)q,Double.toString(meanA));
        inputValues.set((int)q,Double.toString(meanB));

        //Writing file(impure)
        String fNOO="Attept"+Integer.toString(p)+".txt";
        try{
          FileWriter write = new FileWriter(fNOO, false);
          PrintWriter print_line = new PrintWriter(write);
          for(int i=0;i<std.size();i++){
            double real=Double.parseDouble(std.get(i));
            if(real<0.001){
              real=0.001;
            }
            print_line.printf( "%s" + "%n" , inputValues.get(i)+" "+IFValues.get(i)+" "+real+" ");
          }
          print_line.close();
        }catch(Exception ex){System.out.println("OHNO");}
        String fNOOO="AtteptA"+Integer.toString(p)+".txt";
        try{
          //FileWriter writer = new FileWriter(fNOOO, false);
          File file=new File(fNOOO);
          PrintWriter writer = new PrintWriter(new FileOutputStream(file, false));
          for(int i=0;i<3;i++){

            for(int j=0;j<std.size();j++){
              double real=Double.parseDouble(std.get(j));
              if(real<0.001){
                real=0.001;
              }
              switch(i){
                case 2:
                writer.write(real+",");
                break;
                case 0:
                writer.write(inputValues.get(j)+",");
                break;
                case 1:
                writer.write(IFValues.get(j)+",");
                break;
              }
            }
            writer.println(" ");
          }
          writer.close();
        }catch(Exception ex){System.out.println("OHNO");}
        //caught++;
        p++;
      }
    //}

  }
}
