package zcd.ts4u.kanban.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zcd.ts4u.kanban.domain.Backlog;
import zcd.ts4u.kanban.domain.ProjectTask;
import zcd.ts4u.kanban.repositories.BacklogRepository;
import zcd.ts4u.kanban.repositories.ProjectTaskRepository;
import zcd.ts4u.kanban.service.ProjectService;
import zcd.ts4u.kanban.service.ProjectTaskService;

@Service
@RequiredArgsConstructor
public class ProjectTaskServiceImpl implements ProjectTaskService {
    // Here we need to inject instances of backlog,  project task, and project service into this class.
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepository projectTaskRepository;
    @Autowired
    private ProjectService projectService;
    @Override
    public ProjectTask createProjectTask(String identifier, ProjectTask projectTask) {
        //first we need to retrieve the backlog object associated with the given identifier.
        Backlog backlog = backlogRepository.findByProjectIdentifier(identifier);
        projectTask.setBacklog(backlog);
        //the given project task object will be then assigned to the backlog
        //there is an instance variable named PTSequence in the backlog entity. we want to assign its value such a way that it will be generated automatically whenever a project task is being created.
//        Integer backlogPTSequence = backlog.getPTSequence();
//        backlogPTSequence++;
        //and we want to assign the value for project identifier of the project task same as the project.
        projectTask.setProjectIdentifier(identifier);
        //and then we save that project task in the repository and return it.
        return projectTaskRepository.save(projectTask);
    }

    @Override
    public Iterable<ProjectTask> getProjectTaskFromProject(String identifier) {
        //this method will check if a project exist in the repository with the given parameter identifier with the help from project service.
        projectService.getProjectByIdentifier(identifier);
        // we then call all the project tasks associated with that project from the project task repository and return those.
        Iterable<ProjectTask> projectTasks = projectTaskRepository.findByProjectIdentifier(identifier);
        return projectTasks;
        // We also need to build a service method, so that on the controller we can handle our errors.
    }
}
