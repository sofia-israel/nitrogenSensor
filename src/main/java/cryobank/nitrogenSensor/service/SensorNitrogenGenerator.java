package cryobank.nitrogenSensor.service;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event.ID;

import com.fasterxml.jackson.databind.ObjectMapper;

import cryobank.nitrogenSensor.dto.SensorNitrogenDto;

@Service
public class SensorNitrogenGenerator {
// этот класс должен открыть output канал, чтобы отправить значение в Кафку
	// вернее он должен сформировать Bean Supplier
	int sNumber; // тут мы должны пройтись по все нашим датчикам и что-то отправить
	ObjectMapper mapper = new ObjectMapper();// чтобы превратить объект в JSON

	@Autowired
	ISensorNitrogenGetData gen;// = new SensorNitrogenGetDataImpl();
	
	@Bean
	// типизируем Supplier-а тем что он будет отправлять в Кафку - String-ом, потому что JSON
	public Supplier<String> sendSensorData() {
		//здесь мы должны вернуть функцию, которая ничего не принимает, но что-то делает
		return () ->
		{
			SensorNitrogenDto data = gen.getSensorNitrogenData(sNumber++);
			try {
				return mapper.writeValueAsString(data);
			} catch (Exception e)
			{
				return null;
			}
		};
	}

	
	
}
