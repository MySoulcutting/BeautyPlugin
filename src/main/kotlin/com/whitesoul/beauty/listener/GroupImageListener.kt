package com.whitesoul.aichat.listener

import com.whitesoul.aichat.BeautyPlugin
import com.whitesoul.aichat.ai.getDeepSeek
import com.whitesoul.aichat.util.*
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.QuoteReply
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.data.sendTo
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import java.io.File

object GroupImageListener: ListenerHost {

    @EventHandler
    suspend fun GroupImage(event: GroupMessageEvent) {
        val target = event.group
        val sender = event.sender
        val qq = sender.id
        val message = event.message
        if (message.contentToString().startsWith("pic")) {
            if (message.contentToString() == "pic") {
                buildMessageChain {
                    add(QuoteReply(message))
                    add("目前图片包含以下类型：\n")
                    add("[黑丝] [白丝] [美女] [原神] [jk] [acg]\n")
                    add("[妹子] [淘宝] [网红] [cos] [美腿]\n")
                    add("示例:pic 黑丝")
                }.sendTo(target)
                return
            }
            if (BeautyPlugin.cooldownManager.hasCooldown(qq,"pic")) {
                return
            } else {
                if (qq != 1462958459L) {
                    BeautyPlugin.cooldownManager.setCooldown(qq, "pic", 5)
                }
            }
            when (message.contentToString().replaceFirst("pic ","")) {
                "黑丝" -> {
                    val image = target.uploadImage(getHeiSiImage())
                    target.sendMessage(image)
                }

                "白丝" -> {
                    val image = target.uploadImage(getBaiSiImage())
                    target.sendMessage(image)
                }

                "美女" -> {
                    val image = target.uploadImage(getMeiNvImage())
                    target.sendMessage(image)
                }

                "原神" -> {
                    val image = target.uploadImage(getYuanShenCosImage())
                    target.sendMessage(image)
                }

                "jk" -> {
                    val image = target.uploadImage(getJKImage())
                    target.sendMessage(image)
                }
                "妹子" -> {
                    val image = target.uploadImage(getMeiZiImage())
                    target.sendMessage(image)
                }
                "淘宝" -> {
                    val image = target.uploadImage(getTaoBaoImage())
                    target.sendMessage(image)
                }
                "网红" -> {
                    val image = target.uploadImage(getWangHongImage())
                    target.sendMessage(image)
                }
                "cos" -> {
                    val image = target.uploadImage(getCosImage())
                    target.sendMessage(image)
                }
                "美腿" -> {
                    val image = target.uploadImage(getMeiTuiImage())
                    target.sendMessage(image)
                }
                "acg" -> {
                    val image = target.uploadImage(getACGImage())
                    target.sendMessage(image)
                }
                "可乐" -> {
                    if (target.id == 914085636L) {
                        val ex = File("${BeautyPlugin.folder.path}\\可乐.jpg").toExternalResource("jpg")
                        val image = target.uploadImage(ex)
                        target.sendMessage(image)
                    }
                }
                else -> {
                    buildMessageChain {
                        add(QuoteReply(message))
                        add("目前图片包含以下类型：\n")
                        add("[黑丝] [白丝] [美女] [原神] [jk] [acg]\n")
                        add("[妹子] [淘宝] [网红] [cos] [美腿]\n")
                        add("示例:pic 黑丝")
                    }.sendTo(target)
                }
            }
        }
    }
}