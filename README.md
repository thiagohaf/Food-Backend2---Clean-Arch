# Food Backend 2 (Fase 2) - Clean Architecture

Este repositório é a entrega oficial da **Fase 2 do Tech Challenge** para o projeto **Food Backend 2**. O sistema atende a um grupo de restaurantes locais que precisa gerenciar cadastros essenciais (tipos de usuários, restaurantes e itens de cardápio) de forma robusta, enquanto clientes podem consultar dados e preparar pedidos online.

O desenvolvimento foi realizado **seguindo estritamente Clean Architecture**, priorizando separação de responsabilidades, testabilidade e manutenção.

## 📌 Sobre o Projeto

Na nossa região, um conjunto de restaurantes decidiu contratar estudantes para construir um sistema de gestão compartilhado. O objetivo do sistema é permitir que:

- Restaurantes gerenciem suas operações de forma eficiente (cadastro e organização do que oferecem);
- Clientes encontrem informações com base na comida disponível (organização do cardápio);
- A solução seja evolutiva por fases, sem comprometer qualidade e estrutura do código.

Na **Fase 2**, expandimos o sistema com:

- **Gestão de tipos de usuários** (diferenciando papéis no sistema);
- **Cadastro de restaurantes** (incluindo dono associado);
- **CRUD de itens do cardápio** (incluindo preço e regra de disponibilidade local).

## 🏛️ Arquitetura (Clean Architecture)

A aplicação foi estruturada com Clean Architecture para isolar regras de negócio de frameworks e detalhes de infraestrutura.

De forma prática:

- A camada **`core`** concentra **regras de negócio**, **entidades** e **use cases** (fluxos da aplicação). Ela não depende de Spring, JPA, controllers ou mecanismos específicos de I/O.
- A camada **`infra`** contém implementações voltadas à execução: **Spring Boot (`@RestController`)**, **persistência (JPA)**, **mapeamentos (MapStruct)**, **gateways/adapters** e detalhes de integração.

Assim, o fluxo típico fica:

- `Controller` (infra) recebe a requisição HTTP e converte para inputs;
- `UseCase` (core) executa a lógica de domínio e validações;
- `Gateway/Repository` (infra) persiste/consulta dados;
- A resposta retorna em DTOs (infra).

## 🚀 Funcionalidades Entregues

A Fase 2 exige CRUDs e regras de negócio específicas. Os domínios implementados foram:

- **Cadastro de Tipo de Usuário (Dono de Restaurante e Cliente)**: Implementamos CRUD de tipos de usuário em `core`/`infra` e associamos o tipo ao usuário no cadastro. Endpoints principais: `/api/v1/tipos-usuario` e `/api/v1/usuarios`.

- **Cadastro de Restaurantes**: CRUD completo com os campos exigidos: **nome**, **endereço**, **tipo de cozinha**, **horário de funcionamento** e **dono do restaurante** (associado a um usuário existente). Validações de regra de negócio no domínio garantem consistência, incluindo o formato do horário. Endpoints principais: `/api/v1/restaurantes`.

- **Cadastro dos Itens do Cardápio**: CRUD completo com os campos exigidos: **nome**, **descrição**, **preço**, **disponibilidade para consumo no restaurante** e **foto do prato**. Conforme orientação do PDF, **não fazemos upload real de imagem**: salvamos e retornamos apenas o **caminho** da foto no campo `fotoPath` (ex.: `"/img/hamburguer.png"`). A regra de “apenas no restaurante” é representada pelo boolean `disponibilidadeLocal` no domínio; na API, o controller mantém a disponibilidade para consumo local. Endpoint principal: `/api/v1/itens-cardapio` (inclui listagem por `restauranteId`).

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3** (Web MVC, JPA)
- **Springdoc OpenAPI UI**
- **MapStruct** (mapeamento entre domínio e persistência/DTO)
- **Flyway** (migrations SQL para MySQL)
- **MySQL** (produção via Docker / infra)
- **Testcontainers** (MySQL real para testes de integração)
- **RestAssured** (testes de API)
- **JUnit 5** e **Hamcrest**
- **JaCoCo** (relatório de cobertura)
- **Lombok**

## 📦 Como Executar a Infraestrutura

