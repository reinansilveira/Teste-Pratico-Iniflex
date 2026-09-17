package br.com.iniflex;

import br.com.iniflex.model.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PrincipalTest {

    @Test
    void deveCadastrarFuncionariosNaOrdemDaTabelaERemoverJoao() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();

        assertAll(
                () -> assertEquals(10, funcionarios.size()),
                () -> assertEquals("Maria", funcionarios.get(0).getNome()),
                () -> assertEquals("Helena", funcionarios.get(9).getNome()));

        Principal.removerFuncionario(funcionarios, "João");

        assertEquals(9, funcionarios.size());
        assertFalse(funcionarios.stream().anyMatch(f -> f.getNome().equals("João")));
    }

    @Test
    void deveAplicarAumentoEAgruparPorFuncao() {
        List<Funcionario> funcionarios = funcionariosAposRemocaoEAumento();

        assertEquals(new BigDecimal("2210.38"), funcionarios.get(0).getSalario());

        Map<String, List<Funcionario>> grupos = Principal.agruparPorFuncao(funcionarios);

        assertAll(
                () -> assertEquals(7, grupos.size()),
                () -> assertEquals(2, grupos.get("Operador").size()),
                () -> assertEquals(2, grupos.get("Gerente").size()));
    }

    @Test
    void deveEncontrarAniversariantesEMaisVelho() {
        List<Funcionario> funcionarios = funcionariosAposRemocaoEAumento();
        List<Funcionario> aniversariantes = Principal.filtrarAniversariantes(funcionarios, 10, 12);

        assertAll(
                () -> assertEquals(2, aniversariantes.size()),
                () -> assertEquals("Maria", aniversariantes.get(0).getNome()),
                () -> assertEquals("Miguel", aniversariantes.get(1).getNome()),
                () -> assertEquals("Caio", Principal.encontrarMaisVelho(funcionarios).getNome()),
                () -> assertEquals(65,
                        Principal.calcularIdade(LocalDate.of(1961, 5, 2), LocalDate.of(2026, 9, 17))));
    }

    @Test
    void deveCalcularTotalEFormatarNoPadraoBrasileiro() {
        List<Funcionario> funcionarios = funcionariosAposRemocaoEAumento();

        assertAll(
                () -> assertEquals(new BigDecimal("50906.82"), Principal.calcularTotalSalarios(funcionarios)),
                () -> assertEquals("18/10/2000", Principal.formatarData(LocalDate.of(2000, 10, 18))),
                () -> assertEquals("19.119,88", Principal.formatarNumero(new BigDecimal("19119.88"))));
    }

    private List<Funcionario> funcionariosAposRemocaoEAumento() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();
        Principal.removerFuncionario(funcionarios, "João");
        Principal.aplicarAumento(funcionarios, new BigDecimal("1.10"));
        return funcionarios;
    }
}
