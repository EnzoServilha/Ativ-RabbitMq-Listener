package school.sptech.RabbitMq_Listener;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import school.sptech.RabbitMq_Listener.messaging.OrdemServicoCriadaListener;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(properties = "spring.rabbitmq.listener.simple.auto-startup=false")
class RabbitMqListenerApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void deveConsumirMensagemDeOrdemServicoCriada() {
		OrdemServicoCriadaListener listener = new OrdemServicoCriadaListener(objectMapper);
		String payload = """
				{
				  "evento": "ORDEM_SERVICO_CRIADA",
				  "ordemServicoId": 1,
				  "nomeCliente": "Enzo",
				  "modeloCarro": "Honda Civic 2018",
				  "placa": "ABC1D23",
				  "descricaoProblema": "Barulho na suspensao dianteira",
				  "mecanicoResponsavel": "Toretto",
				  "valorEstimado": 450.00,
				  "status": "RECEBIDA",
				  "criadaEm": "2026-10-04T16:30:00"
				}
				""";
		Message message = MessageBuilder.withBody(payload.getBytes(StandardCharsets.UTF_8)).build();

		assertThatCode(() -> listener.consumirOrdemServicoCriada(message)).doesNotThrowAnyException();
	}

	@Test
	void deveRejeitarMensagemInvalidaSemReenfileirar() {
		OrdemServicoCriadaListener listener = new OrdemServicoCriadaListener(objectMapper);
		Message message = MessageBuilder.withBody("{".getBytes(StandardCharsets.UTF_8)).build();

		assertThatThrownBy(() -> listener.consumirOrdemServicoCriada(message))
				.isInstanceOf(AmqpRejectAndDontRequeueException.class);
	}

}
