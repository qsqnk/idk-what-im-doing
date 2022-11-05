package application

import application.model.Command
import application.utils.commandFilter
import application.utils.excludeCommandsFilter
import application.utils.getArgs
import application.utils.getLogger
import application.utils.send
import application.utils.textAfterCommand
import application.utils.words
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.message
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
            messageHandler()
            commandGetHandler()
            commandWordCountHandler()
            questionHandler()
        }
    }

    private fun Dispatcher.messageHandler() {
        message(excludeCommandsFilter(*Command.values())) {
            val content = message.text ?: return@message
            val sender = message.from?.username ?: return@message
            MessageCreateRq(content = content, sender = sender)
                .let { messageRepository.save(it) }
        }
    }

    private fun Dispatcher.commandGetHandler() {
        message(commandFilter(Command.GET)) {
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

    private fun Dispatcher.commandWordCountHandler() {
        message(commandFilter(Command.WORD_COUNT)) {
            val username = getArgs().first()
            val wordToCount = messageRepository
                .getBySender(username)
                .flatMap { it.content.words() }
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedByDescending { (_, count) -> count }
            val text = wordToCount
                .joinToString("\n") { (word, count) -> "$word: $count" }
                .let("Words used by $username:\n\n"::plus)
            send(text)
        }
    }

    private fun Dispatcher.questionHandler() {
        message(commandFilter(Command.QUESTION)) {
            val question = textAfterCommand()
                .removeSuffix("?")
                .lowercase()
            val probability = (0..100).random()
            send("Вероятность того, что $question - $probability%")
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