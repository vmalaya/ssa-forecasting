package com.models;

public class TrajectoryMatrix {

    private Integer L;
    private Integer K;
    private Double[] data;
    private Double[][] trajectoryMatrix;

    public TrajectoryMatrix(Double[] data, Integer L) {
        this.data = data;
        this.L = L;
    }

    public Integer getK() {
        K = data.length - L + 1;
        return K;
    }


    public Double[] getData() {
        return data;
    }

    private Double[][] getTrajectoryMatrix() {
        Integer K = this.getK();
        trajectoryMatrix = new Double[K][L];
        for (int i = 0; i < K ; i++) {
            for (int j = 0; j < L; j++) {
                trajectoryMatrix[i][j] = data[i+j] ;
            }
        }
        return trajectoryMatrix;
    }
}
