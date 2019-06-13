package com.controllers;

public class EstimationCoeffisientLRFController {
    double[][] eigenVectors;
    double verticalityCoefficient;
    double[]  coeffisientLRF;
    int r;
    int L;

    public EstimationCoeffisientLRFController(double[][] eigenVectors, int r) {
        this.eigenVectors = eigenVectors;
        this.r = r;
        this.L = eigenVectors.length;
        coeffisientLRF = new double[L-1];
    }

    public double[][] getEigenvectors() {

        return eigenVectors;
    }

    public void setEigenvectors(double[][] eigenVectors) {
        this.eigenVectors = eigenVectors;
    }

    public double getVerticalityCoefficient() {
        for (int j = 0; j < r ; j++) {
            verticalityCoefficient += Math.pow(eigenVectors[L-1][j], 2);
        }
        return verticalityCoefficient;
    }

    public void setVerticalityCoefficient(double verticalityCoefficient) {
        this.verticalityCoefficient = verticalityCoefficient;
    }

    public double[] getCoeffisientLRF() {
        double vc = this.getVerticalityCoefficient();


        for (int i = 0; i < L - 1 ; i++) {
            for (int j = 0; j < r ; j++) {
                coeffisientLRF[i] += (1.0/(1.0 - vc)) * (eigenVectors[L-1][j] * eigenVectors[i][j]);
            }
        }


        return coeffisientLRF;
    }

    public void setCoeffisientLRF(double[] coeffisientLRF) {
        this.coeffisientLRF = coeffisientLRF;
    }
}
