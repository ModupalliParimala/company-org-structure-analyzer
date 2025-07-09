package com.bigcompany.organalyzer.service;

import com.bigcompany.organalyzer.model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportingDepthAnalyzer {
    private final Map<String, Employee> employeeMap;
    private final Map<String, Integer> depthCache = new HashMap<>();

    public ReportingDepthAnalyzer(List<Employee> employees) {
        this.employeeMap = new HashMap<>();
        for (Employee e : employees) employeeMap.put(e.id(), e);
    }

    public Map<Employee, Integer> analyzeDepths(List<Employee> employees) {
        Map<Employee, Integer> result = new HashMap<>();
        for (Employee e : employees) {
            result.put(e, computeDepth(e));
        }
        return result;
    }

    private int computeDepth(Employee e) {
        if (e.managerId() == null) return 0;
        if (depthCache.containsKey(e.id())) return depthCache.get(e.id());

        Employee manager = employeeMap.get(e.managerId());
        int depth = manager == null ? 1 : 1 + computeDepth(manager);
        depthCache.put(e.id(), depth);
        return depth;
    }
}
