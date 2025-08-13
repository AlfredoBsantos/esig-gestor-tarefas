# Sistema de Gestão de Tarefas - Processo Seletivo ESIG Group

## Visão Geral

Este projeto é uma aplicação Web desenvolvida como parte da atividade prática para o processo seletivo de estágio na ESIG Group. Trata-se de um sistema de gestão de tarefas (To-Do list) que permite a criação, edição, remoção, conclusão e filtragem de tarefas, além de contar com um sistema de autenticação de usuários.

A aplicação foi construída seguindo as especificações técnicas solicitadas, com foco em boas práticas, código limpo, documentação de arquitetura e uma experiência de usuário consistente.

## 🚀 Link para a Aplicação em Produção

Você pode acessar e testar a aplicação em funcionamento no seguinte link:

**[https://esig-gestor-tarefas.onrender.com/](https://esig-gestor-tarefas.onrender.com/)**

**Observação:** A aplicação está hospedada no plano gratuito do Render.com. O primeiro acesso pode demorar um pouco (cerca de 30 a 60 segundos) para o servidor "acordar" após um período de inatividade.

## Diagramas de Arquitetura (UML)

Para ilustrar a arquitetura e o funcionamento do sistema, foram criados os seguintes diagramas:

### Diagrama de Casos de Uso
*Este diagrama oferece uma visão de alto nível sobre as funcionalidades do sistema e a interação do usuário com elas.*

<img width="598" height="307" alt="Diagrama de casos de uso - SIstema de Gestao de Tarefas" src="https://github.com/user-attachments/assets/0834bc99-58d9-4247-a8fa-6f9b1569773d" />


### Diagrama de Classes
*Este diagrama detalha a estrutura estática do projeto, mostrando as principais classes, seus atributos, métodos e relacionamentos.*

<img width="609" height="648" alt="Diagrama de Classes - Sistema de Gestao de Tarefas" src="https://github.com/user-attachments/assets/b92c3e2a-0e6a-4d39-aa7b-bfc9a7f53527" />


---

## Tecnologias Utilizadas

* **Linguagem:** Java 8
* **Servidor de Aplicação:** Apache Tomcat 9.0
* **Containerização:** Docker
* **Plataforma de Nuvem:** Render.com
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
- **Concluir Tarefa**: Uma ação na listagem marca a tarefa como "Concluída".
- **Filtros na Listagem**: A tela de listagem permite filtrar as tarefas por número, título/descrição, responsável e situação.

### Diferenciais (Itens Opcionais)
- **(c) Utilização de JPA**: A camada de persistência foi implementada com JPA e Hibernate para o mapeamento objeto-relacional.
- **(d) Testes de Unidade**: Foram criados testes unitários com JUnit 5 para a classe `TarefaDAO`, validando as operações de CRUD e a lógica de busca.
- **(e) Publicação na Nuvem (Deploy)**: A aplicação foi empacotada com **Docker** e está em produção na plataforma **Render.com**, acessível publicamente.
- **(f) Outros Diferenciais Implementados**:
    - **Sistema de Login Seguro**: Implementação de autenticação e cadastro de usuários com hashing de senhas via **BCrypt**.
    - **Filtro de Autenticação**: Uso de um `Servlet Filter` para proteger as páginas do sistema.
    - **Paginação na Listagem**: A tabela de tarefas é paginada para melhor performance e UX.
    - **Consistência de Interface (IHC)**: As telas foram estilizadas com CSS externo e a navegação foi projetada para ser consistente e intuitiva.
    - **Documentação de Arquitetura**: Criação de diagramas UML (Casos de Uso, Classes) e documentação de decisões técnicas.

## Decisões de Arquitetura e Design

* **Stack Java EE 8 (Java 8 / `javax.*`)**: A escolha foi feita para alinhar o projeto com ambientes corporativos mais estabelecidos que ainda utilizam essa versão LTS do Java, garantindo ampla compatibilidade.
* **Padrão DAO (Data Access Object)**: A lógica de acesso ao banco de dados foi centralizada em classes DAO para separar as responsabilidades de persistência das regras de negócio e da apresentação, facilitando a manutenção e os testes.
* **CDI para Injeção de Dependência**: O CDI foi utilizado para gerenciar o ciclo de vida dos beans e desacoplar as camadas da aplicação (ex: injetar um DAO em um Bean de controle), promovendo um design mais limpo.
* **Deploy com Docker**: Para o deploy na nuvem, a aplicação foi containerizada com Docker. Essa abordagem garante um ambiente de produção consistente e portável, independente da máquina onde for executado.

## Roteiro de Testes Manuais

Para facilitar a avaliação, siga os cenários de teste abaixo para verificar as principais funcionalidades do sistema.

| Cenário de Teste | Passos para Execução | Resultado Esperado |
| :--- | :--- | :--- |
| **1. Cadastro de Novo Usuário** | 1. Acesse a [aplicação](https://esig-gestor-tarefas.onrender.com/). 2. Na tela de login, clique em "Cadastre-se". 3. Preencha um nome de usuário e senhas iguais. 4. Clique em "Cadastrar". | O sistema redireciona para a tela de login com uma mensagem de sucesso verde. |
| **2. Tentativa de Login Inválido** | 1. Na tela de login, insira um usuário/senha incorreto. 2. Clique em "Entrar". | O sistema permanece na tela de login e exibe uma mensagem de erro vermelha. |
| **3. Login e Logout** | 1. Na tela de login, insira credenciais válidas. 2. Clique em "Entrar". 3. Na página de listagem, encontre e clique no link/botão de "Sair". | Login redireciona para a listagem de tarefas. Logout redireciona de volta para a tela de login. |
| **4. Ciclo de Vida de uma Tarefa** | 1. Faça o login. 2. Clique em "Cadastrar Nova Tarefa". 3. Preencha todos os campos e salve. 4. Verifique se a nova tarefa aparece na lista. 5. Clique em "Editar" na nova tarefa. 6. Altere o título e salve. 7. Verifique se o título foi atualizado na lista. 8. Clique em "Concluir". 9. Verifique se a tarefa desapareceu da lista (pois o filtro padrão é "Em Andamento"). 10. Clique em "Excluir" em outra tarefa. | Todas as ações devem ser refletidas na tabela com mensagens de sucesso. |
| **5. Filtros e Paginação** | 1. Cadastre 6 tarefas com diferentes responsáveis. 2. Verifique se apenas 5 aparecem e a paginação está ativa. 3. Use o filtro de "Responsável" para buscar um nome específico. | A tabela deve exibir apenas os resultados que correspondem ao filtro aplicado. |


## Como Executar o Projeto Localmente

### Pré-requisitos
1.  **JDK 8**
2.  **Apache Maven 3.6+**
3.  **PostgreSQL**
4.  **Apache Tomcat 9.0**

### Passos
1.  **Clone o Repositório:**
    ```shell
    git clone [https://github.com/seu-usuario/esig-gestor-tarefas.git](https://github.com/seu-usuario/esig-gestor-tarefas.git)
    cd esig-gestor-tarefas
    ```
2.  **Configure o Banco de Dados:**
    * Crie um banco de dados no PostgreSQL chamado `esig_tarefas`.
    * Se necessário, altere o usuário e senha no arquivo `src/main/resources/META-INF/persistence.xml`.

3.  **Compile com o Maven:**
    ```shell
    mvn clean install
    ```
4.  **Implante no Tomcat:**
    * Copie o arquivo `target/esig-gestor-tarefas-1.0-SNAPSHOT.war` para a pasta `webapps` do seu Tomcat 9.
    * Inicie o servidor.

5.  **Acesse a Aplicação:**
    * `http://localhost:8080/esig-gestor-tarefas-1.0-SNAPSHOT/`

### Credenciais
A aplicação não cria um usuário padrão. Por favor, utilize a tela de **"Cadastre-se"** para criar sua primeira conta e acessar o sistema.
