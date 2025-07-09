package com.bigcompany.organalyzer.service;

import com.bigcompany.organalyzer.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationAnalyzerTest {

    @Test
    void testAnalyzeSalaryValidation() {
        Employee ceo = new Employee("1", "CEO", "Pari", 60000, null);
        Employee mgr = new Employee("2", "Keerthi", "Mgr", 45000, "1");
        Employee emp1 = new Employee("3", "Sam", "Mgr", 47000, "1");
        Employee emp2 = new Employee("4", "John", "doe", 50000, "2");
        Employee emp3 = new Employee("5", "Bren", "avalon", 34000, "4");

        List<Employee> employees = List.of(ceo, mgr, emp1, emp2, emp3);
        OrganizationAnalyzer analyzer = new OrganizationAnalyzer();
        OrganizationAnalyzer.AnalyzerResult result = analyzer.analyze(employees);

        Map<Employee, Double> underpaid = result.getUnderpaid();
        Map<Employee, Integer> depths = result.getAllDepths();
        assertTrue(underpaid.containsKey(mgr));
        assertEquals(3, depths.get(emp3)); // CEO → Keerthi → John -> Bren
    }
}
