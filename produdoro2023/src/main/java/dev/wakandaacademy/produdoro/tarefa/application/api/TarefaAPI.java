package dev.wakandaacademy.produdoro.tarefa.application.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tarefa")
public interface TarefaAPI {
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    TarefaIdResponse postNovaTarefa(@RequestBody @Valid TarefaRequest tarefaRequest);

    @GetMapping("/{idTarefa}")
    @ResponseStatus(code = HttpStatus.OK)
    TarefaDetalhadoResponse detalhaTarefa(@RequestHeader(name = "Authorization",required = true) String token, 
    		@PathVariable UUID idTarefa);
    
    @DeleteMapping("/{idTarefa}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deletaTarefa(@RequestHeader(name = "Authorization",required = true) String token,
    		@PathVariable UUID idTarefa);

    @PatchMapping("/{idTarefa}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void editaTarefa(@RequestHeader(name = "Authorization",required = true) String token,
		@Valid @PathVariable UUID idTarefa,
		@RequestBody TarefaRequest tarefaRequestEditada);
    
    @PatchMapping("/{idTarefa}/ativa") 
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void ativaTarefa(@RequestHeader(name = "Authorization",required = true) String token,
                     @RequestParam UUID idUsuario, @PathVariable UUID idTarefa);

    @GetMapping("/usuario/{idUsuario}")
    @ResponseStatus(code = HttpStatus.OK)
    List<TarefaResponse>buscaTarefasPorUsuario(@RequestHeader(name = "Authorization",required = true) String token,
    		@PathVariable UUID idUsuario);

    @PostMapping("/{idTarefa}/incrementa-pomodoro")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void incrementaPomodoro(@RequestHeader(name = "Authorization", required = true) String token,
                                    @PathVariable UUID idTarefa);
}