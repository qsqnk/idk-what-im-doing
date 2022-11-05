package web

import application.TelegramBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import web.dto.MessageDto

@RestController
class TelegramBotWebService @Autowired constructor(
    private val telegramBot: TelegramBot,
) {

    @PostMapping("/send/{chatId}")
    fun send(
        @PathVariable chatId: Long,
        @RequestBody message: MessageDto,
    ) {
        telegramBot.send(
            content = message.content,
            chatId = chatId,
        )
    }
}