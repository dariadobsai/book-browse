package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.adapters.BookListAdapter
import hu.dobszai.bookbrowse.base.BaseFragment
import hu.dobszai.bookbrowse.base.NavigationCommand
import hu.dobszai.bookbrowse.databinding.FragmentFavoritesBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.utils.SwipeToDeleteCallback
import hu.dobszai.bookbrowse.viewmodels.BookViewModel

class FavoritesFragment : BaseFragment(), BookListAdapter.ClickListener {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var bookAdapter: BookListAdapter
    override val _viewModel: BookViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = this@FavoritesFragment
            booksViewModel = _viewModel

            inToolbar.customTitle.text = getString(R.string.nav_favorites)
        }

        setUpRecyclerView()

        deleteFavorite()

        return binding.root
    }

    override fun setUpRecyclerView() {
        bookAdapter = BookListAdapter(this)
        binding.listFavBooks.adapter = bookAdapter
        populateList()
    }

    override fun populateList() {
        _viewModel.favoriteBooks.observe(viewLifecycleOwner, {
            bookAdapter.setBookList(it)
            binding.emptyList.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else View.GONE
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.inToolbar.toolbar, findNavController())
    }

    override fun onBookClick(books: List<Book>, bookPosition: Int) {
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(
                FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(
                    bookPosition,
                    books.toTypedArray()
                )
            )
        )
    }

    private fun deleteFavorite(){
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                _viewModel.favoriteBooks.value?.drop(pos)

                _viewModel.favoriteBooks.value?.get(pos)?.let {
                    _viewModel.favoriteOrUnfavoriteBook(
                        it, false)
                }

                bookAdapter.notifyItemRemoved(pos)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView( binding.listFavBooks)
    }
}