package is.fistlab.services;

import is.fistlab.database.entities.Operation;

import java.util.List;

public interface OperationService {
    List<Operation> addAll(List<Operation> operations);
    Operation add(Operation operation);
}
