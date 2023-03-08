package zcd.ts4u.kanban.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zcd.ts4u.kanban.domain.Backlog;
import zcd.ts4u.kanban.domain.Project;
import zcd.ts4u.kanban.exception.ProjectIdException;
import zcd.ts4u.kanban.repositories.BacklogRepository;
import zcd.ts4u.kanban.repositories.ProjectRepository;
import zcd.ts4u.kanban.service.ProjectService;

//The project service class is defined as a spring-managed service using @service annotation.
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    //these two repositories will be injected into the service by Spring.
    //the saveOrUpdate method is defined to create or update a project. it will take project object as an input and also will give a project object as an output.
    @Override
    public Project saveOrUpdate(Project project) {
        try {//We will first set the project identifier
            project.setProjectIdentifier(project.getProjectIdentifier());

            //then we check whether the project ID is null and if the project ID is null, a new Backlog object will be created that is associated with the Project.
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier());
            }
            //if the project id is not null, the Backlog object will be retrieved from the backlog repository and set the backlog to the project
            if (project.getId() != null){
                Backlog backlog = backlogRepository.findByProjectIdentifier(project.getProjectIdentifier());
                project.setBacklog(backlog);
            }
            //after that, we can save the project in the project repository and return the project object as an output.
            projectRepository.save(project);
            return project;
//            but if any exception occurs, then we need to handle the exception. to solve the exception problem we first use the try catch, and then we also will create a custom exception that will handle the exception.
        } catch (Exception e) {
            //here we will throw our custom exception.
            throw new ProjectIdException("project Id " + project.getProjectIdentifier() + " already exist");
            // in advance, we are going to create a global exception handler for the controller to handle if the exception happens.
        }
    }

    @Override
    public Project getProjectByIdentifier(String identifier) {
        //this method will retrieve a project by its projectIdentifier
        Project project = projectRepository.findByProjectIdentifier(identifier);
        //if the project is not found, we will throw an exception with appropriate error message.
        if (project == null) {
            throw new ProjectIdException("Project of " + identifier + "doesn't exist");
        }
        return project;
    }

    @Override
    public void deleteProjectByIdentifier(String identifier) {
        //this method will first retrieve the project by its project identifier from the project repository and if the project is not found we will throw our custom exception and if it is found, it will be deleted from the repository.
        Project project = projectRepository.findByProjectIdentifier(identifier);
        if (project == null) {
            throw new ProjectIdException("Project name " + identifier + "does not exist");
        }
        projectRepository.delete(project);
    }

    @Override
    public Iterable<Project> getAllProjects() {
        //this method return all the projects in the repository.
        return projectRepository.findAll();
        //as now, we have built our project service, next we build our service for project task.
    }
}
