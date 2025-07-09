package com.bigcompany.organalyzer.service;

import com.bigcompany.organalyzer.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationAnalyzerTest {

    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employees = List.of(
                new Employee("1", "Alice", "Smith", 150000, null),
                new Employee("2", "Bob", "Brown", 90000, "1"),
                new Employee("3", "Charlie", "Davis", 100000, "1"),
                new Employee("4", "Diana", "Evans", 80000, "2")
        );
    }

    @Test
    void testAnalyze_underpaidAndOverpaidDetection() {
        ReportingDepthAnalyzer depthAnalyzer = new ReportingDepthAnalyzer(employees);
        SalaryEvaluationStrategy strategy = new DefaultSalaryEvaluationStrategy();
        OrganizationAnalyzer analyzer = new OrganizationAnalyzer(strategy, depthAnalyzer);

        OrganizationAnalyzer.AnalyzerResult result = analyzer.analyze(employees);

        assertTrue(result.getUnderpaid().containsKey(employees.get(1))); // Bob should be underpaid
        assertEquals(1, result.getUnderpaid().size());
        assertTrue(result.getOverpaid().containsKey(employees.get(0))); // Alice should be overpaid
        assertEquals(1, result.getOverpaid().size());
    }

    @Test
    void testAnalyze_reportingDepths() {
        ReportingDepthAnalyzer depthAnalyzer = new ReportingDepthAnalyzer(employees);
        SalaryEvaluationStrategy strategy = new DefaultSalaryEvaluationStrategy();
        OrganizationAnalyzer analyzer = new OrganizationAnalyzer(strategy, depthAnalyzer);

        OrganizationAnalyzer.AnalyzerResult result = analyzer.analyze(employees);
        Map<Employee, Integer> depths = result.getAllDepths();

        assertEquals(0, depths.get(employees.get(0))); // Alice
        assertEquals(1, depths.get(employees.get(1))); // Bob
        assertEquals(1, depths.get(employees.get(2))); // Charlie
        assertEquals(2, depths.get(employees.get(3))); // Diana
    }
}