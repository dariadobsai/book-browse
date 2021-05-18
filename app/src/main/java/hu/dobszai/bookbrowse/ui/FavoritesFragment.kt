package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.adapters.BookListFavAdapter
import hu.dobszai.bookbrowse.base.BaseFragment
import hu.dobszai.bookbrowse.databinding.FragmentFavoritesBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.viewmodels.BookViewModel
import kotlinx.android.synthetic.main.toolbar.view.*

class FavoritesFragment : BaseFragment(), BookListFavAdapter.ClickListener {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var bookAdapter: BookListFavAdapter
    override val _viewModel: BookViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = this@FavoritesFragment
            booksViewModel = _viewModel

            inToolbar.toolbar.custom_title.text = getString(R.string.nav_favorites)
        }

        setUpRecyclerView()

        return binding.root
    }

    override fun setUpRecyclerView() {
        bookAdapter = BookListFavAdapter(this, this, _viewModel)
        binding.listFavBooks.adapter = bookAdapter
        populateList()
    }

    override fun populateList() {
        _viewModel.booksFav.observe(viewLifecycleOwner, {
            bookAdapter.setBookList(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.inToolbar.toolbar, findNavController())
    }

    override fun onBookClick(book: Book) {
        TODO("Not yet implemented")
    }
}