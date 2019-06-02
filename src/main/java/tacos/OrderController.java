package tacos;

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
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepo;
	
	@GetMapping("/current")
	public String orderForm(Model model) {
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid @ModelAttribute("order") Order order, Errors errors, 
								SessionStatus status) {
		if(errors.hasErrors()) {
			log.info(errors.getAllErrors().toString());
			return "orderForm";
		}
		log.info(order.toString());
		orderRepo.save(order);
		status.setComplete();
		log.info("Order submitted: " + order);
		
		return "redirect:/";
	}
}
