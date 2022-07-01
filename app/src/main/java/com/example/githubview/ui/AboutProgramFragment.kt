package com.example.githubview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubview.R
import com.example.githubview.databinding.FAboutProgramBinding
import com.example.githubview.ui.theme.AppTheme


class AboutProgramFragment : Fragment() {



    private var _binding: FAboutProgramBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FAboutProgramBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            AppTheme {
                AboutProgram()
            }


        }

    }

    @Composable
    fun AboutProgram(modifier: Modifier=Modifier){
        Box(modifier=modifier, contentAlignment= Alignment.Center){
            Text(text= stringResource(R.string.app_name))

        }

    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewAboutProgram(){
        AppTheme {
            AboutProgram()
        }
    }
}