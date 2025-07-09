package com.bigcompany.organalyzer.model;

public record Employee(
    String id,
    String firstName,
    String lastName,
    double salary,
    String managerId
) {
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isCEO() {
        return managerId == null || managerId.isBlank();
    }

    @Override
    public String toString() {
        return getFullName() + " (" + id + ")";
    }
}
