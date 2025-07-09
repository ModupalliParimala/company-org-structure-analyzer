package com.bigcompany.organalyzer.parser;

import com.bigcompany.organalyzer.model.Employee;

import java.io.*;
import java.util.*;

public class CsvFileParser {

    public List<Employee> parse(Reader reader) throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }

                String[] tokens = line.split(",",-1); // include trailing empty strings
                if (tokens.length < 5) continue;

                String id = tokens[0].trim();
                String firstName = tokens[1].trim();
                String lastName = tokens[2].trim();
                double salary = Double.parseDouble(tokens[3].trim());
                String managerId = tokens[4].trim().isEmpty() ? null : tokens[4].trim();

                employees.add(new Employee(id, firstName, lastName, salary, managerId));
            }
        }

        return employees;
    }
}