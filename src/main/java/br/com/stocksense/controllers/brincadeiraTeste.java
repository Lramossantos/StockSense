package br.com.stocksense.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class brincadeiraTeste {
	@GetMapping("/testeBrincadeira")
	public ModelAndView testeBrincadeira() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("brincadeira/testeBrincadeira");			
		return mv;		
	}
}
