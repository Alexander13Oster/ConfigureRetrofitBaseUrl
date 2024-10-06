package de.alexander13oster.runtimeinterceptor

enum class Environment(val host: String, val port: Int) {
    PROD("yesno.wtf", 443),
    LOCAL("localhost", 8080)
}