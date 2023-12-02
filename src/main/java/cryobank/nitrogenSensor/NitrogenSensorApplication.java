package cryobank.nitrogenSensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NitrogenSensorApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext cac = SpringApplication.run(NitrogenSensorApplication.class, args);
		
		// мы хотим чтобы приложение работало пол минуты, сгенерило столько сигналов, сколько 
		// успеет за это время сгенерить, и потом закрылось
		Thread.sleep(30000);
		cac.close();
	}

}
