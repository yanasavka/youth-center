package com.example.youthcenter;

import com.example.youthcenter.models.Task;
import com.example.youthcenter.models.User;
import com.example.youthcenter.models.VolunteerProject;
import com.example.youthcenter.repositories.TaskRepository;
import com.example.youthcenter.repositories.VolunteerProjectRepository;
import com.example.youthcenter.services.UserService;
import com.example.youthcenter.services.VolunteerProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VolunteerProjectIntegrationTest {
    @Autowired
    private VolunteerProjectService volunteerProjectService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private VolunteerProjectRepository volunteerProjectRepository;
    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Test
    public void testAddProjectAndTask() {
        User user = userService.getUserById(1L);
        VolunteerProject project = new VolunteerProject();
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setUser(user);
        volunteerProjectService.addProject(null, project);
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Task Description");
        task.setDeadline(LocalDateTime.now().plusDays(7));
        task.setVolunteerProject(project);
        task.setSender(user);
        volunteerProjectService.addTask(task, null, project);
        VolunteerProject savedProject = volunteerProjectRepository.findById(project.getId()).orElse(null);
        assertNotNull(savedProject);
        assertEquals("Test Project", savedProject.getName());
        Task savedTask = taskRepository.findById(task.getId()).orElse(null);
        assertNotNull(savedTask);
        assertEquals("Test Task", savedTask.getTitle());
        assertEquals(savedProject, savedTask.getVolunteerProject());
    }
}
