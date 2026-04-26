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

        List<Integer> inputData2 = new ArrayList<>();
        inputData2.add(9);
        inputData2.add(4);
        inputData2.add(25);
        inputData2.add(49);

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
        JavaRDD<Integer> myRdd2 = sparkContext.parallelize(inputData2);


        // Ensuite on peut faire des operations sur les RDD

        // Reduce
        Double result = myRdd.reduce((value1, value2 ) -> value1 + value2);
        System.out.println("The result is: " + result);

        // Mapping
        JavaRDD<Double> sqrtRDD = myRdd2.map(inp -> Math.sqrt(inp));

        // Dans un objet de type JavaRDD, on a une methode foreach qui prend en entree une fonction et cette fonction sera applique a chaque element.
        sqrtRDD.foreach( value -> System.out.println("La racine carre est: " + value) );

        // Combien d'element dans sqrtRDD
        // System.out.println(sqrtRDD.count());

        // Combien d'elements (en utilisant un map et un reduce)
        JavaRDD<Integer> sqrtRDDMaped = myRdd2.map( value -> value = 1);
        Integer count = sqrtRDDMaped.reduce((value1, value2) -> value1 + value2);
        System.out.println("Count: " + count);

        sparkContext.close();

    }
}