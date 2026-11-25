# Passo a passo para utilizar JWT e Autorização/Autenticação com diferentes usuários

* 1º Rodar no terminal:.\gradlew.bat runCommand "-Pargs=s2-quickstart linketinder.rest.api Usuario Role"
* 2º Adicionar conf/application → grails.plugin.springsecurity.userLookup.usernamePropertyName = 'email'
* 3º Adicionar em grails-app/init/linketinder/rest/api/BootStrap.groovy ->
  "@Transactional
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
  }"
* 