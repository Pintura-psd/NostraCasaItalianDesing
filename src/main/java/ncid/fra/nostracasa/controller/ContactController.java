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
import ncid.fra.nostracasa.service.EmailService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/contact")
public class ContactController {

    public final MessagesService messagesService;
    public final EmailService emailService;


    public ContactController(MessagesService messagesService, EmailService emailService) {
        this.messagesService = messagesService;
        this.emailService = emailService;
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
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("selectedPage", "contact");
            return "contact/form";
        }

        try {
            messagesService.save(messageDTO);
            emailService.sendContactEmail(messageDTO);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Message send successfully.");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error sending the message.");
        }

        return "redirect:/contact";
    }

}
