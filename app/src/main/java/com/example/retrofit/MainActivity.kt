package com.example.retrofit
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.databinding.ItemDigiBinding
import com.example.retrofit.databinding.MainLayoutBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var adapter: DigiAdapter
    private val digiImages = mutableListOf<String>()
    private lateinit var binding: MainLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        binding.svDigi.setOnQueryTextListener(this)
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://digimon-api.vercel.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getDigibyImg("digimon/name/$query")
            val digimon = call.body()
            if(call.isSuccessful){
                val digimonImg = digimon?.component1() ?: String
                digiImages.clear()
                digiImages.add(digimonImg.toString())
                adapter.notifyDataSetChanged()
            }else{
                showError()
            }
            hideKeyboard()
        }
    }
    private fun initRecyclerView() {
        adapter = DigiAdapter(digiImages)
        binding.rvDigi.layoutManager = LinearLayoutManager(this)
        binding.rvDigi.adapter = adapter
    }
    private fun showError() {
        Looper.prepare()
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.lowercase(Locale.getDefault()))
        }
        return true
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
}
class DigiViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDigiBinding.bind(view)

    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivDigi)
    }
}
class DigiAdapter(private val images: List<String>) : RecyclerView.Adapter<DigiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DigiViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DigiViewHolder(layoutInflater.inflate(R.layout.item_digi, parent, false))
    }

    override fun getItemCount(): Int = images.size


    override fun onBindViewHolder(holder: DigiViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }
}
