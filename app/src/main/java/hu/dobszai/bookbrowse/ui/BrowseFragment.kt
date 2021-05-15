package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.adapters.BookListAdapter
import hu.dobszai.bookbrowse.databinding.FragmentBrowseBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.utils.appBarConfiguration
import hu.dobszai.bookbrowse.utils.disableAppBarTitle
import hu.dobszai.bookbrowse.viewmodels.BookViewModel

class BrowseFragment : Fragment(), BookListAdapter.ClickListener {

    private lateinit var viewModel: BookViewModel
    private lateinit var binding: FragmentBrowseBinding
    private lateinit var adapter: BookListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBrowseBinding.inflate(inflater)

        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
            disableAppBarTitle()
        }

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        binding.apply {
            lifecycleOwner = this@BrowseFragment
            booksViewModel = viewModel
        }

        setUpRecyclerView()
        populateBookList()
        searchBook()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(
            binding.toolbar,
            findNavController(),
            appBarConfiguration
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorites, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return view?.let {
            NavigationUI.onNavDestinationSelected(
                item,
                it.findNavController()
            )
        } == true || super.onOptionsItemSelected(item)
    }

    private fun searchBook() {
        binding.btnSearch.setOnClickListener {
            if(binding.searchView.toString().isNotEmpty()){
                viewModel.findBook(binding.searchView.toString())
            }else{
                Toast.makeText(context, R.string.book_title_required, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = BookListAdapter(this)
        binding.listBooks.adapter = adapter
    }

    private  fun populateBookList() {
        viewModel.books.observe(viewLifecycleOwner, {
            it?.let {
                adapter.setBookList(it)
            }
        })
    }

    override fun onBookClick(book: Book) {
        TODO("Not yet implemented")
    }
}