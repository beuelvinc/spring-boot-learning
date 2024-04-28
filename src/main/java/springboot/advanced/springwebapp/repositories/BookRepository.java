package springboot.advanced.springwebapp.repositories;
import org.springframework.data.repository.CrudRepository;
import springboot.advanced.springwebapp.domain.Book;

public interface BookRepository extends CrudRepository<Book,Long> {
}
