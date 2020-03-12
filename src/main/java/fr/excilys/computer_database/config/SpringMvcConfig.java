package fr.excilys.computer_database.config;

import org.springframework.web.bind.annotation.GetMapping;

public class SpringMvcConfig {
	@GetMapping("/dashboard")
    public String showForm() {
        return "sample";
    }
}
