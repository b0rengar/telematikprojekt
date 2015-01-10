package com.tds.obdImpl;

import com.tds.obd.IOBDService;

public class OBDParser {

    String resultText;

    public float getFloat(String line, String type) {
        resultText = line.substring(line.lastIndexOf(type) + 4, line.lastIndexOf(type) + 6);
        System.out.println("result for int = #" + resultText + "#");
        int number = Integer.parseInt(resultText, 16);
        float result = 0;
        if (IOBDService.TYPE_CONSUMPTION_RESULT.equals(type)) {
            result = number * 0.05f;
        }
        if (IOBDService.TYPE_SPEED_RESULT.equals(type)) {
            result = number;
        }
        if (IOBDService.TYPE_TEMPERATURE_ENGINE_RESULT.equals(type)) {
            result = number - 40;
        }
        if (IOBDService.TYPE_TEMPERATURE_INDOOR_RESULT.equals(type)) {
            result = number - 40;
        }
        if (IOBDService.TYPE_TEMPERATURE_OUTDOOR_RESULT.equals(type)) {
            result = number - 40;
        }

        return result;
    }

    public int getInt(String line, String type) {
        int result = 0;
        resultText = line.substring(line.lastIndexOf(type) + 4, line.lastIndexOf(type) + 8);
        System.out.println("result for int = #" + resultText + "#");
        int number = Integer.parseInt(resultText, 16);
        if (IOBDService.TYPE_RPM_RESULT.equals(type)) {
            result = number / 4;
        }

        return result;
    }
}
