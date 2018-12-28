/*
 * Copyright 2018 mrMaderoPadero
 */

package neuralnetwork;

import java.util.LinkedList;
import java.io.Serializable;
/**
 * @author Gregory
 */

public class Node implements Serializable{
    
    int count = 0;
    LinkedList<Axon> inbound;
    LinkedList<Axon> outbound;
    double inputSum;
    double outputSum;
    int type;
    double error;
    double backpropTemp;
    
    
    public Node(int n)
    {
        this.inbound = new LinkedList();
        this.outbound = new LinkedList();
        this.inputSum=0;
        this.outputSum = 0;
        this.type = n;
        this.error = 0;
        this.backpropTemp = 0;
    }
    
    public void print()
    {
    
    }
    public void outputSum()
    {
        
        if (this.type==0)
        {
           this.outputSum = this.inputSum;

        } else {
            this.outputSum = Helpers.Sigmoid(this.inputSum);
    
        }
                
 
    }
    public void forwardPass()
    {
        this.outputSum();
        
          if(this.type!=2){
              
                 for(Axon a :  this.outbound)
                     {
                    double temp = outputSum*a.weight;
                    a.end.inputSum+=temp;
        
                 }

            }
       
    
    
    }
}
