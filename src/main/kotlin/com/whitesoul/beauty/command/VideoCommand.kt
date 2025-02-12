package com.whitesoul.beauty.command

import com.whitesoul.beauty.BeautyPlugin
import com.whitesoul.beauty.util.PERM_DRL_VIDEO
import com.whitesoul.beauty.util.getFmImage
import com.whitesoul.beauty.util.getWponVideo
import com.whitesoul.beauty.util.getXaVideo
import com.whitesoul.beauty.util.getYMVideo
import com.whitesoul.beauty.util.getYujnVideo
import com.whitesoul.beauty.util.respond
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.console.command.isNotUser
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.QuoteReply
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.data.toForwardMessage
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource

object VideoCommand: SimpleCommand(BeautyPlugin, "video") {
    private val types = mapOf(
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

    @Handler
    suspend fun CommandContext.execute() {
        this.respond(helpMessage(this.originalMessage))
    }

    @Handler
    suspend fun CommandContext.execute(prefix: String) {
        val rPrefix = types[prefix]
        if (rPrefix == null) {  // invalid
            this.respond(helpMessage(this.originalMessage))
            return
        }

        val target = this.sender
        if (target.isNotUser()) {
            this.respond("此命令只允许用户执行")
        } else {
            val user = target.user!!
            val id = user.id
            if (BeautyPlugin.cooldownManager.hasCooldown(id,"video")) {
                this.respond("达到速率限制，请稍后再试")
                return
            } else {
                if (!sender.hasPermission(PERM_DRL_VIDEO)) {
                    BeautyPlugin.cooldownManager.setCooldown(id, "video", 5)
                }
            }

            val vid = when(rPrefix) {
                "WponAPI" ->
                    getWponVideo()
                "YujnAPI" ->
                    getYujnVideo()
                // 玉足API
                "mvyz" ->
                    getXaVideo(prefix)
                // 吊带API
                "mvdd" ->
                    getXaVideo(prefix)
                // 远梦API
                else ->
                    getYMVideo(prefix)
            }.use { it.toExternalResource("mp4") }
            val cover = getFmImage().use { it.toExternalResource("jpg") }
            val vidUpload = (target.getGroupOrNull() ?: user ).uploadShortVideo(cover, vid)
            target.sendMessage(vidUpload.toForwardMessage(
                id, "小视频", (System.currentTimeMillis() / 1000).toInt()
            ))

            vid.close()
            cover.close()
        }
    }

    suspend fun helpMessage(quoteSource: MessageChain) =
        buildMessageChain {
            add(QuoteReply(quoteSource))
            add("目前视频包含以下类型：\n")
            add("[jk] [欲梦] [女大] [热舞] [清纯] [白丝]\n")
            add("[玉足] [蛇姐] [穿搭] [高质] [汉服] [黑丝] \n")
            add("[变装] [萝莉] [甜妹] [小姐姐] [吊带]\n")
            add("示例:video 黑丝\n")
            add("所有视频均来自网络接口, 如有视频不对还请见谅!")
        }
}