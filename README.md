# Introduction a Spark
### On peut facilement transformer les donnees d'entrees en une structure RDD

```java
import org.apache.spark.SparkConf;
// 1)
SparkConf conf  = new SparkConf()
        .setAppName("startingSpark")
        .setMaster("local[*]");

// 2)
JavaSparkContext sparkContext = new JavaSparkContext(conf);

// Apres ca on peut commencer a faire des operations avec Spark

// 3)
sparkContext.parallelize(inputData);
```
    1)
    Ceci est un objet de configuration Spark
    C’est simplement un conteneur de paramètres qui va dire à Spark :
    
    - comment s’appelle ton application
    - où elle doit s’exécuter (local, cluster…)
    - combien de ressources utiliser (CPU, mémoire…)

    setMaster("local[*]")
    → indique où exécuter le job
    
    local → sur ta machine
    [*] → utilise tous les cœurs CPU
    Sans * ca sera en singleThread
    ...

    2) Ceci represente un connexion a notre cluster spark, Ca nous permet de communiquer avec spark
    
    3) Chargement des donnees on transforme en RDD ceci retourne notre RDD

#### Spark est implementer en Scala beaucoup d'objets sont ecrits en scala. Le createur de spark a fourni une variete de classes pour combler la fausse entre Java et scala. ex JavaRDD

#### javaRDD: C'est une representation Java d'un RDD ca nous permet de communiquer avec le RDD en utilisant java mais en dessous du capot, ce JavaRDD communique avec du scala RDD