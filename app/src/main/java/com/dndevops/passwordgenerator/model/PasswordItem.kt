package com.dndevops.passwordgenerator.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "PasswordItem_table")
data class PasswordItem(

    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "created_date")
    var createdDate: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable