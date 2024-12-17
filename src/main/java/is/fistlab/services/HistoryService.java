package is.fistlab.services;

import is.fistlab.database.entities.History;

public interface HistoryService {

    History getHistory();
    History add(History history);

}
