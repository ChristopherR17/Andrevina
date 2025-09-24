package com.christopher.andrevina

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numeroSecret = 0
    private var intents = 0

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var tvHistorial: TextView
    private lateinit var tvIntents: TextView
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialitzar vistes
        editText = findViewById(R.id.editTextNumberDecimal)
        button = findViewById(R.id.endevinaButton)
        tvHistorial = findViewById(R.id.tvHistorial)
        tvIntents = findViewById(R.id.tvIntents)
        scrollView = findViewById(R.id.scrollView2)

        // Generar número inicial
        reiniciarPartida()

        // Listener botó
        button.setOnClickListener {
            val entrada = editText.text.toString()
            if (entrada.isEmpty()) {
                Toast.makeText(this, "Introdueix un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numero = entrada.toInt()
            intents++
            tvIntents.text = "Intents: $intents"

            when {
                (numero < numeroSecret) -> {
                    afegirHistorial("$numero → El número és més gran")
                    Toast.makeText(this, "El número secret és més gran", Toast.LENGTH_SHORT).show()
                }
                (numero > numeroSecret) -> {
                    afegirHistorial("$numero → El número és més petit")
                    Toast.makeText(this, "El número secret és més petit", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    afegirHistorial("$numero → Correcte!")
                    mostrarFiPartida()
                }
            }

            editText.text.clear()
        }
    }

    private fun afegirHistorial(text: String) {
        tvHistorial.append("\n$text")
        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun mostrarFiPartida() {
        AlertDialog.Builder(this)
            .setTitle("Felicitats!")
            .setMessage("Has encertat el número en $intents intents.\nVols tornar a jugar?")
            .setPositiveButton("Sí") { _, _ -> reiniciarPartida() }
            .setNegativeButton("No") { _, _ -> finishAffinity() }
            .show()
    }

    private fun reiniciarPartida() {
        numeroSecret = Random.nextInt(1, 101)
        intents = 0
        tvIntents.text = "Intents: 0"
        tvHistorial.text = "Historial:"
    }
}
