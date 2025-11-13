# 🚀 AlgaMoney API - Deployment no Render

## 📦 Estrutura do Projeto

```
algamoney-api/
├── .dockerignore
├── .gitignore
├── Dockerfile
├── GUIA_TESTES_OAUTH2.txt
├── mvnw / mvnw.cmd
├── pom.xml
├── render.yaml                    # Configuração do Render
│
├── src/
│   ├── main/
│   │   ├── java/com/algaworks/algamoney/algamoney_api/
│   │   │   ├── AlgamoneyApiApplication.java
│   │   │   │
│   │   │   ├── config/                    # Configurações
│   │   │   │   ├── ResourceServerConfig.java
│   │   │   │   ├── TokenService.java
│   │   │   │   └── property/
│   │   │   │       └── AlgamoneyApiProperty.java
│   │   │   │
│   │   │   ├── model/                     # Entidades JPA
│   │   │   │   ├── Categoria.java
│   │   │   │   ├── Pessoa.java
│   │   │   │   ├── Lancamento.java
│   │   │   │   ├── Usuario.java
│   │   │   │   ├── Permissao.java
│   │   │   │   └── RefreshToken.java
│   │   │   │
│   │   │   ├── repository/                # Repositórios JPA
│   │   │   │   ├── CategoriaRepository.java
│   │   │   │   ├── PessoaRepository.java
│   │   │   │   ├── LancamentoRepository.java
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   ├── RefreshTokenRepository.java
│   │   │   │   ├── filter/
│   │   │   │   │   └── LancamentoFilter.java
│   │   │   │   ├── lancamento/
│   │   │   │   │   ├── LancamentoRepositoryImpl.java
│   │   │   │   │   └── LancamentoRespositoryQuery.java
│   │   │   │   └── projection/
│   │   │   │       └── ResumoLancamentos.java
│   │   │   │
│   │   │   ├── resource/                  # Controllers REST
│   │   │   │   ├── AuthController.java    # Login/Refresh Token
│   │   │   │   ├── TokenResource.java     # Revoke Token
│   │   │   │   ├── CategoriaResource.java
│   │   │   │   ├── PessoaResource.java
│   │   │   │   └── LancamentoResource.java
│   │   │   │
│   │   │   ├── security/                  # Segurança
│   │   │   │   └── AppUserDetailsService.java
│   │   │   │
│   │   │   ├── service/                   # Regras de negócio
│   │   │   │   ├── LancamentoService.java
│   │   │   │   ├── PessoaService.java
│   │   │   │   ├── RefreshTokenService.java
│   │   │   │   └── exception/
│   │   │   │       └── PessoaInexistenteOuInativaException.java
│   │   │   │
│   │   │   ├── event/                     # Eventos
│   │   │   │   ├── RecursoCriadoEvent.java
│   │   │   │   └── listener/
│   │   │   │       └── RecursoCriadoListener.java
│   │   │   │
│   │   │   └── exceptionhandler/         # Tratamento de erros
│   │   │       └── AlgamoneyExceptionHandler.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       ├── messages.properties
│   │       ├── ValidationMessages.properties
│   │       └── db/migration/              # Flyway migrations
│   │           ├── V01__criar_e_registrar_categorias.sql
│   │           ├── V02__criar_e_resgistrar_pessoas.sql
│   │           ├── V03__criar_tabela_pessoa.sql
│   │           ├── V04__alterar_tabela_pessoa.sql
│   │           ├── V05__adicionar_cep_pessoa.sql
│   │           ├── V06__criar_e_registrar_lancamentos.sql
│   │           ├── V07__criar_tabela_refresh_token.sql
│   │           └── V08__criar_e_registrar_usuarios_e_permissoes.sql
│   │
│   └── test/
│       └── java/com/algaworks/algamoney/algamoney_api/
│           └── AlgamoneyApiApplicationTests.java
│
└── target/                               # Build output (gerado)
    └── algamoney-api-0.0.1-SNAPSHOT.jar
```

## 🌐 Deploy no Render

### URL de Produção
```
https://apijavamoney.onrender.com
```

### Passo a passo:

1. **Commit e Push para GitHub:**
   ```bash
   git add .
   git commit -m "Configuração para produção no Render"
   git push origin main
   ```

2. **No Render Dashboard:**
   - Crie um novo **Web Service**
   - Conecte ao repositório GitHub: `FilipeGH03/ApiJavaMoney`
   - Branch: `main`
   - O Render detectará automaticamente o `render.yaml`

3. **Configuração Automática:**
   O arquivo `render.yaml` já configura:
   - ✅ Ambiente Docker
   - ✅ Profile Spring: `prod`
   - ✅ Banco de dados PostgreSQL/MySQL
   - ✅ Variáveis de ambiente

4. **Variáveis de Ambiente (caso manual):**
   ```
   SPRING_PROFILES_ACTIVE=prod
   DATABASE_URL=jdbc:mysql://host:port/database
   DATABASE_USERNAME=user
   DATABASE_PASSWORD=password
   ```

## 🔐 Segurança

### Profiles:
- **dev**: HTTP, localhost CORS
- **prod**: HTTPS, Render URL CORS

### Autenticação:
- JWT com RSA-2048
- Refresh Token em HttpOnly Cookie
- Permissões baseadas em roles

### Usuários Padrão:
```
admin@algamoney.com / admin (todas permissões)
maria@algamoney.com / maria (apenas leitura)
```

## 📚 Endpoints Principais

### Autenticação:
```
POST /oauth/token              # Login
POST /oauth/token/refresh      # Renovar token
POST /oauth/token/revoke       # Logout
DELETE /tokens/revoke          # Logout (alternativo)
```

### Recursos:
```
GET/POST    /categorias
GET/POST/PUT/DELETE  /pessoas
GET/POST/DELETE      /lancamentos
GET /lancamentos?resumo        # Projeção resumida
```

## 🛠️ Tecnologias

- **Spring Boot 3.5.7**
- **Java 21**
- **Spring Security 6** + JWT
- **MySQL 8.0**
- **Flyway** (migrations)
- **Docker** (deployment)
- **Maven** (build)

## 📝 Comandos Úteis

```bash
# Build local
./mvnw clean package -DskipTests

# Build Docker
docker build -t algamoney-api .

# Run Docker
docker run -p 8080:8080 algamoney-api

# Run com profile prod
java -jar target/algamoney-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

---

**Desenvolvido por:** FilipeGH03  
**Repositório:** https://github.com/FilipeGH03/ApiJavaMoney
