/*
 * Copyright 2018 mrMaderoPadero

    based on:https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
 */

//TODO
//ERROR function
//Backpropegation
//logging errors and learning rate;
//saving state
//loading state
//stop and think first, before you start implementing anything.
package neuralnetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.io.Serializable;
/**
 *
 * @author Gregory
 */
public class NeuralNetwork implements Serializable
{
    LinkedList<LinkedList<Node>> Layers = new LinkedList<>();
    double learningRate = 0.1;
    double totalError=0;
    double[] target;
    double[] input;
    int NodeCounter = 0;
    int AxonCounter = 0;
    private static final long serialVersionUID = 2L;

    public NeuralNetwork()
    {
    System.out.println("No parameter has been passed, initialisation failure");
    
    }
    
    public NeuralNetwork(int[] layers)
    {
         

        for(int i = 0; i<layers.length; i++)
        {
            LinkedList<Node> temp= new LinkedList<>();
            Layers.add(temp);
            int type =1;
            if(i==0)
            {
                type=0;
            } else if(i==layers.length-1)
            {
                type=2;
            }
            
            //adding nodes    
            for(int k = 0; k<layers[i]; k++)
                {
                 Node newNode = new Node(type);
                 NodeCounter++;
                 newNode.count = this.NodeCounter;
                 temp.add(newNode);
                }
            //what is wrong here????
             if(i>0) {
            LinkedList<Node> previousLayer = Layers.get(i-1);
            //n current layer
            //m previous layer
                for(Node n : temp)
                {
                    for(Node m : previousLayer)
                    {
                        Axon newAxon = new Axon();
                        AxonCounter++;

                        newAxon.count = AxonCounter;
                        //add to current layer's neuron's inbound
                        //add to previous layer's neuron's outbound
                        //add each one of them to the Axon's start and end point;
                        n.inbound.add(newAxon);
                        m.outbound.add(newAxon);
                        newAxon.start = m;
                        newAxon.end =n;


                    }


                }
            
            }
            
            
            
            
            
           
        }
    
    
    }
    
    
    public void newInput()
    {
        LinkedList<Node> temp = this.Layers.get(0);
            for(int i =0; i<input.length; i++)
            {   
                temp.get(i).inputSum = input[i];
                temp.get(i).outputSum(); 
            }
    
    }
    
    public void forwardPass()
    {
        for(int i =0; i<this.Layers.size(); i++)
        {
            LinkedList<Node> temp = this.Layers.get(i);
            
            for(Node n : temp)
            {
                n.forwardPass();
            
            }
        
        
        }
    
    
    }
    
    public void error()
    {
        LinkedList<Node> lastLayer = this.Layers.get(this.Layers.size()-1);
        
        for(int i=0; i<lastLayer.size(); i++)
        {
            Node temp = lastLayer.get(i);
            temp.error = Helpers.Error(this.target[i], temp.outputSum);
            this.totalError+=temp.error;
            
           
        }
    /**
     * @param args the command line arguments
     */
    }
    public void backPropegationCalculation()
    {
        for(int i = this.Layers.size()-1; i>0; i--){
           LinkedList<Node> currentLayer = this.Layers.get(i);
          //if you are working on the output layers
           //you will have to insert an if clause here!!!
          if(i==this.Layers.size()-1)
          {
           
                for(int k = 0; k<currentLayer.size(); k++)
                {
                    Node tempNode = currentLayer.get(k);
                    for(Axon a : tempNode.inbound)
                    {
                        Node startNode = a.start;
                        double y = a.start.outputSum;

                        a.newWeight = -(target[k]-tempNode.outputSum)
                                *Helpers.SigmoidDerivative(tempNode.outputSum)
                                *startNode.outputSum;
                        startNode.backpropTemp+=-(target[k]-tempNode.outputSum)
                                *Helpers.SigmoidDerivative(tempNode.outputSum)
                                *a.weight;

                    }

                }
           }  else{
              //here, magic will happen shortly
                    for(int k = 0; k<currentLayer.size(); k++)
                {
                    Node tempNode = currentLayer.get(i);
                    for(Axon a : tempNode.inbound)
                    {
                        Node startNode = a.start;
                        double y = a.start.outputSum;

                        a.newWeight = tempNode.backpropTemp
                                *Helpers.SigmoidDerivative(startNode.outputSum)
                                *startNode.outputSum;
                                

                        startNode.backpropTemp+=tempNode.backpropTemp
                                *Helpers.SigmoidDerivative(tempNode.outputSum)
                                *a.weight;

                    }

                }
 
          } 
   
        }
    
    }
    public  void backPropegationUpdate()
    {
        for(LinkedList<Node> i : this.Layers)
        {
            for(Node k : i)
            {
                for(Axon a : k.outbound)
                {
                    a.weight +=a.newWeight*this.learningRate;
                }
            
            }
        }
    }
    public void teachMe(double[] input, double[] target)
    {
        this.input = input;
        this.target = target;
        this.newInput();
        this.forwardPass();
        this.error();
        this.backPropegationCalculation();
        this.backPropegationUpdate();

  
    }
    public void checkMe(double[] input)
    {
    
     this.input = input;
     this.target = target;
     this.newInput();
     this.forwardPass();
     int ocunter = 0;
     for(Node n : this.Layers.get(this.Layers.size()-1))
     {
         String outText = "Input was "+input[ocunter]+" ,Node " + n.count+" says " +n.outputSum;
         System.out.println(outText);
         ocunter++;
     }
    }
    public void saveObjectToFile()
    {
        String s = System.getProperty("file.separator");
        String dirPath = "."+s+"Save";
        String filePath = dirPath+s+"save_"+System.currentTimeMillis();
        File newDir = new File(dirPath);
        File newFile = new File(filePath);
        try
        {
            newDir.mkdirs();
            newFile.createNewFile();
            
            FileOutputStream fileOut = new FileOutputStream(newFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            
            
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    
    }
    
     public static NeuralNetwork readObjectFromFile(String filename)
    {
        String s = System.getProperty("file.separator");
        String dirPath = "."+s+"Save";
        String filePath = dirPath+s+filename;
        File newFile = new File(filePath);
        try
        {
            
            
            FileInputStream fileIn = new FileInputStream(newFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return (NeuralNetwork) objectIn.readObject();
            
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return new NeuralNetwork();
    }

}
//////