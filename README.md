# Mastermind

The project has two games, the Mastermind and the Recherche +/-.

## Getting Started

You can download the `mastermind.jar` from the directory  `mastermind/target/mastermind.jar` and execute it in the console with command `java -jar mastermind.jar`.

### Prerequisites

You can need install **Apache Maven 3.5.3** and **JRE 1.8.0_172**.

* [Maven](https://maven.apache.org/install.html) - Installing Apache Maven
* [JAVA](https://java.com/en/download/help/download_options.xml) - Installing Java

### Deployment

You can clone the project and execute files `assembly.bat` on Windows or `assembly.sh` on Linux for packaging the project in tha JAR file.

Or you can use the command in the console

```
mvn compile assembly:single
```
### Configuration

File config.properties in the directory  mastermind/src/main/resources/config.properties

`secretBlockLength=4` - Length of the secret block. Parameter can't be less then 1.

`tryNumber=8` - Number of try before defeat. Parameter can't be less then 1.

`nmbrUtilisable=6` - Number of figures utilisable. Parameter can't be less than 4 and more then 10. For 4 it has [0 - 3] figures and for 10 it has [0 - 9] figures.

`modeDeveloper=0` - Choice of the developer mode `0 = false` and `1 = true`.

## Executing

You can use the command `java -jar target\mastermind.jar` in the root directory of the project or `start.bat` on Windows or `start.sh` on Linux.

For developer mode use argument `java -jar target\mastermind.jar -dev`

_For right encoding on Windows, before executing in the console, use the command:_ `chcp 1252`

## Authors

* **Artem GORBOUNOV** - *Projet #3. Mettez votre logique à l'épreuve* - [Développeur d'application - Java](https://openclassrooms.com/fr/paths/88-developpeur-dapplication-java)

## Acknowledgments

[Five-guess algorithm](http://www.cs.uni.edu/~wallingf/teaching/cs3530/resources/knuth-mastermind.pdf) - In 1977, Donald Knuth demonstrated that the codebreaker can solve the pattern in five moves or fewer, using an algorithm that progressively reduced the number of possible patterns.
