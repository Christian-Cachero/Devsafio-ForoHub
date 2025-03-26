package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.exceptions.EntityNoExistsException;
import com.alurachallengers.forohub.model.Respuesta;
import com.alurachallengers.forohub.model.Topico;
import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.RespuestaDTO;
import com.alurachallengers.forohub.model.mappers.RespuestaMapper;
import com.alurachallengers.forohub.repository.RespuestaRepository;
import com.alurachallengers.forohub.repository.TopicoRepository;
import com.alurachallengers.forohub.repository.UsuarioRepository;
import com.alurachallengers.forohub.service.RespuestaService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private RespuestaMapper respuestaMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public List<RespuestaDTO> getAllRespuestasDTO() {
        return respuestaMapper.toRespuestaDTOs(respuestaRepository.findAll());
    }

    @Override
    public Optional<RespuestaDTO> getRespuestaById(Long id) {

        Optional<RespuestaDTO> respuestaDTO = respuestaRepository.findById(id).map(respuestaMapper::toRespuestaDTO);

        if (respuestaDTO.isPresent()) {
            return respuestaDTO;
        }
        else {
            throw new EntityNoExistsException("Respuesta no encontrada.");
        }
    }

    @Override
    public RespuestaDTO createRespuesta(Long topicoId, RespuestaDTO respuestaDTO) {

        /*el usuario obtenido es de hecho el que tiene la sección activa, por ende se asume
        * que este es el usuario correcto*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long usuarioId = ((Usuario) authentication.getPrincipal()).getId();

        Topico topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new EntityNoExistsException("Tópico no encontrado"));

        Usuario autor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNoExistsException("Usuario no encontrado"));

        Respuesta respuesta = Respuesta.builder()
                .contenidoRespuesta(respuestaDTO.contenidoRespuesta())
                .topico(topico)
                .autor(autor)
                .vistaPreviaMensaje(topico.getMensaje())
                .build();

        return respuestaMapper.toRespuestaDTO(respuestaRepository.save(respuesta));
    }

    @Override
    @SneakyThrows //sirve para general el código de excepción sin tener que declararlo explícitamente
    public Optional<RespuestaDTO> updateRespuesta(Long id, RespuestaDTO respuestaDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long autorId = ((Usuario) authentication.getPrincipal()).getId();

        if (respuestaRepository.existsById(id)) {
            Respuesta respuestaAModificar = respuestaRepository.findById(id)
                    .orElseThrow(() -> new EntityNoExistsException("Respuesta no encontrada"));

            if (!respuestaAModificar.getAutor().getId().equals(autorId)) {
                throw new AccessDeniedException("No eres el autor de esta respuesta");
            }
            else{
                if (respuestaDTO.contenidoRespuesta() != null){
                    respuestaAModificar.setContenidoRespuesta(respuestaDTO.contenidoRespuesta());
                }
            }

            Respuesta respuestaModificada = respuestaRepository.save(respuestaAModificar);
            return Optional.of(respuestaMapper.toRespuestaDTO(respuestaModificada));
        }
        throw new EntityNoExistsException("Respuesta seleccionada no encontrada");
    }

    @Override
    @SneakyThrows
    public void deleteRespuesta(Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long autorId = ((Usuario) authentication.getPrincipal()).getId();

        if (respuestaRepository.existsById(id)){
            Respuesta respuestaAModificar = respuestaRepository.findById(id)
                    .orElseThrow(() -> new EntityNoExistsException("Respuesta no encontrada"));

            if (!respuestaAModificar.getAutor().getId().equals(autorId)) {
                throw new AccessDeniedException("No eres el autor de esta respuesta");
            }
            else{
                respuestaRepository.deleteById(id);
            }
        }
        else{
            throw new EntityNoExistsException("La respuesta a eliminar no se encuentra disponible");
        }
    }
}
