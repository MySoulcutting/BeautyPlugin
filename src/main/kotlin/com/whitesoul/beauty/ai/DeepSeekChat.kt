package com.whitesoul.aichat.ai

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Message(val role: String, val content: String)

@Serializable
data class ChatCompletionRequest(
    val model: String,
    val messages: List<Message>
)

@Serializable
data class ChatCompletionResponse(
    val choices: List<Choice>
) {
    @Serializable
    data class Choice(val message: Message)
}

suspend fun getDeepSeek(model: String,system: String, user: String): String {

    // 简单耗时检测
    val startTime = System.currentTimeMillis()
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }
    val apiKey = "sk-cea8dafa65b24034bb18efbc29b615a1" // 替换为你的 DeepSeek API Key
    val request = ChatCompletionRequest(
        model = model,
        messages = listOf(
            Message(
                role = "system",
                content = system
            ),
            Message(
                role = "user",
                content = user
            )
        )
    )

    try {
        val response: ChatCompletionResponse = client.post("https://api.deepseek.com/v1/chat/completions") {
            header(HttpHeaders.Authorization, "Bearer $apiKey")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
        // 打印响应内容
        val reply = response.choices[0].message.content
        val endTime = System.currentTimeMillis()
        println("DeepSeek Ai内容生成耗时: ${endTime - startTime}ms")
        return reply
    } catch (e: Exception) {
        println("DeepSeek Ai内容生成出错: ${e.message}")
        return ("deepseek又炸了,请稍后重试")
    } finally {
        client.close()
    }
}