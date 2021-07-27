package com.mateusandreatta.cartaofidelidade.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.mateusandreatta.cartaofidelidade.App
import com.mateusandreatta.cartaofidelidade.R
import com.mateusandreatta.cartaofidelidade.adapters.FidelityCardAdapter
import com.mateusandreatta.cartaofidelidade.data.FidelityCard
import com.mateusandreatta.cartaofidelidade.databinding.ActivityMainBinding
import com.mateusandreatta.cartaofidelidade.util.Image
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as App).repository)
    }

    private val adapter by lazy { FidelityCardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpPermissions()
        binding.rvCards.adapter = adapter
        getAllFidelityCards()
        setupListeners()
    }

    private fun setupListeners(){
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }
        adapter.listnerChangeStamp = { stamp, fidelityCard ->
            updateStamp(stamp, fidelityCard)
        }
        adapter.listnerMenu = {view, fidelityCard ->
            showDialog(view, fidelityCard)
        }
    }

    private fun setUpPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
    }

    private fun updateStamp(stamp: View, fidelityCard: FidelityCard) {
        when(stamp.id){
            R.id.imageViewStamp1 -> {fidelityCard.checkedStamp1 = !fidelityCard.checkedStamp1}
            R.id.imageViewStamp2 -> {fidelityCard.checkedStamp2 = !fidelityCard.checkedStamp2}
            R.id.imageViewStamp3 -> {fidelityCard.checkedStamp3 = !fidelityCard.checkedStamp3}
            R.id.imageViewStamp4 -> {fidelityCard.checkedStamp4 = !fidelityCard.checkedStamp4}
            R.id.imageViewStamp5 -> {fidelityCard.checkedStamp5 = !fidelityCard.checkedStamp5}
            R.id.imageViewStamp6 -> {fidelityCard.checkedStamp6 = !fidelityCard.checkedStamp6}
            R.id.imageViewStamp7 -> {fidelityCard.checkedStamp7 = !fidelityCard.checkedStamp7}
            R.id.imageViewStamp8 -> {fidelityCard.checkedStamp8 = !fidelityCard.checkedStamp8}
            R.id.imageViewStamp9 -> {fidelityCard.checkedStamp9 = !fidelityCard.checkedStamp9}
            R.id.imageViewStamp10 -> {fidelityCard.checkedStamp10 = !fidelityCard.checkedStamp10}
        }
        updateCard(fidelityCard)
    }

    private fun updateCard(fidelityCard: FidelityCard){
        mainViewModel.update(fidelityCard)
        adapter.notifyDataSetChanged()
    }

    private fun deleteCard(fidelityCard: FidelityCard){
        mainViewModel.delete(fidelityCard)
        getAllFidelityCards()
    }

    private fun getAllFidelityCards(){
        lifecycleScope.launch {
            adapter.submitList(mainViewModel.getAll())
        }
    }

    private fun showDialog(view: View,fidelityCard: FidelityCard){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Opções")
            setMessage("O que deseja alterar no cartão de: " + fidelityCard.clientName + " ?")
            setPositiveButton("Editar") { _, _ ->
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                intent.putExtra("card", fidelityCard)
                startActivity(intent)
            }
            setNegativeButton("Deletar") { _, _ ->
                deleteCard(fidelityCard)
            }
            setNeutralButton("Compartilhar") { _, _ ->
                Image.share(this@MainActivity, view)
            }
        }.create().show()
    }

    override fun onResume() {
        super.onResume()
        getAllFidelityCards()
    }

}