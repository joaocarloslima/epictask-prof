package br.com.fiap.epictaskg.task;

import br.com.fiap.epictaskg.config.MessageHelper;
import br.com.fiap.epictaskg.user.User;
import br.com.fiap.epictaskg.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MessageHelper messageHelper;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, MessageHelper messageHelper, UserService userService) {
        this.taskRepository = taskRepository;
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

    public List<Task> getAllTasks(){
        return  taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.delete(getTask(id));
    }

    public void pick(Long id, User user) {
        var task = getTask(id);
        if(task.getUser() != null){
            throw new IllegalStateException(messageHelper.get("task.pick.already"));
        }
        task.setUser(user);
        taskRepository.save(task);
    }

    public void drop(Long id, User user) {
        var task = getTask(id);
        if(!task.getUser().equals(user)){
            throw new IllegalStateException(messageHelper.get("task.drop.notowner"));
        }
        task.setUser(null);
        taskRepository.save(task);
    }

    public void incrementTaskStatus(Long id, User user) {
        var task = getTask(id);
        task.setStatus(task.getStatus() + 10);
        if(task.getStatus() > 100) task.setStatus(100);

        if(task.getStatus() == 100){
            userService.addScore(user, task.getScore());
        }

        taskRepository.save(task);
    }

    public void decrementTaskStatus(Long id, User user) {
        var task = getTask(id);
        task.setStatus(task.getStatus() - 10);
        if(task.getStatus() < 0) task.setStatus(0);
        taskRepository.save(task);
    }

    private Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(messageHelper.get("task.notfound"))
        );
    }

    public List<Task> getTasksUndone() {
        return taskRepository.findByStatusLessThan(100);
    }
}
