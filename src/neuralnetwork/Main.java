/*
 * Copyright 2018 mrMaderoPadero
 */

package neuralnetwork;

/**
 * @author Gregory
 */

public class Main 
{
    
    public static void main(String[] args)
    {
        int[] layers = {1,3,1};
       // NeuralNetwork testNeural = new NeuralNetwork(layers);
       NeuralNetwork testNeural =NeuralNetwork.readObjectFromFile("save_1545964623998");
      int iterations = 100000;
    
//      for(int i =0; i<iterations; i++)
//      {
//          double[] input = {Math.floor (Math.random()*7)};
//          int tar = (input[0]>=5)?1:0;
//          double[] target = {tar};
//          testNeural.teachMe(input, target);
//      
//      System.out.println(i);
//      
//      }
          double[] test = {1};
      testNeural.checkMe(test);
//       testNeural.saveObjectToFile();
   
     
    }
    
    

}
