package com.example.githubview.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
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
        Surface( modifier = modifier
            .fillMaxSize().padding(10.dp), color = MaterialTheme.colors.primarySurface,border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(Modifier.size(125.dp), contentAlignment = Alignment.Center) {
                    AsyncImage(
                        model = user.avatar_url,
                        contentDescription = "Аватар",
                        modifier = Modifier
                            .size(100.dp).clip(CircleShape)
                            .padding(5.dp),
                        placeholder = painterResource(R.drawable.ic_person_24)
                    )
                    Text(
                        text = "${user.followers}, ${user.following}",
                        modifier.fillMaxSize(),
                        textAlign = TextAlign.Right
                    )
                }
                Text(text = user.name, style = MaterialTheme.typography.h5)
                Text(text = user.bio, style=MaterialTheme.typography.body1)
                Row(verticalAlignment=Alignment.CenterVertically) {
                    Text(text = user.blog, style = MaterialTheme.typography.subtitle2, textAlign = TextAlign.Center)
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_launch_24),
                        contentDescription = "Ссылка", modifier = Modifier.padding(4.dp)
                    )
                }
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
                    blog="https://vedmak.fandom.com/wiki/Цирилла"

            })
        }
    }


}