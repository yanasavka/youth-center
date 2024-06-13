package com.example.youthcenter.controllers;

import com.example.youthcenter.models.Task;
import com.example.youthcenter.models.User;
import com.example.youthcenter.models.VolunteerProject;
import com.example.youthcenter.repositories.VolunteerProjectRepository;
import com.example.youthcenter.services.UserService;
import com.example.youthcenter.services.VolunteerProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.List;

@Controller
public class VolunteerProjectController {
    private final VolunteerProjectRepository volunteerProjectRepository;
    private final VolunteerProjectService volunteerProjectService;
    private final UserService userService;
    @Autowired
    public VolunteerProjectController(VolunteerProjectService volunteerProjectService,
                                      UserService userService,
                                      VolunteerProjectRepository volunteerProjectRepository) {
        this.volunteerProjectService = volunteerProjectService;
        this.userService = userService;
        this.volunteerProjectRepository = volunteerProjectRepository;
    }
    @GetMapping("/my/volunteer/projects")
    public String getAllVolunteerProjects(@RequestParam(name = "name", required = false) String query,
                                          Model model) {

        List<VolunteerProject> volunteerProjects;
        if (query != null && !query.isEmpty()) {
            volunteerProjects = volunteerProjectRepository.findAllByNameStartingWithIgnoreCase(query);
        } else {
            volunteerProjects = volunteerProjectService.getAllVolunteerProjects();
        }
        model.addAttribute("volunteerProjects", volunteerProjects);
        return "volunteer-projects-manager";
    }

    @GetMapping("/my/volunteer/projects/{projectId}")
    public String getVolunteerProject(@PathVariable(value = "projectId") Long projectId, Model model) {
        VolunteerProject volunteerProject = volunteerProjectService.getVolunteerProjectById(projectId);
        List<User> participants = volunteerProject.getParticipants();
        model.addAttribute("participants", participants);
        model.addAttribute("volunteerProject", volunteerProject);
        return "for-manager/volunteer-project-details";
    }

    @GetMapping("/my/volunteer/new")
    public String getAddProjectForm(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "for-manager/volunteer-project-add";
    }

    @PostMapping("/my/volunteer/new")
    public String addingVolunteerProject(VolunteerProject volunteerProject, Principal principal,
                                         @RequestParam("participants") List<Long> participantIds, Model model){
        List<User> participants = userService.getUsersByIds(participantIds);
        volunteerProject.setParticipants(participants);
        volunteerProjectService.addProject(principal, volunteerProject);
        return "redirect:/my/volunteer/projects";
    }

    @GetMapping("/my/volunteer/projects/{id}/edit")
    public String getEditProjectForm(@PathVariable(value = "id") Long id, Model model){
        model.addAttribute("project", volunteerProjectService.getVolunteerProjectById(id));
        return "for-manager/edit-project";
    }

    @PostMapping("/my/volunteer/projects/{id}/edit")
    public String editVolunteerProject(@PathVariable(value = "id") Long id, Model model,
                                       @Validated VolunteerProject volunteerProject){
        model.addAttribute("volunteerProject", volunteerProject);
        volunteerProjectService.editProject(volunteerProject);
        return "redirect:/my/volunteer/projects/" + id;
    }
    @GetMapping("/my/volunteer/projects/{projectId}/tasks/new")
    public String getAddTaskForm(@PathVariable(value = "projectId") Long projectId, Model model){
        VolunteerProject volunteerProject = volunteerProjectService.getVolunteerProjectById(projectId);
        model.addAttribute("volunteerProject", volunteerProject);
        return "for-manager/task-add";
    }
    @PostMapping("/my/volunteer/projects/{projectId}/tasks/new")
    public String addingTaskForParticipant(@PathVariable(value = "projectId") Long projectId, Task task, Principal principal){
        VolunteerProject volunteerProject = volunteerProjectService.getVolunteerProjectById(projectId);
        volunteerProjectService.addTask(task, principal, volunteerProject);
        return "redirect:/my/volunteer/projects/{projectId}";
    }
}
