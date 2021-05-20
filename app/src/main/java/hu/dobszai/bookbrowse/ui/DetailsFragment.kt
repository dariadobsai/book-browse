package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import hu.dobszai.bookbrowse.adapters.DetailListAdapter
import hu.dobszai.bookbrowse.databinding.FragmentDetailsBinding
import hu.dobszai.bookbrowse.models.Book
import kotlin.properties.Delegates

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private var argBookPosition by Delegates.notNull<Int>()
    private lateinit var argBookList: Array<Book>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)

        argBookPosition = DetailsFragmentArgs.fromBundle(requireArguments()).argBookPosition
        argBookList = DetailsFragmentArgs.fromBundle(requireArguments()).argBookList

        setUpCards()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
    }

    private fun setUpCards() {
        val manager = CardStackLayoutManager(context)
        val detailsAdapter = DetailListAdapter()
        binding.cardStackView.apply {
            layoutManager = manager
            adapter = detailsAdapter
        }

        detailsAdapter.setBookList(argBookList.toList())
        binding.cardStackView.scrollToPosition(argBookPosition)
    }
}
