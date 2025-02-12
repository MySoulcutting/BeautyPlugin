package com.whitesoul.aichat

import com.whitesoul.aichat.listener.FriendListener
import com.whitesoul.aichat.listener.GroupImageListener
import com.whitesoul.aichat.listener.GroupVideoListener
import com.whitesoul.aichat.util.Cooldown
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent


internal object BeautyPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "com.whitesoul.aichat.BeautyPlugin",
        name = "AiChat",
        version = "1.0.0"
    )
) {
    val cooldownManager = Cooldown()
    val folder = dataFolder
    override fun onEnable() {
        logger.info("AiChat 插件已启用")
        // 创建data文件夹
        dataFolder
        val eventChannel = GlobalEventChannel.parentScope(this)
        // 注册群聊监听
        eventChannel.registerListenerHost(GroupImageListener)
        eventChannel.registerListenerHost(GroupVideoListener)
        // 私聊监听
        eventChannel.registerListenerHost(FriendListener)
        // 自动同意好友
        eventChannel.subscribeAlways<NewFriendRequestEvent> {
            accept()
        }
        // 自动同意加群
        eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent> {
            accept()
        }
    }
}