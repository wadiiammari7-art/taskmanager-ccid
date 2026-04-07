package com.esiet.taskmanager;

/**
 * Point d'entrée de l'application TaskManager.
 */
public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        System.out.println("=== TaskManager - ESIET Demo ===");

        manager.addTask("Rédiger le cahier des charges", "Document initial du projet", "HIGH");
        manager.addTask("Configurer l'environnement Git", "Initialiser le dépôt GitHub", "HIGH");
        manager.addTask("Écrire les tests unitaires", "JUnit 5 + JaCoCo", "MEDIUM");
        manager.addTask("Réviser la documentation", "Javadoc et README", "LOW");

        manager.updateStatus(1, "DONE");
        manager.updateStatus(2, "IN_PROGRESS");

        System.out.println("\n--- Toutes les tâches ---");
        manager.getAllTasks().forEach(System.out::println);

        System.out.println("\n--- Tâches terminées : " + manager.getDoneCount() + "/" + manager.getTotalCount() + " ---");
        System.out.println("\n--- Tâches HIGH priority ---");
        manager.getTasksByPriority("HIGH").forEach(System.out::println);
    }
}
