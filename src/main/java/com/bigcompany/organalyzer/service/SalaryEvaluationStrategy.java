package com.bigcompany.organalyzer.service;

import com.bigcompany.organalyzer.model.Employee;

import java.util.List;
import java.util.Map;

public interface SalaryEvaluationStrategy {
    void evaluate(Employee manager, List<Employee> subordinates,
                  Map<Employee, Double> underpaid, Map<Employee, Double> overpaid);
}
