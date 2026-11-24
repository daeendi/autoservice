package com.example.autoservice.model;

public class AdminUser extends User {
    private String accessLevel;

    public AdminUser(Long id, String name, String email, String accessLevel) {
        super(id, name, email, UserRole.ADMIN);
        this.accessLevel = accessLevel;
    }

    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }

    @Override
    public String getDescription() { return "Администратор с уровнем доступа: " + accessLevel; }
}