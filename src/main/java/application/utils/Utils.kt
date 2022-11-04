package application.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> T.getLogger(): Logger = LoggerFactory.getLogger(
    if (T::class.isCompanion) T::class.java.enclosingClass else T::class.java
)
fun String?.words() = orEmpty().split(" ")

fun Enum<*>.lowercaseName() = name.lowercase()