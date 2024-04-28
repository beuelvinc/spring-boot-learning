package springboot.advanced.springwebapp.services;

import springboot.advanced.springwebapp.domain.Book;

public interface BookService {

    Iterable<Book> findAll();
}