1. Acesse a pasta do projeto (onde está o `docker-compose.yml`):
   - `Food Backend2 - Clean Arch`
2. Execute o comando para subir **Java + MySQL integrados**:
   - `docker-compose up -d --build`
3. Aguarde a healthcheck do MySQL e as migrations (Flyway) concluírem.
4. A API estará disponível em:
   - `http://localhost:8080`
   - MySQL exposto em `localhost:3306` com credenciais configuradas no `docker-compose.yml` (root/app).

Observações sobre a integração:

- O serviço `app` depende do `mysql` via `depends_on` com `condition: service_healthy`.
- O `Dockerfile` utiliza build multi-stage (primeiro compila via Maven, depois gera imagem enxuta apenas com JRE).

## 📚 Documentação (Javadoc e Swagger)

### Javadoc

A documentação da API Java (classes e pacotes públicos) é gerada pelo **Maven Javadoc Plugin** e escrita na pasta **`docs/`** na raiz do repositório (índice em [`docs/index.html`](docs/index.html)).

Para (re)gerar após alterações no código:

```bash
mvn javadoc:javadoc
```

Abra `docs/index.html` no navegador (arquivo local) para navegar pelo Javadoc.

### Swagger / OpenAPI (Springdoc)

Com a aplicação em execução (por exemplo em `http://localhost:8080` após `docker-compose up` ou `mvn spring-boot:run`):

| Recurso | URL |
|--------|-----|
| **Swagger UI** (explorar e testar endpoints) | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| **OpenAPI 3 (JSON)** | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) |

A interface Swagger é fornecida pela dependência **springdoc-openapi** (`springdoc-openapi-starter-webmvc-ui` no `pom.xml`).

## 🧪 Qualidade e Testes (Superando a Meta)

O PDF da Fase 2 exige:

- **Testes unitários** com cobertura mínima de **80%**
- **Testes de integração** para garantir que os componentes necessários funcionam em conjunto

O projeto implementa:

- **Testes unitários** para use cases, validadores e mapeamentos (JaCoCo habilitado via `jacoco-maven-plugin`).
- **Testes de integração automatizados** com Testcontainers usando `MySQLContainer` (banco real em Docker) e RestAssured para validar endpoints e retornos JSON.

Além disso, há uma **collection do Postman** para auxiliar a execução manual dos endpoints em `docs/postman/food-backend2-postman.json`.

Evidência de Testcontainers e RestAssured:

- A classe `AbstractIntegrationTest` inicia o `MySQLContainer` e configura dinamicamente `spring.datasource.url`.
- Os testes de integração chamam os endpoints com `given()...when()...then()` (RestAssured).

Cobertura:

- O relatório do **JaCoCo** gerado em `target/site/jacoco/` indica cobertura global acima da meta do PDF.
- Cobertura global do report do JaCoCo: **93.32% (LINE)** e **95.41% (INSTRUCTION)**.

## 🔗 Endpoints da API

Base URL: `http://localhost:8080`

### Tipos de Usuário

- `POST /api/v1/tipos-usuario`
- `GET /api/v1/tipos-usuario`
- `GET /api/v1/tipos-usuario/{id}`
- `PUT /api/v1/tipos-usuario/{id}`
- `DELETE /api/v1/tipos-usuario/{id}`

### Usuários

- `POST /api/v1/usuarios`
- `GET /api/v1/usuarios`
- `GET /api/v1/usuarios/{id}`
- `PUT /api/v1/usuarios/{id}`
- `DELETE /api/v1/usuarios/{id}`

### Restaurantes

- `POST /api/v1/restaurantes`
- `GET /api/v1/restaurantes`
- `GET /api/v1/restaurantes/{id}`
- `PUT /api/v1/restaurantes/{id}`
- `DELETE /api/v1/restaurantes/{id}`

### Itens do Cardápio

- `POST /api/v1/itens-cardapio`
- `GET /api/v1/itens-cardapio` (listagem por restaurante via query param: `GET /api/v1/itens-cardapio?restauranteId={uuid}`)
- `GET /api/v1/itens-cardapio/{id}`
- `PUT /api/v1/itens-cardapio/{id}`
- `DELETE /api/v1/itens-cardapio/{id}`

