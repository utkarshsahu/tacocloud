package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
//	Iterable<Ingredient> findAll();
	
//	Ingredient findOne(String id);
	
//	Ingredient save(Ingredient ingredient);

//	Ingredient findByName(String name);
}
