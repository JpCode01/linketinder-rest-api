package linketinder.rest.api

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        inicializarRoles()
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

    def destroy = {
    }
}