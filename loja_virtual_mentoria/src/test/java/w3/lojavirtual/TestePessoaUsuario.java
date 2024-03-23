package w3.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import w3.lojavirtual.controller.PessoaController;
import w3.lojavirtual.enums.TipoEndereco;
import w3.lojavirtual.model.Endereco;
import w3.lojavirtual.model.PessoaFisica;
import w3.lojavirtual.model.PessoaJuridica;
import w3.lojavirtual.repository.PessoaRepository;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase{
	
	
	@Autowired
	private PessoaController pessoaController;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Test
	public void testCadPessoaJuridica() throws ExceptionMentoriaJava {

		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("36.638.392/0001-66");
		pessoaJuridica.setNome("admiinPJ");
		pessoaJuridica.setEmail("w3payy100@gmail.com");
		pessoaJuridica.setTelefone("345999795800");
		pessoaJuridica.setInscEstadual("365556565656665");
		pessoaJuridica.setInscMunicipal("455554565656565");
		pessoaJuridica.setNomeFantasia("544556565665");
		pessoaJuridica.setRazaoSocial("46556656566");

		pessoaController.salvarPj(pessoaJuridica);
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("556556565");
		endereco1.setComplemento("Casa cinza");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogra("Av. são joao sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUF("PR");
		endereco1.setCidade("Curitiba");
		
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Maracana");
		endereco2.setCep("7878778");
		endereco2.setComplemento("Andar 4");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("555");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogra("Av. maringá");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUF("PR");
		endereco2.setCidade("Curitiba");
		
		pessoaJuridica.getEnderecos().add(endereco2);
		pessoaJuridica.getEnderecos().add(endereco1);
		
		
		pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();
		
		assertEquals(true, pessoaJuridica.getId() > 0 );
		
		for (Endereco endereco : pessoaJuridica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaJuridica.getEnderecos().size());

		}
	
	
	@Test
	public void testCadPessoaFisica() throws ExceptionMentoriaJava {
		
		PessoaJuridica pessoaJuridica = pessoaRepository.existeCnpjCadastrado("1709598312739");
		
			
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf("456.954.520-31");
		pessoaFisica.setNome("admiiiiiiin");
		pessoaFisica.setEmail("w3pay13@gmail.com");
		pessoaFisica.setTelefone("345999795800");
		pessoaFisica.setEmpresa(pessoaJuridica);

		pessoaController.salvarPf(pessoaFisica);
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("556556565");
		endereco1.setComplemento("Casa cinza");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaFisica);
		endereco1.setRuaLogra("Av. são joao sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUF("PR");
		endereco1.setCidade("Curitiba");
		
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Maracana");
		endereco2.setCep("7878778");
		endereco2.setComplemento("Andar 4");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("555");
		endereco2.setPessoa(pessoaFisica);
		endereco2.setRuaLogra("Av. maringá");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUF("PR");
		endereco2.setCidade("Curitiba");
		
		pessoaFisica.getEnderecos().add(endereco2);
		pessoaFisica.getEnderecos().add(endereco1);
		
		
		pessoaFisica = pessoaController.salvarPf(pessoaFisica).getBody();
		
		assertEquals(true, pessoaFisica.getId() > 0 );
		
		for (Endereco endereco : pessoaFisica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaFisica.getEnderecos().size());

		}
	
	
		
		
	
	}


