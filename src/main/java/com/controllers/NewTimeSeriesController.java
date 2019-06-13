package com.controllers;

public class NewTimeSeriesController {

    private double[][] orthogonalProjection;
    private double[] newTimeSeries;
    private double[][] trajectoryMatrix;
    private double[][] eigenVectors;
    private int r;
    private int N;
    private int L;
    private int K;

    public NewTimeSeriesController(double[][] trajectoryMatrix, double[][] eigenVectors, int r, int N) {
        this.trajectoryMatrix = trajectoryMatrix;
        this.eigenVectors = eigenVectors;
        this.r = r;
        this.N = N;
        this.L = trajectoryMatrix.length;
        this.K = N - L + 1;
    }

    public void setOrthogonalProjection(double[][] orthogonalProjection) {
        this.orthogonalProjection = orthogonalProjection;
    }

    public double[][] getOrthogonalProjection() {
        double[][] tempV = new double[L][L];
        orthogonalProjection = new double[L][K];

        for (int j = 0; j < r ; j++) {
            for (int i = 0; i < L ; i++) {
                for (int k = 0; k < L ; k++) {
                    tempV[i][k] = eigenVectors[i][j] * eigenVectors[k][j];
                }
            }
            for (int i = 0; i < L  ; i++) {
                for (int k = 0; k < K ; k++) {
                    for (int l = 0; l < L ; l++) {
                        orthogonalProjection[i][k] += tempV[i][l] * trajectoryMatrix[l][k];
                    }

                }

            }

        }

        return orthogonalProjection;
    }


    public double[] getNewTimeSeries() {
        double[][] orthogonalProjection = this.getOrthogonalProjection();
        newTimeSeries = new double[N];
        for (int s = 1; s <= N ; s++) {
            if (s <= L){
                for (int i = 1; i <= s ; i++) {
                    newTimeSeries[s-1] += (1.0 / ( Double.valueOf(s-1) + 1.0)) * orthogonalProjection[i - 1][s - i];
                }
            }else if(s <= K){
                for (int i = 1; i <= L ; i++) {
                    newTimeSeries[s-1] += (1.0/Double.valueOf(L)) * orthogonalProjection[i - 1][s-i];
                }
            }else if(s <= N){
                for (int i = 1; i <= N - s +1 ; i++) {
                    newTimeSeries[s-1] += (1.0/Double.valueOf(N - s + 1)) * orthogonalProjection[i + s - K - 2 ][K - i];
                }
            }
        }

        return newTimeSeries;
    }

    public void setNewTimeSeries(double[] newTimeSeries) {
        this.newTimeSeries = newTimeSeries;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }
}
