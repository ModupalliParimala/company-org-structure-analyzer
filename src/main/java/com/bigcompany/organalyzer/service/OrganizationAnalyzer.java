package com.bigcompany.organalyzer.service;

import com.bigcompany.organalyzer.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrganizationAnalyzer {
    private final SalaryEvaluationStrategy salaryStrategy;
    private final ReportingDepthAnalyzer depthAnalyzer;

    public OrganizationAnalyzer(SalaryEvaluationStrategy salaryStrategy,
                                ReportingDepthAnalyzer depthAnalyzer) {
        this.salaryStrategy = salaryStrategy;
        this.depthAnalyzer = depthAnalyzer;
    }

    public AnalyzerResult analyze(List<Employee> employees) {
        Map<Employee, Double> underpaid = new HashMap<>();
        Map<Employee, Double> overpaid = new HashMap<>();
        Map<Employee, Integer> allDepths = depthAnalyzer.analyzeDepths(employees);

        Map<String, List<Employee>> managerToSubordinates = new HashMap<>();
        for (Employee e : employees) {
            if (e.managerId() != null) {
                managerToSubordinates.computeIfAbsent(e.managerId(), k -> new ArrayList<>()).add(e);
            }
        }

        for (Employee manager : employees) {
            List<Employee> subs = managerToSubordinates.getOrDefault(manager.id(), List.of());
            if (!subs.isEmpty()) {
                salaryStrategy.evaluate(manager, subs, underpaid, overpaid);
            }
        }

        return new AnalyzerResult(underpaid, overpaid, allDepths);
    }

    public static class AnalyzerResult {
        private final Map<Employee, Double> underpaid;
        private final Map<Employee, Double> overpaid;
        private final Map<Employee, Integer> allDepths;

        public AnalyzerResult(Map<Employee, Double> underpaid,
                              Map<Employee, Double> overpaid,
                              Map<Employee, Integer> allDepths) {
            this.underpaid = underpaid;
            this.overpaid = overpaid;
            this.allDepths = allDepths;
        }

        public Map<Employee, Double> getUnderpaid() { return underpaid; }
        public Map<Employee, Double> getOverpaid() { return overpaid; }
        public Map<Employee, Integer> getAllDepths() { return allDepths; }
    }
}