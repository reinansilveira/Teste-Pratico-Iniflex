# Teste Prático de Programação — Iniflex

Solução em Java para o teste prático proposto pela Prothera. O programa cadastra os funcionários da tabela fornecida e executa, em sequência, todos os itens solicitados.

## Decisões técnicas

- `LocalDate` para representar datas sem fuso horário;
- `BigDecimal` criado a partir de texto para evitar imprecisão monetária;
- `NumberFormat` com localidade `pt-BR` para milhar com ponto e decimal com vírgula;
- `LinkedHashMap` no agrupamento para manter uma saída previsível;
- classes de domínio independentes da apresentação no terminal;
- testes automatizados com JUnit 5, executados pelo Maven.

## Requisitos atendidos

- [x] Pessoa com nome e data de nascimento;
- [x] Funcionário herdando de Pessoa, com salário e função;
- [x] cadastro na ordem da tabela e remoção de João;
- [x] datas e valores no padrão brasileiro;
- [x] aumento de 10%;
- [x] agrupamento e impressão por função;
- [x] aniversariantes dos meses 10 e 12;
- [x] funcionário mais velho e sua idade;
- [x] ordem alfabética;
- [x] total dos salários;
- [x] quantidade de salários mínimos por funcionário.

## Requisitos

- JDK 17 ou superior;
- Maven 3.8 ou superior apenas se optar pela execução com Maven.

## Executar

### Windows

No Explorador de Arquivos, execute `testar.bat`. Ou, no Prompt de Comando aberto na pasta do projeto:

```bat
testar.bat
```

### Linux/macOS

```bash
./testar.sh
```

Os scripts executam os testes JUnit quando o Maven está instalado e, em seguida, mostram o resultado completo. Se apenas o JDK estiver disponível, compilam e executam o programa normalmente.

### Com Maven

```bash
mvn clean verify
java -jar target/teste-pratico-iniflex-1.0.0.jar
```

### Pela IDE (Windows, Linux ou macOS)

Importe a pasta como projeto Maven, abra a classe `Principal` e execute o método `main`.

## Testes automatizados

Na raiz do projeto:

```bash
mvn test
```

Os testes conferem os dados de entrada, remoção, aumento, grupos, aniversariantes, funcionário mais velho, total dos salários e formatação brasileira.

> Observação: a numeração original do enunciado salta diretamente do item 3.6 para o 3.8; não existe item 3.7 no teste fornecido.
