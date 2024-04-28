package springboot.advanced.springwebapp.services;

import org.springframework.stereotype.Service;
import springboot.advanced.springwebapp.domain.Book;
import springboot.advanced.springwebapp.repositories.BookRepository;


@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }
}
