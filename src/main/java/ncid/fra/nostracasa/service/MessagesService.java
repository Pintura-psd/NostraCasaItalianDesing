package ncid.fra.nostracasa.service;

import jakarta.validation.Valid;
import ncid.fra.nostracasa.dto.MessagesDTO;
import ncid.fra.nostracasa.model.Messages;
import ncid.fra.nostracasa.repository.MessagesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;

    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public List<Messages> findAll(){
        return messagesRepository.findAll();
    }

    public Messages findById(Long id){
        return messagesRepository.findById(id).orElse(null);
    }

    public Messages save(Messages messages){
        return messagesRepository.save(messages);
    }

    //Concersión a entidad
    public void save(MessagesDTO dto) {

        Messages message = new Messages();

        message.setName(dto.getName());
        message.setEmail(dto.getEmail());
        message.setPhone(dto.getPhone());
        message.setSubject(dto.getSubject());
        message.setMessageText(dto.getMessageText());
        message.setSentAt(LocalDateTime.now());

        messagesRepository.save(message);
    }


}
