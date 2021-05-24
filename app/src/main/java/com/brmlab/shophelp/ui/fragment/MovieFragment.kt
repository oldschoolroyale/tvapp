package com.brmlab.shophelp.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.brmlab.shophelp.adapter.MovieCastAdapter
import com.brmlab.shophelp.databinding.FragmentMovieBinding
import com.brmlab.shophelp.model.MovieCredModel
import com.brmlab.shophelp.model.VideoModel
import com.brmlab.shophelp.ui.vm.DescriptionViewModel
import com.brmlab.shophelp.utils.BaseModel
import com.brmlab.shophelp.utils.Status
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie.view.*


@AndroidEntryPoint
class MovieFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    companion object{
        private const val PERCENTAGE_TO_SHOW_IMAGE = 20
    }

    var mMaxScrollSize = 0
    var mIsImageHidden = true

    lateinit var mFab : View
    private var player: SimpleExoPlayer? = null
    lateinit var playerView : PlayerView
    //Binding
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    //NavAgrs
    private val args by navArgs<MovieFragmentArgs>()

    //viewModel
    private val descriptionViewModel by viewModels<DescriptionViewModel>()

    private val compositeDisposable = CompositeDisposable()

    private val adapter = MovieCastAdapter()

    //observers
    private lateinit var videoObserver: Observer<BaseModel<VideoModel>>
    private lateinit var personObserver: Observer<BaseModel<MovieCredModel>>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = args.model

        playerView = binding.fragmentMovieExoPlayer

        mFab = binding.flexibleExampleFab
        val model = args.model

        val recyclerView = binding.fragmentMovieRecyclerCred
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL, false)


        videoObserver = Observer {
            when(it.status){
                Status.LOADING ->{
                    view.fragmentMovieLoader.visibility = View.VISIBLE
                }
                Status.SUCCESS ->{
                    view.fragmentMovieLoader.visibility = View.INVISIBLE
                    if (it.response?.data?.results!!.count() > 0){
                        val link = "https://www.youtube.com/watch?v=${it.response.data.results[0].key}"
                        subscribeOnTotalCount(link)
                    }

                }
                Status.ERROR ->{
                    view.fragmentMovieLoader.visibility = View.INVISIBLE
                }
            }
        }
        descriptionViewModel.video_list.observe(requireActivity(), videoObserver)

        personObserver = Observer {
            descriptionViewModel.getVideo(model.id)
            if (it.status == Status.SUCCESS) {
                if (it.response?.data?.cast?.isNotEmpty() == true){
                    adapter.newList(it.response.data.cast)
                    Log.d("LOG_TAG", "tut")
                }
            }
        }
        descriptionViewModel.movie_cred.observe(requireActivity(), personObserver)
        descriptionViewModel.getMovieCred(model.id)


        val appBar = binding.flexibleExampleAppbar
        appBar.addOnOffsetChangedListener(this)

    }


    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player?.playWhenReady = false
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player?.stop()
        player = null
    }

    private fun subscribeOnTotalCount(url: String) {
        compositeDisposable.add(urlFromYoutube(url)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    player = SimpleExoPlayer.Builder(requireContext()).build()
                    playerView.player = player
                    it?.let {
                        player?.prepare(it)
                    }
                    player!!.playWhenReady = true
                },{
                    Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("MyLog", "Rx $it")
                }))
    }

    private fun urlFromYoutube(url: String) : Single<MediaSource?> {
        return Single.create{ subscriber ->
            val dataSourceFactory = DefaultDataSourceFactory(requireContext(), "sample")
            object : YouTubeExtractor(requireContext()) {
                override fun onExtractionComplete(
                    ytFiles: SparseArray<YtFile>,
                    vMeta: VideoMeta
                ) {
                    if (ytFiles != null) {
                        val itag = 22
                        try {
                            val downloadUrl = ytFiles[itag].url
                            subscriber.onSuccess(ProgressiveMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(Uri.parse(downloadUrl)))
                        }
                        catch (e: Exception){
                            subscriber.onError(e)
                        }
                    }
                }
            }.extract(url, true, true)

        }

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout!!.totalScrollRange

        val currentScrollPercentage: Int = (Math.abs(verticalOffset) * 100
                / mMaxScrollSize)

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true
                ViewCompat.animate(mFab).scaleY(0f).scaleX(0f).start()
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false
                ViewCompat.animate(mFab).scaleY(1f).scaleX(1f).start()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        Log.d("LOG_TAG", "onDView")
        compositeDisposable.dispose()
        releasePlayer()
        descriptionViewModel.video_list.removeObserver(videoObserver)
        descriptionViewModel.movie_cred.removeObserver(personObserver)
        super.onDestroyView()
    }
}