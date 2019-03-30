package com.yuuitanabe.pipres.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.yuuitanabe.pipres.bmp180.BMP180Lib;

@RestController
@EnableAutoConfiguration

@RequestMapping(value = "/", method = RequestMethod.GET)
public class WebAPIController {
	@GetMapping(params = "pressure")
	@ResponseBody
	List<String> pressure() {
		Date date = new Date();
		List<String> list = new ArrayList<String>();
		try {
			list.add(date.toString());
			list.add(String.valueOf(BMP180Lib.pressure()));
			// catch all the exceptions that throwed.
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
	// API Mapping that output Altitude
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