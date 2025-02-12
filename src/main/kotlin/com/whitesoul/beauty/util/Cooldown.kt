package com.whitesoul.aichat.util
import java.util.concurrent.ConcurrentHashMap

class Cooldown {
    // 使用线程安全的 ConcurrentHashMap 存储冷却时间数据
    // 键格式为 "玩家UUID_冷却时间Key"，值为冷却结束的时间戳（秒）
    private val cooldowns: MutableMap<String, Long> = ConcurrentHashMap()

    /**
     * 为玩家设置冷却时间。
     *
     * @param player 玩家对象
     * @param cooldownKey 冷却时间的唯一标识（例如命令名称）
     * @param duration 冷却时间长度（秒）
     */
    fun setCooldown(player: Long, cooldownKey: String, duration: Long) {
        val currentTime = System.currentTimeMillis() / 1000 // 转换为秒
        cooldowns["${player}_$cooldownKey"] = currentTime + duration
    }

    /**
     * 获取玩家剩余的冷却时间。
     *
     * @param player 玩家对象
     * @param cooldownKey 冷却时间的唯一标识
     * @return 剩余的冷却时间（秒），如果冷却时间已过或不存在，则返回 0
     */
    fun getCooldown(player: Long, cooldownKey: String): Long {
        val key = "${player}_$cooldownKey"
        val endTime = cooldowns[key] ?: return 0 // 如果不存在冷却时间，返回 0
        val currentTime = System.currentTimeMillis() / 1000 // 转换为秒

        return if (currentTime >= endTime) {
            cooldowns.remove(key) // 如果冷却时间已过，移除并返回 0
            0
        } else {
            endTime - currentTime // 返回剩余的冷却时间（秒）
        }
    }

    /**
     * 检查玩家是否处于冷却时间中。
     *
     * @param player 玩家对象
     * @param cooldownKey 冷却时间的唯一标识
     * @return 如果玩家处于冷却时间中，返回 true；否则返回 false
     */
    fun hasCooldown(player: Long, cooldownKey: String): Boolean {
        return getCooldown(player, cooldownKey) > 0
    }

    /**
     * 清除玩家的冷却时间。
     *
     * @param player 玩家对象
     * @param cooldownKey 冷却时间的唯一标识
     */
    fun clearCooldown(player: Long, cooldownKey: String) {
        cooldowns.remove("${player}_$cooldownKey")
    }
    /**
     * 清空所有玩家的冷却时间。
     *
     */
    fun clearCooldownCache() {
        cooldowns.clear()
    }
}