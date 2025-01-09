package is.fistlab.services.impl;

import is.fistlab.database.entities.Coordinates;
import is.fistlab.database.repositories.CoordinatesRepository;
import is.fistlab.services.CoordinateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor
@Slf4j
@Service
@Transactional
public class CoordinatesServiceImpl implements CoordinateService {

    private final CoordinatesRepository coordinatesRepository;

    @Override
    public List<Coordinates> addAll(List<Coordinates> coordinatesList) {
        return coordinatesRepository.saveAll(coordinatesList);
    }
}
