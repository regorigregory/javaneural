/*
 * Copyright 2018 mrMaderoPadero
 */

package neuralnetwork;
import java.io.Serializable;
/**
 * @author Gregory
 */

public class Axon implements Serializable{

    Node start;
    Node end;
    double weight;
    double newWeight;
    double derivative;
    int count = 0;
    
    public Axon(Node in, Node out)
    {
        this.start = in;
        this.end = out;
        this.weight = Math.random();
    
    }
    public Axon()
    {
            this.weight = Math.random();

    }
    public static void print()
    {
    }
}
