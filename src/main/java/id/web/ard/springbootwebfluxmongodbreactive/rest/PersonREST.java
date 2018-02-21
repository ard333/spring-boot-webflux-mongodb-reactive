package id.web.ard.springbootwebfluxmongodbreactive.rest;

import id.web.ard.springbootwebfluxmongodbreactive.model.Person;
import id.web.ard.springbootwebfluxmongodbreactive.repository.PersonRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author ardiansyah
 */
@RestController
public class PersonREST {
	
	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping(value = "/person", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Person> getAllPerson() {
		return personRepository.findAll();
	}
	
	@PostMapping(value = "/person", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ResponseEntity<Person>> createPerson(@Valid @RequestBody Person personInput) {
		return personRepository.save(personInput).map(person -> {
			return ResponseEntity.ok(person);
		}).defaultIfEmpty(
			new ResponseEntity<>(HttpStatus.NOT_FOUND)
		);
	}

	@GetMapping(value = "/person/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ResponseEntity<Person>> getPersonById(@PathVariable(value = "id") String id) {
		return personRepository.findById(id).map(person -> {
			return ResponseEntity.ok(person);
		}).defaultIfEmpty(
			new ResponseEntity<>(HttpStatus.NOT_FOUND)
		);
	}

	@PutMapping(value = "/person/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ResponseEntity<Person>> updatePerson(@PathVariable(value = "id") String id, @Valid @RequestBody Person personInput) {
		return personRepository.findById(id).flatMap(person -> {
			person.setName(personInput.getName());
			person.setDob(personInput.getDob());
			return personRepository.save(person);
		}).map(updatedPerson -> {
			return ResponseEntity.ok(updatedPerson);
		}).defaultIfEmpty(
			new ResponseEntity<>(HttpStatus.NOT_FOUND)
		);
	}

	@DeleteMapping(value = "/person/{id}")
	public Mono<ResponseEntity<Void>> deletePerson(@PathVariable(value = "id") String id) {
		return personRepository.findById(id).flatMap(person ->
			personRepository.delete(person).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
		).defaultIfEmpty(
			new ResponseEntity<>(HttpStatus.NOT_FOUND)
		);
	}
}
