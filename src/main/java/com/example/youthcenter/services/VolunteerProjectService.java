package com.example.youthcenter.services;

import com.example.youthcenter.models.Task;
import com.example.youthcenter.models.TaskStatus;
import com.example.youthcenter.models.User;
import com.example.youthcenter.models.VolunteerProject;
import com.example.youthcenter.repositories.TaskRepository;
import com.example.youthcenter.repositories.VolunteerProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VolunteerProjectService {

    private final VolunteerProjectRepository volunteerProjectRepository;
    private final UserService userService;
    private final TaskRepository taskRepository;

    @Autowired
    public VolunteerProjectService(VolunteerProjectRepository volunteerProjectRepository, UserService userService, TaskRepository taskRepository) {
        this.volunteerProjectRepository = volunteerProjectRepository;
        this.userService = userService;
        this.taskRepository = taskRepository;
    }
    public List<VolunteerProject> getAllVolunteerProjects() {
        return volunteerProjectRepository.findAll();
    }
    public VolunteerProject getVolunteerProjectById(Long id) {
        return volunteerProjectRepository.findById(id).orElse(null);
    }
    public void addProject(Principal principal, VolunteerProject volunteerProject) {
        volunteerProject.setUser(userService.getUserByPrincipal(principal).orElse(null));
        VolunteerProject savedProject = volunteerProjectRepository.save(volunteerProject);
        List<User> participants = volunteerProject.getParticipants();
        if (participants != null) {
            for (User participant : participants) {
                participant.getVolunteerProjects().add(savedProject);
            }
            savedProject.setParticipants(participants);
            volunteerProjectRepository.save(savedProject);  // Зберегти з учасниками
        }
    }
    public void addTask(Task task, Principal principal, VolunteerProject volunteerProject){
        task.setVolunteerProject(volunteerProject);
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setSender(userService.getUserByPrincipal(principal).orElse(null));
        taskRepository.save(task);
    }

    public void editProject(VolunteerProject volunteerProject) {
        volunteerProject.setUpdatedAt(LocalDateTime.now());
        volunteerProjectRepository.save(volunteerProject);
    }
}
