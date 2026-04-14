package com.esiet.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestionnaire de tâches – classe principale de la logique métier.
 */
public class TaskManager {

    private List<Task> tasks;
    private int nextId;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
    }

    /**
     * Ajoute une nouvelle tâche.
     */
    public Task addTask(String title, String description, String priority) {
        Task task = new Task(nextId++, title, description, priority);
        tasks.add(task);
        return task;
    }

    /**
     * Recherche une tâche par son identifiant.
     */
    public Task findById(int id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Change le statut d'une tâche.
     */
    public boolean updateStatus(int id, String newStatus) {
        Task task = findById(id);
        if (task == null) return false;
        task.setStatus(newStatus);
        return true;
    }

    /**
     * Supprime une tâche par son identifiant.
     */
    public boolean removeTask(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }

    /**
     * Retourne toutes les tâches.
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Retourne les tâches filtrées par statut.
     */
    public List<Task> getTasksByStatus(String status) {
        return tasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    /**
     * Retourne les tâches filtrées par priorité.
     */
    public List<Task> getTasksByPriority(String priority) {
        return tasks.stream()
                .filter(t -> t.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    /**
     * Retourne le nombre total de tâches.
     */
    public int getTotalCount() {
        return tasks.size();
    }

    /**
     * Retourne le nombre de tâches terminées.
     */
    public int getDoneCount() {
        return (int) tasks.stream().filter(Task::isDone).count();
    }
}
