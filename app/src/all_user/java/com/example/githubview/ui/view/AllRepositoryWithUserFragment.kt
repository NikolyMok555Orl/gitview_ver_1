package com.example.githubview.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.findNavController
import com.example.githubview.data.model.Repository

class AllRepositoryWithUserFragment :AllRepositoryFragment() {

     @Composable
     override fun StateItem(repository: Repository, modifier: Modifier){
        Item(repository, { navToUser(repository.nameOwner) }, modifier)
    }

     private fun navToUser(user: String){
        val directions=AllRepositoryWithUserFragmentDirections.actionAllRepositoryWithUserFragmentToUserFragment(user)
        view?.findNavController()?.navigate(directions)
    }
}