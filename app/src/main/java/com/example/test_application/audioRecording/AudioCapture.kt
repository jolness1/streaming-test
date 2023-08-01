package com.example.test_application.audioRecording
import android.media.MediaRecorder

class AudioCapture {
    private val recorder: MediaRecorder = MediaRecorder()

    init {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
    }

    fun startRecording(outputFile: String) {
        recorder.setOutputFile(outputFile)
        recorder.prepare()
        recorder.start()
    }

    fun stopRecording() {
        recorder.stop()
        recorder.reset()
        recorder.release()
    }
}