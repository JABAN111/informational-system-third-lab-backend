package is.fistlab.services.impl;

import is.fistlab.database.entities.Operation;
import is.fistlab.database.repositories.OperationRepository;
import is.fistlab.services.OperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;


    @Override
    public List<Operation> addAll(List<Operation> operations) {
        return List.of();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Operation add(Operation operation) {
        return operationRepository.save(operation);
    }


}
