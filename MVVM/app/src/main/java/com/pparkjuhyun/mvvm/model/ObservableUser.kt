package com.pparkjuhyun.mvvm.model

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class ObservableUser {
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val age = ObservableInt()
}