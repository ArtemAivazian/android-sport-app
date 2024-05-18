package com.aivazart.navigation.view.protein

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.aivazart.navigation.R
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class ComposeFileProvider : FileProvider(R.xml.file_paths) {
    companion object {
        fun getTempImageFile(context: Context): File {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            return File.createTempFile("profile_image_", ".jpg", directory)
        }

        fun getUriForFile(context: Context, file: File): Uri {
            val authority = context.packageName + ".fileprovider"
            return FileProvider.getUriForFile(context, authority, file)
        }

        fun getImageUri(context: Context, fileName: String): Uri {
            val file = File(context.filesDir, "images/$fileName.jpg")
            return getUriForFile(context, file)
        }
    }
}