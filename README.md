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
* etc.