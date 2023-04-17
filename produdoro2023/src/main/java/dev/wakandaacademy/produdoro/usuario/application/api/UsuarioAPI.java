package dev.wakandaacademy.produdoro.usuario.application.api;

import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/v1/usuario")
public interface UsuarioAPI {
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	UsuarioCriadoResponse postNovoUsuario(@RequestBody @Valid UsuarioNovoRequest usuarioNovo);

	@GetMapping(value = "/{idUsuario}")
	@ResponseStatus(code = HttpStatus.OK)
	UsuarioCriadoResponse buscaUsuarioPorId(@PathVariable UUID idUsuario);

	@PatchMapping(value = "/{idUsuario}/pausa-longa")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	void mudaStatusPausaLonga(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable UUID idUsuario);
	
	@PostMapping(value = "/{idUsuario}/pausa-curta")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	void mudaStatusParaPausaCurta(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable UUID idUsuario);
}