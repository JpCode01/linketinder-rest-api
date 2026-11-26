// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'linketinder.rest.api.Usuario'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'linketinder.rest.api.UsuarioRole'
grails.plugin.springsecurity.authority.className = 'linketinder.rest.api.Role'
grails.plugin.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugin.springsecurity.rest.login.active = true
grails.plugin.springsecurity.rest.login.endpointUrl = '/api/v1/auth/login'
grails.plugin.springsecurity.rest.login.useJsonCredentials = true
grails.plugin.springsecurity.rest.login.usernamePropertyName = 'email'
grails.plugin.springsecurity.rest.login.passwordPropertyName = 'password'
grails.plugin.springsecurity.rest.token.storage.jwt.useSignedJwt = true
grails.plugin.springsecurity.rest.token.storage.jwt.secret = System.getenv('JWT_SECRET')
grails.plugin.springsecurity.rest.token.generation.jwt.algorithm = 'HS256'
grails.plugin.springsecurity.rest.token.storage.jwt.expiration = 900
grails.plugin.springsecurity.rest.token.storage.jwt.refreshExpiration = 604800
grails.plugin.springsecurity.rest.token.validation.enableAnonymousAccess = true
grails.plugin.springsecurity.rest.token.validation.useBearerToken = true
grails.plugin.springsecurity.rest.token.validation.endpointUrl = '/api/v1/auth/validate'
grails.plugin.springsecurity.securityConfigType = 'Annotation'
grails.plugin.springsecurity.rejectIfNoRule = true
grails.plugin.springsecurity.fii.rejectPublicInvocations = false

grails.plugin.springsecurity.filterChain.chainMap = [
		[
				pattern: '/**',
				filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
		]
]

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
		[pattern: '/api/v1/auth/login', access: ['permitAll'], httpMethod: 'POST'],
		[pattern: '/api/v1/auth/refresh', access: ['permitAll'], httpMethod: 'POST'],
		[pattern: '/api/v1/auth/validate', access: ['isAuthenticated()']],
		[pattern: '/error', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

