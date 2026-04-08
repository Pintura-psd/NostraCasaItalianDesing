package ncid.fra.nostracasa.controller;

import jakarta.validation.Valid;
import ncid.fra.nostracasa.dto.MessagesDTO;
import ncid.fra.nostracasa.model.Messages;
import ncid.fra.nostracasa.service.MessagesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/contact")
public class ContactController {

    public final MessagesService messagesService;


    public ContactController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping
    public String form(Model model) {
        MessagesDTO messageDTO = new MessagesDTO();
        model.addAttribute("messageDTO", messageDTO);
        model.addAttribute("selectedPage", "contact");
        return "contact/form";
    }

    @PostMapping
    public String submitForm(
            @Valid @ModelAttribute("messageDTO") MessagesDTO messageDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            System.out.println("ERRORS");
            result.getAllErrors().forEach(e -> System.out.println(e.getDefaultMessage()));
            model.addAttribute("selectedPage", "contact");
            return "contact/form";
        }

        messagesService.save(messageDTO);

        return "redirect:/contact?success";
    }

}
