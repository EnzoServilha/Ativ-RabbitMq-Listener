package school.sptech.RabbitMq_Listener.messaging;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdemServicoCriadaMessage(
		String evento,
		Long ordemServicoId,
		String nomeCliente,
		String modeloCarro,
		String placa,
		String descricaoProblema,
		String mecanicoResponsavel,
		BigDecimal valorEstimado,
		String status,
		LocalDateTime criadaEm
) {
}
