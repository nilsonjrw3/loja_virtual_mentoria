package w3.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import w3.lojavirtual.model.Usuario;
import w3.lojavirtual.repository.UsuarioRepository;


@Service
public class ImplementacaoUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Usuario usuario = usuarioRepository.findUserByLogin(username); /*Receber o login para consulta*/
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario nao encontrado!");
		}
		
		
		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
	}

}
