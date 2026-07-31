package org.example;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

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

        sparkContext.setLogLevel("WARN");

        // Chargement des donnees (Transformation en RDD)
        JavaRDD<Double> myRdd = sparkContext.parallelize(inputData);
        JavaRDD<Integer> myRdd2 = sparkContext.parallelize(inputData2);


        // Ensuite, on peut faire des operations sur les RDD

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

        // Tuples
        // ça pourrait etre une solution, mais trop de code, c'est la ou les tuples entrent en jeu.
        JavaRDD<IntegerWithSquare> sansTupleRDD = myRdd2.map(value -> new IntegerWithSquare(value));

        // Implementation avec des tuples les tuples vont jusqu'à 22
        JavaRDD<Tuple2<Integer, Double>> avecTupleRDD = myRdd2.map(value -> new Tuple2<>(value, Math.sqrt(value)));

        // Definition d'un tuple en java
        Tuple2<Integer, Double> myValue = new Tuple2<>(2, 3.0);

        List<String> inputDataLogs = new ArrayList<>();
        inputDataLogs.add("INFO: Monday 12 January 0830");
        inputDataLogs.add("WARN: Tuesday 4 September 0405");
        inputDataLogs.add("ERROR: Wednesday 18 March 2215");
        inputDataLogs.add("FATAL: Thursday 23 April 0310");
        inputDataLogs.add("WARN: Friday 7 February 1745");
        inputDataLogs.add("ERROR: Saturday 15 June 0940");
        inputDataLogs.add("FATAL: Sunday 29 July 2355");
        inputDataLogs.add("WARN: Wednesday 11 October 1205");
        inputDataLogs.add("ERROR: Monday 2 December 0645");
        inputDataLogs.add("FATAL: Friday 20 August 1840");

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        JavaRDD<String> logs = sparkContext.parallelize(inputDataLogs);

        // Separation en deux colonnes : PairRDD <String, String> Difference avec Map en java, ici on peut avoir plusieurs instances de la meme cle
        // De plus avec les PairRDD, on peut avoir des méthodes supplémentaires
        // La methode mapToPair boucle sur chaque valeur du RDD
        // Mais ces PairRDD utilise des Tuples
        JavaPairRDD<String, String> pairRDD = logs.mapToPair(rawValue -> {
            String[] columns = rawValue.split(":");
            String level = columns[0];
            String date = columns[1];

            return new Tuple2<String, String>(level, date);
        });

        // Extra methodes qui n'existent pas avec les RDDs simples
        JavaPairRDD<String, Long> pairRDD2 = logs.mapToPair(rawValue -> {
            String[] columns = rawValue.split(":");
            String level = columns[0];

            return new Tuple2<>(level, 1L);
        });

        JavaPairRDD<String, Long> sumsRDD = pairRDD2.reduceByKey((value1, value2) -> value1 + value2);
        sumsRDD.foreach(tuple -> System.out.println(tuple._1 + " --- " + tuple._2));

        // Using the fluent API

        sparkContext.close();

    }
}