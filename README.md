Flow Log Data Analysis

Parse a file containing flow log data and maps each row to a tag based on a lookup table. 

Installation

Prerequisites
Software or library name (version)

1. Apache Commons CSV.
2. Apache Commons IO.
3. Apache Commons Codec.

Steps
Clone the repository:

Copy code
git clone https://github.com/shejomathew/LogFlowAnalysis.git 

Navigate into the project directory src folder:

Copy code

javac -cp ".:dependency/commons-csv-1.12.0.jar:dependency/commons-io-2.17.0.jar:dependency/commons-codec-1.17.1.jar" LogDataAnalysis.java 

java -cp ".:dependency/commons-csv-1.12.0.jar:dependency/commons-io-2.17.0.jar:dependency/commons-codec-1.17.1.jar" LogDataAnalysis 

Output will be stored in resources/outputCounts.csv

Contact
Shejo Mathew - shejomathew24@gmail.com

Project Link: https://github.com/shejomathew/LogFlowAnalysis
