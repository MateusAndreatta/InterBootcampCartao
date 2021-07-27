package com.mateusandreatta.cartaofidelidade.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FidelityCardDao {

    @Query("SELECT * FROM FidelityCard")
    fun getAllLifeData(): LiveData<List<FidelityCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fidelityCard: FidelityCard)

    @Update()
    fun update(fidelityCard: FidelityCard)

    @Delete
    fun delete(fidelityCard: FidelityCard)

    @Query("SELECT * FROM FidelityCard")
    suspend fun getAll(): List<FidelityCard>
}