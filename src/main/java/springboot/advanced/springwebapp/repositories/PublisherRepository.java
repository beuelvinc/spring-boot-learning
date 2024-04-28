package springboot.advanced.springwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import springboot.advanced.springwebapp.domain.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher,Long> {
}
