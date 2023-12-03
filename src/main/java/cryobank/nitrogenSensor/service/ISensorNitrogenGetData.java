package cryobank.nitrogenSensor.service;

import cryobank.nitrogenSensor.dto.SensorNitrogenDto;

public interface ISensorNitrogenGetData {
	SensorNitrogenDto getSensorNitrogenData(int sensorID);
	// плохие данные будем отправлять по второму каналу только для теста!
	SensorNitrogenDto getSensorAlarmNitrogenData(int sensorID);
}
