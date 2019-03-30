package com.yuuitanabe.pipres.bmp180;

import java.io.IOException;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

public class BMP180Lib {
	public static double pressure() throws UnsupportedBusNumberException, InterruptedException, IOException {
        BMP180Drv bmp180 = new BMP180Drv();                         // use I2C bus 1, standard mode
    	// BMP180 bmp180 = new BMP180(BMP180.ULTRAHIGHRES);   // use I2C bus 1, ultra high resolution mode
    	// BMP180 bmp180 = new BMP180(BMP180.HIGHRES);        // use I2C bus 1, high resolution mode
    	// BMP180 bmp180 = new BMP180(BMP180.STANDARD);       // use I2C bus 1, standared mode
    	// BMP180 bmp180 = new BMP180(BMP180.ULTRALOWPOWER);  // use I2C bus 1, ultra low power mode
    	double pressure = bmp180.readPressure();
    	return pressure;
	}
	public static double temp() throws UnsupportedBusNumberException, InterruptedException, IOException {
        BMP180Drv bmp180 = new BMP180Drv();                         // use I2C bus 1, standard mode
    	// BMP180 bmp180 = new BMP180(BMP180.ULTRAHIGHRES);   // use I2C bus 1, ultra high resolution mode
    	// BMP180 bmp180 = new BMP180(BMP180.HIGHRES);        // use I2C bus 1, high resolution mode
    	// BMP180 bmp180 = new BMP180(BMP180.STANDARD);       // use I2C bus 1, standared mode
    	// BMP180 bmp180 = new BMP180(BMP180.ULTRALOWPOWER);  // use I2C bus 1, ultra low power mode
    	double temp = bmp180.readTemperature();
    	return temp;
	}
	public static double alt() throws UnsupportedBusNumberException, InterruptedException, IOException {
        BMP180Drv bmp180 = new BMP180Drv();                         // use I2C bus 1, standard mode
    	// BMP180 bmp180 = new BMP180(BMP180.ULTRAHIGHRES);   // use I2C bus 1, ultra high resolution mode
    	// BMP180 bmp180 = new BMP180(BMP180.HIGHRES);        // use I2C bus 1, high resolution mode
    	// BMP180 bmp180 = new BMP180(BMP180.STANDARD);       // use I2C bus 1, standared mode
    	// BMP180 bmp180 = new BMP180(BMP180.ULTRALOWPOWER);  // use I2C bus 1, ultra low power mode
    	double alt = bmp180.readAltitude();
    	return alt;
	}
}