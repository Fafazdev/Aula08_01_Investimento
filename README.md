# Aula08_01_Investimento

Aplicacao Java Swing para calcular o rendimento de uma aplicacao financeira usando juros compostos.

## Estrutura

- `src/business`: regra de calculo e interface `IAplicacao`.
- `src/view`: janela Swing e classe `Principal`.

## Executar

No Windows, usando JDK instalado:

```powershell
javac -d out src\business\*.java src\view\*.java
java -cp out view.Principal
```

Antes da entrega, atualize o campo `ALUNOS` em `src/view/Principal.java` com os nomes dos integrantes.
