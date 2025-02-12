package com.whitesoul.beauty.util

import com.whitesoul.beauty.api.getNYAPIImage
import com.whitesoul.beauty.api.getXXAPIImage
import com.whitesoul.beauty.api.getYHAPIImage
import com.whitesoul.beauty.api.getYMAPIImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

// 获取黑丝
    suspend fun getHeiSiImage(): InputStream {
        val url = URL(getXXAPIImage("heisi"))
        val uc = withContext(Dispatchers.IO) {
            url.openConnection()
        }
        return withContext(Dispatchers.IO) {
            uc.getInputStream()
        }
    }
   // 获取白丝
   suspend fun getBaiSiImage(): InputStream {
    val url = URL(getXXAPIImage("baisi"))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
  // 获取美女
  suspend fun getMeiNvImage(): InputStream {
      val url = URL(getXXAPIImage("meinvpic"))
      val uc = withContext(Dispatchers.IO) {
          url.openConnection()
      }
      return withContext(Dispatchers.IO) {
          uc.getInputStream()
      }
  }
// 获取原神Cos
suspend fun getYuanShenCosImage(): InputStream {
    val url = URL(getXXAPIImage("yscos"))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
// 获取jk
suspend fun getJKImage(): InputStream {
    val url = URL(getXXAPIImage("jk"))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
// 获取妹子
suspend fun getMeiZiImage(): InputStream {
    val url = URL(getYMAPIImage("meizi"))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
// 获取淘宝
suspend fun getTaoBaoImage(): InputStream {
    val url = URL(getYMAPIImage("taobao"))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
// 获取网红
suspend fun getWangHongImage(): InputStream {
    val url = URL(getYMAPIImage("kuaishou"))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
// 获取Cos
suspend fun getCosImage(): InputStream {
    val url = URL(getYMAPIImage("cos"))
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
// 获取美腿
suspend fun getMeiTuiImage(): InputStream {
    val url = URL(getNYAPIImage())
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}
// 获取ACG
suspend fun getACGImage(): InputStream {
    val url = URL(getYHAPIImage())
    val uc = withContext(Dispatchers.IO) {
        url.openConnection()
    }
    return withContext(Dispatchers.IO) {
        uc.getInputStream()
    }
}