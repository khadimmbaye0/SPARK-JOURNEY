package org.example;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Double> inputData = new ArrayList<>();
        inputData.add(35.5);
        inputData.add(12.49943);
        inputData.add(90.32);
        inputData.add(20.32);

        // Pour limiter les logs
        Logger.getLogger("org.apache").setLevel(Level.WARN);

        // Configurer spark
        SparkConf conf  = new SparkConf()
                .setAppName("startingSpark")
                .setMaster("local[*]");

        // Contexte spark
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        // Chargement des donnees (Transformation en RDD)
        JavaRDD<Double> myRdd = sparkContext.parallelize(inputData);

        // Ensuite on peut faire des operations sur les RDD

        // Reduce
        Double result = myRdd.reduce((value1, value2 ) -> value1 + value2);
        System.out.println("The result is: " + result);

        sparkContext.close();

    }
}