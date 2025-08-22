package com.exemplo;

import com.exemplo.agencia.model.Cliente;
import com.exemplo.agencia.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(); // Cria uma instância limpa antes de cada teste
    }

    @Test
    void salvarClienteComSucesso() {
        Cliente cliente = new Cliente("Maria", "maria2000@gmail.com", "12345678",
                "123.456.789-00", LocalDate.of(2000, 1, 1), "(11) 91234-5678");
      //chama o metodo
        clienteService.salvarCliente(cliente);
      //verifica se foi add na lista
        assertEquals(1, clienteService.getClientes().size());
        assertEquals("Maria", clienteService.getClientes().get(0).getNome());
    }
    //teste para lançar erro para email
    @Test
    void deveLancarErroParaEmailInvalido() {
        Cliente cliente = new Cliente("Maria", "email_invalido", "12345678",
                "123.456.789-00", LocalDate.of(2000, 1, 1), "(11) 91234-5678");
        //verifica se lança exceção
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> clienteService.salvarCliente(cliente));

        assertEquals("Email inválido", ex.getMessage());
    }

    @Test
    void deveAutenticarClienteCorretamente() {
        Cliente cliente = new Cliente("Ana", "ana@gmail.com", "senha123",
                "987.654.321-00", LocalDate.of(1995, 5, 5), "(11) 92345-6789");

        clienteService.salvarCliente(cliente);
      // Testa autenticação correta
        assertTrue(clienteService.autenticar("ana@gmail.com", "senha123"));
        // Testa autenticação incorreta
        assertFalse(clienteService.autenticar("ana@gmail.com", "senhaErrada"));
    }
}
