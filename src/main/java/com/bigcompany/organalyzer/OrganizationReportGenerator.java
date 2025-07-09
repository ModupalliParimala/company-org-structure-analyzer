package com.bigcompany.organalyzer;

import com.bigcompany.organalyzer.model.Employee;
import com.bigcompany.organalyzer.parser.CsvFileParser;
import com.bigcompany.organalyzer.service.OrganizationAnalyzer;

import java.io.*;
import java.util.List;

public class OrganizationReportGenerator {
    private static final int maxAllowedDepth = 2;

    public static void main(String[] args) throws Exception {
        try (InputStream inputStream = OrganizationReportGenerator.class.getClassLoader().getResourceAsStream("Employees.csv")) {

            if (inputStream == null) {
                throw new FileNotFoundException("employees.csv not found in resources.");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                CsvFileParser parser = new CsvFileParser();
                List<Employee> employees = parser.parse(reader);

                OrganizationAnalyzer analyzer = new OrganizationAnalyzer();
                OrganizationAnalyzer.AnalyzerResult result = analyzer.analyze(employees);

                System.out.println("\nUnderpaid Managers:");
                result.getUnderpaid().forEach((e, diff) ->
                        System.out.printf("%s is underpaid by %.2f\n", e.getFullName(), diff));

                System.out.println("\nOverpaid Managers:");
                result.getOverpaid().forEach((e, diff) ->
                        System.out.printf("%s is overpaid by %.2f\n", e.getFullName(), diff));

                System.out.println("\nEmployees with too long reporting chain:");

                result.getAllDepths().entrySet().stream()
                        .filter(entry -> entry.getValue() > maxAllowedDepth)
                        .forEach(entry -> {
                            Employee e = entry.getKey();
                            int depth = entry.getValue();
                            System.out.printf("%s has a depth of %d (too long by %d)\n", e.getFullName(), depth, depth - maxAllowedDepth);
                        });
            }
        }
    }
}