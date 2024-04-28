package springboot.advanced.springwebapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import springboot.advanced.springwebapp.domain.Author;

public interface AuthorRepository extends CrudRepository<Author,Long> {
}
