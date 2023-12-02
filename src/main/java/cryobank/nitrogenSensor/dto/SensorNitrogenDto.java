package cryobank.nitrogenSensor.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SensorNitrogenDto {
	public long timestamp;
	public int sensorID;
	public int nitrogen_level_value;
	
	public SensorNitrogenDto(long timestamp, int nitrogen_level_value) {
		super();
		this.timestamp = timestamp;
		this.nitrogen_level_value = nitrogen_level_value;
	}
}
