package id.web.ard.springbootwebfluxmongodbreactive.repository;

import id.web.ard.springbootwebfluxmongodbreactive.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ardiansyah
 */
@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String>{

}
