package com.christopher.andrevina

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HallOfFameActivity : AppCompatActivity() {

    companion object {
        val llistaPartides: ArrayList<String> = ArrayList()

        fun afegirPartida(info: String) {
            llistaPartides.add(info)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this)
        tv.textSize = 18f

        if (llistaPartides.isEmpty()) {
            tv.text = "Encara no hi ha partides registrades."
        } else {
            tv.text = llistaPartides.joinToString("\n")
        }

        setContentView(tv)
    }
}
