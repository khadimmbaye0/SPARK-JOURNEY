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

### Operation reduce sur RDD
    reduce est une action sur un RDD qui sert à :
    combiner tous les éléments du RDD pour obtenir une seule valeur
    exemple: function = value1 + value2

### Mapping and Outputting
    L'operation map nous permet de transformer la structure du RDD d'une forme a une autre
    exemple: function = sqrt(value). Cela entraine la creation d'un nouveau RDD.
    Un RDD est immuable, Lorsque c'est creer, ca ne peut plus etre modifier, c'est pour ca que les transformations creer de nouveaux RDDs

### Outputting to the console
    On doit penser a comment montrer la sortie. En se posant des questions, notamment sur notre architecture, si on a un cluster (ou on aura pas de terminal) ou autre.
    La maniere de faire la plus courrante est d'ecrire la sortie sur un fichier. Si c'est un grand Dataset qui peut meme pas se tenir sur un fichier java en memoire, on songera a ecrire sur disque, en utilisant HDFS.
    Dans un objet de type JavaRDD, on a une methode foreach qui prend en entree une fonction et cette fonction sera applique a chaque element.
    La fonction ne retournera jamais rien (void)