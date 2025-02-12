package com.whitesoul.aichat.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class XxAPI {
    @Serializable
    data class ApiResponse(
        val code: Int,
        val msg: String,
        val data: String,
        val request_id: String
    )
}

/**
 * 从指定URL获取JSON数据并解析出data字段
 *
 * @param url 目标URL
 * @return data字段的值，如果请求失败或解析失败则返回null
 */
suspend fun getXXAPIImage(api: String): String? {
    return try {
        val client = HttpClient(CIO) // 创建 Ktor HTTP 客户端
        val response: HttpResponse = client.get("https://v2.xxapi.cn/api/$api") // 发送 GET 请求
        val jsonString = response.bodyAsText() // 获取响应体的文本内容

        // 解析 JSON
        val apiResponse = Json.decodeFromString<XxAPI.ApiResponse>(jsonString)
        apiResponse.data // 返回 data 字段
    } catch (e: Exception) {
        println("请求或解析失败: ${e.message}") // 打印错误信息
        null // 返回 null 表示失败
    }
}
