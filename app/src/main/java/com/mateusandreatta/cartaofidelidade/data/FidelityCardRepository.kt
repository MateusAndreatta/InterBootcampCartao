package com.mateusandreatta.cartaofidelidade.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FidelityCardRepository(private val dao: FidelityCardDao) {

    fun insert(fidelityCard: FidelityCard) = runBlocking {
        launch (Dispatchers.IO){
            dao.insert(fidelityCard)
        }
    }

    fun update(fidelityCard: FidelityCard) = runBlocking {
        launch (Dispatchers.IO){
            dao.update(fidelityCard)
        }
    }

    fun delete(fidelityCard: FidelityCard) = runBlocking {
        launch (Dispatchers.IO){
            dao.delete(fidelityCard)
        }
    }

    suspend fun getAll() = dao.getAll()

}