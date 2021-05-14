package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.databinding.FragmentDetailsBinding
import hu.dobszai.bookbrowse.databinding.FragmentFavoritesBinding
import hu.dobszai.bookbrowse.utils.disableAppBarTitle

private lateinit var binding: FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

    }
}