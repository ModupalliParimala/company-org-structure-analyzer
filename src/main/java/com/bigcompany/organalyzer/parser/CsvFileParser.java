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

                String[] tokens = line.split(",", -1); // include trailing empty strings
                if (tokens.length < 5) continue;

                String id = tokens[0].trim();
                String firstName = tokens[1].trim();
                String lastName = tokens[2].trim();
                String salaryStr = tokens[3].trim();
                String managerId = tokens[4].trim().isEmpty() ? null : tokens[4].trim();

                // Validate required fields
                if (id.isEmpty() || firstName.isEmpty()) {
                    continue; // skip incomplete or invalid entries
                }

                employees.add(new Employee(id,
                        firstName,
                        lastName,
                        salaryStr.isEmpty() ? 0.0 : Double.parseDouble(salaryStr),
                        managerId));
            }
        }

        return employees;
    }
}