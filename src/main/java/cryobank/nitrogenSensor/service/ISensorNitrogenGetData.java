package cryobank.nitrogenSensor.service;

import cryobank.nitrogenSensor.dto.SensorNitrogenDto;

public interface ISensorNitrogenGetData {
	SensorNitrogenDto getSensorNitrogenData(int sensorID);
}
