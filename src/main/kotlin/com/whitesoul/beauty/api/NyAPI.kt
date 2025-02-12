package com.whitesoul.aichat.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class NyAPI {
    @Serializable
    data class ApiResponse(
        val code: Int,
        val msg: String,
        val url: String,
    )
}
suspend fun getNYAPIImage(): String? {
    return try {
        val client = HttpClient(CIO) // 创建 Ktor HTTP 客户端
        val response: HttpResponse = client.get("https://api.nuoyo.cn/api/mttp") // 发送 GET 请求
        val jsonString = response.bodyAsText() // 获取响应体的文本内容

        // 解析 JSON
        val apiResponse = Json.decodeFromString<NyAPI.ApiResponse>(jsonString)
        apiResponse.url // 返回 字段
    } catch (e: Exception) {
        println("请求或解析失败: ${e.message}") // 打印错误信息
        null // 返回 null 表示失败
    }
}