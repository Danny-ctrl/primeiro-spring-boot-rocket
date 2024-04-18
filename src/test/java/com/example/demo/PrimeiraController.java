package com.example.demo;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraController")
public class PrimeiraController {

	@GetMapping("/primeiroMetodo/{id}")
	public String primeiroMetodo(@PathVariable String id) {
		return "O parametro é " + id;
	}

	@GetMapping("metodoComQueryParams")
	public String metodoComQueryParams(@RequestParam String id) {
		return "O parametro com metodoComParam é " + id;
	}

	@GetMapping("metodoComQueryParams2")
	public String metodoComQueryParams2(@RequestParam Map<String, String> allParams) {
		return "O parametro com metodoComParam2 é " + allParams.entrySet();
	}
}
