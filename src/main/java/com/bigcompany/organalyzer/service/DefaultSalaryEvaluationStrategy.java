package com.bigcompany.organalyzer.service;

import com.bigcompany.organalyzer.model.Employee;

import java.util.List;
import java.util.Map;

public class DefaultSalaryEvaluationStrategy implements SalaryEvaluationStrategy {
    @Override
    public void evaluate(Employee manager, List<Employee> subs,
                         Map<Employee, Double> underpaid, Map<Employee, Double> overpaid) {
        double avg = subs.stream().mapToDouble(Employee::salary).average().orElse(0.0);
        double minAllowed = avg * 1.2;
        double maxAllowed = avg * 1.5;

        if (manager.salary() < minAllowed) {
            underpaid.put(manager, minAllowed - manager.salary());
        } else if (manager.salary() > maxAllowed) {
            overpaid.put(manager, manager.salary() - maxAllowed);
        }
    }
}
