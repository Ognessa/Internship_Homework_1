package com.onix.internship.objects

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.File

class DeviceGalleryInjector(private val context: Context) {

    fun saveMediaIntoGallery(mediaPath: File) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageIntoGallerySdk29(context, mediaPath)
        } else {
            saveImageIntoGallery(context, mediaPath)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageIntoGallerySdk29(context: Context, mediaPath: File) {
        val imageFileName = "image_" + System.currentTimeMillis() + ".png"
        val imageValue = ContentValues().apply {
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/" + "Edited_photo"
            )
            put(MediaStore.Images.Media.TITLE, imageFileName)
            put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val uriSavedImage = context.contentResolver.insert(collection, imageValue) ?: return

        context.contentResolver.openFileDescriptor(uriSavedImage, "w")
            .use { parcelFileDescription ->
                ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescription)
                    .write(mediaPath.readBytes())
            }

        imageValue.clear()
        imageValue.put(MediaStore.Images.Media.IS_PENDING, 0)
        context.contentResolver.update(
            uriSavedImage, imageValue, null, null
        )
    }

    private fun saveImageIntoGallery(context: Context, mediaFilePath: File) {
        val imageFileName = "image_" + System.currentTimeMillis() + ".png"

        val target = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "${Environment.DIRECTORY_PICTURES}/Edited_photo/$imageFileName"
        )

        mediaFilePath.copyTo(target)

        val imageValue = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, imageFileName)
            put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.DATA, target.path)
        }
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageValue)
    }
}