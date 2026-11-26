# Passo a passo para utilizar JWT e Autorização/Autenticação com diferentes usuários

* 1º Rodar no terminal:.\gradlew.bat runCommand "-Pargs=s2-quickstart linketinder.rest.api Usuario Role"
* 2º Adicionar conf/application → grails.plugin.springsecurity.userLookup.usernamePropertyName = 'email'
* 3º Adicionar em grails-app/init/linketinder/rest/api/BootStrap.groovy
```
  @Transactional
  void inicializarRoles() {
  List<String> authorities = [
  'ROLE_CANDIDATO',
  'ROLE_EMPRESA',
  'ROLE_RECRUTADOR'
  ]

        authorities.each { String authority ->
            if (!Role.findByAuthority(authority)) {
                new Role(authority: authority)
                    .save(failOnError: true)
            }
        }
  }
```
Adicionar no método init: inicializarRoles()
* 4º Configurar conexão com o BD em grails-app/application.yml
* 5º Em init/linktinder/rest/api, em BootStrap, testar os Roles:
```
    @Transactional
    void inicializarUsuarioTeste() {
        if (!Usuario.findByEmail('teste@linketinder.com')) {
            Usuario usuario = new Usuario(
                    email: 'teste@linketinder.com',
                    password: 'SenhaTeste123'
            ).save(failOnError: true, flush: true)

            Role role = Role.findByAuthority('ROLE_CANDIDATO')

            new UsuarioRole(
                    usuario: usuario,
                    role: role
            ).save(failOnError: true)
        }
    }
```
Adicionar no método init: inicializarUsuarioTeste()
* 6º Incializar com Migrations com Liquibase do Grails modificando build.gradle:
```
buildscript {
    repositories {
        maven { url "https://repo.grails.org/grails/core" }
        mavenCentral()
    }
    dependencies { // Not Published to Gradle Plugin Portal
        classpath "org.grails:grails-gradle-plugin:6.2.4"
        classpath "org.grails.plugins:hibernate5:8.1.0"
        classpath "org.grails.plugins:views-gradle:3.2.3"
     -> classpath 'org.grails.plugins:database-migration:5.0.0'
    }
}
sourceSets {
    main {
        resources {
            srcDir 'grails-app/migrations'
        }
    }
}
dependencies {
    profile "org.grails.profiles:rest-api"
    implementation "org.grails:grails-core"
    implementation "org.grails:grails-logging"
    implementation "org.grails:grails-plugin-databinding"
    implementation "org.grails:grails-plugin-i18n"
    implementation "org.grails:grails-plugin-interceptors"
    implementation "org.grails:grails-plugin-rest"
    implementation "org.grails:grails-plugin-services"
    implementation "org.grails:grails-plugin-url-mappings"
    implementation "org.grails:grails-web-boot"
    implementation "org.grails.plugins:hibernate5"
    implementation "org.grails.plugins:views-json"
    implementation "org.grails.plugins:views-json-templates"
    implementation "org.springframework.boot:spring-boot-autoconfigure"
    implementation "org.springframework.boot:spring-boot-starter"
    implementation "org.springframework.boot:spring-boot-starter-actuator"
    implementation "org.springframework.boot:spring-boot-starter-logging"
    implementation "org.springframework.boot:spring-boot-starter-tomcat"
    implementation "org.springframework.boot:spring-boot-starter-validation"
    implementation 'org.grails.plugins:spring-security-core:6.1.2'
 -> implementation 'org.grails.plugins:database-migration:5.0.0'
 -> implementation 'org.liquibase:liquibase-core:4.19.0'
    compileOnly "io.micronaut:micronaut-inject-groovy"
    console "org.grails:grails-console"
    runtimeOnly "org.postgresql:postgresql"
    runtimeOnly "org.apache.tomcat:tomcat-jdbc"
    testImplementation "io.micronaut:micronaut-inject-groovy"
    testImplementation "org.grails:grails-gorm-testing-support"
    testImplementation "org.grails:views-json-testing-support"
    testImplementation "org.spockframework:spock-core"
}
```
* 7º Modificar application.yml em conf:
```
grails:
 -> plugin:
    databasemigration:
      changelogFileName: changelog.groovy
      updateOnStart: false
      updateOnStartFileName: changelog.groovy
environments:
  development:
    dataSource:
   -> dbCreate: none
      url: ${DB_URL}
```
* 8º Iniciar ChangeLog:
```
.\gradlew.bat runCommand "-Pargs=dbm-generate-gorm-changelog changelog.groovy"
```
* 9º Renomear arquivo criado pelo nome da Migration: ```001-create-security-tables.groovy```, criar a segunda Migration: ``` 002-insert-default-roles.groovy``` (OBS: Se tiver no Intelijj, não crie diretamente uma classe groovy, crie um file e nomeie manualmente a extensão ```.groovy```)
e criar ```changelog.groovy``` vázio.
* Em ```changelog.groovy``` adicionar:
```
databaseChangeLog = {
    include file: '001-create-security-tables.groovy'

    include file: '002-insert-default-roles.groovy'
}
```
* 10º Adicionar a Migration 002:
```
databaseChangeLog = {

    changeSet(author: "linketinder", id: "insert-default-roles") {

        ['ROLE_CANDIDATO', 'ROLE_EMPRESA', 'ROLE_RECRUTADOR'].each { authority ->

            insert(tableName: "role") {
                column(name: "id", valueComputed: "nextval('hibernate_sequence')")
                column(name: "version", valueNumeric: 0)
                column(name: "authority", value: authority)
            }
        }
    }
}
```
* 11º Voltar para a versão original do BootStrap:
```
package linketinder.rest.api

class BootStrap {

    def init = { servletContext ->
    }

    def destroy = {
    }
}
```
* Verificar application.yml:
```
grails:
  plugin:
    databasemigration:
      changelogFileName: changelog.groovy
   -> updateOnStart: true
      updateOnStartFileName: changelog.groovy
```
* 12º Iniciar com Spring Security REST:
```
implementation 'org.grails.plugins:spring-security-core:6.1.2'
implementation 'org.grails.plugins:spring-security-rest:5.0.1'
```
* 13º Em conf, no arquivo application.groovy, adicionar:
```
    // Added by the Spring Security Core plugin:
    grails.plugin.springsecurity.userLookup.userDomainClassName = 'linketinder.rest.api.Usuario'
    grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'linketinder.rest.api.UsuarioRole'
    grails.plugin.springsecurity.authority.className = 'linketinder.rest.api.Role'
    grails.plugin.springsecurity.userLookup.usernamePropertyName = 'email'
 -> grails.plugin.springsecurity.rest.login.active = true
 -> grails.plugin.springsecurity.rest.login.endpointUrl = '/api/v1/auth/login'
 -> grails.plugin.springsecurity.rest.login.useJsonCredentials = true
 -> grails.plugin.springsecurity.rest.login.usernamePropertyName = 'email'
 -> grails.plugin.springsecurity.rest.login.passwordPropertyName = 'password'
    grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/',               access: ['permitAll']],
        [pattern: '/error',          access: ['permitAll']],
        [pattern: '/index',          access: ['permitAll']],
        [pattern: '/index.gsp',      access: ['permitAll']],
        [pattern: '/shutdown',       access: ['permitAll']],
        [pattern: '/assets/**',      access: ['permitAll']],
        [pattern: '/**/js/**',       access: ['permitAll']],
        [pattern: '/**/css/**',      access: ['permitAll']],
        [pattern: '/**/images/**',   access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
    ]
```
* 14º Gerando chave secreta para criptografar Token, no CMD:
```
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
[Convert]::ToBase64String($bytes)
$rng.Dispose()
```
OBS: Copiar resultado em .env ou como estiver lidando com secrets  
* 15º Adicionar em ```application.groovy```:
```
grails.plugin.springsecurity.rest.token.storage.jwt.useSignedJwt = true
grails.plugin.springsecurity.rest.token.storage.jwt.secret = System.getenv('JWT_SECRET')
grails.plugin.springsecurity.rest.token.generation.jwt.algorithm = 'HS256'
grails.plugin.springsecurity.rest.token.storage.jwt.expiration = 900
grails.plugin.springsecurity.rest.token.storage.jwt.refreshExpiration = 604800
```

## Explicação rápida do fluxo

* 1º Ao realizar login, User recebe Access Token e Refresh Token

* 2º Quando um Access Token é validado, o User recebe as autorizações respectivas de seu nível de acesso.

* 3º Access Token expira, o Refresh Token permite renovação

* 4º Um novo Access Token é gerado

* 5º Quando o Refresh Token expirar, será necessário que o User se autentique novamente 