package web.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageDto(
    @JsonProperty("content") val content: String,
)
