package com.example.retrofit

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class raceResponse(
     var Nombre: List<String>,
     var Imagen: List<String>,
     var Nivel: List<String>
)