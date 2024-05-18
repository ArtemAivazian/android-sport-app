package com.aivazart.navigation.view.protein

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.aivazart.navigation.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ComposeFileProvider : FileProvider(R.xml.file_paths) {
    companion object {
        fun getTempImageFile(context: Context): File {
            val directory = File(context.cacheDir, "cache/images")
            if (!directory.exists()) {
                directory.mkdirs()
            }
            return File.createTempFile("profile_image_", ".jpg", directory)
        }

        fun getUriForFile(context: Context, file: File): Uri {
            val authority = context.packageName + ".fileprovider"
            return FileProvider.getUriForFile(context, authority, file)
        }

        fun getImageUri(context: Context, fileName: String): Uri {
            val file = File(context.filesDir, "files/images/$fileName.jpg")
            return getUriForFile(context, file)
        }

        fun saveImageToDirectory(context: Context, bitmap: Bitmap, fileName: String): File? {
            val directory = File(context.filesDir, "files/images")
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, "$fileName.jpg")
            var fos: FileOutputStream? = null
            return try {
                fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                file
            } catch (e: IOException) {
                e.printStackTrace()
                null
            } finally {
                fos?.close()
            }
        }
    }
}