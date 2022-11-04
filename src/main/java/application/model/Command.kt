package application.model

enum class Command(val argCount: Int) {
    GET(argCount = 1),
    WORD_COUNT(argCount = 1),
}