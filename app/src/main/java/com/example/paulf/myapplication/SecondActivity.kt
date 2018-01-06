package com.example.paulf.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        //casting de widgets kotlin
        val textView=findViewById(R.id.textViewIntent) as TextView
        //get extras de otro activity kotlin
        val bundle= intent.extras
        if(!bundle.isEmpty && bundle.getString("saludo")!=null){
            val saludo=bundle.getString("saludo")
            textView.text=saludo

        }else{
            Toast.makeText(this,"no hay comunicacion con el mainActivity",Toast.LENGTH_SHORT).show()
        }

        btnToThirdActivity.setOnClickListener{
            val launcher= Intent(this,ThirdActivity::class.java)
            startActivity(launcher)
            finish()
        }

    }
}
