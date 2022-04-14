package tech.tennoji.igncodefoo.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tech.tennoji.igncodefoo.R
import tech.tennoji.igncodefoo.databinding.FragmentVideoBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_START_INDEX = "startIndex"
private const val ARG_COUNT = "count"

/**
 * A simple [Fragment] subclass.
 * Use the [VideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoFragment : Fragment() {
    private var param1: Int? = null
    private var param2: Int? = null

    private val viewModel: VideoViewModel by lazy {
        val factory = VideoViewModelFactory()
        ViewModelProvider(this, factory).get(VideoViewModel::class.java)
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
    ): View {
        val binding = DataBindingUtil.inflate<FragmentVideoBinding>(
            inflater,
            R.layout.fragment_video,
            container,
            false
        )
        binding.lifecycleOwner = this
        val adapter = VideoAdapter(VideoListener { slug -> viewModel.onCardClick(slug) })
        val recyclerView = binding.videoRecyclerView
        recyclerView.adapter = adapter
        viewModel.videoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.openLink.observe(viewLifecycleOwner) {
            it?.let {
                val bundle = Bundle()
                bundle.putString("url", "https://ign.com/videos/$it")
                findNavController().navigate(R.id.action_mainFragment_to_webViewFragment, bundle)
                viewModel.onOpenLinkComplete()
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show()
                viewModel.onErrorToastComplete()
            }
        }
        viewModel.getVideoData(param1!!, param2!!)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VideoFragment.
         */
        @JvmStatic
        fun newInstance(startIndex: Int = 0, count: Int = 10) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_START_INDEX, startIndex)
                    putInt(ARG_COUNT, count)
                }
            }
    }
}