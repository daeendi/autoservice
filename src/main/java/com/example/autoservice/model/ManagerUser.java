package com.example.autoservice.model;

public class ManagerUser extends User {
    private String department;

    public ManagerUser(Long id, String name, String email, String department) {
        super(id, name, email, UserRole.MANAGER);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String getDescription() { return "Менеджер отдела: " + department; }
}