package cryobank.nitrogenSensor.service;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event.ID;

import com.fasterxml.jackson.databind.ObjectMapper;

import cryobank.nitrogenSensor.dto.SensorNitrogenDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SensorNitrogenGenerator {
// этот класс должен открыть output канал, чтобы отправить значение в Кафку
	// вернее он должен сформировать Bean Supplier
	int sNumber; // тут мы должны пройтись по все нашим датчикам и что-то отправить
	ObjectMapper mapper = new ObjectMapper();// чтобы превратить объект в JSON

	@Autowired
	ISensorNitrogenGetData gen;// = new SensorNitrogenGetDataImpl();
	
    @Autowired
    StreamBridge streamBridge;
    
	@Value("${min_value:30}")
	private int minValue;
	@Value("${max_value:250}")
	private int maxValue;
	
	@Value("${app.sensor.alarmproducer.binding.name:alarm_producer-out-0}")
	String bindingName;
	
	//for test
	boolean b = true;
	
	@Bean
	// типизируем Supplier-а тем что он будет отправлять в Кафку - String-ом, потому что JSON
	public Supplier<String> sendSensorData() {
		//здесь мы должны вернуть функцию, которая ничего не принимает, но что-то делает
		return () ->
		{
			SensorNitrogenDto data;
			if (b == true)
			{
				data = gen.getSensorNitrogenData(sNumber++);
				b = false;
			}
			else
			{
				data = gen.getSensorAlarmNitrogenData(sNumber++);
				b = true;
			}
			
			// perhaps alarm output
			if (data.nitrogen_level_value > maxValue || data.nitrogen_level_value < minValue)
			{
				log.trace("SensorNitrogen ALARM really sent data for sensorId {}", data.sensorID);
			   streamBridge.send(bindingName, data);
			}
			
			// our standard output
			try {
				log.trace("SensorNitrogen really sent data for sensorId {}", data.sensorID);
				return mapper.writeValueAsString(data);
			} catch (Exception e)
			{
				return null;
			}
		};
	}

	
	
}
