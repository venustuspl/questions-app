package pl.stormit.ideas.validation.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.validation.domain.ForbiddenWord;
import pl.stormit.ideas.validation.repository.ForbiddenWordRepository;

import java.util.List;

@Service
public class ForbiddenWordService {
    private ForbiddenWordRepository forbiddenWordRepository;

    public List<ForbiddenWord> getAllForbiddenWords() {
       return forbiddenWordRepository.findAll();
    }
}
