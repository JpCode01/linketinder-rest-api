package linketinder.rest.api

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        inicializarRoles()
        inicializarUsuarioTeste()
    }

    @Transactional
    void inicializarRoles() {
        List<String> authorities = [
                'ROLE_CANDIDATO',
                'ROLE_EMPRESA',
                'ROLE_RECRUTADOR'
        ]

        authorities.each {String authority ->
            if (!Role.findByAuthority(authority)) {
                new Role(authority: authority)
                .save(failOnError: true)
            }
        }
    }

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

    def destroy = {
    }
}