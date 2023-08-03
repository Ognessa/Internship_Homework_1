package com.onix.internship.ui.gallery.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.util.Timer
import java.util.TimerTask

class AudioRecorderImpl(
    private val context: Context
) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var timer: Timer? = null
    private var timeListener: RecordedTimeListener? = null
    private var time: Long = 0

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)
            setAudioSamplingRate(44100)
            setAudioChannels(1)
            setAudioEncodingBitRate(64000)

            setOnErrorListener { mr, what, extra ->
                Log.d("DEBUG", "Error: ${what} -> ${extra}")
            }

            try {
                prepare()
                start()
                startTimer()
                recorder = this
            } catch (e: Exception) {
                stop()
                reset()
                e.printStackTrace()
            }
        }
    }

    private fun startTimer() {
        timer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    time += TIMER_PERIOD
                    timeListener?.onTimeUpdated(time)
                }
            }, TIMER_PERIOD, TIMER_PERIOD)
        }
    }

    override fun stop() {
        recorder?.let {
            it.stop()
            it.reset()
            timer?.cancel()
            timer = null
            time = 0
        }
        recorder = null
    }

    override fun setRecorderListener(action: (Long) -> Unit) {
        timeListener = object : RecordedTimeListener {
            override fun onTimeUpdated(millis: Long) {
                action.invoke(time)
            }
        }
    }

    interface RecordedTimeListener {
        fun onTimeUpdated(millis: Long)
    }

    companion object {
        private const val TIMER_PERIOD: Long = 100
    }
}