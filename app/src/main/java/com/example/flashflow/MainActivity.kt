package com.example.flashflow

import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private var isFlashOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager


        val flashlightButton: Button = findViewById(R.id.flashlightButton)
        flashlightButton.setOnClickListener {
            toggleFlashlight()
        }
    }

    private fun toggleFlashlight() {
        try {
            if (!isFlashOn) {
                if (hasFlash()) {
                    cameraId = cameraManager.cameraIdList[0]
                    cameraManager.setTorchMode(cameraId!!, true)
                    isFlashOn = true
                }
            } else {
                cameraManager.setTorchMode(cameraId!!, false)
                isFlashOn = false
            }
        } catch (e: CameraAccessException) {
            Log.e("FlashlightApp", "Error accessing camera: ${e.message}")
        }
    }

    private fun hasFlash(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    override fun onPause() {
        super.onPause()
        if (isFlashOn) {
            cameraManager.setTorchMode(cameraId!!, false)
            isFlashOn = false
        }
    }
}