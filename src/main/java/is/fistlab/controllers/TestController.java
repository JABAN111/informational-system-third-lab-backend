package is.fistlab.controllers;

import is.fistlab.database.entities.History;
import is.fistlab.services.Test;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/all")
@AllArgsConstructor
public class TestController {
    private final Test test;

    @GetMapping("/data")
    public CompletableFuture<List<History>> getAllHistory(){
        return test.getAllHistoryAsync();
    }
}
