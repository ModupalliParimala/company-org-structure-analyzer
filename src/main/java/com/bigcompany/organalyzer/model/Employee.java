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

    @Override
    public String toString() {
        return getFullName() + " (" + id + ")";
    }
}
