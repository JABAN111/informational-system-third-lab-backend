package is.fistlab.controllers;

import is.fistlab.database.repositories.LocationRepository;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.services.AsyncService;
import is.fistlab.services.impl.AsyncServiceImpl;
import is.fistlab.services.impl.AuthServiceImpl;
import is.fistlab.services.impl.ssyncProcessing.AsyncProcessing;
import is.fistlab.utils.AuthenticationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/import")
@AllArgsConstructor
@Slf4j
public class ImportController {
    private final AsyncService importService;
    private final StudyGroupRepository studyGroupRepository;
    private final LocationRepository locationRepository;
    private final PersonRepository personRepository;
    private final AsyncProcessing asyncProcessing;
    private final AuthServiceImpl authServiceImpl;
    private final AsyncServiceImpl asyncServiceImpl;

    @PostMapping("/csv")
    public ResponseEntity<Response<Integer>> importStudyGroups(@RequestParam("file") MultipartFile file){
        try {
            String fileName = file.getOriginalFilename();
            var fil = asyncServiceImpl.readFile(file.getInputStream());
//            var result = importService.importStudyGroups(file.getInputStream());
            asyncProcessing.processAsync(fil, authServiceImpl.getCurrentUser());
            return ResponseEntity.ok(new Response<>("Сохранение начали"));
//                            result)
        }catch (Exception e){
            log.error(e.getMessage());
            throw new NotImplementedException("csv");

        }
    }


    @PostMapping("/drop")
    public void dropAll(){
        studyGroupRepository.deleteAll();
        personRepository.deleteAll();
        locationRepository.deleteAll();
    }
}


