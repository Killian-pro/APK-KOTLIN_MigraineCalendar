package com.example.migraine


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.lang.Integer.parseInt
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        time();
        affichage();

    }

    fun time() {
        val register = findViewById(R.id.register) as Button
        //recuperation du temps actuelle
        val timeactual = System.currentTimeMillis();

        //recuperation de l'ancienne valeur enregistré
        var prefs: SharedPreferences? = null
        prefs = this.getSharedPreferences("KIKI", 0)

        val OLD = prefs!!.getLong("time", 0)
        var init = prefs!!.getString("push", "0")
        // difference en MILLISECOND
        val Diff = timeactual - OLD
        // DIFF EN HEURE
        var hours = (Diff / (1000 * 60 * 60))

        register.setOnClickListener {
            // METTRE <2
            if (hours < 2) {
                showDialog();
            } else {

                var pre_init = parseInt(init);
                pre_init++
                init = pre_init.toString();
                val editor = prefs!!.edit()
                editor.putLong("time", System.currentTimeMillis());
                editor.putString("push", init);
                val mavalue = prefs!!.getString("push", "")
                Log.v("VALUE", mavalue!!)
                // SAUVEGARDE DE LA DATE
                val now = LocalDateTime.now();
                var year = now.year.toString()
                var month = "0" + now.monthValue.toString()
                var day = "0" + now.dayOfMonth.toString()
                //ternaire IF LA DATE JOLIE
                if (day.length > 2) day = day.substring(1)
                if (month.length > 2) month = month.substring(1)
                val String_Date = "$day-$month-$year"
                Log.v("time", String_Date.toString()!!)
                editor.putString(init, String_Date.toString());
                editor.apply()
                affichage()
            }

        }

    }


    fun affichage() {

        val textView = findViewById(R.id.textView) as TextView
        var prefs: SharedPreferences? = null
        //recuperation du temps actuelle
        val timeactual = System.currentTimeMillis();

        //recuperation de l'ancienne valeur enregistré
        prefs = this.getSharedPreferences("KIKI", 0)

        val OLD = prefs!!.getLong("time", 0)


        // difference en MILLISECOND
        val Diff = timeactual - OLD

        // EN HEURE ET EN MINUTES
        var affichage = ""
        var hours = (Diff / (1000 * 60 * 60)).toString() + " Heures";
        var minutes = (Diff / (1000 * 60)).toString() + " Minutes";
        var day = (Diff / (1000 * 60 * 60 * 24)).toString() + " Jours";

        if ((Diff / (1000 * 60)) < 60) {
            affichage = minutes
        } else if ((Diff / (1000 * 60 * 60)) < 24) {
            affichage = hours
        } else {
            affichage = day
        }
        textView.setText(affichage);


    }


    fun showDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Tu as pris le medicament il y a moins de 2H")

        // Set a message for alert dialog
        //builder.setMessage("This is a sample message of AlertDialog.")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> toast("Non recommandé")
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("OK", dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }


    // Extension function to show toast message
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun OPENCALENDAR(view: View) {
        val Calendar = Intent(this, Calendar::class.java)
        // Do something in response to button
        startActivity(Calendar)
        //finish();
    }

}
