package com.example.githubview.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.example.githubview.R
import com.example.githubview.data.MainAndUserRepository
import com.example.githubview.data.MainRepository
import com.example.githubview.data.model.User
import com.example.githubview.databinding.FUserBinding
import com.example.githubview.ui.theme.AppTheme
import com.example.githubview.ui.viewmodel.MainViewModel
import com.example.githubview.ui.viewmodel.MainViewModelFactory
import com.example.githubview.ui.viewmodel.UserViewModel
import com.example.githubview.ui.viewmodel.UserViewModelFactory
import com.example.githubview.utils.Status


class UserFragment :Fragment(R.layout.f_user){

    private var _binding: FUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    private val args: UserFragmentArgs  by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = UserViewModelFactory(MainAndUserRepository())
        userViewModel = ViewModelProvider(this,factory)[UserViewModel::class.java]
        userViewModel.getUser(args.user)
        binding.composeView.setContent {
            AppTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    val reviews = userViewModel.user.observeAsState()
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
                                it.data?.let {
                                    user->UserView(user)
                                }

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
        }
    }

    @Composable
    fun UserView(user: User,modifier: Modifier=Modifier){
        Column(modifier=modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Row(modifier=modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                AsyncImage(model = user.avatar_url, contentDescription = "Аватар", modifier=Modifier.size(100.dp))
                Text(text = "${user.followers}, ${user.following}")

            }
            Text(text = user.name)
            Text(text = user.bio)
            Row() {
                Text(text = user.blog)
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_launch_24), contentDescription = "Ссылка")
            }
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewUser(){
        AppTheme() {
            UserView(User().apply {
                    name="Цири"
                    bio="Ведьмачка"
                    avatar_url="https://i.playground.ru/i/pix/1484606/image.jpg"
                    followers=2
                    following=5

            })
        }
    }


}