package com.whitesoul.aichat.util

import com.whitesoul.aichat.api.getWponAPIVideo
import com.whitesoul.aichat.api.getXaVideoAPI
import com.whitesoul.aichat.api.getYMAPIVideo
import com.whitesoul.aichat.api.getYUJNAPIVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

// 获取远梦API视频
suspend fun getYMVideo(api: String): InputStream {
    val url = URL(getYMAPIVideo(api))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
// 获取WponAPI视频
suspend fun getWponVideo(): InputStream {
    val url = URL("https:${getWponAPIVideo()}")
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()

    }
}
// 获取YujnAPI视频
suspend fun getYujnVideo(): InputStream {
    val url = URL(getYUJNAPIVideo())
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()

    }
}
// 获取小哎API视频
suspend fun getXaVideo(api: String): InputStream {
    val url = URL(getXaVideoAPI(api))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()

    }
}