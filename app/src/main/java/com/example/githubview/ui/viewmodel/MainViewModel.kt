package com.example.githubview.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.githubview.data.MainRepository
import com.example.githubview.data.model.Repositories
import com.example.githubview.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


open class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val repositorise: MutableLiveData<Resource<Repositories>> by lazy { MutableLiveData<Resource<Repositories>>(null) }

      fun search(q: String="")  {
          viewModelScope.launch {
              repositorise.value= Resource.loading(data = null)
              try {
                  repositorise.value = Resource.success(data = mainRepository.getRepositories(q))
              } catch (exception: Exception) {
                  repositorise.value =
                      Resource.error(data = null, message = exception.message ?: "Ошибка")
              }
          }
    }


}