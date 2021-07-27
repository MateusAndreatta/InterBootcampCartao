package com.mateusandreatta.cartaofidelidade.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class FidelityCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var companyName: String,
    var clientName: String,
    var description: String,
    var background: String?,
    var companyImage: ByteArray?,
    var checkedStamp1: Boolean = false,
    var checkedStamp2: Boolean = false,
    var checkedStamp3: Boolean = false,
    var checkedStamp4: Boolean = false,
    var checkedStamp5: Boolean = false,
    var checkedStamp6: Boolean = false,
    var checkedStamp7: Boolean = false,
    var checkedStamp8: Boolean = false,
    var checkedStamp9: Boolean = false,
    var checkedStamp10: Boolean = false
) : Serializable