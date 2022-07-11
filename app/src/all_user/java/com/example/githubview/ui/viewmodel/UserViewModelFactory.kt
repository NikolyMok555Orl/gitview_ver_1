package com.example.githubview.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubview.data.MainAndUserRepository
import com.example.githubview.data.MainRepository

class UserViewModelFactory (val mainRepository: MainAndUserRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(mainRepository) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }
}