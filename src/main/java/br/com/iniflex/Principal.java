package br.com.iniflex;

import br.com.iniflex.model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat FORMATO_NUMERO = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final BigDecimal FATOR_AUMENTO = new BigDecimal("1.10");

    static {
        FORMATO_NUMERO.setMinimumFractionDigits(2);
        FORMATO_NUMERO.setMaximumFractionDigits(2);
    }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = criarFuncionarios();

        removerFuncionario(funcionarios, "João");
        imprimirSecao("3.3 - Funcionários após remover João", funcionarios);

        aplicarAumento(funcionarios, FATOR_AUMENTO);
        imprimirSecao("3.4 - Funcionários após aumento de 10%", funcionarios);

        Map<String, List<Funcionario>> porFuncao = agruparPorFuncao(funcionarios);
        imprimirSecaoAgrupada("3.5/3.6 - Funcionários agrupados por função", porFuncao);

        List<Funcionario> aniversariantes = filtrarAniversariantes(funcionarios, 10, 12);
        imprimirSecao("3.8 - Aniversariantes de outubro e dezembro", aniversariantes);

        Funcionario maisVelho = encontrarMaisVelho(funcionarios);
        int idade = calcularIdade(maisVelho.getDataNascimento(), LocalDate.now());
        System.out.printf("%n3.9 - Funcionário com maior idade%n%s | idade: %d anos%n", maisVelho.getNome(), idade);

        List<Funcionario> ordemAlfabetica = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
        imprimirSecao("3.10 - Funcionários em ordem alfabética", ordemAlfabetica);

        BigDecimal totalSalarios = calcularTotalSalarios(funcionarios);
        System.out.printf("%n3.11 - Total dos salários: %s%n", formatarMoeda(totalSalarios));

        System.out.println("\n3.12 - Quantidade de salários mínimos por funcionário");
        funcionarios.forEach(f -> {
            BigDecimal quantidade = f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
            System.out.printf("%s: %s salários mínimos%n", f.getNome(), formatarNumero(quantidade));
        });
    }

    static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    private static void imprimirSecao(String titulo, List<Funcionario> funcionarios) {
        System.out.println("\n" + titulo);
        funcionarios.forEach(Principal::imprimirFuncionario);
    }

    private static void imprimirSecaoAgrupada(String titulo, Map<String, List<Funcionario>> grupos) {
        System.out.println("\n" + titulo);
        grupos.forEach((funcao, funcionarios) -> {
            System.out.println(funcao + ":");
            funcionarios.forEach(Principal::imprimirFuncionario);
        });
    }

    private static void imprimirFuncionario(Funcionario funcionario) {
        System.out.printf("- %s | nascimento: %s | salário: %s | função: %s%n",
                funcionario.getNome(), formatarData(funcionario.getDataNascimento()),
                formatarMoeda(funcionario.getSalario()), funcionario.getFuncao());
    }

    static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase(nome));
    }

    static void aplicarAumento(List<Funcionario> funcionarios, BigDecimal fator) {
        funcionarios.forEach(funcionario -> funcionario.setSalario(
                funcionario.getSalario().multiply(fator).setScale(2, RoundingMode.HALF_UP)));
    }

    static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        LinkedHashMap::new,
                        Collectors.toList()));
    }

    static List<Funcionario> filtrarAniversariantes(List<Funcionario> funcionarios, int... meses) {
        return funcionarios.stream()
                .filter(funcionario -> {
                    int mesNascimento = funcionario.getDataNascimento().getMonthValue();
                    for (int mes : meses) {
                        if (mesNascimento == mes) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();
    }

    static Funcionario encontrarMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow(() -> new IllegalArgumentException("A lista de funcionários está vazia"));
    }

    static BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    static String formatarData(LocalDate data) {
        return FORMATO_DATA.format(data);
    }

    static String formatarMoeda(BigDecimal valor) {
        return "R$ " + formatarNumero(valor);
    }

    static String formatarNumero(BigDecimal valor) {
        return FORMATO_NUMERO.format(valor);
    }

    static int calcularIdade(LocalDate nascimento, LocalDate hoje) {
        return Period.between(nascimento, hoje).getYears();
    }
}
