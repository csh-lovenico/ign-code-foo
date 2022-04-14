package tech.tennoji.igncodefoo.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import tech.tennoji.igncodefoo.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_START_INDEX = "startIndex"
private const val ARG_COUNT = "count"

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment() {
    private var param1: Int? = null
    private var param2: Int? = null

    private val viewModel: ArticleViewModel by lazy {
        val factory = ArticleViewModelFactory()
        ViewModelProvider(this, factory).get(ArticleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_START_INDEX)
            param2 = it.getInt(ARG_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getArticleData(param1!!, param2!!)
        val adapter = ArticleAdapter(ArticleListener { slug -> viewModel.onCardClick(slug) })
        val recyclerView = view.findViewById<RecyclerView>(R.id.article_recycler_view)
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }
        viewModel.articleList.observe(viewLifecycleOwner) {
            adapter.submitList(it.data)
        }

        viewModel.openLink.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.i("article", it)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param startIndex Parameter 1.
         * @param count Parameter 2.
         * @return A new instance of fragment ArticleFragment.
         */
        @JvmStatic
        fun newInstance(startIndex: Int = 0, count: Int = 10) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_START_INDEX, startIndex)
                    putInt(ARG_COUNT, count)
                }
            }
    }
}