package com.example.p2pcs_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class visualizza_macchina : AppCompatActivity() {
    private var modello: TextView? = null
    private var marca: TextView? = null
    private var targa: TextView? = null
    private var anno: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)
        modello = findViewById<TextView>(R.id.Modello)
        marca = findViewById<TextView>(R.id.Marca)
        targa = findViewById<TextView>(R.id.Targa)
        anno = findViewById<TextView>(R.id.AnnoProduzione)


        getUsers()
    }

    // function for network call
    fun getUsers() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://ec2-18-206-124-50.compute-1.amazonaws.com/visualizzamacchina.php"

        // Request a string response from the provided URL.
        //object property needed to override getparams
        val stringReq = object: StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->

                val strResp = response.toString()
                val jsonArray: JSONArray = JSONArray(strResp)
                //val jsonArray: JSONArray = jsonObj.getJSONArray() //devo mettere un nome in qualche modo all'array su php
                var modellos: String = ""
                var marcas: String = ""
                var targas: String = ""
                var annos: String = ""

                for (i in 0 until jsonArray.length()) {
                    val jsonInner: JSONObject = jsonArray.getJSONObject(i)
                    modellos =  "\n" + jsonInner.get("Modello")
                    marcas= "\n" + jsonInner.get("Marca")
                    targas= "\n" + jsonInner.get("Targa")
                    annos= "\n" + jsonInner.get("Anno_produzione")
                }
                modello!!.text = "$modellos "
                marca!!.text = "$marcas "
                targa!!.text = "$targas "
                anno!!.text = "$annos "
            },
            Response.ErrorListener {
                modello!!.text = it.toString()
                marca!!.text = it.toString()
                targa!!.text = it.toString()
                anno!!.text = it.toString()

            })
        //need to override getparams to get the post request
        {
            override fun getParams() : Map<String,String> {
                val params = HashMap<String, String>()
                params.put("USER","admin")
                return params
            }
        }

        queue.add(stringReq)
    }
}


//textView!!.text = "That didn't work!"










/*import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class volley_test : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley_test)
    }
}*/
