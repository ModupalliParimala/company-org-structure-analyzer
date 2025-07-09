# Company Organization Structure Analyzer

A Java-based console application to analyze a company's organizational structure from a CSV file.  
It detects:

- Managers whose salaries violate company compensation rules
- Employees whose reporting line is too long

---

## Features

- Reads employee data from a CSV file
- Identifies:
    - Managers earning **less than 20%** more than their average direct reports
    - Managers earning **more than 50%** more than their average direct reports
    - Employees with more than **4 levels of management** between them and the CEO
- Outputs clear, human-readable summaries to the console
- Uses **only Java SE** and **JUnit** (no third-party libraries)
- Built with Maven

---

## Assumptions


- The Reporting depth is assumed as 2.
- Depth is measured from the CEO (depth 0) downward.
- An employee is flagged as too deep if there are more than 4 levels of managers above them.

- CSV file format:

  ```csv
  Id,FirstName,LastName,Salary,ManagerId
  123,Joe,Doe,60000,
  124,Martin,Chekov,45000,123
  ...
- I have modified the csv to output all kinds of responses(Underpaid,Overpaid and long reporting chain)

## Technologies Used
Java 17 (or any version you prefer)
Maven
JUnit 5

## How to Run

1. **Clone this repo**

    git clone https://github.com/ModupalliParimala/company-org-structure-analyzer.git
    cd company-org-structure-analyzer

2. **Place the input file**

    The `employees.csv` file should be placed in `src/main/resources` and will be loaded via the classpath.
    Modify `maxAllowedDepth` constant value in `src/main/java/com/bigcompany/organalyzer/OrganizationReportGenerator.java` file

3. **Build the project**

    mvn clean install

4. **Run the application**

    *Command Prompt*: mvn exec:java -Dexec.mainClass=com.bigcompany.organalyzer.OrganizationReportGenerator

    *PowerShell*: mvn exec:java "-Dexec.mainClass=com.bigcompany.organalyzer.OrganizationReportGenerator"

    *IDE*: Just go to OrganizationReportGenerator.java file and run OrganizationReportGenerator.main() method 

5. **Running Tests**

   mvn test
