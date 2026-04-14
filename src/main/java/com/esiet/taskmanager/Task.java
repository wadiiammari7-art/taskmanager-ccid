package com.esiet.taskmanager;

/**
 * Classe représentant une tâche dans le gestionnaire de tâches.
 */
public class Task {

    private int id;
    private String title;
    private String description;
    private String status; // "TODO", "IN_PROGRESS", "DONE"
    private String priority; // "LOW", "MEDIUM", "HIGH"

    public Task(int id, String title, String description, String priority) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide.");
        }
        if (!priority.equals("LOW") && !priority.equals("MEDIUM") && !priority.equals("HIGH")) {
            throw new IllegalArgumentException("Priorité invalide : " + priority);
        }
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = "TODO";
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }

    public void setStatus(String status) {
        if (!status.equals("TODO") && !status.equals("IN_PROGRESS") && !status.equals("DONE")) {
            throw new IllegalArgumentException("Statut invalide : " + status);
        }
        this.status = status;
    }

    public boolean isDone() {
        return "DONE".equals(this.status);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " | " + priority + " | " + status;
    }
}
