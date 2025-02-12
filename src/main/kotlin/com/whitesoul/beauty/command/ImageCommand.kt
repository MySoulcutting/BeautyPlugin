package com.whitesoul.beauty.command

import com.whitesoul.beauty.BeautyPlugin
import com.whitesoul.beauty.util.PERM_DRL_IMAGE
import com.whitesoul.beauty.util.getACGImage
import com.whitesoul.beauty.util.getBaiSiImage
import com.whitesoul.beauty.util.getCosImage
import com.whitesoul.beauty.util.getHeiSiImage
import com.whitesoul.beauty.util.getJKImage
import com.whitesoul.beauty.util.getMeiNvImage
import com.whitesoul.beauty.util.getMeiTuiImage
import com.whitesoul.beauty.util.getMeiZiImage
import com.whitesoul.beauty.util.getTaoBaoImage
import com.whitesoul.beauty.util.getWangHongImage
import com.whitesoul.beauty.util.getYuanShenCosImage
import com.whitesoul.beauty.util.respond
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.console.command.isNotUser
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.QuoteReply
import net.mamoe.mirai.message.data.buildMessageChain
import java.io.InputStream


object ImageCommand: SimpleCommand(BeautyPlugin, "pic") {
    private val types = hashMapOf<String, suspend () -> InputStream>(
        "黑丝" to ::getHeiSiImage,
        "白丝" to ::getBaiSiImage,
        "美女" to ::getMeiNvImage,
        "原神" to ::getYuanShenCosImage,
        "jk" to ::getJKImage,
        "妹子" to ::getMeiZiImage,
        "淘宝" to ::getTaoBaoImage,
        "网红" to ::getWangHongImage,
        "cos" to ::getCosImage,
        "美腿" to ::getMeiTuiImage,
        "acg" to ::getACGImage
    )

    @Handler
    suspend fun CommandContext.execute() {
        this.respond(helpMessage(this.originalMessage))
    }

    @Handler
    suspend fun CommandContext.execute(type: String) {
        val target = this.sender
        val getter = types[type.lowercase()]

        if (target.isNotUser()) {
            this.respond("此命令只允许用户执行")
        } else if (getter == null) {   // 无效
            this.respond(helpMessage(this.originalMessage))
        } else {
            val user = target.user!!
            val id = user.id
            if (BeautyPlugin.cooldownManager.hasCooldown(id,"pic")) {
                this.respond("达到速率限制，请稍后再试")
                return
            } else {
                if (!sender.hasPermission(PERM_DRL_IMAGE)) {
                    BeautyPlugin.cooldownManager.setCooldown(id, "pic", 5)
                }
            }

            val image = getter().use { (target.getGroupOrNull() ?: user).uploadImage(it) }
            target.sendMessage(image)
        }
    }

    private fun helpMessage(quoteSource: MessageChain) = buildMessageChain {
        add(QuoteReply(quoteSource))
        add("目前图片包含以下类型：\n")
        add("[黑丝] [白丝] [美女] [原神] [jk] [acg]\n")
        add("[妹子] [淘宝] [网红] [cos] [美腿]\n")
        add("示例:pic 黑丝")
        add("所有图片均来自网络接口, 如有图片不对还请见谅")
    }
}
