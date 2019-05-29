package tacos;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {
	
	private long id;
	
	private Date createdAt;
	
	@NotNull
	private String name;
	
	@Size(min = 1, message = "You must choose at least one ingredient")
	private List<String> ingredients;
}
