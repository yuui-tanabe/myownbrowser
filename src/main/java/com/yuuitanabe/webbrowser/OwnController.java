package com.yuuitanabe.webbrowser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration

public class OwnController {

	@RequestMapping("/hello")
	@ResponseBody
	public List<String> top(@RequestParam(value="name", required=false) String name) {
		List<String> list = new ArrayList<String>();
		list.add("spring");
		return list;
	}

}
