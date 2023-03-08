package zcd.ts4u.kanban.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zcd.ts4u.kanban.domain.Project;
import zcd.ts4u.kanban.service.MapValidationErrorService;
import zcd.ts4u.kanban.service.ProjectService;

import javax.validation.Valid;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    @Autowired
    private MapValidationErrorService errorService;
    @Autowired
    private ProjectService projectService;
    //first we need to build API for creating projects. For that, we need to send a Project object as Json format and a BindingResult object to check if error occurs in the runtime. After that, the application will save that Project object in the repository and give client a response.
    @PostMapping("/create")
    public ResponseEntity<?> handleSaveOrUpdate(@Valid @RequestBody Project project, BindingResult result){
        ResponseEntity<?> errorMap = errorService.mapValidationService(result);
        if (errorMap != null){
            return errorMap;
        }
        Project project1 = projectService.saveOrUpdate(project);
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }
    //we will build our second API for get a single project
    @GetMapping("/{identifier}")
    public ResponseEntity<Project> handleGetProject(@PathVariable String identifier){
        Project project = projectService.getProjectByIdentifier(identifier);
        return new ResponseEntity<>(project,HttpStatus.OK);
    }
    // Third API for getting all the projects
    @GetMapping("/all")
    public ResponseEntity<Iterable<Project>> handleGetAllProjects(){
        Iterable<Project> allProjects = projectService.getAllProjects();
        return new ResponseEntity<>(allProjects,HttpStatus.OK);
    }
    //fourth API to delete a project
    @DeleteMapping("/delete/{identifier}")
    public ResponseEntity<String> handleDeleteProject(@PathVariable String identifier){
        projectService.deleteProjectByIdentifier(identifier);
        return new ResponseEntity<>("Your project is deleted successfully", HttpStatus.OK);
    }
    // Now let's create another controller for our project task.
}
