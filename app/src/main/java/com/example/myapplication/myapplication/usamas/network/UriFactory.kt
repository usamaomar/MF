package com.example.myapplication.myapplication.usamas.network

interface NewsAPI {
    fun path(): String
    fun scope(): Scope
    fun version(): Version
    fun environment(): Environment
}

interface UrlFactory {
    fun toUrl(api: NewsAPI): String
}

class NewsUrlFactory(private val environment: Environment, private val protocol: Protocol) :
    UrlFactory {
    override fun toUrl(api: NewsAPI): String =
        api.toUrl(environment = environment, protocol = protocol)

}


fun NewsAPI.toUrl(environment: Environment, protocol: Protocol): String {
    return "${protocol.value}${scope().value}.${environment().domain}/${version().value}/${path()}"
}

fun NewsAPI.toUrl(factory: UrlFactory): String {
    return factory.toUrl(this)
}

enum class Protocol(val value: String) {
    HTTP("http://"),
    HTTPS("https://")
}

enum class Environment(val domain: String) {
    OrgDomain("org"),
    IoDomain("io")
}

enum class Version(val value: String) {
    V2("v2"),
    V1("api/1")
}

enum class Scope(val value: String) {
    Newsapi("newsapi"),
    NewsData("newsdata"),
}