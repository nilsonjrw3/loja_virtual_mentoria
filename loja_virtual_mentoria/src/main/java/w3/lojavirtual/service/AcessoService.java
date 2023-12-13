package w3.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import w3.lojavirtual.model.Acesso;
import w3.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {

	
	@Autowired
	private AcessoRepository acessoRepository;
	
	
	public Acesso save(Acesso acesso) {
		
		return acessoRepository.save(acesso);
		
	}
}
