package com.example.ipet

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginLeft
import androidx.fragment.app.FragmentContainerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Petshops : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petshops)
        listartPeshop()
    }

    fun listartPeshop() {
        val getPetshops = ApiIpet.criar().getPetshop()

        val argumentos = Bundle()
        val fragmento = FragmentContainerView(this)

        val transaction = supportFragmentManager.beginTransaction()

        getPetshops.enqueue(object : Callback<List<Petshop>> {
            override fun onResponse(call: Call<List<Petshop>>, response: Response<List<Petshop>>) {


                fragmento.id = View.generateViewId()
                fragmento.removeAllViews()
                val teste = findViewById<LinearLayout>(R.id.ll_linha_petshop)
                teste.removeAllViews()
                teste.addView(fragmento)

                if (response.isSuccessful) {
                    response.body()?.forEach { petshop ->
                        argumentos.putString("nome", petshop.nome)
                        transaction.add(fragmento.id, LinhaPetshop::class.java, argumentos)
                    }
                    transaction.commit()
                } else {
                    Toast.makeText(baseContext, "Sem petshops", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Petshop>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(baseContext, "Erro na api", Toast.LENGTH_SHORT).show()
            }
        })
    }
}