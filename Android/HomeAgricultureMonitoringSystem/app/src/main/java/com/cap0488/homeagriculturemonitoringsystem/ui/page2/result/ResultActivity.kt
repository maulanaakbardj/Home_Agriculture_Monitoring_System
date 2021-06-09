package com.cap0488.homeagriculturemonitoringsystem.ui.page2.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import com.cap0488.homeagriculturemonitoringsystem.databinding.ActivityResultBinding
import com.google.firebase.firestore.FirebaseFirestore

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var db = FirebaseFirestore.getInstance()
    private lateinit var hasil:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        hasil = intent.getStringExtra("hasil").toString()
        val img  = intent.getStringExtra("uri")
        binding.imgResult.setImageURI(img?.toUri())
        hasil = hasil.substring(0,hasil.length-1)
        title = "Hasil Diagnosa"
        binding.sehat.visibility = View.INVISIBLE
        setResult()



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setResult(){

        db.collection("penyakit")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("cekhasilloop",document.data.toString())


                        var tempt  = document.getString("nama").toString()
                        if(tempt == hasil){
                            Log.d("cekhasil3",document.getString("nama").toString())

                            binding.penyakit.text = document.getString("nama")
                            binding.penjelasanPenyakit.text = document.getString("penjelasan")
                            binding.solusiPenyakit.text = document.getString("solusi")
                            binding.progresBar.visibility=View.GONE

                            if(tempt == "Sehat"){
                                binding.wadahSolusi.visibility = View.INVISIBLE
                                binding.wadahPenjelasan.visibility = View.INVISIBLE
                                binding.wadahNama.visibility = View.INVISIBLE
                                binding.sehat.visibility = View.VISIBLE

                            }

                        }



                    }
                }
                .addOnFailureListener { exception ->
                }



    }




    companion object{
        const val EXTRA = "uri"
        const val HASIL = "hasil"
    }
}