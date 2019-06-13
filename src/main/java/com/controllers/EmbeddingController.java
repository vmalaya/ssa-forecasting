package com.controllers;

import java.util.Arrays;

public class EmbeddingController {
    private Integer L;
    private Integer K;
    private Double[] data;
    private double[][] trajectoryMatrix;

    public EmbeddingController(Double[] data,Integer l) {
        this.L = l;
        this.data = data;
        this.K = data.length - L + 1;
    }

    public Integer getL() {
        return L;
    }

    public void setL(Integer l) {
        L = l;
    }

    public Integer getK() {
        return K;
    }

    public void setK(Integer k) {
        K = k;
    }

    public Double[] getData() {
        return data;
    }

    public void setData(Double[] data) {
        this.data = data;
    }

//    public double[][] getTrajectoryMatrix() {
//
//        trajectoryMatrix = new double[K][L];
//        for (int i = 0; i < K ; i++) {
//            for (int j = 0; j < L; j++) {
//                trajectoryMatrix[i][j] = data[i+j] ;
//            }
//        }
//        return trajectoryMatrix;
//    }
        public double[][] getTrajectoryMatrix() {

        trajectoryMatrix = new double[L][K];
        for (int j = 0; j < K ; j++) {
            for (int i = 0; i < L; i++) {
                trajectoryMatrix[i][j] = data[i+j] ;
            }
        }
        return trajectoryMatrix;
    }

    public void setTrajectoryMatrix(double[][] trajectoryMatrix) {
        this.trajectoryMatrix = trajectoryMatrix;
    }

    @Override
    public String toString() {
        return "EmbeddingController{" +
                "L=" + L +
                ", K=" + K +
                ", data=" + Arrays.toString(data) +
                ", trajectoryMatrix=" + Arrays.toString(trajectoryMatrix) +
                '}';
    }
}
