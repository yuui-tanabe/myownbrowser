package com.yuuitanabe.pipres;

import java.io.IOException;
import java.util.Collection;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

public class BMP180Run {
	public static String pressure() throws IOException, UnsupportedBusNumberException, InterruptedException {
        BMP180 bmp180 = new BMP180(1, 0x77, BMP180.STANDARD );// use I2C bus 1, standard mode
    	// BMP180 bmp180 = new BMP180(BMP180.ULTRAHIGHRES);   // use I2C bus 1, ultra high resolution mode
    	// BMP180 bmp180 = new BMP180(BMP180.HIGHRES);        // use I2C bus 1, high resolution mode
    	// BMP180 bmp180 = new BMP180(BMP180.STANDARD);       // use I2C bus 1, standared mode
    	// BMP180 bmp180 = new BMP180(BMP180.ULTRALOWPOWER);  // use I2C bus 1, ultra low power mode
    	String pressure = String.valueOf(bmp180.readPressure());
    	return pressure;
	}
}