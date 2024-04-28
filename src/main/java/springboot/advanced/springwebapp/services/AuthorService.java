package springboot.advanced.springwebapp.services;

import springboot.advanced.springwebapp.domain.Author;

public interface AuthorService {

    Iterable<Author> findAll();
}
