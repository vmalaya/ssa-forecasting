package com.parsing;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.models.Company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CSVParser {

    private String[] dates;
    private Double[] openPrices;
    private Double[] highPrices;
    private Double[] lowPrices;
    private Double[] closePrices;
    private Company company;
    private String filename;
    private int length;

    private CsvMapper csvMapper = new CsvMapper();

    public CSVParser(String filename) throws IOException {
        this.filename = filename;
    }

    public Company getCompany() throws IOException {
        this.getData();

        return new Company(dates,openPrices,highPrices,lowPrices,closePrices);
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    private int getLength () throws IOException {
        length = 0;
        csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        ObjectReader oReader = csvMapper.reader(String[].class);
        try (Reader reader = new FileReader(filename)) {
            MappingIterator<String[]> mi = oReader.readValues(reader);
            while (mi.hasNext()) {
                mi.next();
                length++;
            }
            length--;
        }
        return length;
    }

    private void getData() throws IOException {
        length = this.getLength();

        dates = new String[length];
        openPrices = new Double[length];
        highPrices = new Double[length];
        lowPrices = new Double[length];
        closePrices = new Double[length];


        csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        ObjectReader oReader = csvMapper.reader(String[].class);

        try (Reader reader = new FileReader(filename)) {
            MappingIterator<String[]> mi = oReader.readValues(reader);
            int counter = 0;
            while (mi.hasNext()) {
                if (counter != 0) {
                    String[] data = mi.next();
                    for (int i = 0; i < data.length; i++) {
                        switch (i) {
                            case 0:
                                dates[counter - 1] = data[i];
                                break;
                            case 1:
                                openPrices[counter - 1] = Double.parseDouble(data[i]);
                                break;
                            case 2:
                                highPrices[counter - 1] = Double.parseDouble(data[i]);
                                break;
                            case 3:
                                lowPrices[counter - 1] = Double.parseDouble(data[i]);
                                break;
                            case 4:
                                closePrices[counter - 1] = Double.parseDouble(data[i]);
                                break;
                        }
                    }
                } else {
                    mi.next();
                }
                counter++;
            }

        }
    }

}
