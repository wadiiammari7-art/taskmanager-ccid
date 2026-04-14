package com.esiet.taskmanager;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests unitaires pour TaskManager (JUnit 5).
 * Référence : Cours Qualité Logicielle – ESIET
 */
@DisplayName("Tests - TaskManager")
class TaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
    }

    // =============================================
    // Tests : addTask()
    // =============================================

    @Test
    @DisplayName("Ajout d'une tâche valide")
    void testAddTask_valid() {
        Task t = manager.addTask("Corriger le bug #42", "Voir issue GitHub", "HIGH");
        assertNotNull(t);
        assertEquals("Corriger le bug #42", t.getTitle());
        assertEquals("HIGH", t.getPriority());
        assertEquals("TODO", t.getStatus());
    }

    @Test
    @DisplayName("Ajout d'une tâche avec titre vide → exception")
    void testAddTask_emptyTitle_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
            manager.addTask("", "description", "LOW")
        );
    }

    @Test
    @DisplayName("Ajout d'une tâche avec priorité invalide → exception")
    void testAddTask_invalidPriority_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
            manager.addTask("Tâche X", "desc", "URGENT")
        );
    }

    @Test
    @DisplayName("Les IDs sont auto-incrémentés")
    void testAddTask_autoIncrementIds() {
        Task t1 = manager.addTask("Tâche 1", "", "LOW");
        Task t2 = manager.addTask("Tâche 2", "", "MEDIUM");
        assertEquals(1, t1.getId());
        assertEquals(2, t2.getId());
    }

    // =============================================
    // Tests : findById()
    // =============================================

    @Test
    @DisplayName("Recherche par ID existant")
    void testFindById_existing() {
        manager.addTask("Analyse", "desc", "MEDIUM");
        Task found = manager.findById(1);
        assertNotNull(found);
        assertEquals("Analyse", found.getTitle());
    }

    @Test
    @DisplayName("Recherche par ID inexistant retourne null")
    void testFindById_notFound() {
        assertNull(manager.findById(999));
    }

    // =============================================
    // Tests : updateStatus()
    // =============================================

    @Test
    @DisplayName("Mise à jour du statut vers IN_PROGRESS")
    void testUpdateStatus_valid() {
        manager.addTask("Tâche A", "", "HIGH");
        boolean result = manager.updateStatus(1, "IN_PROGRESS");
        assertTrue(result);
        assertEquals("IN_PROGRESS", manager.findById(1).getStatus());
    }

    @Test
    @DisplayName("Mise à jour du statut → statut invalide → exception")
    void testUpdateStatus_invalidStatus_throwsException() {
        manager.addTask("Tâche B", "", "LOW");
        assertThrows(IllegalArgumentException.class, () ->
            manager.updateStatus(1, "CANCELLED")
        );
    }

    @Test
    @DisplayName("Mise à jour du statut → ID inexistant retourne false")
    void testUpdateStatus_notFound() {
        boolean result = manager.updateStatus(999, "DONE");
        assertFalse(result);
    }

    // =============================================
    // Tests : removeTask()
    // =============================================

    @Test
    @DisplayName("Suppression d'une tâche existante")
    void testRemoveTask_existing() {
        manager.addTask("À supprimer", "", "LOW");
        assertTrue(manager.removeTask(1));
        assertEquals(0, manager.getTotalCount());
    }

    @Test
    @DisplayName("Suppression d'un ID inexistant retourne false")
    void testRemoveTask_notFound() {
        assertFalse(manager.removeTask(42));
    }

    // =============================================
    // Tests : getTasksByStatus() / getTasksByPriority()
    // =============================================

    @Test
    @DisplayName("Filtrage par statut DONE")
    void testGetTasksByStatus_done() {
        manager.addTask("T1", "", "HIGH");
        manager.addTask("T2", "", "LOW");
        manager.updateStatus(1, "DONE");

        List<Task> done = manager.getTasksByStatus("DONE");
        assertEquals(1, done.size());
        assertEquals("T1", done.get(0).getTitle());
    }

    @Test
    @DisplayName("Filtrage par priorité HIGH")
    void testGetTasksByPriority_high() {
        manager.addTask("Urgente", "", "HIGH");
        manager.addTask("Normale", "", "MEDIUM");

        List<Task> highPrio = manager.getTasksByPriority("HIGH");
        assertEquals(1, highPrio.size());
    }

    // =============================================
    // Tests : isDone() / getDoneCount()
    // =============================================

    @Test
    @DisplayName("isDone() retourne vrai après passage à DONE")
    void testIsDone_afterDone() {
        manager.addTask("Tâche finale", "", "MEDIUM");
        manager.updateStatus(1, "DONE");
        assertTrue(manager.findById(1).isDone());
    }

    @Test
    @DisplayName("getDoneCount() compte correctement les tâches terminées")
    void testGetDoneCount() {
        manager.addTask("T1", "", "HIGH");
        manager.addTask("T2", "", "LOW");
        manager.addTask("T3", "", "MEDIUM");
        manager.updateStatus(1, "DONE");
        manager.updateStatus(3, "DONE");
        assertEquals(2, manager.getDoneCount());
    }
}
