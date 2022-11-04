package application

import org.slf4j.LoggerFactory

inline fun <reified T> T.getLogger() = LoggerFactory.getLogger(
    if (T::class.isCompanion) T::class.java.enclosingClass else T::class.java
)

inline fun <reified T : Enum<T>> lowercaseNamesOf() = enumValues<T>().map { it.name.lowercase() }

fun Enum<*>.lowercaseName() = name.lowercase()