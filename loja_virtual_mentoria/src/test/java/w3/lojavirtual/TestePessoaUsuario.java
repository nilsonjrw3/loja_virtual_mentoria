package w3.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import w3.lojavirtual.controller.PessoaController;
import w3.lojavirtual.model.PessoaJuridica;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase{
	
	
	@Autowired
	private PessoaController pessoaController;

	@Test
	public void testCadPessoaFisica() throws ExceptionMentoriaJava {

		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("AAlex fernando");
		pessoaJuridica.setEmail("ttestesalvarpj@gmail.com");
		pessoaJuridica.setTelefone("345999795800");
		pessoaJuridica.setInscEstadual("365556565656665");
		pessoaJuridica.setInscMunicipal("455554565656565");
		pessoaJuridica.setNomeFantasia("544556565665");
		pessoaJuridica.setRazaoSocial("46556656566");

		pessoaController.salvarPj(pessoaJuridica);

		}
		
		
	
	}


