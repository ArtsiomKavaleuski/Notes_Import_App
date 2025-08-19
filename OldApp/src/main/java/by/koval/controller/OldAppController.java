package by.koval.controller;

import by.koval.dto.ReqParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OldAppController {
    @PostMapping("/clients")
    public String getClients() {
        return null;
    }

    @PostMapping("/notes")
    public String getNotes(@RequestBody ReqParam requestParam) {
        return null;
    }
}
