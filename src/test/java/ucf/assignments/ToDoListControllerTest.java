package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;


/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Edelis Molina
 */

class ToDoListControllerTest {
    ToDoListController controller;

    @BeforeEach
    void setUp() {
        controller = new ToDoListController();
    }

    @Test
    void loadFile() {
        ObservableList<Task> loadedTasks = controller.loadFile(new File("C:\\Users\\EDELITA\\Desktop\\OOPExercises\\assignment4part2\\inputFiles\\test.txt"));

        assertEquals(7, loadedTasks.size());
    }

    @Test
    void saveFileTest() {
        ObservableList<Task> list = FXCollections.observableArrayList();
        list.add(new Task("Task1", LocalDate.of(2021, Month.JULY, 3), "Completed"));

        controller.saveFile(list, new File("C:\\Users\\EDELITA\\Desktop\\OOPExercises\\assignment4part2\\outputFiles\\out.txt"));

        // After writing a Task to the file, the writer writes a new line
        String expectedStr = "Task1,2021-07-03,Completed"+System.lineSeparator();

        // Read in file content
        String actualStr = "";
        try {
            actualStr = new String(Files.readAllBytes(Paths.get("C:\\Users\\EDELITA\\Desktop\\OOPExercises\\assignment4part2\\outputFiles\\out.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(expectedStr, actualStr);
    }


    @Test
    void clearObservableListTest() {
        // Get a list of testing tasks
        ObservableList<Task> testList = controller.getTasks();
        // Check list is not empty
        assertFalse(testList.isEmpty());

        // Call method from Controller class to clear an ObservableList
        controller.clearObservableList(testList);

        // Check if list is empty
        assertTrue(testList.isEmpty());
    }


    @Test
    void addNewTaskTest() {

        ObservableList<Task> testList = FXCollections.observableArrayList();
        // Call function to add a new task to the list
        controller.addNewTask(testList, "Added Task", LocalDate.of(2021, Month.JULY, 3));

        assertFalse(testList.isEmpty());

        assertEquals(testList.get(0).convertToString(), "Added Task,2021-07-03,Incompleted");
    }


    @Test
    void deleteTaskTest() {
        // Get a list of testing tasks
        ObservableList<Task> testList = controller.getTasks();
        assertEquals(7, testList.size());

        // Check task at index 0 before deleting this task
        assertEquals(testList.get(0).getTaskDescription(), "Task1");

        // Remove the first task of the list
        controller.deleteTask(testList, 0);

        // Check the new list size is now 6
        assertEquals(6, testList.size());
        // Check that the task at index 0 is now Task2
        assertEquals(testList.get(0).getTaskDescription(), "Task2");
    }


    @Test
    void updateTaskTest() {
        Task selectedTask = new Task("Task", LocalDate.of(2021, Month.JULY, 3), "Completed");

        Task updatedTask = controller.updateTask(selectedTask, "Task has been updated", LocalDate.of(2030, Month.JULY, 3));

        assertEquals(updatedTask.getTaskDescription(), "Task has been updated");
        assertEquals(updatedTask.getDueDate(), LocalDate.of(2030, Month.JULY, 3));
    }
}