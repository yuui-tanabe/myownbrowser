package com.yuuitanabe.pipres;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

@RestController
@EnableAutoConfiguration

@RequestMapping(value = "/", method = RequestMethod.GET)
public class WebAPIController {
	@GetMapping(params = "pressure")
	@ResponseBody
	List<String> pressure() {
		List<String> list = new ArrayList<String>();
		try {
			list.add(String.valueOf(BMP180Lib.pressure()));
		} catch(IOException | InterruptedException | UnsupportedBusNumberException e) {
			e.printStackTrace();
		}
		return list;
	}
	@GetMapping(params = "temperature")
	@ResponseBody
	List<String> temp() {
		List<String> list = new ArrayList<String>();
		try {
			list.add(String.valueOf(BMP180Lib.temp()));
		} catch(IOException | InterruptedException | UnsupportedBusNumberException e) {
			e.printStackTrace();
		}
		return list;
	}
	@GetMapping(params = "altitude")
	@ResponseBody
	List<String> alt() {
		List<String> list = new ArrayList<String>();
		try {
			list.add(String.valueOf(BMP180Lib.alt()));
		} catch(IOException | InterruptedException | UnsupportedBusNumberException e) {
			e.printStackTrace();
		}
		return list;
	}
}