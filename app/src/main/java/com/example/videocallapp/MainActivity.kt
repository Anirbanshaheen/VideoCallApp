package com.example.videocallapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    var userRole = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reqPermission()
    }

    private fun reqPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            ), 1
        )
    }

    fun onSubmit(view: View) {
        val channelName = findViewById<View>(R.id.channel) as EditText
        val radioBtn = findViewById<View>(R.id.radioGroup) as RadioGroup

        val checked = radioBtn.checkedRadioButtonId
        val audienceBtn = findViewById<View>(R.id.audience) as RadioButton

        userRole = if (checked == audienceBtn.id) {
            0
        } else {
            1
        }

        val intent = Intent(this,VideoActivity::class.java)
        intent.putExtra("ChannelName", channelName.text.toString())
        intent.putExtra("UserRole", userRole)
        startActivity(intent)
    }
}