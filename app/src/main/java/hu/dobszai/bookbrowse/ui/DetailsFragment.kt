package hu.dobszai.bookbrowse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.base.NavigationCommand
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import hu.dobszai.bookbrowse.adapters.DetailListAdapter
import hu.dobszai.bookbrowse.base.BaseFragment
import hu.dobszai.bookbrowse.databinding.FragmentDetailsBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.viewmodels.DetailViewModel
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates


class DetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailsBinding

    private var argBookPosition by Delegates.notNull<Int>()
    private lateinit var argBookList: Array<Book>

    override val _viewModel: DetailViewModel by inject()

    private lateinit var adapter: DetailListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)

        argBookPosition = DetailsFragmentArgs.fromBundle(requireArguments()).argBookPosition
        argBookList = DetailsFragmentArgs.fromBundle(requireArguments()).argBookList

        setUpRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
    }

    override fun setUpRecyclerView() {
        val manager = CardStackLayoutManager(context)
        manager.apply {
            setStackFrom(StackFrom.None)
            setDirections(Direction.HORIZONTAL)
        }

        adapter = DetailListAdapter()

        binding.cardStackView.apply {
            layoutManager = manager
            adapter = adapter
        }
    }

    override fun populateList() {
        adapter.setBookList(argBookList.toList())
        binding.cardStackView.apply {
            scrollToPosition(argBookPosition)
        }
    }

    /* binding.cardStackView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
          override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

              recyclerView.findViewHolderForAdapterPosition()

              if (argBookList.toList().size <= newState + 1) {
                  _viewModel.navigationCommand.postValue(
                      NavigationCommand.Back
                  )
              }
          }
      })*/

}
