package is.fistlab.services.impl;

import is.fistlab.database.entities.History;
import is.fistlab.database.repositories.HistoryRepository;
import is.fistlab.services.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public History getHistory() {
        return null;
    }

    @Override
    public History add(History history) {
        return historyRepository.save(history);
    }
}
