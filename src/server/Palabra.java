package server;

import java.io.*;
import java.util.Random;

class Palabra {
    String getWord() {
        try{
            LineNumberReader  lnr = new LineNumberReader(new FileReader(new File("words.txt")));
            lnr.skip(Long.MAX_VALUE);
            int noOfWords = lnr.getLineNumber();
           
            FileInputStream fstream = new FileInputStream("words.txt");
        
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(noOfWords);
            
             
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));     
            int i;
            for (i=0 ; i<randomInt ; i++) 
                br.readLine();
            String strLine = br.readLine(); 
            
            in.close();
        
            return strLine;
       }catch (Exception e){
            return "ERROR";
       }
    }
}