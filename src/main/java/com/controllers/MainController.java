package com.controllers;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import com.models.Company;
import com.parsing.CSVParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Callback<Class<?>, Object> callback;
    Double[] closePrices;
    double[][] U;
    Company company;
    double[] g;

    @FXML
    private ImageView btnImport;

    @FXML
    private ImageView btnSettings;

    @FXML
    private ImageView btnForecast;

    @FXML
    private ImageView btnExit;

    @FXML
    private AnchorPane importData;

    @FXML
    private AnchorPane settingsMethods;

    @FXML
    private AnchorPane results;

    @FXML
    private TextField fileName;

    @FXML
    private TextField windowLength;

    @FXML
    private TextField R;


    @FXML
    private TextField M;


    @FXML
    private Label initialLabel;


    @FXML
    private void handleButtonAction(MouseEvent event){
        if(event.getTarget() == btnImport){
            importData.setVisible(true);
            results.setVisible(false);
            settingsMethods.setVisible(false);
            initialLabel.setVisible(false);

        }else if(event.getTarget() == btnSettings){
            settingsMethods.setVisible(true);
            importData.setVisible(false);
            results.setVisible(false);

        }else if(event.getTarget() == btnForecast ){
            results.setVisible(true);
            settingsMethods.setVisible(false);
            importData.setVisible(false);
        }else if(event.getTarget() == btnExit){
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }

    }

    @FXML
    private void handleImportButtonClick(ActionEvent event) throws IOException {
       String data =  fileName.getText();
        CSVParser csvParser = new CSVParser(data);
        company = csvParser.getCompany();
        closePrices = company.getClosePrices();
        settingsMethods.setVisible(true);

    }

    @FXML
    private void handleForecastButtonClick(ActionEvent actionEvent){
        int r = Integer.parseInt(R.getText());
        int l = Integer.parseInt(windowLength.getText());
        int m = Integer.parseInt(M.getText());

        EmbeddingController ec = new EmbeddingController(closePrices, l);

        double[][] matrix = ec.getTrajectoryMatrix();

        SingularValueDecomposition svd = new SingularValueDecomposition(new Matrix(matrix));
        U = svd.getU().getArray();

        NewTimeSeriesController timeSeriesController = new NewTimeSeriesController(matrix, U, r, closePrices.length);
        double[] newTimeSeries = timeSeriesController.getNewTimeSeries();
        EstimationCoeffisientLRFController estimationCoeffisientLRFController = new EstimationCoeffisientLRFController(U,r);
        double[] acoeffisientLRF = estimationCoeffisientLRFController.getCoeffisientLRF();
        double[] coeffisientLRF = new double[acoeffisientLRF.length];
        for (int i = 0; i < coeffisientLRF.length ; i++) {
            coeffisientLRF[i] = acoeffisientLRF[coeffisientLRF.length - i - 1];
        }

        double mape = 0.0;
        g = new double[closePrices.length + m];
        for (int i = 0; i < closePrices.length ; i++) {
            g[i] = newTimeSeries[i];
            if(i > closePrices.length - m - 1)
                mape += ((Math.abs(closePrices[i] - newTimeSeries[i])/closePrices[i])/Double.valueOf(m));
        }

        for (int i = closePrices.length; i < g.length; i++) {
            for (int j = 0; j < l - 1; j++) {
                g[i] += coeffisientLRF[j] * g[i - j - 1];
            }
        }



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("MAPE:");
        alert.setContentText(String.valueOf(mape));


        alert.showAndWait();

    }

    @FXML
    private void getForecastResults(MouseEvent event){
        String[] dates = company.getDates();

        Stage stage = new Stage();
        Group root = new Group();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Date");
        yAxis.setLabel("Value");

        final LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);

        lineChart.getXAxis().setAutoRanging(true);
        lineChart.getYAxis().setAutoRanging(true);

        lineChart.setPrefWidth(800);
        lineChart.setPrefHeight(500);


        lineChart.setTitle("LineChart");
        XYChart.Series realData = new XYChart.Series();
        realData.setName("Real data");

        XYChart.Series forecast = new XYChart.Series();
        forecast.setName("Forecast");

        for (int i = 0; i < closePrices.length ; i++) {
            realData.getData().add(new XYChart.Data(dates[i], closePrices[i]));
        }

        for (int i = 0; i < g.length ; i++) {
            if(i < closePrices.length) {
                forecast.getData().add(new XYChart.Data<String, Double>(dates[i], g[i]));
            }else{
                forecast.getData().add(new XYChart.Data<String, Double>(String.valueOf(i),g[i]));
            }
        }

        lineChart.getData().addAll(realData,forecast);

        root.getChildren().add(lineChart);
        Scene scene = new Scene(root, 820, 500);
        scene.getStylesheets().add("/styles/chart.css");
        stage.setScene(scene);
        stage.show();


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
