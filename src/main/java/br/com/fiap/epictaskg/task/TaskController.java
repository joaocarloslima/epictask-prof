package br.com.fiap.epictaskg.task;

import br.com.fiap.epictaskg.config.MessageHelper;
import br.com.fiap.epictaskg.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final MessageSource messageSource;
    private final MessageHelper messageHelper;
    private final UserService userService;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        var tasks = taskService.getTasksUndone();
        System.out.println(user);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/form")
    public String form(Task task){
        return "form";
    }

    @PostMapping("/form")
    public String create(@Valid Task task, BindingResult result, RedirectAttributes redirect ){ //biding

        if(result.hasErrors()) return "form";

        taskService.save(task);
        redirect.addFlashAttribute("message", messageHelper.get("task.create.success"));
        return "redirect:/task"; //301
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        taskService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("task.delete.success"));
        return "redirect:/task";
    }

    @PutMapping("/pick/{id}")
    public String pick(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        taskService.pick(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("/drop/{id}")
    public String drop(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        taskService.drop(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("/inc/{id}")
    public String increment(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        taskService.incrementTaskStatus(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("/dec/{id}")
    public String decrement(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        taskService.decrementTaskStatus(id, userService.register(principal));
        return "redirect:/task";
    }















}
