package com.whitesoul.beauty.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class YujnVideoAPI {
    @Serializable
    data class ApiResponse(
        val code: Int,
        val video_count: Int,
        val title: String,
        val data: String,
        val tips: String,
    )
}
suspend fun getYUJNAPIVideo(): String? {
    return try {
        val client = HttpClient(CIO) // 创建 Ktor HTTP 客户端
        val response: HttpResponse = client.get("https://api.yujn.cn/api/zzxjj.php?type=json") // 发送 GET 请求
        val jsonString = response.bodyAsText() // 获取响应体的文本内容
        // 解析 JSON
        val apiResponse = Json.decodeFromString<YujnVideoAPI.ApiResponse>(jsonString)
        apiResponse.data // 返回  字段
    } catch (e: Exception) {
        println("请求或解析失败: ${e.message}") // 打印错误信息
        null // 返回 null 表示失败
    }
}