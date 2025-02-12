package com.whitesoul.beauty.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


object YmImgAPI {
    @Serializable
    data class ApiResponse(
        val 状态: String,
        val url: String,
    )
}

    /**
     * 从指定URL获取JSON数据并解析出data字段
     *
     * @param url 目标URL
     * @return data字段的值，如果请求失败或解析失败则返回null
     */
    suspend fun getYMAPIImage(api: String): String? {
        return try {
            val client = HttpClient(CIO) // 创建 Ktor HTTP 客户端
            val response: HttpResponse = client.get("https://api.mmp.cc/api/kswallpaper?category=$api&type=json") // 发送 GET 请求
            val jsonString = response.bodyAsText() // 获取响应体的文本内容

            // 解析 JSON
            val apiResponse = Json.decodeFromString<YmImgAPI.ApiResponse>(jsonString)
            apiResponse.url // 返回  字段
        } catch (e: Exception) {
            println("请求或解析失败: ${e.message}") // 打印错误信息
            null // 返回 null 表示失败
        }
    }
