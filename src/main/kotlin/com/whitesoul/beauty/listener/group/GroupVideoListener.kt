package com.whitesoul.beauty.listener.group

import com.whitesoul.beauty.BeautyPlugin
import com.whitesoul.beauty.util.*
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.ExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import java.io.File

object GroupVideoListener: ListenerHost {
    @EventHandler
    suspend fun GroupVideo(event: GroupMessageEvent) {
        var video: ExternalResource
        val target = event.group
        val sender = event.sender
        val qq = sender.id
        val message = event.message
        if (message.contentToString().startsWith("video")) {
            if (message.contentToString() == "video") {
                sendNotMessages(message, target)
                return
            }
            // 获取前缀
            if (message.contentToString().split(" ").size == 1) {
                sendNotMessages(message, target)

                return
            }
            val prefixOld = message.contentToString().split(" ")[1]
            // 替换的字符
            val replacements = mapOf(
                "jk" to "jk",
                "欲梦" to "YuMeng",
                "女大" to "NvDa",
                "热舞" to "ReWu",
                "清纯" to "QingCun",
                "蛇姐" to "SheJie",
                "穿搭" to "ChuanDa",
                "高质" to "GaoZhiLiangXiaoJieJie",
                "汉服" to "HanFu",
                "黑丝" to "HeiSi",
                "变装" to "BianZhuang",
                "萝莉" to "LuoLi",
                "白丝" to "BaiSi",
                // 返回API
                "玉足" to "mvyz",
                "甜妹" to "YujnAPI",
                "小姐姐" to "WponAPI",
                "吊带" to "mvdd"
            )
            // 如果没有该类新视频
            if (prefixOld !in replacements.keys) {
                sendNotMessages(message, target)
                return
            }
            // 冷却判断
            if (BeautyPlugin.cooldownManager.hasCooldown(qq, "video")) {
                buildMessageChain {
                    add(QuoteReply(message))
                    add("冷却中, 请稍后再试")
                }.sendTo(target)
                return
            } else {
                if (qq != 1462958459L) {
                    BeautyPlugin.cooldownManager.setCooldown(qq, "video", 60)
                }
          }
            val prefix = replacements[prefixOld] ?: prefixOld
            // 获取视频类型
            video = when (prefix) {
                // 小姐姐API
                "WponAPI" -> {
                    getWponVideo().toExternalResource("mp4")
                }
                "YujnAPI" -> {
                    getYujnVideo().toExternalResource("mp4")
                }
                // 玉足API
                "mvyz" -> {
                    getXaVideo(prefix).toExternalResource("mp4")
                }
                // 吊带API
                "mvdd" -> {
                    getXaVideo(prefix).toExternalResource("mp4")
                }
                // 远梦API
                else -> {
                    getYMVideo(prefix).toExternalResource("mp4")
                }
            }
            // 封面
            val image = getFmImage().toExternalResource("jpg")
            // 生成并发送消息
            val messages = target.uploadShortVideo(image, video, prefix)
            target.sendMessage(messages.toForwardMessage(qq, "小视频", System.currentTimeMillis().toInt()))

        }
    }
    suspend fun sendNotMessages(message: MessageChain, target: Group) {
        buildMessageChain {
            add(QuoteReply(message))
            add("目前视频包含以下类型：\n")
            add("[jk] [欲梦] [女大] [热舞] [清纯] [白丝]\n")
            add("[玉足] [蛇姐] [穿搭] [高质] [汉服] [黑丝] \n")
            add("[变装] [萝莉] [甜妹] [小姐姐] [吊带]\n")
            add("示例:video 黑丝\n")
            add("所有视频均来自网络接口, 如有视频不对还请见谅!")
        }.sendTo(target)
    }
}
