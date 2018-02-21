package id.web.ard.springbootwebfluxmongodbreactive.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author ardiansyah
 */
@ToString @Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "person")
public class Person {
	
	@Id
	private String id;
	
	private String name;
	
	private Date dob;
	
	
}
