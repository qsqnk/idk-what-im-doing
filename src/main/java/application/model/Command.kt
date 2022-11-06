package application.model

enum class Command(val argCount: Int? = null) {
    GET(argCount = 1),
    WORD_COUNT(argCount = 1),
    QUESTION,
    FIND_MESSAGE(argCount = 1),
}