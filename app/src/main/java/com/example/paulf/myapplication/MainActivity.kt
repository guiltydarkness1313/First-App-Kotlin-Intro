package com.example.paulf.myapplication

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val SALUDO="Hola desde el activity main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //forzar icono en el Action Bar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setIcon(R.mipmap.ic_launcher)



        Toast.makeText(this,"hola mundo",Toast.LENGTH_LONG).show()

        buttonCalcular.setOnClickListener{
            val anoNacimiento:Int=editTextFecha.text.toString().toInt()
            val anoActual= Calendar.getInstance().get(Calendar.YEAR)
            val miEdad=anoActual-anoNacimiento
            textView.text="mi Edad es ${miEdad.toString()}"
        }

        buttonNext.setOnClickListener {
            startActivity(this,SecondActivity::class.java)
        }
    }


    fun startActivity(activity:Activity,nextActivity:Class<*>){
        val intent=Intent(activity,nextActivity)
        intent.putExtra("saludo",SALUDO)
        activity.startActivity(intent)
    }

}
