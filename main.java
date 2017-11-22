import java.util.*;
import java.io.*;
/*
  Program written by Luke Clement on 16/11/2017
  For use with data procssing of Radio Mixer experiment
  Feel free to use, just be sure to cite me! :D
*/
public class main{
  public static void main(String[] args){
    /*
      Experiment 1
      Performed on lab day 1, data analysis done on day 2.
    */
    //Getting name of file and recording incoming data
    String fileName="data1.csv";
    String fNO="pure1.txt";
    String fNO2="pure2.txt";
    String line=null;
    //Values that are needed
    List<String> iF=new ArrayList<>();
    List<String> input=new ArrayList<>();
    List<String> eff=new ArrayList<>();
    //Reading file
    try{
      FileReader fileReader = new FileReader(fileName);
      BufferedReader br=new BufferedReader(fileReader);
      while((line=br.readLine())!=null){
        String[] values=line.split(",");
        input.add(values[1]);
        iF.add(values[2]);
        eff.add(values[5]);
      }
      br.close();
    }catch(Exception ex){System.out.println("File not read");}
    //Getting values to plot
    List<String> IFValues=new ArrayList<>();
    List<String> inputValues=new ArrayList<>();
    List<String> effValues=new ArrayList<>();
    List<String> stdI=new ArrayList<>();
    List<String> stdE=new ArrayList<>();

    for(int i=0;i<21;i++){
      //Finding mean and stuff
      Mean meanIF=new Mean(iF,i*10,10);
      Mean meanI=new Mean(input,i*10,10);
      Mean meanE=new Mean(eff,i*10,10);
      IFValues.add(Double.toString(Math.round(meanIF.getMean()*1000)/1000.0));
      inputValues.add(Double.toString(Math.round(meanI.getMean()*1000)/1000.0));
      effValues.add(Double.toString(Math.round(meanE.getMean()*1000)/1000.0));
      stdI.add(Double.toString(Math.round(meanIF.getDev()*1000)/1000.0));
      stdE.add(Double.toString(Math.round(meanE.getDev()*1000)/1000.0));
    }


    //Writing file 1(pure, experiment 1)
    try{
      FileWriter write = new FileWriter(fNO, false);
      PrintWriter print_line = new PrintWriter(write);
      for(int i=0;i<stdI.size();i++){
        print_line.printf( "%s" + "%n" , inputValues.get(i)+" "+IFValues.get(i)+" "+stdI.get(i)+" ");
      }
      print_line.close();
    }catch(Exception ex){}
    //Writing file 2(pure, experiment 2)
    try{
      FileWriter write = new FileWriter(fNO2, false);
      PrintWriter print_line = new PrintWriter(write);
      for(int i=0;i<stdI.size();i++){
        print_line.printf( "%s" + "%n" , inputValues.get(i)+" "+effValues.get(i)+" "+stdE.get(i)+" ");
      }
      print_line.close();
    }catch(Exception ex){}


    //Finding anomolies and printing
    List<Integer> anomolies=new ArrayList<>();
    anomolies.add(0);
    int p=0;
    //while(anomolies.size()>0){
      anomolies.clear();
      for(int i=0;i<21;i++){
        for(int j=0;j<10;j++){
          double reading=Double.parseDouble(iF.get(i*10+j));
          double mean=Double.parseDouble(IFValues.get(i));
          double sigma=Double.parseDouble(stdI.get(i));
          if((mean-reading)/sigma>2||(mean-reading)/sigma<-2){
            anomolies.add(i*10+j);
            System.out.println(i*10+j);
          }
        }
      }

      for(int a:anomolies){
        double q=Math.floor(a/10);
        double target=a-q*10;
        double sumA=0;
        double sumB=0;
        double sumC=0;
        for(double j=0;j<10;j++){
          if(j!=target){
            sumA+=Double.parseDouble(iF.get((int)(j+q*10)));
            sumB+=Double.parseDouble(input.get((int)(j+q*10)));
            sumC+=Double.parseDouble(eff.get((int)(j+q*10)));
          }
        }
        //A is IF, B is input
        double meanA=sumA/(9);
        double meanC=sumC/(9);
        double x=0;
        double y=0;
        for(double j=0;j<10;j++){
          if(j!=target){
            x+=Math.pow(Double.parseDouble(iF.get((int)(j+q*10)))-meanA,2);
            y+=Math.pow(Double.parseDouble(eff.get((int)(j+q*10)))-meanC,2);
          }
        }
        double meanB=sumB/(9);
        double stdIA=Math.pow(x/9.0,0.5);
        double stdEA=Math.pow(y/9.0,0.5);
        stdI.set((int)q,Double.toString(Math.round(stdIA*1000)/1000.0));
        stdE.set((int)q,Double.toString(Math.round(stdEA*1000)/1000.0));
        IFValues.set((int)q,Double.toString(Math.round(meanA*1000)/1000.0));
        inputValues.set((int)q,Double.toString(Math.round(meanB*1000)/1000.0));
        effValues.set((int)q,Double.toString(Math.round(meanC*1000)/1000.0));


        //Writing files (impure)
        //Removing first 4 anomolies
        if(p<4){

          String fNOO="E1aAttept"+Integer.toString(p)+".txt";
          try{
            FileWriter write = new FileWriter(fNOO, false);
            PrintWriter print_line = new PrintWriter(write);
            for(int i=0;i<stdI.size();i++){
              double real=Double.parseDouble(stdI.get(i));
              if(real<0.001){
                real=0.001;
              }
              print_line.printf( "%s" + "%n" , inputValues.get(i)+" "+IFValues.get(i)+" "+real+" ");
            }
            print_line.close();
          }catch(Exception ex){System.out.println("OHNO");}
          String fNOOO="E1aAtteptA"+Integer.toString(p)+".txt";
          try{
            File file=new File(fNOOO);
            PrintWriter writer = new PrintWriter(new FileOutputStream(file, false));
            for(int i=0;i<3;i++){

              for(int j=0;j<stdI.size();j++){
                double real=Double.parseDouble(stdI.get(j));
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


          String fNOO2="E1bAttept"+Integer.toString(p)+".txt";
          try{
            FileWriter write = new FileWriter(fNOO2, false);
            PrintWriter print_line = new PrintWriter(write);
            for(int i=0;i<stdI.size();i++){
              double real=Double.parseDouble(stdE.get(i));
              if(real<0.001){
                real=0.001;
              }
              print_line.printf( "%s" + "%n" , inputValues.get(i)+" "+effValues.get(i)+" "+real+" ");
            }
            print_line.close();
          }catch(Exception ex){System.out.println("OHNO");}
          String fNOOO2="E1bAtteptA"+Integer.toString(p)+".txt";
          try{
            File file=new File(fNOOO2);
            PrintWriter writer = new PrintWriter(new FileOutputStream(file, false));
            for(int i=0;i<3;i++){

              for(int j=0;j<stdE.size();j++){
                double real=Double.parseDouble(stdE.get(j));
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
                  writer.write(effValues.get(j)+",");
                  break;
                }
              }
              writer.println(" ");
            }
            writer.close();
          }catch(Exception ex){System.out.println("OHNO");}

        }

        p++;
      }
      /*
      */

  }
}
