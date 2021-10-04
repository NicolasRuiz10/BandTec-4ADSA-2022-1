package com.br.springsprint2.controle;

import com.br.springsprint2.dominio.ItensPedido;
import com.br.springsprint2.dominio.Pedido;
import com.br.springsprint2.dominio.Usuario;
import com.br.springsprint2.repositorio.ItensPedidosRepository;
import com.br.springsprint2.repositorio.PedidoRepository;
import com.br.springsprint2.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("pedido")
public class PedidoController {
    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ItensPedidosRepository itensRepository;

    @Autowired
    private UsuarioRepository userRepository;

    @PostMapping("/{fkIntensPedidos}/{fkUsuario}")
    public ResponseEntity createPedido(@RequestBody Pedido novoPedido, @PathVariable int fkIntensPedidos, @PathVariable int fkUsuario) {
        ItensPedido itens = itensRepository.findById(fkIntensPedidos).get();
        novoPedido.setFkIntensPedidos(itens);
        Usuario usuario = userRepository.findById(fkUsuario).get();
        novoPedido.setFkUsuario(usuario);
        repository.save(novoPedido);
        return status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity getPedidos() {
        List<Pedido> lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPedido(@PathVariable int id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePedido(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}
