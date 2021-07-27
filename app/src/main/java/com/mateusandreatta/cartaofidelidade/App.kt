package com.mateusandreatta.cartaofidelidade

import android.app.Application
import com.mateusandreatta.cartaofidelidade.data.AppDatabase
import com.mateusandreatta.cartaofidelidade.data.FidelityCardRepository

class App : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { FidelityCardRepository(database.fidelityDao()) }
}