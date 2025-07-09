package com.bigcompany.organalyzer.service;

import com.bigcompany.organalyzer.model.Employee;

import java.util.*;

public class OrganizationAnalyzer {

    public AnalyzerResult analyze(List<Employee> employees) {
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, List<Employee>> managerToSubordinates = new HashMap<>();

        for (Employee e : employees) {
            employeeMap.put(e.id(), e);
            if (e.managerId() != null) {
                managerToSubordinates
                        .computeIfAbsent(e.managerId(), k -> new ArrayList<>())
                        .add(e);
            }
        }

        Map<Employee, Double> underpaid = new HashMap<>();
        Map<Employee, Double> overpaid = new HashMap<>();
        Map<Employee, Integer> allDepths = new HashMap<>();

        for (Employee manager : employees) {
            List<Employee> subs = managerToSubordinates.getOrDefault(manager.id(), List.of());
            if (subs.isEmpty()) continue;

            double avg = subs.stream().mapToDouble(Employee::salary).average().orElse(0.0);
            double minAllowed = avg * 1.2;//20% of average salary -> avg + (20% of avg) = avg + (0.2 * avg) = avg(1+0.2)=avg*1.2
            double maxAllowed = avg * 1.5;//50% of average salary -> avg + (50% of avg) = avg + (0.5 * avg) = avg(1+0.5)=avg*1.5

            if (manager.salary() < minAllowed) {
                underpaid.put(manager, minAllowed - manager.salary());
            } else if (manager.salary() > maxAllowed) {
                overpaid.put(manager, manager.salary() - maxAllowed);
            }
        }
        Map<String, Integer> depthCache = new HashMap<>();
        for (Employee e : employees) {
            int depth = getDepth(e, employeeMap, depthCache);
            allDepths.put(e, depth);
        }

        return new AnalyzerResult(underpaid, overpaid, allDepths);
    }

    private int getDepth(Employee e, Map<String, Employee> employeeMap, Map<String, Integer> depthCache) {
        if (e.managerId() == null) return 0;
        if (depthCache.containsKey(e.id())) return depthCache.get(e.id());

        Employee manager = employeeMap.get(e.managerId());
        int depth = manager == null ? 1 : 1 + getDepth(manager, employeeMap, depthCache);
        depthCache.put(e.id(), depth);
        return depth;
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

        public Map<Employee, Double> getUnderpaid() {
            return underpaid;
        }

        public Map<Employee, Double> getOverpaid() {
            return overpaid;
        }

        public Map<Employee, Integer> getAllDepths() {
            return allDepths;
        }
    }

}