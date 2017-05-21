import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.io.FileReader;
import java.lang.String;
import java.util.Arrays;
import java.util.Collections;
import java.lang.IndexOutOfBoundsException;


public class AlgoritmoDeSubstituicaoDePaginas{

	public static void main(String args[]){
		
		
	try{
			String arquivo = "/home/leonardodantas/Área de Trabalho/Programas/SO/Projeto2/t2.txt";
	        BufferedReader ler = new BufferedReader(new FileReader(arquivo));
	       	ArrayList<String> acessSequence = new ArrayList<String>();
	       	int framesNumber = Integer.parseInt(ler.readLine());	         
	        String line;
	        while ((line = ler.readLine()) != null) {
	        	acessSequence.add(line);
	        	
	        }
	       //  // System.out.println(framesNumber + "\n");
	       // for(String i : acessSequence){
	       // 	System.out.println(i);
	       // }
	        ler.close();
		
		FIFO(acessSequence, framesNumber);		
		OTM(acessSequence,framesNumber);
		LRU(acessSequence,framesNumber);

	  	}catch (IOException ex) {
	    	System.out.println("CATCH1");
	        System.err.print(ex.getMessage());
	    }catch (IndexOutOfBoundsException e) {
	    	System.out.println("CATCH2");
	        System.err.print(e.getMessage());
	    }
	}

public static void FIFO(ArrayList<String> acessSequence, int framesNumber){
        ArrayList<Integer> frames = new ArrayList<>(Collections.nCopies(framesNumber, -1));
        int missingPages = 0;
        int lastIndexAcess = 0;
 
        for(String i : acessSequence){
            int j = Integer.parseInt(i);
   
            if(frames.indexOf(j) == -1){
                frames.set(lastIndexAcess, j);
                lastIndexAcess = (lastIndexAcess+1) % framesNumber;
                missingPages++;
            }
        }
 
        System.out.println("FIFO: "+ missingPages);
    }

public static void OTM(ArrayList<String> acessSequence, int framesNumber){
	ArrayList<Integer> frames = new ArrayList<>(Collections.nCopies(framesNumber,-1));
	int missingPages = 0;
	int index = 0;
	int indexacess = 0;

	for(String i : acessSequence){
            int j = Integer.parseInt(i);
             if(frames.indexOf(j) == -1){
	             if(index < framesNumber){
	             	frames.set(index, j);
	             	index++;
	             	missingPages++;
	             }else{
	             	int tmp = 0;
	             	int gap = 0;
	             	int largegap = 0;

	             	for(int f:frames){

	             		for(int z = indexacess; z < acessSequence.size();z++){
	             			if(Integer.parseInt(acessSequence.get(z)) == f){
	             				break;
	             			}   
	            			gap++;
	             		}

	             		if(gap > largegap){
	             			largegap = gap;
	             			tmp = f;
	             		
	             		}
	             		gap = 0;

	             	}

	             	frames.set(frames.indexOf(tmp),j);
	             	missingPages++;
	            	}	
	       
			}
			 indexacess++;
	  
}
System.out.println("OTM: "+ missingPages);

}

public static void LRU(ArrayList<String> acessSequence, int framesNumber){
        int missingPages = 0;
        int index = 0;
        int count = 0;
        LRUStack stack = new LRUStack();
        ArrayList<Integer> frames = new ArrayList<>(Collections.nCopies(framesNumber, -1));

        for(String f : acessSequence){
            int aux = Integer.parseInt(f);
            stack.push(aux);

            if(frames.indexOf(aux) == -1){
                if(count < framesNumber){
                    frames.set(index, aux);
                    missingPages++;
                    index++;
                    count++;
                }
                else{
                     int lru = stack.takeBase();
                     while(frames.indexOf(lru) == -1){
                         stack.remove(lru);
                         lru = stack.takeBase();
                     }

                     index = frames.indexOf(lru);
                     frames.set(index, aux);
                     missingPages++;
                }
            }
        }

        System.out.println("LRU: "+missingPages);
    }
}
