# BNPTicketing

## Récupération des sources - compilation - utilisation

````shell
git clone https://github.com/vian42/BNPTicketing.git
ls
cd BNPTicketing/
mvn install
java -jar target/BNPTicketing-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/resources/CandidateInputExample.json result.txt
cat result.txt
````
## Description du kata

[TestDetails.md](./src/main/resources/TestDetails.md)