package com.example.sweater.controller;

import com.example.sweater.domain.MessageAndAuthor;
import com.example.sweater.domain.UserAndDetails;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// https://temp-mail.org/

@Controller
public class MainController {

//    @Autowired
    private final MessageRepo messageRepo;
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }
    @Value("${upload.path}")
    String uploadPath;

    @GetMapping("/")
    public String greeting(//@RequestParam(name = "name", required = false, defaultValue = "world") String name,
                           //Map<String, Object> model
    ){
        //model.put("name", name);
        return "greeting";
    }
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        Iterable<MessageAndAuthor> messages = messageRepo.findAll();
        if (filter!=null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal UserAndDetails user,
            @Valid MessageAndAuthor message,
            BindingResult bindingResult, Model model // mem! this order: BindingResult, Model
            //@RequestParam String text, @RequestParam String tag,
            //Map<String, Object> model
            ,@RequestParam("file") MultipartFile file
    ) throws IOException
    {
        //MessageAndAuthor message = new MessageAndAuthor(text, tag, user);
        message.setAuthor(user);
        if (bindingResult.hasErrors()){
            //Map<String, String> errorMap = getErrors(bindingResult);
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("message", message);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(String.valueOf(uploadPath));
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadDir + "/" + resultFilename));
                message.setFilename(resultFilename);
            }
            messageRepo.save(message);
        }
        Iterable<MessageAndAuthor> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }


/*    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<MessageAndAuthor> messages;
        if(filter!=null && !filter.isEmpty()) messages = messageRepo.findByTag(filter);
        else messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";}*/
}

