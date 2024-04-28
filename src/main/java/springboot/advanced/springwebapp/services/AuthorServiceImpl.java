package springboot.advanced.springwebapp.services;

import org.springframework.stereotype.Service;
import springboot.advanced.springwebapp.domain.Author;
import springboot.advanced.springwebapp.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }
}
