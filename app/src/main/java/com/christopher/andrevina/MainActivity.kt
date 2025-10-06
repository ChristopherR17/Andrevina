package com.christopher.andrevina

import android.content.Intent
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
    private lateinit var btnRecords: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialitzar vistes
        editText = findViewById(R.id.editTextNumberDecimal)
        button = findViewById(R.id.endevinaButton)
        tvHistorial = findViewById(R.id.tvHistorial)
        tvIntents = findViewById(R.id.tvIntents)
        scrollView = findViewById(R.id.scrollView2)
        btnRecords = findViewById(R.id.btnRecords)

        // Generar número inicial
        reiniciarPartida()

        // Listener del botó "Endevina"
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

        // Listener del botó "Veure Rècords"
        btnRecords.setOnClickListener {
            val intent = Intent(this, HallOfFameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun afegirHistorial(text: String) {
        tvHistorial.append("\n$text")
        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun mostrarFiPartida() {
        val input = EditText(this)
        input.hint = "Escriu el teu nom"

        AlertDialog.Builder(this)
            .setTitle("Felicitats!")
            .setMessage("Has encertat el número en $intents intents.\nIntrodueix el teu nom:")
            .setView(input)
            .setPositiveButton("Sí") { _, _ ->
                val nom = input.text.toString().ifEmpty { "Jugador Anònim" }
                HallOfFameActivity.afegirPartida("$nom - $intents intents")
                reiniciarPartida()
            }
            .setNegativeButton("No") { _, _ ->
                val nom = input.text.toString().ifEmpty { "Jugador Anònim" }
                HallOfFameActivity.afegirPartida("$nom - $intents intents")

                val intent = Intent(this, HallOfFameActivity::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }

    private fun reiniciarPartida() {
        numeroSecret = Random.nextInt(1, 101)
        intents = 0
        tvIntents.text = "Intents: 0"
        tvHistorial.text = "Historial:"
    }
}
