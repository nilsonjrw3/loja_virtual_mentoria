package w3.lojavirtual.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import w3.lojavirtual.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.login = ?1")
	Usuario findUserByLogin(String login);
	
	@Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login =?2")
	Usuario findUserByPessoa(Long id, String email);

	@Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_acesso' and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
	String consultaConstraintAcesso();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into usuarios_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = 'ROLE_USER'))")
	void insereAcessoUserPj(Long iduser);
	
}
