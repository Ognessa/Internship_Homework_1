package com.onix.internship.data.download

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment


class DownloadManager(val context: Context) {

    private val manager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

    fun downloadRequest(fileName : String, url : String){
        val request: DownloadManager.Request =
            DownloadManager.Request(Uri.parse(url))
        request.setTitle(fileName)
            .setDescription("File is downloading...")
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS, fileName
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        manager.enqueue(request)
    }

}