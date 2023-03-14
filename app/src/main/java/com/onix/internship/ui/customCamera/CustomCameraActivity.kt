package com.onix.internship.ui.customCamera

import android.content.Context
import android.hardware.camera2.*
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.view.Surface
import android.view.SurfaceView
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import com.onix.internship.R
import com.onix.internship.arch.ui.activity.BaseActivity
import com.onix.internship.databinding.ActivityCustomCameraBinding
import java.util.*


class CustomCameraActivity :
    BaseActivity<ActivityCustomCameraBinding>(R.layout.activity_custom_camera) {

    private lateinit var cameraManager: CameraManager
    private var cameraDevice: CameraDevice? = null
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var previewSurfaceView: SurfaceView
    private lateinit var previewSurface: Surface

    private val mediaRecorder = MediaRecorder()

    private var isRecording = false

    private lateinit var captureButton: Button
    private lateinit var switchButton: Button
    override val navController: NavController by lazy { NavController(this) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        // Initialize UI elements
        previewSurfaceView = binding.previewSurfaceView
        captureButton = binding.captureButton
        switchButton = binding.switchButton

        // Initialize CameraManager
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = CameraCharacteristics.LENS_FACING_BACK.toString()
        cameraManager.openCamera(cameraId, cameraStateCallback, null)

        // Set up button click listeners
        captureButton.setOnClickListener {
            if (isRecording) {
                stopRecording()
            } else {
                startRecording()
            }
        }

        switchButton.setOnClickListener {
            switchCamera()
        }
    }

    private val cameraStateCallback: CameraDevice.StateCallback =
        object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera;
                try {
                    previewSurface = previewSurfaceView.getHolder().getSurface();
                    camera.createCaptureSession(
                        Collections.singletonList(previewSurface),
                        cameraCaptureSessionCallback, null
                    )
                } catch (e: CameraAccessException) {
                    e.printStackTrace();
                }
            }

            override fun onDisconnected(camera: CameraDevice) {
                camera.close()
                cameraDevice = null
            }

            override fun onError(camera: CameraDevice, error: Int) {
                camera.close()
                cameraDevice = null
            }
        };

    private val cameraCaptureSessionCallback: CameraCaptureSession.StateCallback =
        object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                cameraCaptureSession = session
                try {
                    val previewRequestBuilder =
                        cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                    previewRequestBuilder.addTarget(previewSurface)
                    cameraCaptureSession.setRepeatingRequest(
                        previewRequestBuilder.build(),
                        null,
                        null
                    )
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }

            override fun onConfigureFailed(session: CameraCaptureSession) {
                Toast.makeText(
                    applicationContext,
                    "Failed to configure camera capture session.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun startRecording() {
        try {
            // Configure media recorder
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT)
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder.setVideoEncodingBitRate(10000000)
            mediaRecorder.setVideoFrameRate(30)
            mediaRecorder.setVideoSize(1280, 720)
            val fileName =
                getExternalFilesDir(Environment.DIRECTORY_MOVIES).toString() + "/video_" + System.currentTimeMillis() + ".mp4"
            mediaRecorder.setOutputFile(fileName)
            mediaRecorder.setOrientationHint(90)
            mediaRecorder.prepare()

            // Configure preview
            val recorderSurface = mediaRecorder.surface
            val surfaces: MutableList<Surface> = ArrayList()
            surfaces.add(previewSurface)
            surfaces.add(recorderSurface)
            val builder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_RECORD)
            builder.addTarget(previewSurface)
            builder.addTarget(recorderSurface)

            // Start recording
            isRecording = true
            mediaRecorder.start()
            cameraCaptureSession.setRepeatingRequest(builder.build(), null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        try {
            // Stop recording
            isRecording = false
            mediaRecorder.stop()
            mediaRecorder.reset()

            // Reset preview
            val builder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            builder.addTarget(previewSurface)
            cameraCaptureSession.setRepeatingRequest(builder.build(), null, null)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun switchCamera() {
        // Close current camera
        cameraDevice!!.close()
        cameraDevice = null

        // Get ID of other camera
        var cameraId: String? = ""
        try {
            for (id in cameraManager.cameraIdList) {
                val characteristics = cameraManager.getCameraCharacteristics(id)
                if (characteristics.get(CameraCharacteristics.LENS_FACING) != CameraCharacteristics.LENS_FACING_BACK) {
                    cameraId = id
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        // Open other camera
        try {
            cameraManager.openCamera(cameraId!!, cameraStateCallback, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        if (cameraDevice != null) {
            cameraDevice!!.close()
            cameraDevice = null
        }
    }

    private fun switchMode() {
        if (isRecording) {
            // Stop recording
            stopRecording()

            // Switch to photo mode
            captureButton.setText(R.string.capture)
            switchButton.setText(R.string.video_mode)
        } else {
            // Switch to video mode
            captureButton.setText(R.string.record)
            switchButton.setText(R.string.photo_mode)
        }
        isRecording = !isRecording
    }
}