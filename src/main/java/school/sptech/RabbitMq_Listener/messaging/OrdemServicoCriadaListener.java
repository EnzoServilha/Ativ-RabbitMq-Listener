package school.sptech.RabbitMq_Listener.messaging;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
public class OrdemServicoCriadaListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrdemServicoCriadaListener.class);

	private final ObjectMapper objectMapper;

	public OrdemServicoCriadaListener(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@RabbitListener(queues = "${app.rabbitmq.queue}")
	public void consumirOrdemServicoCriada(Message message) {
		String payload = new String(message.getBody(), StandardCharsets.UTF_8);
		try {
			OrdemServicoCriadaMessage ordemServicoCriada = objectMapper.readValue(payload, OrdemServicoCriadaMessage.class);
			LOGGER.info(
					"Ordem de servico criada recebida: id={}, cliente={}, veiculo={}, placa={}, status={}, mecanico={}, valor={}",
					ordemServicoCriada.ordemServicoId(),
					ordemServicoCriada.nomeCliente(),
					ordemServicoCriada.modeloCarro(),
					ordemServicoCriada.placa(),
					ordemServicoCriada.status(),
					ordemServicoCriada.mecanicoResponsavel(),
					ordemServicoCriada.valorEstimado()
			);
		} catch (JacksonException ex) {
			throw new AmqpRejectAndDontRequeueException("Mensagem de ordem de servico criada invalida.", ex);
		}
	}
}
