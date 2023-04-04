package sing.top.douyinmine.fragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import sing.top.douyinmine.R
import java.util.ArrayList

class TabFragment : Fragment() {

    private lateinit var mVideoAdapter: VideoAdapter
    private lateinit var mRecyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mVideoAdapter = VideoAdapter(getList())
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.adapter = mVideoAdapter
    }

    private fun getList(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0..29) {
            list.add("" + i)
        }
        return list
    }

    class VideoAdapter(list: List<String>) : RecyclerView.Adapter<VideoAdapter.VideoHolder>() {
        private var models: List<String> = ArrayList()

        init {
            models = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_video, parent, false)
            return VideoHolder(view)
        }

        override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        }

        override fun getItemCount(): Int {
            return 30
        }

        class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}