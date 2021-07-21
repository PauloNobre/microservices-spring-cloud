package br.com.alura.microservice.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.dto.CompraDTO;
import br.com.alura.microservice.loja.dto.InfoFornecedorDTO;
import br.com.alura.microservice.loja.dto.InfoPedidoDTO;
import br.com.alura.microservice.loja.model.Compra;

@Service
public class CompraService {
	
	/*
	 * @Autowired private RestTemplate client;
	 * 
	 * @Autowired private DiscoveryClient eurekaClient;
	 */
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	@Autowired
	private FornecedorClient fornecedorClient;

	public Compra realizaCompra(CompraDTO compra) {
		/*
		 * ResponseEntity<InfoFornecedorDTO> exchange = client.exchange(
		 * "http://fornecedor/info/" + compra.getEndereco().getEstado(), HttpMethod.GET,
		 * null, InfoFornecedorDTO.class);
		 * 
		 * this.eurekaClient.getInstances("fornecedor").stream().forEach(fornecedor -> {
		 * System.out.println("localhost:" + fornecedor.getPort()); });
		 * 
		 * System.out.println(exchange.getBody().getEndereco());
		 */
		
		final String estado = compra.getEndereco().getEstado();
		LOG.info("Buscando informações do fornecedor de {}", estado);
		InfoFornecedorDTO info = this.fornecedorClient.getInfoPorEstado(estado);
		LOG.info("Informações recebidas do endereço do fornecedor {}", info.getEndereco());
		
		LOG.info("Realizando um pedido");
		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());
		
		Compra compraSalva = new Compra();
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		return compraSalva;
	}
}
