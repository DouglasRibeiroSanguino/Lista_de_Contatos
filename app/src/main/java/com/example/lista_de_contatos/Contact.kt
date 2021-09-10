package com.example.lista_de_contatos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var name: String,
    var phone: String,
    var photo: String
) : Parcelable

// Em contact xml usar tools:src="@tools:sample/avatars" para definir um avatar de exemplo