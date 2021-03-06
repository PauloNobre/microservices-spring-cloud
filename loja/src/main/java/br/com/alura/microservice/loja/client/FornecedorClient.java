package br.com.alura.microservice.loja.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.alura.microservice.loja.dto.InfoFornecedorDTO;
import br.com.alura.microservice.loja.dto.InfoPedidoDTO;
import br.com.alura.microservice.loja.dto.ItemDaCompraDTO;

@FeignClient("fornecedor")
public interface FornecedorClient {

	@GetMapping("/info/{estado}")
	public InfoFornecedorDTO getInfoPorEstado(@PathVariable String estado);

	@PostMapping("/pedido")
	public InfoPedidoDTO realizaPedido(@RequestBody List<ItemDaCompraDTO> list);
}
