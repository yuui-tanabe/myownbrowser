package com.yuuitanabe.pipres.bmp180;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

public class BMP180Drv {
    // Hardware pressure sampling accuracy modes
    public static final int ULTRALOWPOWER = 0;
    public static final int STANDARD      = 1;
    public static final int HIGHRES       = 2;
    public static final int ULTRAHIGHRES  = 3;

    private int mode;

    // Registers
    private static final int CAL_AC1   = 0xAA;
    private static final int CTRL_MEAS = 0xF4;
    private static final int OUT_MSB   = 0xF6;

    // Commands
    private static final byte CMD_READTEMP     = 0x2E;
    private static final byte CMD_READPRESSURE = 0x34;

    private static final int I2C_BUS     = I2CBus.BUS_1;
    private static final int I2C_ADDRESS = 0x77;
    private I2CDevice device;

    private int AC1;
    private int AC2;
    private int AC3;
    private int AC4;
    private int AC5;
    private int AC6;
    private int B1 ;
    private int B2 ;
    private int MB ;
    private int MC ;
    private int MD ;

    private double standardSeaLevelPressure = 1013.89; // avarage sea level pressure in Tokyo

    public BMP180Drv(int i2cBus, int i2cAddress, int mode) throws UnsupportedBusNumberException, IOException {
        // Create I2C bus
        I2CBus bus = I2CFactory.getInstance(i2cBus);

        // Get I2C device
        device = bus.getDevice(i2cAddress);

        // Calibration Coefficients stored in EEPROM of the device
        // Read 22 bytes of data from address 0xAA(170)
        byte[] data = new byte[22];
        device.read(CAL_AC1, data, 0, data.length);

        // Convert the data
        AC1 = INT (data[ 0], data[ 1]);
        AC2 = INT (data[ 2], data[ 3]);
        AC3 = INT (data[ 4], data[ 5]);
        AC4 = UINT(data[ 6], data[ 7]);
        AC5 = UINT(data[ 8], data[ 9]);
        AC6 = UINT(data[10], data[11]);
        B1  = INT (data[12], data[13]);
        B2  = INT (data[14], data[15]);
        MB  = INT (data[16], data[17]);
        MC  = INT (data[18], data[19]);
        MD  = INT (data[20], data[21]);

        this.mode = mode;
    }

    public BMP180Drv(int mode) throws UnsupportedBusNumberException, IOException {
        this(I2C_BUS, I2C_ADDRESS, mode);
    }

    public BMP180Drv() throws UnsupportedBusNumberException, IOException {
        this(BMP180Drv.STANDARD);
    }

    private int readAndCalcB5() throws IOException, InterruptedException {
        // Select measurement control register
        // Enable temperature measurement
        device.write(CTRL_MEAS, CMD_READTEMP);
        TimeUnit.MILLISECONDS.sleep(5);

        // Read 2 bytes of data from address 0xF6(246)
        // temp msb, temp lsb
        byte[] data = new byte[2];
        device.read(OUT_MSB, data, 0, data.length);

        // Convert the data
        int UT = UINT(data[0], data[1]);

        // Callibration for Temperature
        int X1 = ((UT - AC6) * AC5) >> 15;
        int X2 = (MC << 11) / (X1 + MD);
        int B5 = X1 + X2;

        return B5;
    }

    public double readTemperature() throws IOException, InterruptedException {
        return ((readAndCalcB5() + 8) >> 4) / 10.0;
    }

    public static double convertToFahrenheit(double c) {
        return c * 1.8 + 32.0;
    }

    public double readPressure() throws IOException, InterruptedException {
        // Select measurement control register
        // Enable pressure measurement
        device.write(CTRL_MEAS, (byte)(CMD_READPRESSURE + (mode << 6)));
        switch (mode) {
        case ULTRALOWPOWER:
            TimeUnit.MILLISECONDS.sleep(5);
            break;
        case STANDARD:
            TimeUnit.MILLISECONDS.sleep(8);
            break;
        case HIGHRES:
            TimeUnit.MILLISECONDS.sleep(14);
            break;
        default:
            TimeUnit.MILLISECONDS.sleep(26); // ULTRAHIGHRES mode
            break;
        }

        // Read 3 bytes of data from address 0xF6(246)
        // pres msb1, pres msb, pres lsb
        byte[] data = new byte[3];
        device.read(OUT_MSB, data, 0, data.length);

        int UP = UINT(data[0], data[1], data[2]) >> (8 - mode);

        // Calibration for Pressure
        int B6 = readAndCalcB5() - 4000;
        int X1 = (B2 * (B6 * B6) >> 12) >> 11;
        int X2 = (AC2 * B6) >> 11;
        int X3 = X1 + X2;
        int B3 = (((AC1 * 4 + X3) << mode) + 2) / 4;

        X1 = (AC3 * B6) >> 13;
        X2 = (B1 * ((B6 * B6) >> 12)) >> 16;
        X3 = ((X1 + X2) + 2) >> 2;
        int B4 = (AC4 * (X3 + 32768)) >> 15;
        int B7 = (UP - B3) * (50000 >> mode);

        int p = B7 < 0x80000000 ? (B7 * 2) / B4 : (B7 / B4) * 2;

        X1 = (p >> 8) * (p >> 8);
        X1 = (X1 * 3038) >> 16;
        X2 = (-7357 * p) >> 16;
        p = p + ((X1 + X2 + 3791) >> 4);

        return p / 100.0;
    }

    public void setStandardSeaLevelPressure(double standardSeaLevelPressure) {
        this.standardSeaLevelPressure = standardSeaLevelPressure;
    }

    public double readAltitude() throws IOException, InterruptedException {
        // Calculates the altitude in meters
        double pressure = readPressure();
        return 44330.0 * (1.0 - Math.pow(pressure / standardSeaLevelPressure, 0.1903));
    }

    private int INT(byte msb, byte lsb) {
        int hi = msb & 0xFF;
        return ((hi > 127 ? hi - 256 : hi) << 8) + (lsb & 0xFF);
    }

    private int UINT(byte msb, byte lsb) {
        return ((msb & 0xFF) << 8) + (lsb & 0xFF);
    }

    private int UINT(byte msb, byte lsb, byte xlsb) {
        return ((msb & 0xFF) << 16) + UINT(lsb, xlsb);
    }
}