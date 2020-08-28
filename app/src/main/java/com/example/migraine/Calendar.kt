package com.example.migraine

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.lang.Integer.parseInt
import java.util.regex.Pattern

class Calendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        onload()
        afficher("2019")
    }

    fun onload() {
        val YEAR = findViewById(R.id.YEAR) as EditText

        //RECUPERE LA VALEUR DE YEAR
        var Mon_Annee_choisi = "2020"

        //RECUPERE LA VALEUR DE YEAR
        YEAR.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Mon_Annee_choisi = s.toString()
                afficher(Mon_Annee_choisi)
            }
        })


    }

    fun afficher(Mon_Annee_choisi: String) {

        //ancienne valeur
        var valeur_final =
            "\n25-08-2020\n22-08-2020\n20-07-2020\n15-07-2020\n01-07-2020\n23-06-2020\n14-06-2020\n07-06-2020\n10-05-2020\n24-04-2020\n03-04-2020\n01-03-2020\n28-02-2020\n23-02-2020\n05-02-2020\n09-01-2020\n03-01-2020\n25-12-2019\n18-12-2019\n16-12-2019\n13-12-2019\n07-12-2019\n08-12-2019\n25-11-2019\n19-11-2019\n15-11-2019\n16-11-2019\n06-10-2019\n12-09-2019\n13-09-2019\n18-09-2019\n19-09-2019\n22-09-2019\n28-09-2019"

        val EditText = findViewById(R.id.MULTI) as EditText
        val sharedPref = getSharedPreferences("KIKI", Context.MODE_PRIVATE) ?: return
        sharedPref.edit()
        val init = sharedPref.getString("push", "0")
        val nombre_val = parseInt(init)

        for (val_increment in 0..nombre_val) {
            val valeur = sharedPref.getString(val_increment.toString(), "")
            if (valeur != null) {
                //  Log.v("NB VAL", valeur)

                valeur_final = valeur + "\n" + valeur_final

                //choisir tout sauf l'année ecrit >> ne marche pas
                //val regex = Regex("^((?!$Mon_Annee_choisi).)*$")
                val regex = Regex("......$Mon_Annee_choisi")
                val regex2 = Regex("(?m)^[ \\t]*\\r?\\n")
                valeur_final = valeur_final.replace(regex, "")
                //enlever tout les sauts de ligne
                valeur_final=valeur_final.replace(regex2,"")
                EditText.setText(valeur_final.trim());
            }
        }

    }

}