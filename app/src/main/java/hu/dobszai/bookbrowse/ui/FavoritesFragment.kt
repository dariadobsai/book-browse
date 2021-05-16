package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import hu.dobszai.bookbrowse.adapters.BookListAdapter
import hu.dobszai.bookbrowse.adapters.BookListFavAdapter
import hu.dobszai.bookbrowse.base.BaseFragment
import hu.dobszai.bookbrowse.databinding.FragmentFavoritesBinding
import hu.dobszai.bookbrowse.models.Book

class FavoritesFragment : BaseFragment(), BookListFavAdapter.ClickListener{

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var bookAdapter: BookListFavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = this@FavoritesFragment
            booksViewModel = viewModel
        }

        setUpRecyclerView()

        return binding.root
    }

    override fun setUpRecyclerView() {
        bookAdapter = BookListFavAdapter(this, this, viewModel)
        binding.listFavBooks.adapter = bookAdapter
        populateList()
    }

    override fun populateList() {
        viewModel.booksFav.observe(viewLifecycleOwner, {
            bookAdapter.setBookList(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
    }

    override fun onBookClick(book: Book) {
        TODO("Not yet implemented")
    }
}