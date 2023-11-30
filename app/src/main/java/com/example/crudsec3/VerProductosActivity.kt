package com.example.crudsec3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudsec3.Adapter.AdapterProducto
import com.example.crudsec3.Models.Producto
import com.example.crudsec3.databinding.ActivityVerProductosBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerProductosActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityVerProductosBinding
    //Lista productos
    private lateinit var productosList : ArrayList<Producto>
    //Adaptador
    private lateinit var adapterProducto: AdapterProducto
    //Firebase
    private lateinit var database: DatabaseReference
    //Recicler view
    private lateinit var productoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productoRecyclerView = binding.rvProductos
        productoRecyclerView.layoutManager = LinearLayoutManager(this)
        productoRecyclerView.hasFixedSize()

        productosList = arrayListOf<Producto>()

        getProducto()


    }

    private fun getProducto() {

        //Ruta de los datos
        database = FirebaseDatabase.getInstance().getReference("Productos")

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (productosSnapshot in snapshot.children){
                        val producto = productosSnapshot.getValue(Producto::class.java)
                        productosList.add(producto!!)
                    }
                    adapterProducto = AdapterProducto(productosList)
                    productoRecyclerView.adapter = adapterProducto
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }


}