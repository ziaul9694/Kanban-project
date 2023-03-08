package zcd.ts4u.kanban.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zcd.ts4u.kanban.domain.ProjectTask;
import zcd.ts4u.kanban.service.MapValidationErrorService;
import zcd.ts4u.kanban.service.ProjectTaskService;

import javax.validation.Valid;

@RestController
@RequestMapping("/backlog")
@RequiredArgsConstructor
public class BacklogController {
    //let's inject dependencies
    @Autowired
    private MapValidationErrorService errorService;
    @Autowired
    private ProjectTaskService projectTaskService;
    // first we create a project task object associated with the project's backlog with the API that will take a parameters of a project task in Json format with the body of the request, an identifier in the path variable, and an object of Binding result that will handle the validation.
    @PostMapping("/create/{identifier}")
    public ResponseEntity<?> addProjectTaskToBacklog(
            @Valid @RequestBody ProjectTask projectTask,
            BindingResult result,
            @PathVariable String identifier)
    {
        ResponseEntity<?> errorMap = errorService.mapValidationService(result);
        if( errorMap != null){
            return errorMap;
        }
        ProjectTask projectTask1 = projectTaskService.createProjectTask(identifier, projectTask);
        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }
    // we then create second API to get project tasks from backlog
    @GetMapping("/{identifier}")
    public ResponseEntity<Iterable<ProjectTask>> getBacklog(@PathVariable String identifier){
        Iterable<ProjectTask> tasks = projectTaskService.getProjectTaskFromProject(identifier);
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }
}
