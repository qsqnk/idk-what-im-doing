package application

import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.extensions.filters.Filter

fun MessageHandlerEnvironment.send(text: String) {
    bot.sendMessage(
        chatId = ChatId.fromId(message.chat.id),
        text = text,
    )
}

fun MessageHandlerEnvironment.getArgs() =
    message.text
        .orEmpty()
        .split(" ")
        .drop(1)

fun commandFilter(command: Command) =
    Filter.Custom {
        text.orEmpty()
            .run {
                startsWith(command.lowercaseName())
                    && split(" ").size - 1 == command.argCount
            }
    }

fun excludeCommandsFilter(vararg commands: Command) =
    commands
        .map { messageWithPrefix(it.lowercaseName()).not() }
        .reduce(Filter::and)

fun messageWithPrefix(prefix: String) =
    Filter.Custom {
        text.orEmpty().startsWith(prefix)
    }