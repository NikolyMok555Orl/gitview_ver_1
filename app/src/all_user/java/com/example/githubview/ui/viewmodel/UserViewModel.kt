package com.example.githubview.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubview.data.MainAndUserRepository
import com.example.githubview.data.MainRepository
import com.example.githubview.data.model.Repositories
import com.example.githubview.data.model.User
import com.example.githubview.utils.Resource
import kotlinx.coroutines.launch

class UserViewModel(private val mainRepository: MainAndUserRepository) : ViewModel() {

        val user: MutableLiveData<Resource<User>> by lazy { MutableLiveData<Resource<User>>(null) }

        fun getUser(newUser: String)  {
                viewModelScope.launch {
                        user.value= Resource.loading(data = null)
                        try {
                                user.value = Resource.success(data = mainRepository.getNewUser(newUser))
                        } catch (exception: Exception) {
                                user.value =
                                        Resource.error(data = null, message = exception.message ?: "Ошибка")
                        }
                }
        }

}