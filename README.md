# Introduction a Spark
### On peut facilement transformer les donnees d'entrees en une structure RDD

```java
import org.apache.spark.SparkConf;
SparkConf conf = new SparkConf()
```

    Ceci est un objet de configuration Spark
    C’est simplement un conteneur de paramètres qui va dire à Spark :
    
    - comment s’appelle ton application
    - où elle doit s’exécuter (local, cluster…)
    - combien de ressources utiliser (CPU, mémoire…)
    ...