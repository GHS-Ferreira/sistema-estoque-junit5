package com.example.danhpaiva;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SistemaEstoqueTest {

    private SistemaEstoque sistemaEstoque;

    @BeforeEach
    public void setUp() {
        sistemaEstoque = new SistemaEstoque();
    }

    @Test
    void testAdicionarProduto() {
        sistemaEstoque.adicionarProduto("Iphone 16e", 5);
        assertEquals(5, sistemaEstoque.consultarEstoque("Iphone 16e"));
    }

    @Test
    void testAdicionarProdutoProdutoExistente() {
        sistemaEstoque.adicionarProduto("Iphone 16e", 5);
        sistemaEstoque.adicionarProduto("Iphone 16e", 3);
        assertEquals(8, sistemaEstoque.consultarEstoque("Iphone 16e"));
    }

    @Test
    void testAdicionarProdutoComQuantidadeZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.adicionarProduto("Iphone 16e", 0);
        });
        assertEquals("A quantidade deve ser maior que zero.", thrown.getMessage());
    }

    @Test
    void testAdicionarProdutoComNomeVazio() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.adicionarProduto("", 5);
        });
        assertEquals("Nome do produto não pode ser nulo ou vazio.", thrown.getMessage());
    }

    @Test
    void testRemoverProduto() {
        sistemaEstoque.adicionarProduto("Iphone 16e", 10);
        sistemaEstoque.removerProduto("Iphone 16e", 5);
        assertEquals(5, sistemaEstoque.consultarEstoque("Iphone 16e"));
    }

    @Test
    void testRemoverProdutoComEstoqueInsuficiente() {
        sistemaEstoque.adicionarProduto("Iphone 16e", 5);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.removerProduto("Iphone 16e", 10);
        });
        assertEquals("Estoque insuficiente para remover 10 unidade(s) de Iphone 16e.", thrown.getMessage());
    }

    @Test
    void testRemoverProdutoComNomeVazio() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.removerProduto("", 5);
        });
        assertEquals("Nome do produto não pode ser nulo ou vazio.", thrown.getMessage());
    }

    @Test
    void testConsultarEstoque() {
        sistemaEstoque.adicionarProduto("Iphone 16e", 5);
        int estoque = sistemaEstoque.consultarEstoque("Iphone 16e");
        assertEquals(5, estoque);
    }

    @Test
    void testConsultarEstoqueProdutoNaoExistente() {
        int estoque = sistemaEstoque.consultarEstoque("Produto Inexistente");
        assertEquals(0, estoque);
    }

    @Test
    void testObterHistoricoTransacoes() {
        sistemaEstoque.adicionarProduto("Iphone 16e", 5);
        sistemaEstoque.removerProduto("Iphone 16e", 3);
        List<String> historico = sistemaEstoque.obterHistoricoTransacoes();
        assertEquals(2, historico.size());
        assertEquals("Adicionado 5 unidade(s) de Iphone 16e", historico.get(0));
        assertEquals("Removido 3 unidade(s) de Iphone 16e", historico.get(1));
    }

    @Test
    void testVerificarDisponibilidade() {
        sistemaEstoque.adicionarProduto("Iphone 16e", 5);
        assertTrue(sistemaEstoque.verificarDisponibilidade("Iphone 16e", 3));
        assertFalse(sistemaEstoque.verificarDisponibilidade("Iphone 16e", 6));
    }

    @Test
    void testVerificarDisponibilidadeComNomeVazio() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.verificarDisponibilidade("", 5);
        });
        assertEquals("Nome do produto não pode ser nulo ou vazio.", thrown.getMessage());
    }

    @Test
    void testVerificarDisponibilidadeComQuantidadeZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            sistemaEstoque.verificarDisponibilidade("Iphone 16e", 0);
        });
        assertEquals("A quantidade necessária deve ser maior que zero.", thrown.getMessage());
    }
}
