package com.example.test_application.audioRecording
import android.media.MediaRecorder
import java.io.IOException
import android.util.Log


class AudioCapture {
    private var recorder: MediaRecorder? = null
    private var isRecording = false

    init {

    }
    fun startRecording(outputFile: String) {
            recorder = MediaRecorder()
            recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder?.setOutputFile(outputFile)
            try {
                recorder?.prepare()
                recorder?.start()
                isRecording = true
            } catch (e: IOException) {
                Log.e("Not recording","Recording not properly started")
            }
    }

    fun stopRecording() {
            try {
                recorder?.stop()
                recorder?.reset()
            } catch (e: IllegalStateException) {
                Log.e("Not stopping","Recording not properly stopped")
            } finally {
                recorder?.release()
                recorder = null
                isRecording = false
            }
        }
}1