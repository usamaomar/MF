enum class BuildConfigFields(val label: String) {
    IdentityBaseUrl("IDENTITY_BASE_URL"),
}

enum class Protocol(val value: String) {
    HTTP("http://"),
    HTTPS("https://")
}

enum class Environment(val domain: String) {
    Production("com"),
    Development("group")
}

enum class Version(val value: String) {
    V1("v1")
}

enum class Scope(val value: String) {
    Identity("identity"),
    Content("content"),
    Media("media"),
    Engagement("engagement"),
    Payment("payment");
}

fun Scope.toUrl(environment: Environment, protocol: Protocol): String {
    return "${protocol.value}${value}.${environment.domain}/"
}