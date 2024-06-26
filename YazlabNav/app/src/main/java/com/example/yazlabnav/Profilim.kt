package com.example.yazlabnav

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.yazlabnav.R
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class Profilim : AppCompatActivity() {

    private lateinit var adText: TextInputEditText
    private lateinit var adGuncellemeButton: Button
    var textToSpeech:TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profilim)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adText = findViewById(R.id.adText)
        adGuncellemeButton = findViewById(R.id.adGuncelle)
        val sharedPref=getSharedPreferences("myPref", MODE_PRIVATE)
        val editor=sharedPref.edit()
        val name: String? = sharedPref.getString("name",null)

        name?.let {
            adText?.setText(it)
        }

        adGuncellemeButton?.setOnClickListener {
            editor.apply{
                putString("name", adText?.text.toString())
                apply()
            }
            textToSpeech?.speak("Kullanıcı adı başarıyla güncellendi.", TextToSpeech.QUEUE_FLUSH, null, null)
            Toast.makeText(this, "Kullanıcı adı başarıyla güncellendi.", Toast.LENGTH_SHORT).show()

        }
        val geriTusProfilim = findViewById<ImageButton>(R.id.geritusu_profil)
        geriTusProfilim.setOnClickListener {
            geriButtonProfilim()
        }

    }

    fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Dil değiştirmek isterseniz alttaki tr yi başka dil koduyla değiştirin //Kaan
            val result = textToSpeech?.setLanguage(Locale("tr"))

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Toast.makeText(this, "Bu dil desteklenmiyor.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "TextToSpeech başlatılamadı.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun geriButtonProfilim() {
        val geriTusProfilim = Intent(this, MainActivity::class.java)
        startActivity(geriTusProfilim)
    }


}