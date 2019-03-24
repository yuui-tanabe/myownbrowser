package com.yuuitanabe.pipres;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

@Controller
@EnableAutoConfiguration
@EnableOAuth2Sso

public class OwnController {
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	@ResponseBody
	public void error() {}
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public void logout() {}
	@RequestMapping(value = "/pressure", method = RequestMethod.GET)
	@ResponseBody
	public String top(@RequestParam(value="name", required=false) String name) throws IOException, UnsupportedBusNumberException, InterruptedException {
		return BMP180Run.pressure();
	}
}

