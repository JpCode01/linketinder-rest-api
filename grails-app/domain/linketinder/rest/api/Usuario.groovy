package linketinder.rest.api

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='email')
@ToString(includes='email', includeNames=true, includePackage=false)
class Usuario implements Serializable {

    private static final long serialVersionUID = 1

    String email
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Set<Role> getAuthorities() {
        (UsuarioRole.findAllByUsuario(this) as List<UsuarioRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        email nullable: false, blank: false, unique: true
    }

    static mapping = {
        
        password column: '`password`'
    }
}
