package cryobank.nitrogenSensor.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cryobank.nitrogenSensor.dto.SensorNitrogenDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SensorNitrogenGetDataImpl implements ISensorNitrogenGetData {

	@Value("${min_value:30}")
	private int minValue;
	@Value("${max_value:250}")
	private int maxValue;
	
	@Value("${app.myTest:100}") // в тесте у нас зашито 2, а в продакшене можно конфигурировать
    int myTest;
	
	@Override
	public SensorNitrogenDto getSensorNitrogenData(int sensorID) {
		log.trace("SensorNitrogen sent data for sensorId {}", sensorID);
	    System.out.println("myTest" + myTest);
		return getRandomSensorData(sensorID);
	}
	
	private SensorNitrogenDto getRandomSensorData(int sensorID) {
		long timestamp = System.currentTimeMillis();
		int value = getRandomNumber(minValue, maxValue);
		SensorNitrogenDto data = new SensorNitrogenDto(timestamp, value);
		data.sensorID = sensorID;
		return data;
	}

	private int getRandomNumber(int min, int max) {
		return new Random().ints(1, min, max+1).findFirst().getAsInt();
	}

}
