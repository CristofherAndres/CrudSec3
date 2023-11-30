package com.example.crudsec3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crudsec3.Models.Producto
import com.example.crudsec3.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    //Activar firebase
    private lateinit var database: DatabaseReference

    //Activar viewBindin
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Iniciar la base de datos
        database = FirebaseDatabase.getInstance().getReference("Productos")

        binding.btnGuardar.setOnClickListener {
            //Obtener la data del formulario

            val nombre = binding.etNombreProducto.text.toString()
            val precio = binding.etPrecioProducto.text.toString()
            val descripcion = binding.etDescripcionProducto.text.toString()
            //Generar un id unico
            val id = database.child("Productos").push().key

            if(nombre.isEmpty()){
                binding.etNombreProducto.error = "Por favor ingresar nombre"
                return@setOnClickListener
            }

            if(precio.isEmpty()){
                binding.etPrecioProducto.error = "Por favor ingresar precio"
                return@setOnClickListener
            }

            if(descripcion.isEmpty()){
                binding.etDescripcionProducto.error = "Por favor ingresar descripción"
                return@setOnClickListener
            }

            val producto = Producto(id, nombre, precio, descripcion)

            database.child(id!!).setValue(producto)
                .addOnSuccessListener {
                    binding.etPrecioProducto.setText("")
                    binding.etDescripcionProducto.setText("")
                    binding.etNombreProducto.setText("")
                    Snackbar.make(binding.root, "Producto Agregado", Snackbar.LENGTH_SHORT)
                        .show()
                }

        }

        binding.btnVer.setOnClickListener {
            val intent = Intent(this, VerProductosActivity::class.java)
            startActivity(intent)
        }

    }
}