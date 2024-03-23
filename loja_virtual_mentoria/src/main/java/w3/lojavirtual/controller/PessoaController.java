package w3.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import w3.lojavirtual.ExceptionMentoriaJava;
import w3.lojavirtual.model.Endereco;
import w3.lojavirtual.model.PessoaFisica;
import w3.lojavirtual.model.PessoaJuridica;
import w3.lojavirtual.model.dto.CepDTO;
import w3.lojavirtual.repository.EnderecoRepository;
import w3.lojavirtual.repository.PessoaFisicaRepository;
import w3.lojavirtual.repository.PessoaRepository;
import w3.lojavirtual.service.PessoaUserService;
import w3.lojavirtualw3.util.ValidaCNPJ;
import w3.lojavirtualw3.util.ValidaCPF;

@RestController
public class PessoaController {
	
	@Autowired
	private PessoaRepository pesssoaRepository;
	
	@Autowired
	private PessoaFisicaRepository pesssoaFisicaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	@ResponseBody
	@GetMapping(value = "/consultaPfNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome){
		
	 List<PessoaFisica> fisicas = pesssoaFisicaRepository.pesquisaPorNomePF(nome.trim().toUpperCase());
	 
	 return new ResponseEntity<List<PessoaFisica>>(fisicas,HttpStatus.OK);
		
	}
	

	@ResponseBody
	@GetMapping(value = "/consultaPfCpf/{cpf}")
	public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf){
		
	 List<PessoaFisica> fisicas = pesssoaFisicaRepository.pesquisaPorCpfPF(cpf.trim().toUpperCase());
	 
	 return new ResponseEntity<List<PessoaFisica>>(fisicas,HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "/consultaCnpjPj/{cnpj}")
	public ResponseEntity<List<PessoaJuridica>> consultaCnpjPj(@PathVariable("cnpj") String cnpj){
		
	 List<PessoaJuridica> juridicas = pesssoaRepository.existeCnpjCadastradoList(cnpj.trim().toUpperCase());
	 
	 return new ResponseEntity<List<PessoaJuridica>>(juridicas,HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "/consultaNomePJ/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaNomePj(@PathVariable("nome") String nome){
		
	 List<PessoaJuridica> juridicas = pesssoaRepository.pesquisaPorNomePJ(nome.trim().toUpperCase());
	 
	 return new ResponseEntity<List<PessoaJuridica>>(juridicas,HttpStatus.OK);
		
	}
	
	
	@ResponseBody
	@GetMapping(value = "/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep){
		
	  return new ResponseEntity<CepDTO>(pessoaUserService.consultaCep(cep), HttpStatus.OK);
		
	}
	
	/*end-point é microsservicos é um API*/
	@ResponseBody
	@PostMapping(value = "/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{
		
		if (pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Pessoa juridica nao pode ser NULL");
		}
		
		if (pessoaJuridica.getId() == null && pesssoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
		}
		
		if (pessoaJuridica.getId() == null && pesssoaRepository.existeInsEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoriaJava("Já existe Inscricao estadual cadastrada com o número: " + pessoaJuridica.getInscEstadual());
		}
		
		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("CNPJ : " + pessoaJuridica.getCnpj()+" esta invalido");
		}
		
		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				
				CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
				
				pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUF(cepDTO.getUf());
				
			}
		}else {
			
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				
				Endereco enderecoTemp =  enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
				
				if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
					
					CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
					
					pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUF(cepDTO.getUf());
				}
			}
		}
		
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava{
		
		if (pessoaFisica == null) {
			throw new ExceptionMentoriaJava("Pessoa Fisica nao pode ser NULL");
		}
		
		if (pessoaFisica.getId() == null && pesssoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
			throw new ExceptionMentoriaJava("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
		}
		
		
		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("CPF : " + pessoaFisica.getCpf()+" esta invalido");
		}
		
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
	}

}