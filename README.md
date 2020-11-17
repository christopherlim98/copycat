
# Data Structures and Algorithms
# Source code plagiarism
In this project, we create 3 algorithms for analysing source code plagiarism.
- Naive algorthim condenses code logic to the root node, drawing inspiration from merkle root. We compare trees by the root node. (Scalability)
- Snapshot algorithm looks at each tree breadth-wise and depth-wise to analyse code similarity. We use frequency analysis to give inverted weights to each node type for a more nuanced score. Weights are based on fibonacci sequence, which highlights differences (~60%) and reduces noise in the similarity comparison. (Accuracy)
- Progressive algorithm compares two trees, iteratively exploring each tree level. (Efficiency)

Main data structures used are abstract syntax trees, hashmaps, linked lists and arrays.
Dataset taken from IEEE Dataport: https://ieee-dataport.org/open-access/programming-homework-dataset-plagiarism-detection#files.

## Useful Directories:

#### "codes/src/main" directory -> Contains maven project for 3 algorithms.
	
	1. Naive implementation
	2. Snapshot implementation 
	3. Progression/Dynamic implementation 

#### "c-to-json" directory -> contains c code from https://github.com/deiuch/c-to-json 

 	=> To convert C language codes to ASTs(Abstract Syntax Tree) in the form of JSON.

#### "codes/data" directory -> EDA (Exploratory Data Analysis) of our dataset

	=> Segregation of datasets into small, medium and large file sizes.

#### "codes/helper" directory -> To aid in the count of the number of characters in each file

	=> To perform EDA for the datasets.

## Installation for code running.

#### Intellij (/codes directory)

	Import the project and press the play button.

#### MAVEN

```
pip install maven
cd codes
mvn compile
mvn exec:java  -D"exec.mainClass"="copycat.Application"
```

## Usage for "c-to-json"

#### For linux/ubuntu
```
cd c2json
sh parse.sh
```
Specify the c file you wish to convert and the directory it is found at.

#### For windows
```
Download WSL
```

## Future extensions
To allow for comparisons of entire repos, not just single-file source code. </br>
To allow for analysis of larger source code, which would require progressive algorithm to build compare the trees iteratively instead of having the requisite of having the entire tree built in-memory. </br>
To allow for multiple comparisons usin indexing, which would build on the naive merkle-root algorithm. </br>
To highlight plagiarised portions through a UI.

## Limitations
Plagiarism is subjective. There is still need for manual review of each code on a case by case basis.

## Video
https://www.youtube.com/watch?v=t68WSdoI_Do&feature=youtu.be

## Contributing
Job Seow, John Luke, Kenneth Kho, Wen Yu, Christopher Lim

## License
[MIT](https://choosealicense.com/licenses/mit/)
