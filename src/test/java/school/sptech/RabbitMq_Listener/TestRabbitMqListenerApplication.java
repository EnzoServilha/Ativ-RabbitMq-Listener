package school.sptech.RabbitMq_Listener;

import org.springframework.boot.SpringApplication;

public class TestRabbitMqListenerApplication {

	public static void main(String[] args) {
		SpringApplication.from(RabbitMqListenerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
