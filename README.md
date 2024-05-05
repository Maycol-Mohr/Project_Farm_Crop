# Projeto Agrix realizado durante o Curso de Formação Aceleração em Java na Trybe.

# Tecnologias Utilizadas:

* Java 17;
* Spring Boot;
* Banco de dados MySql;
* Docker;
* JUnit 5 - Testes Unitários;
* Spring Security JWT com Token para autenticaçao e autorização(@Secured ou @PreAuthorize) segundo perfil de Usuários;
* API Rest;
* Projeto realizado em camadas: Entities - Repositories - Services - DTOs - Controllers;
* Exceções Customizadas com RunTimeException na camada de serviços;
* ResourceExceptionHandler das exceções customizadas na camada controller;
* Teste de endpoints com o Postman;
* Spring Data Jpa - anotações necessárias nas entidades para elaboração de associações entre classes e criação das tabelas relacionais;
* Buscas Customizadas;
* Auditoria com @EntityListeners(AuditingEntityListener.class);
* Criação do CRUD - casos de uso para cada entidade;
* Banco H2;
* Configuração de perfil(dev - test - prod) em applicacion.properties;
* IntelliJ IDE;
* Git;
* Hibernate;
* Maven;
* etc.

  # Diagrama do Projeto:

  ![image](https://github.com/Maycol-Mohr/Project_Farm_Crop/assets/80767489/a68c7a41-37cc-43f0-8ddc-fd38a3ac8cda)

  Além destas entidades o projeto conta com as entidades Person (Pessoa) e Role (Papel ou Perfil), para os trabalhos de autenticação e autorização, como por exemplo, o token e permissões a nível de usuários.

  Atributos da Entidade (Classe) Person:

  - id;
  - username;
  - password;
  - role;
 
  Atributo da entidade Role, que é um tipo enumerado:

  - name;

  Os tipos enumerados cadastrados em Role neste projeto são:

  - ADMIN;
  - MANAGER;
  - USER;
  

