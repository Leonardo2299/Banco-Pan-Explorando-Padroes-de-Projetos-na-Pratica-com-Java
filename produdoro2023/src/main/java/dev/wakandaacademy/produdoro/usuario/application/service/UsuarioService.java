package dev.wakandaacademy.produdoro.usuario.application.service;

import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import dev.wakandaacademy.produdoro.credencial.application.service.CredencialApplicationService;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.pomodoro.application.service.PomodoroApplicationService;
import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioCriadoResponse;
import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioNovoRequest;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UsuarioService implements UsuarioApplicationService {
	private final PomodoroApplicationService pomodoroService;
	private final CredencialApplicationService credencialService;
	private final UsuarioRepository usuarioRepository;

	@Override
	public UsuarioCriadoResponse criaNovoUsuario(@Valid UsuarioNovoRequest usuarioNovo) {
		log.info("[start] UsuarioService - criaNovoUsuario");
		var configuracaoPadrao = pomodoroService.getConfiguracaoPadrao();
		credencialService.criaNovaCredencial(usuarioNovo);
		var usuario = new Usuario(usuarioNovo, configuracaoPadrao);
		usuarioRepository.salva(usuario);
		log.info("[finish] UsuarioService - criaNovoUsuario");
		return new UsuarioCriadoResponse(usuario);
	}

	@Override
	public UsuarioCriadoResponse buscaUsuarioPorId(UUID idUsuario) {
		log.info("[inicia] UsuarioApplicationService - buscaUsuarioPorId");
		Usuario usuario = usuarioRepository.buscaUsuarioPorId(idUsuario);
		log.info("[finaliza] UsuarioApplicationService - buscaUsuarioPorId");
		return new UsuarioCriadoResponse(usuario);
	}

	@Override
	public void mudaStatusPausaLonga(String usuarioEmail, UUID idUsuario) {
		log.info("[start] UsuarioService - mudaStatusPausaLonga");
		Usuario usuario = usuarioRepository.buscaUsuarioPorId(idUsuario);
		if (usuarioEmail.equals(usuario.getEmail())) {
			usuario.mudaStatusPausaLonga();
			usuarioRepository.salva(usuario);
		} else {
			throw APIException.build(HttpStatus.UNAUTHORIZED, "Usuário não autorizado!");
		}
		log.info("[finish] UsuarioService - mudaStatusPausaLonga");
	}

	@Override
	public void mudaStatusParaPausaCurta(String usuarioEmail, UUID idUsuario) {
		log.info("[start] UsuarioService - mudaStatusParaPausaCurta");
		Usuario usuario = usuarioRepository.buscaUsuarioPorId(idUsuario);
		if(usuarioEmail.equals(usuario.getEmail())) {
			usuario.pausaCurta();
			usuarioRepository.salva(usuario);
		}else {
			throw APIException.build(HttpStatus.UNAUTHORIZED, "Usuário não autorizado!");
		}		
		log.info("[finish] UsuarioService - mudaStatusParaPausaCurta");
	}
}