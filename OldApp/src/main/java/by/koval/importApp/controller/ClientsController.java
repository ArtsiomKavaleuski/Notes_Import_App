package by.koval.importApp.controller;

import by.koval.importApp.model.Client;
import by.koval.importApp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientsController {
    private final ClientRepository clientRepository;

    @GetMapping("/clients")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/clients")
    public List<Client> getClients() {
        return clientRepository.findAll();
    }
}
