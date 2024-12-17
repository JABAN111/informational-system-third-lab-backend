package is.fistlab.services.impl;

import is.fistlab.database.entities.History;
import is.fistlab.database.repositories.HistoryRepository;
import is.fistlab.services.Test;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class TestImpl implements Test {
    private final HistoryRepository historyRepository;

    @Override
    @Async
    public CompletableFuture<List<History>> getAllHistoryAsync() {
        var res = historyRepository.findAll();
        historyRepository.findAll().forEach(System.out::println);

        for (int i = 0; i < 999999999; i++) {

        }

        return CompletableFuture.completedFuture(res);
    }
}
