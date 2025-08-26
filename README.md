# Projeto POO - Agência de Viagens

Este projeto consiste em uma simulação de uma agência de viagens com funcionalidades de visualização de pacotes, cadastro e autenticação de usuários, reserva e cancelamento de viagens, aplicação de filtros e geração de relatórios em console, persistência dos dados em arquivos `.txt` dentro de `/resources`.

---

## Participantes
- **Luan Dantas Melo**  
- **Natanael dos Santos**  
- **Thayná Luzia Gonçalves Lima**

---

## Funcionalidades

- **Autenticação Simulada** – Usuário entra com login e senha para acessar áreas restritas.  
- **Validação de Dados** – Ex: CPF válido, datas coerentes, email com formato correto.  
- **Listagem de Reservas** – Mostrar reservas feitas por um cliente logado.  
- **Cancelamento de Reserva** – Permitir cancelamento com validação de data, alteração do status da reserva e liberação de vaga.  
- **Cálculo de preço por data** – Alterar preço dinâmico em alta temporada usando polimorfismo.  
- **Aplicação de Interface** – Criar interface `Reservavel` para aplicar em outros tipos de reservas no futuro.  
- **Exceções Customizadas** – Feedbacks de erro mais precisos (`ReservaInvalidaException`, `UsuarioNaoEncontradoException`, etc.).  
- **Responsividade Web** – Melhorar usabilidade em diferentes telas com *media queries*.  
- **Visualização de pacotes de viagem** – Exibição em cards filtráveis.  
- **Cadastro simulado de clientes** – Inclusão de dados básicos e validação via `ValidadorUtils`.  
- **Filtro por destino/data/preço** – Busca avançada via lógica Java.  
- **Simulação de reserva** – Atribuição cliente → pacote com status (sem persistência de dados).  
- **Histórico de reservas do cliente** – Exibição via console.  
- **Geração de relatórios** – Por destino, por cliente ou por período.  

---

## Conceitos de Programação Utilizados (POO)

- **Herança**  
  `PacoteNacional` e `PacoteInternacional` herdam de `PacoteViagem`, obtendo seus atributos e métodos.  

- **Encapsulamento**  
  As classes usam atributos privados e métodos públicos (*getters/setters*).  

- **Polimorfismo**  
  O método `getClonePreco` é sobrescrito em subclasses de `PacoteViagem`, permitindo diferentes comportamentos.  

- **Teste**  
  Foi utilizado **JUnit** para testar funcionalidades do backend.  
  Os testes automatizados ficam em `src/test/java/com/exemplo/ClienteServiceTest.java`, onde métodos importantes são validados para garantir o correto funcionamento do sistema.  

- **Persistência de Dados**  
  A persistência é feita por meio de arquivos texto como `clientes.txt`, `pacotes.txt`, `reservas.txt`.  
  Classes utilitárias, como `ArquivoUtils.java` e `BancoDeDadosSimulado.java`, leem e gravam informações nesses arquivos, simulando um banco de dados.  

- **Classe Abstrata**  
  `PacoteViagem` é declarada como abstrata, servindo como modelo base para outros tipos de pacotes de viagem.  
  Ela não pode ser instanciada diretamente — apenas suas subclasses (`PacoteNacional`, `PacoteInternacional`) podem criar objetos.  

- **Visibilidade**  
  Uso de modificadores como `private` e `public` nos atributos e métodos, controlando o acesso.  

- **Padrão de Projeto adicional (Singleton)**  
  Aplicado ao `BancoDeDadosSimulado` para garantir centralização de uma única fonte de dados em todo o sistema e facilitar persistência e sincronização com arquivos.  
  Também aplicado ao `ArquivoUtils`, centralizando leitura e gravação de arquivos.  
  - **Benefício**: evita cópias duplicadas de dados e problemas de sincronização.  

---

## Conexão do Backend com Frontend (Spring Boot / Maven)

- **Backend (Java/Spring Boot):**  
  Os controllers (`ClienteController.java`, `PacoteController.java`) recebem requisições do navegador, processam dados e retornam respostas ou páginas.  

- **Frontend (HTML/CSS/JS):**  
  Arquivos como `index.html`, `cadastro.html`.  
  Estes podem ser preenchidos dinamicamente pelo Spring Boot usando **Thymeleaf**.  

- **Integração:**  
  Quando o usuário acessa uma URL, o controller correspondente retorna um *template* HTML que contém os dados vindos do backend.  

- **Maven:**  
  O Maven gerencia as dependências (Spring Boot, Thymeleaf, etc.) e o processo de *build*, empacotando backend e frontend juntos.  
  Ao rodar o projeto, tudo é servido pelo **Tomcat** embutido em `AgenciaApplication.java`.  

---

## Estrutura de Pacotes Java

Organização do código em pacotes para modularidade:

- `com.exemplo.agencia.model`  
- `com.exemplo.agencia.controller`  
- `com.exemplo.agencia.service`  
