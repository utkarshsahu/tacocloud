package tacos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	@Autowired
	private IngredientRepository ingredientRepo;
	
	@Autowired
	private TacoRepository designRepo;
	
	private ObjectMapper objectMapper;
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@GetMapping
	public String showDesignForm(Model model) {
		
/*	    List<Ingredient> ingredients = Arrays.asList(
	    	      new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
	    	      new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
	    	      new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
	    	      new Ingredient("CARN", "Carnitas", Type.PROTEIN),
	    	      new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
	    	      new Ingredient("LETC", "Lettuce", Type.VEGGIES),
	    	      new Ingredient("CHED", "Cheddar", Type.CHEESE),
	    	      new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
	    	      new Ingredient("SLSA", "Salsa", Type.SAUCE),
	    	      new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
	    	    );
*/
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
	    Type[] types = Ingredient.Type.values();
	    for(Type type : types) {
	    	model.addAttribute(type.toString().toLowerCase(), ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList()));
	    }
	    model.addAttribute("design", new Taco());
	    return "design";
	}
	
	@PostMapping
	public String processDesign( @ModelAttribute Order order, 
								@ModelAttribute(value = "design") @Valid Taco design,
								 Errors errors) {
		if(errors.hasErrors()) {
			log.info("errors");
			return "redirect:/design";
		}
		
		log.info("Processing design: " + design);
		Taco saved = designRepo.save(design);
		order.addTaco(saved);
		
		return "redirect:/orders/current";
	}
}
