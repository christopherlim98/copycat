
# CS201_G2T8
Source code plagiarism

## Useful Directories:

#### "codes/src/main" directory -> Contains maven project for 3 algorithms.
	
	1. Naive implementation
	2. Snapshot implementation (Accuracy)
	3. Progression/Dynamic implementation (Efficiency)

#### "c-to-json" directory -> contains c code from https://github.com/deiuch/c-to-json 

 	=> To convert C language codes to ASTs(Abstract Syntax Tree) in the form of JSON.

#### "codes/data" directory -> EDA (Exploratory Data Analysis) of our dataset

	=> Segregation of datasets into small, medium and large file sizes.

#### "codes/helper" directory -> In aid to count the number of characters in each file

	=> To perform EDA for the datasets.

## Installation for code running.

#### Intellij (/codes directory)

	Import the project and press the play button.

#### MAVEN

```
pip install maven
cd codes
mvn compile
mvn exec:java  -D"exec.mainClass"="jsonparsing.JsonTestMain"
```

## Usage for "c-to-json"
for linux/ubuntu
```
cd c2json
sh parse.sh
```
Specify the c file you want to convert and its directory.

for windows
download WSL 
## Contributing


## License
[MIT](https://choosealicense.com/licenses/mit/)
