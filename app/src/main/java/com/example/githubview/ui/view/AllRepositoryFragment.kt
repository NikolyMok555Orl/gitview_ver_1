package com.example.githubview.ui.view

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.githubview.R
import com.example.githubview.data.MainRepository
import com.example.githubview.data.model.Repositories
import com.example.githubview.data.model.Repository
import com.example.githubview.databinding.FAllRepositoryBinding
import com.example.githubview.ui.theme.AppTheme
import com.example.githubview.ui.viewmodel.MainViewModel
import com.example.githubview.ui.viewmodel.MainViewModelFactory
import com.example.githubview.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


open class AllRepositoryFragment : Fragment() {

    protected var _binding: FAllRepositoryBinding? = null
    protected val binding get() = _binding!!

    protected lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FAllRepositoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = MainViewModelFactory(MainRepository())
        mainViewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.search(query ?: "")
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
        binding.composeView.setContent {
            AppTheme {
                MainScreen()
            }

        }

    }


    @Composable
    fun MainScreen(modifier: Modifier=Modifier){
        Column(modifier = modifier.fillMaxSize()) {
            val reviews = mainViewModel.repositorise.observeAsState()
            reviews.value?.let {
                when (it.status) {
                    Status.LOADING -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    Status.SUCCESS -> {
                        Text("Найденно: ${mainViewModel.countRepositoryAll}")
                        Items(it.data?.items ?: emptyList())


                    }
                    else -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = it.message ?: "Неизвестная ошибка")
                        }
                    }
                }
            }

        }
    }



    @Composable
    fun Items(repositories: List<Repository>,modifier: Modifier=Modifier){
        val lazyListState: LazyListState = rememberLazyListState()
        LazyColumn(modifier = modifier, state = lazyListState) {
            items(items = repositories, key = { it.id }) { repository ->
                StateItem(repository, modifier)
            }
            if(mainViewModel.countPage>1) {
                item {
                    PaggingPage(
                        mainViewModel.countPage, mainViewModel.page.value ?: 1,
                    )
                }
            }
        }
    }

    @Composable
    open fun StateItem(repository: Repository, modifier: Modifier=Modifier){
        Item(repository, { navToUser() }, modifier)
    }

    @Composable
    fun Item(repository: Repository, onClick: ()->Unit, modifier: Modifier=Modifier){
        Card(
            modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable(onClick = { onClick() }),
            elevation = 5.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = modifier.fillMaxWidth()){
                    AsyncImage(model = repository.avatar_url, contentDescription = "Аватар", modifier=Modifier.size(25.dp),
                        placeholder =painterResource(R.drawable.ic_person_24) )
                    Text(text=repository.full_name)
                    Row(modifier = Modifier.fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.End) {
                        Text(text = repository.stargazers_count.toString())
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_star_24),
                            contentDescription = null)
                    }
                }
                Text(text=repository.description)
                val composableScope = rememberCoroutineScope()
                val mutableState = remember { mutableStateOf("") }
                if(repository.languages.isEmpty()){
                    composableScope.launch() {
                        mutableState.value=repository.getLanguages()
                    }
                }
                Text(text = "ЯП ${mutableState.value}")

                Text(text=repository.updated_at)
            }
        }
    }


    @Composable
    fun PaggingPage(countAll:Int, page:Int,  modifier: Modifier=Modifier){
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement=Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = { mainViewModel.startRepository() }, enabled = page>1) {
                Text("Начало")
            }
            TextButton(onClick = { mainViewModel.previousRepository() }, enabled = page>1) {
                Text("Предыдущие")
            }
            Text(text=page.toString(), style = MaterialTheme.typography.h5 )
            TextButton(onClick = { mainViewModel.nextRepository() }, enabled = page<countAll) {
                Text("Следущие")
            }
            TextButton(onClick = { mainViewModel.finishRepository() }, enabled = page<countAll) {
                Text("Конец")
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun PreviewItem(){
        AppTheme() {
            //Item()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewPaggingPage(){
        AppTheme() {
            PaggingPage(25, 2)
        }
    }


    protected open fun navToUser(){
        Toast.makeText( requireContext(),"В этой версии не доступно просмотр пользователя",
            Toast.LENGTH_SHORT).show()
    }
}