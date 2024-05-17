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
            return getUriForFile(context, authority, file)
        }

        fun copyUriToPrivateFileDir(context: Context, sourceUri: Uri, destFileName: String) {
            if (sourceUri.toString().endsWith(destFileName)) return
            val destFile = File(context.filesDir, "files/$destFileName")
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                inputStream = context.contentResolver.openInputStream(sourceUri)
                outputStream = destFile.outputStream()

                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream?.read(buffer).also { length = it ?: 0 } != -1) {
                    outputStream.write(buffer, 0, length)
                }
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        }
    }
}