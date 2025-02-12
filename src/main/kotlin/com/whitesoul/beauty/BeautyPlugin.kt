package com.whitesoul.beauty

import com.whitesoul.beauty.command.ImageCommand
import com.whitesoul.beauty.command.VideoCommand
import com.whitesoul.beauty.listener.friend.FriendImageListener
import com.whitesoul.beauty.listener.friend.FriendVideoListener
import com.whitesoul.beauty.listener.group.GroupImageListener
import com.whitesoul.beauty.listener.group.GroupVideoListener
import com.whitesoul.beauty.util.Cooldown
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent


internal object BeautyPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "com.whitesoul.aichat.BeautyPlugin",
        name = "Beauty",
        version = "1.0.0"
    ) {
        author("WhiteSoul")
        info("QQ: 1462958459")
    }
) {

    val cooldownManager = Cooldown()
    override fun onEnable() {
        logger.info("Beauty 插件已启用")
        val eventChannel = GlobalEventChannel.parentScope(this)

        CommandManager.registerCommand(ImageCommand)
        CommandManager.registerCommand(VideoCommand)

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