package com.yuuitanabe.pipres;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

@Controller
@EnableAutoConfiguration

public class OwnController{

	@RequestMapping("/Pressure")
	@ResponseBody
	public List<String> top(@RequestParam(value="name", required=false) String name) {
		List<String> list = new ArrayList<String>();
		try {
			list.add(BMP180Run.pressure());
		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		} catch (UnsupportedBusNumberException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
		return list;
	}

}
