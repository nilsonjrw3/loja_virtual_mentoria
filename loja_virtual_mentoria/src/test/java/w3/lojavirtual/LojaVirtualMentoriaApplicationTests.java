package w3.lojavirtual;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;
import w3.lojavirtual.controller.AcessoController;
import w3.lojavirtual.model.Acesso;
import w3.lojavirtual.repository.AcessoRepository;
import w3.lojavirtual.service.AcessoService;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
class LojaVirtualMentoriaApplicationTests extends TestCase{
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private WebApplicationContext wac;
	
	/*Teste de END-Point de Cadastrar acesso*/
	
	@Test
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_COMPRADOR" + Calendar.getInstance().getTimeInMillis());
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
									.perform(MockMvcRequestBuilders.post("/salvarAcesso")
									.content(objectMapper.writeValueAsString(acesso))
									.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API "+ retornoApi.andReturn().getResponse().getContentAsString());
		
		/*Converter o retorno da API para um objeto de Acesso*/
		Acesso objetoRetorno = objectMapper
								.readValue(retornoApi.andReturn().getResponse().getContentAsString(),
								Acesso.class);
		
		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		
		
	}
	
/*Teste de END-Point de Deletar acesso*/
	
	@Test
	public void testRestApiDeletarAcesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_Teste");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
									.perform(MockMvcRequestBuilders.post("/deleteAcesso")
									.content(objectMapper.writeValueAsString(acesso))
									.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API: "+ retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno: "+retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso Removido", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		
	}
	
/*Teste de END-Point de Deletar acesso por ID*/
	
	@Test
	public void testRestApiDeletarPorIdAcesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_Teste_por_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
									.perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
									.content(objectMapper.writeValueAsString(acesso))
									.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API: "+ retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno: "+retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso Removido", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		
	}
	
/*Teste de END-Point de Obter acesso por ID*/
	
	@Test
	public void testRestApiObterAcessoID() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ObterAcesso_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
									.perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
									.content(objectMapper.writeValueAsString(acesso))
									.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON));
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
		
	}
	
/*Teste de END-Point de Obter acesso por Descricao*/
	
	@Test
	public void testRestApiObterAcessoDesc() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_Teste_OBTER_LIST");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
									.perform(MockMvcRequestBuilders.get("/buscarPorDesc/OBTER_LIST")
									.content(objectMapper.writeValueAsString(acesso))
									.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON));
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		
		List<Acesso> retornoApiList = objectMapper.
										readValue(retornoApi.andReturn().
												getResponse().getContentAsString(), 
												new TypeReference<List<Acesso>>() {});
		
		assertEquals(1, retornoApiList.size());
		
		assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());
		
		acessoRepository.deleteById(acesso.getId());
		
	}
	
	
	@Test
	 public void testCadastraAcesso() throws ExceptionMentoriaJava {
		
		
		String descacesso = "ROLE_ADMIN" + Calendar.getInstance().getTimeInMillis();
		Acesso acesso = new Acesso();
		
		
		acesso.setDescricao(descacesso);
		
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		/*Validar Dados*/
		assertEquals(descacesso, acesso.getDescricao());
		
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
		
		/*Valida Carregamento*/
		assertEquals(acesso2.getId(), acesso.getId());
		
		/*Teste de Delete*/
		
		acessoRepository.deleteById(acesso2.getId());
		
		acessoRepository.flush();/*Roda o SQL no Banco*/
		
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);// Vai consultar o id, caso nao encontre mais, volta null
		
		assertEquals(true, acesso3 == null);
		
		
		/*TESTE DE QUERY*/
		
		acesso = new Acesso();//instanciou o acesso
		
		acesso.setDescricao("ROLE_ALUNO");//Passou a descricao
		
		acesso = acessoController.salvarAcesso(acesso).getBody();//Salvou no banco o acesso
		
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());//Usa o metodo criando no repository buscando aluno, sem espaco e tudo maiusculo
		
		assertEquals(1, acessos.size());// Testa se a lista esta com o tamanho 1.

		acessoRepository.deleteById(acesso.getId());//Deleta o acessso.
		
		
	}
	
	

}
