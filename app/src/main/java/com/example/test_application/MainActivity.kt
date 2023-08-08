package com.example.test_application

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.test_application.audioRecording.AudioCapture
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent()
        }
    }
}

@Composable
fun MainActivityContent() {
    val context = LocalContext.current
    val audioCapture = remember { AudioCapture() }
    var isRecording by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                isRecording = true
                val outputFile = getExternalStorageFile(context.contentResolver,"/file.3GP")
                audioCapture.startRecording(outputFile)
            }
        ) {
            Text(text = "Now Start Recording")
        }

        if (isRecording) {
            Button(
                onClick = {
                    isRecording = false
                    audioCapture.stopRecording()
                }
            ) {
                Text(text = "Stop Recording")
            }
        }
    }
}

private fun getExternalStorageFile(contentResolver: ContentResolver, fileName: String): String {
    val values = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "audio/3gpp")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC)
    }

    val audioUri = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
    return if (audioUri != null) {
        audioUri.toString()
    } else {
        Log.e("AudioCapture", "Error creating audio file in MediaStore")
        // Return a default path as a fallback
        val externalStorageDir = Environment.getExternalStorageDirectory()
        val appDir = File(externalStorageDir, "TestApplication")
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        File(appDir, fileName).absolutePath
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MainActivityContent()
}
