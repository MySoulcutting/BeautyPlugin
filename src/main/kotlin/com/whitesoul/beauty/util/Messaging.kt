package com.whitesoul.beauty.util

import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText

suspend fun CommandContext.respond(message: Message) =
    this.sender.sendMessage(message)

suspend fun CommandContext.respond(text: CharSequence) = this.respond(PlainText(text.toString()))
