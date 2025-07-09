package com.bigcompany.organalyzer.parser;

import com.bigcompany.organalyzer.model.Employee;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileParserTest {

    @Test
    void testParse_validCsv_returnsEmployeeList() throws IOException {
        String csvData = """
                ID,FirstName,LastName,Salary,ManagerID
                1,Alice,Smith,150000,
                2,Bob,Brown,90000,1
                """;

        Reader reader = new StringReader(csvData);
        CsvFileParser parser = new CsvFileParser();
        List<Employee> employees = parser.parse(reader);

        assertEquals(2, employees.size());
        assertEquals("Alice", employees.get(0).firstName());
        assertEquals("Bob", employees.get(1).firstName());
    }

    @Test
    void testParse_incompleteLines_skipsInvalid() throws IOException {
        String csvData = """
                ID,FirstName,LastName,Salary,ManagerID
                1,Alice,Smith,150000,
                2,Bob,Brown,,1
                """; // Missing salary

        Reader reader = new StringReader(csvData);
        CsvFileParser parser = new CsvFileParser();
        List<Employee> employees = parser.parse(reader);

        assertEquals(2, employees.size());
    }
}