package ru.mrs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mrs.demo.model.Task;
import ru.mrs.demo.repository.TaskRepository;

@RestController
public class UserController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/users")
    public Task create(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @GetMapping("/tasks")
    public Iterable<Task> get() {//получить все записи
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task get(@PathVariable long id) {//получить запись по id
        return taskRepository.findById(id).orElse(null);
    }


    @PutMapping("/tasks/{id}")
    public Task update(@PathVariable long id, @RequestBody Task task) {//редактирование записи по id
        task.setId(id);
        return taskRepository.save(task);
    }

    @PatchMapping("/tasks/{id}")
    public void patchMethod(@PathVariable Long id, @RequestBody Task task){
        if (task.isDone())
            taskRepository.markAsDone(id);
    }

    @DeleteMapping("/tasks/{id}")
    public void delete(@PathVariable long id) {//удалить запись по id
        taskRepository.deleteById(id);
    }

}


