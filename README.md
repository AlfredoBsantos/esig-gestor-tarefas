# Sistema de Gestão de Tarefas - Processo Seletivo ESIG Group

## Visão Geral

Este projeto é uma aplicação Web desenvolvida como parte da atividade prática para o processo seletivo de estágio na ESIG Group. Trata-se de um sistema de gestão de tarefas (To-Do list) que permite a criação, edição, remoção, conclusão e filtragem de tarefas, além de contar com um sistema de autenticação de usuários.

A aplicação foi construída seguindo as especificações técnicas solicitadas, com foco em boas práticas, código limpo e uma experiência de usuário consistente.

## Tecnologias Utilizadas

* **Linguagem:** Java 8
* **Servidor de Aplicação:** Apache Tomcat 9.0
* **Banco de Dados:** PostgreSQL
* **Framework Web (View):** JavaServer Faces (JSF) 2.3
* **Injeção de Dependência:** CDI (Contexts and Dependency Injection) 2.0
* **Persistência (ORM):** JPA 2.2 com implementação Hibernate 5.6
* **Segurança:** jBCrypt para hashing de senhas
* **Gerenciador de Dependências:** Apache Maven
* **Testes:** JUnit 5 (com banco de dados em memória H2)

## Funcionalidades Implementadas

### Requisitos Obrigatórios
- **(a) Aplicação Java Web com JavaServer Faces (JSF)**: A interface do usuário foi construída utilizando componentes JSF, com o backend gerenciado por CDI Beans.
- **(b) Persistência com PostgreSQL**: Todos os dados da aplicação, como tarefas e usuários, são armazenados em um banco de dados PostgreSQL.
- **CRUD de Tarefas**:
    - **Criar Tarefa**: Funcionalidade implementada na tela de cadastro.
    - **Atualizar Tarefa**: A edição é realizada em uma página dedicada, acessada a partir da listagem.
    - **Remover Tarefa**: Ação disponível na tabela de listagem.
    - **Listar Tarefas**: A página inicial exibe todas as tarefas "Em Andamento".
- **Concluir Tarefa**: Uma ação na listagem marca a tarefa como "Concluída", removendo-a da visualização padrão.
- **Filtros na Listagem**: A tela de listagem permite filtrar as tarefas por número, título/descrição, responsável e situação.

### Diferenciais (Itens Opcionais)
- **(c) Utilização de JPA**: A camada de persistência foi implementada com JPA e Hibernate para o mapeamento objeto-relacional.
- **(d) Testes de Unidade**: Foram criados testes unitários com JUnit 5 para a classe `TarefaDAO`, validando as operações de CRUD e a lógica de busca com filtros. Os testes rodam de forma isolada com um banco de dados em memória (H2).
- **(f) Outros Diferenciais Implementados**:
    - **Sistema de Login Seguro**: Implementação de autenticação e cadastro de usuários. As senhas são armazenadas de forma segura no banco de dados utilizando hashing com BCrypt.
    - **Filtro de Autenticação**: Todas as páginas, exceto as de login e cadastro, são protegidas, garantindo que apenas usuários logados possam acessá-las.
    - **Paginação na Listagem**: A tabela de tarefas é paginada para melhor performance e experiência de usuário com grandes volumes de dados.
    - **Consistência de Interface (IHC)**: As telas foram estilizadas com CSS externo para se aproximarem dos protótipos fornecidos e garantir uma experiência fluida, com redirecionamentos consistentes após as ações do usuário.

## Como Executar o Projeto Localmente

Siga os passos abaixo para configurar e rodar a aplicação em seu ambiente local.

### Pré-requisitos
1.  **JDK 8**: É necessário ter o Java Development Kit na versão 8 instalado e configurado no sistema.
2.  **Apache Maven**: É preciso ter o Maven 3.6+ instalado para o build do projeto.
3.  **PostgreSQL**: Um servidor de banco de dados PostgreSQL deve estar instalado e rodando.
4.  **Apache Tomcat 9**: Um servidor de aplicação compatível com `javax.*` (Java EE 8), como o Tomcat 9.0, é necessário.

### Passos para a Instalação

1.  **Clone o Repositório**
    ```shell
    git clone [https://github.com/seu-usuario/esig-gestor-tarefas.git](https://github.com/seu-usuario/esig-gestor-tarefas.git)
    cd esig-gestor-tarefas
    ```

2.  **Configure o Banco de Dados**
    * No seu servidor PostgreSQL, crie um novo banco de dados com o nome `esig_tarefas`.
        ```sql
        CREATE DATABASE esig_tarefas;
        ```
    * Abra o arquivo `src/main/resources/META-INF/persistence.xml` e, se necessário, altere as propriedades `javax.persistence.jdbc.user` e `javax.persistence.jdbc.password` com as suas credenciais do PostgreSQL.

3.  **Compile o Projeto com o Maven**
    * No terminal, na raiz do projeto, execute o comando abaixo. Ele irá baixar as dependências e gerar o arquivo `.war` na pasta `target/`.
    ```shell
    mvn clean install
    ```

4.  **Implante no Tomcat**
    * Copie o arquivo `target/esig-gestor-tarefas-1.0-SNAPSHOT.war` para a pasta `webapps` do seu servidor Apache Tomcat 9.
    * Inicie o servidor Tomcat. A aplicação será implantada automaticamente.

5.  **Acesse a Aplicação**
    * Abra seu navegador e acesse: `http://localhost:8080/esig-gestor-tarefas-1.0-SNAPSHOT/`
    * Você será redirecionado para a tela de login.

### Credenciais Padrão

A aplicação **não cria mais** um usuário padrão. Por favor, utilize a tela de **"Cadastre-se"** para criar sua primeira conta e acessar o sistema.
