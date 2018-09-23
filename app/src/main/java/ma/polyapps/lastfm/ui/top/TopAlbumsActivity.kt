package ma.polyapps.lastfm.ui.top

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_top_albums.*
import ma.polyapps.lastfm.R
import ma.polyapps.lastfm.api.model.Artist
import ma.polyapps.lastfm.isNetworkAvailable
import ma.polyapps.lastfm.ui.BaseViewModel
import ma.polyapps.lastfm.ui.adapter.AlbumAdapter
import ma.polyapps.lastfm.ui.detail.AlbumDetailActivity
import ma.polyapps.lastfm.ui.listener.OnClickAlbumListener

class TopAlbumsActivity : AppCompatActivity(), OnClickAlbumListener {

    private var model: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_albums)
        init()

        if (savedInstanceState == null)
            fetchAlbums(model?.mArtist)
        else
            showTopAlbums()

    }

    private fun init() {
        model = ViewModelProviders.of(this).get(BaseViewModel::class.java)

        model?.mArtist = intent.getSerializableExtra("artist") as Artist?

        title = getString(R.string.text_top_screen) + " -  ${model?.mArtist?.name}"

        textTitle.text = model?.mArtist?.name

        Glide.with(this).load(model?.mArtist?.images!![3].url).into(imageOverView)

        recyclerViewAlbums.layoutManager = GridLayoutManager(this, 2)
    }

    private fun fetchAlbums(artist: Artist?) {
        startProgress()
        if (this.isNetworkAvailable()) {
            model?.getTopAlbums(artist!!)?.observe(this, Observer {
                Log.d("albums", "size ${it?.size}")

                showTopAlbums()

                endProgress()

            })
        } else {
            showError("Please check your network")
        }
    }

    private fun showTopAlbums() {
        val listAlbum = model?.mAlbumList
        if (listAlbum != null && listAlbum.isNotEmpty()) {
            val adapter = AlbumAdapter(listAlbum, this)
            recyclerViewAlbums.adapter = adapter
            adapter.notifyDataSetChanged()

            recyclerViewAlbums.visibility = View.VISIBLE

            return
        }

        showEmptyView()
    }

    private fun showDetailAlbum(mbId: String) {
        if (this.isNetworkAvailable()) {
            showOverlay()
            model?.getAlbumDetail(mbId)?.observe(this, Observer {
                if (it != null) {
                    Log.d("album", "name ${it.name}")
                    val i = Intent(this, AlbumDetailActivity::class.java)
                    i.putExtra("album", it)
                    startActivity(i)
                    hideOverlay()
                }

            })
        } else {
            showError("Please check your network")
        }
    }

    override fun onClick(id: String) {
        showDetailAlbum(id)
    }

    private fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
        recyclerViewAlbums.visibility = View.INVISIBLE
    }

    private fun startProgress() {
        emptyView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun endProgress() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showOverlay() {
        detailLoaderView.visibility = View.VISIBLE
    }

    private fun hideOverlay() {
        detailLoaderView.visibility = View.INVISIBLE
    }

    private fun showError(desc: String) {
        Snackbar.make(rootView, desc, Snackbar.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()

        return true
    }

}
