package com.christopher.andrevina

import android.content.Intent
import android.os.Bundle
import android.widget.*
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
        setContentView(R.layout.activity_hall_of_fame)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayoutRecords)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Si no hay partidas registradas, mostrar mensaje
        if (llistaPartides.isEmpty()) {
            val tv = TextView(this)
            tv.text = "Encara no hi ha partides registrades."
            tv.textSize = 18f
            tv.setPadding(8, 16, 8, 16)
            tableLayout.addView(tv)
        } else {
            // Añadir cada partida a la tabla
            for (partida in llistaPartides) {
                val parts = partida.split("-")
                val nom = parts.getOrNull(0)?.trim() ?: "Jugador desconegut"
                val intents = parts.getOrNull(1)?.replace("intents", "")?.trim() ?: "?"

                val fila = TableRow(this)

                val tvNom = TextView(this)
                tvNom.text = nom
                tvNom.setPadding(8, 8, 8, 8)

                val tvIntents = TextView(this)
                tvIntents.text = intents
                tvIntents.setPadding(8, 8, 8, 8)

                fila.addView(tvNom)
                fila.addView(tvIntents)

                tableLayout.addView(fila)
            }
        }

        // Botón para volver al MainActivity
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
