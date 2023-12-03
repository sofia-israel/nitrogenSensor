package cryobank.nitrogenSensor;

import com.fasterxml.jackson.databind.ObjectMapper;
import cryobank.nitrogenSensor.dto.SensorNitrogenDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestChannelBinderConfiguration.class)
@SpringBootTest
class NitrogenSensorApplicationTests {

	@Value("${app.myTest:100}") // в тесте у нас зашито 2, а в продакшене можно конфигурировать
    int myTest;

	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	
	@Value("${min_value}")
	private int minValue;
	@Value("${max_value}")
	private int maxValue;

	private String producerBindingName = "sendSensorData-out-0";
	private String consumerBindingName = "sendSensorData-out-0";
	
	@Test
	void contextLoads()
	{
	//    System.out.println("myTest" + myTest);
	}

	@Test
	void sendSensorDataTest() throws IOException {
		SensorNitrogenDto testData = new SensorNitrogenDto(System.currentTimeMillis(), 100);
		//producer.send(new GenericMessage<SensorNitrogenDto>(testData), consumerBindingName);

		Message<byte[]> message = consumer.receive(100, producerBindingName ); // чтобы проверить что вернет
		ObjectMapper mapper = new ObjectMapper();
		SensorNitrogenDto res = mapper.readValue(message.getPayload(), SensorNitrogenDto.class);
		assertTrue(res.sensorID == 0);
		assertTrue(res.nitrogen_level_value <= maxValue);
		assertTrue(minValue <= res.nitrogen_level_value);
	}

}
