package com.example.paulf.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    private val PHONE_CODE=4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //imageButtonCamera
        imageButtonPhone!!.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {

                val phoneNumber=editTextPhone.text.toString()
                if (!phoneNumber.isEmpty()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        if(ChecarPermiso(Manifest.permission.CALL_PHONE)){
                            val intentAceptado=Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber))

                            if (ActivityCompat.checkSelfPermission(this@ThirdActivity,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                                return
                            }
                            startActivity(intentAceptado)
                        }else{
                            //preguntarle por el permiso
                            if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),PHONE_CODE)
                            }else{
                                Toast.makeText(this@ThirdActivity,"Por favor el permiso correspondiente",Toast.LENGTH_LONG).show()
                                val intentSetting=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intentSetting.addCategory(Intent.CATEGORY_DEFAULT)
                                intentSetting.data=Uri.parse("package:"+packageName)
                                intentSetting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intentSetting.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                intentSetting.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                                startActivity(intentSetting)
                            }
                        }
                    }else{
                        versionAntigua(phoneNumber)
                    }
                }else{
                    Toast.makeText(this@ThirdActivity,"debes marcar un numero, intenta nuevamente",Toast.LENGTH_LONG).show()
                }
            }

            /*fun versionAntigua(phoneNumber:String){
                val intentCall=Intent(Intent.ACTION_CALL, Uri.parse("tel"+phoneNumber))
                if(ChecarPermiso(Manifest.permission.CALL_PHONE)){
                    if (ActivityCompat.checkSelfPermission(this@ThirdActivity,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                        return
                    }
                    startActivity(intentCall)
                }
            }*/
        })
        //--boton para la web--
        imageButtonWeb.setOnClickListener{

            val url=editTextWeb.text.toString()

            val intentWeb=Intent()

            intentWeb.action=Intent.ACTION_VIEW
            intentWeb.data=Uri.parse("http://"+url)
            startActivity(intentWeb)
        }
        //llamado para el email
        buttonEmail.setOnClickListener{
            val email= "paul.frankpc@gmail.com"

            val intentEmail=Intent(Intent.ACTION_SEND,Uri.parse(email))
            intentEmail.type="plain/text"
            intentEmail.putExtra(Intent.EXTRA_SUBJECT,"titulo de email")
            intentEmail.putExtra(Intent.EXTRA_TEXT,"Que hay prro?")
            intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("alguien@gmail.com","alguienmas@gmail.com"))
            startActivity(Intent.createChooser(intentEmail,"Elige tu cliente de correo"))
        }


        //boton de llamada sin permisos
        buttonContactPhone!!.setOnClickListener{
            val intentCall=Intent(Intent.ACTION_DIAL,Uri.parse("tel:943237768"))
            startActivity(intentCall)
        }

        //boton para la camara
        imageButtonCamera!!.setOnClickListener{
            val intentCamera=Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intentCamera)
        }
    }

    override fun onCreateOptionsMenu(menu:Menu?):Boolean{
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menuContactos->{
                val intentContactos=Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"))
                startActivity(intentContactos)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun versionAntigua(phoneNumber:String){
        val intentCall=Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))
        if(ChecarPermiso(Manifest.permission.CALL_PHONE)){
            if (ActivityCompat.checkSelfPermission(this@ThirdActivity,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){

            }
            startActivity(intentCall)

        }
    }

    fun ChecarPermiso(permission: String): Boolean{
        val result=this.checkCallingOrSelfPermission(permission)
        return result ==PackageManager.PERMISSION_GRANTED
    }

    //--Metodo asincrono para comprobar permisos --//
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PHONE_CODE->{
                val permisos=permissions[0]
                val result=grantResults[0]

                if(permisos == Manifest.permission.CALL_PHONE){
                    if(result==PackageManager.PERMISSION_GRANTED){
                        val phoneNumber=editTextPhone.text.toString()
                        //Uri es un identificador
                        val intentCode=Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))
                        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                            return
                        }
                        startActivity(intentCode)
                    }else{
                        Toast.makeText(this,"has denegado el permiso",Toast.LENGTH_LONG).show()
                    }
                    //File: //algun archivo.txt
                    //http: //www.ejemplo.com
                    //tel" //888449956
                }
            }
            else-> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }
}
