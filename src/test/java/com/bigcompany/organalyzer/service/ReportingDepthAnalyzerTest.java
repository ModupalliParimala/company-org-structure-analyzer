package com.bigcompany.organalyzer.service;

import com.bigcompany.organalyzer.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportingDepthAnalyzerTest {

    @Test
    void testAnalyzeDepths_correctDepthsCalculated() {
        List<Employee> employees = List.of(
                new Employee("1", "A", "X", 100, null),
                new Employee("2", "B", "Y", 100, "1"),
                new Employee("3", "C", "Z", 100, "2")
        );

        ReportingDepthAnalyzer analyzer = new ReportingDepthAnalyzer(employees);
        Map<Employee, Integer> depths = analyzer.analyzeDepths(employees);

        assertEquals(0, depths.get(employees.get(0)));
        assertEquals(1, depths.get(employees.get(1)));
        assertEquals(2, depths.get(employees.get(2)));
    }
}