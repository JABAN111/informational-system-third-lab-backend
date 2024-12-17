package is.fistlab.services;

import is.fistlab.database.entities.History;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Test {

    CompletableFuture<List<History>> getAllHistoryAsync();
}
