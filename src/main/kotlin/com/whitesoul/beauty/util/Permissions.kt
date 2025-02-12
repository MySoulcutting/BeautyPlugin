package com.whitesoul.beauty.util

import net.mamoe.mirai.console.permission.Permission
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.console.permission.RootPermission

val PERM_DRL = permission("noratelimit")
val PERM_DRL_IMAGE = permission("noratelimit", "image", parent = PERM_DRL)
val PERM_DRL_VIDEO = permission("noratelimit", "video", parent = PERM_DRL)

private fun permission(vararg nodes: String, parent: Permission = RootPermission) =
    PermissionService.INSTANCE.register(PermissionId("beauty", nodes.joinToString(".")), "BeautyPlugin permission ${nodes.joinToString(".")}", parent)
