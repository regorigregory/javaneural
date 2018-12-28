/*
 * Copyright 2018 mrMaderoPadero
 */

package neuralnetwork;

/**
 * @author Gregory
 */

public class Helpers {

    public static double Sigmoid(double n)
    {

              return  Math.pow((1+Math.pow(Math.E, -n)), -1);
    }
    
    public static double Error(double target, double output)
    {
    
        return 0.5*Math.pow((target-output), 2);
    
    }
    public static double ErrorDerivative(double target, double output)
    {
    
        return -(target-output);
    
    }
    public static double SigmoidDerivative(double n)
    {
        return (1-n)*n;
    
    }
    
}
