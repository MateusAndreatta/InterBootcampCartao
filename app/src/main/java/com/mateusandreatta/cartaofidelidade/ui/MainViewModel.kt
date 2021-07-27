package com.mateusandreatta.cartaofidelidade.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mateusandreatta.cartaofidelidade.data.FidelityCard
import com.mateusandreatta.cartaofidelidade.data.FidelityCardRepository

class MainViewModel(private val fidelityCardRepository: FidelityCardRepository) : ViewModel() {

    fun insert(fidelityCard: FidelityCard){
        fidelityCardRepository.insert(fidelityCard)
    }

    fun update(fidelityCard: FidelityCard){
        fidelityCardRepository.update(fidelityCard)
    }

    fun delete(fidelityCard: FidelityCard){
        fidelityCardRepository.delete(fidelityCard)
    }

    suspend fun getAll(): List<FidelityCard> {
        return fidelityCardRepository.getAll()
    }

}

class MainViewModelFactory(private val repository: FidelityCardRepository) :
    ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}