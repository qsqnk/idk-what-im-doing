package application

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.extensions.filters.Filter
import domain.model.MessageCreateRq
import domain.repository.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class TelegramPolling @Autowired constructor(
    private val messageRepository: MessageRepository,
    @Value("\${telegram.bot.token:}") private val telegramBotToken: String,
) {
    private val bot = bot {
        token = telegramBotToken
        dispatch {
            message(
                Filter.All.and(
                    lowercaseNamesOf<Command>()
                        .map { messageWithPrefix(it).not() }
                        .reduce(Filter::and)
                )
            ) {
                val content = message.text ?: return@message
                val sender = message.from?.username ?: return@message
                MessageCreateRq(content = content, sender = sender)
                    .let { messageRepository.save(it) }
            }

            message(commandFilter(Command.GET.lowercaseName(), argCount = 1)) {
                val username = getArgs().first()
                val text = messageRepository
                    .getBySender(username)
                    .takeIf { it.isNotEmpty() }
                    ?.withIndex()
                    ?.joinToString("\n") { (i, message) -> "${i + 1}. ${message.content}" }
                    ?.let("Messages from $username:\n\n"::plus)
                    ?: "No messages from $username"
                send(text)
            }
        }
    }

    @PostConstruct
    fun init() {
        LOG.info("Telegram bot is starting polling")
        bot.startPolling()
    }

    @PreDestroy
    fun tearDown() {
        LOG.info("Telegram bot is finishing polling")
        bot.stopPolling()
    }

    companion object {
        private val LOG = getLogger()
    }
}