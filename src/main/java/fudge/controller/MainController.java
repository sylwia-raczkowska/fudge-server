package fudge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class MainController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}

}
