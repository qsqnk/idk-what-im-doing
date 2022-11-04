package application.utils

import application.model.Command
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
        .words()
        .drop(1)

fun commandFilter(command: Command) =
    Filter.Custom {
        text.words()
            .run {
                firstOrNull() == command.lowercaseName() && size == command.argCount + 1
            }
    }

fun excludeCommandsFilter(vararg commands: Command) =
    commands
        .map { commandFilter(it).not() }
        .fold(Filter.All, Filter::and)
