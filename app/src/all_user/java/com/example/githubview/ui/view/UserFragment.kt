package com.example.githubview.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import com.example.githubview.R
import com.example.githubview.databinding.FUserBinding
import com.example.githubview.ui.theme.AppTheme


class UserFragment :Fragment(R.layout.f_user){

    private var _binding: FUserBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            AppTheme {
                User()
            }
        }
    }


    @Composable
    fun User(modifier: Modifier=Modifier){
        Column(modifier=modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Row(modifier=modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                AsyncImage(model = "", contentDescription = "Аватар", modifier=Modifier.size(100.dp))
                Text(text = "0, 0")

            }
            Text(text = "Ник")
            Text(text = "Bio")
            Row() {
                Text(text = "ссылка")
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_launch_24), contentDescription = "Ссылка")
            }
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewUser(){
        AppTheme() {
            User()
        }
    }


}