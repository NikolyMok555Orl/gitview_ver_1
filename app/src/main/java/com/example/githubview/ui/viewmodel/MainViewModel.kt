package com.example.githubview.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.githubview.Api
import com.example.githubview.data.MainRepository
import com.example.githubview.data.model.Repositories
import com.example.githubview.notifyObservers
import com.example.githubview.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Math.ceil


open class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val repositorise: MutableLiveData<Resource<Repositories>> by lazy {
        MutableLiveData<Resource<Repositories>>(
            null
        )
    }

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    val page: MutableLiveData<Int> = MutableLiveData(1)

    val query: MutableLiveData<String> = MutableLiveData("")

    val countRepositoryAll: Int
        get() {
            return repositorise.value?.data?.total_count ?: -1
        }

    val countPage: Int
        get() {
            var page=(kotlin.math.ceil(countRepositoryAll / Repositories.per_page.toDouble())).toInt()
            if(page>100) page=100
            return page
        }



    fun getRepositories(){
        viewModelScope.launch {
            _isRefreshing.emit(true)
            repositorise.value= Resource.loading(data = null)
            try {
                repositorise.value = Resource.success(data = mainRepository.getRepositories(q=query.value?:"", page=page.value?:1))

            } catch (exception: Exception) {
                repositorise.value =
                    Resource.error(data = null, message = exception.message ?: "Ошибка")
            }
            _isRefreshing.emit(false)
        }
    }



     /*fun getLanguages(id: Int){
         viewModelScope.launch {

             val repositori=repositorise.value?.data?.items?.find { it->it.id==id }
             repositori?.languages = "Загружается"
             repositorise.notifyObservers()
             repositori?.languages = try {
                 val res =
                     Resource.success(data = Api.get().getLanguages(url=repositori?.languages_url?:"").keys.toList())
                 (res.data ?: emptyList()).joinToString()
             } catch (exception: Exception) {
                 "Ошибка при загрузки"
             }
             repositorise.notifyObservers()
         }
    }*/

      fun search(q: String="")  {
          page.value=1
          query.value=q
          getRepositories()
    }

    fun startRepository(){
        if(countRepositoryAll>0){
            page.value=1
            getRepositories()
        }
    }

    fun finishRepository(){
        if(countRepositoryAll>0){
            page.value= countPage
            getRepositories()
        }
    }

    fun previousRepository(){
        if(countRepositoryAll > 0 && (page.value ?: 1) > 1){
            page.value= page.value?.minus(1)
            getRepositories()
        }
    }

    fun nextRepository(){
        if(countRepositoryAll > 0 && (page.value ?: 1) < countPage){
            page.value= page.value?.plus(1)
            getRepositories()
        }
    }

}