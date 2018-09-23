package ma.polyapps.lastfm.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_album_detail.*
import ma.polyapps.lastfm.R
import ma.polyapps.lastfm.api.model.AlbumDetail
import ma.polyapps.lastfm.db.entity.AlbumEntity
import ma.polyapps.lastfm.toBitmap
import ma.polyapps.lastfm.toByteArray
import ma.polyapps.lastfm.ui.BaseViewModel
import ma.polyapps.lastfm.ui.adapter.TrackAdapter
import ma.polyapps.lastfm.ui.adapter.TrackStoredAdapter

class AlbumDetailActivity : AppCompatActivity() {
    private var model: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
        init()


        fatSaveBtn.setOnClickListener {
            when (fatSaveBtn.tag) {
                "yet" -> {
                    model?.albumImage = (imageAlbum.drawable as BitmapDrawable).bitmap.toByteArray()

                    model?.storeAlbum()?.observe(this, Observer {
                        if (it != null) {
                            runOnUiThread { Snackbar.make(rootView, "Album successfully saved", Snackbar.LENGTH_LONG).show() }
                            fatSaveBtn.setImageResource(android.R.drawable.ic_menu_save)
                            fatSaveBtn.tag = "saved"
                        } else {
                            runOnUiThread { Snackbar.make(rootView, "Action failed", Snackbar.LENGTH_LONG).show() }
                        }
                    })

                }
                "saved" -> {
                    model?.deleteAlbum()?.observe(this, Observer {
                        if (it != null) {
                            runOnUiThread { Snackbar.make(rootView, "Album successfully removed", Snackbar.LENGTH_LONG).show() }
                            fatSaveBtn.setImageResource(android.R.drawable.ic_menu_delete)
                            fatSaveBtn.tag = "yet"
                        } else {
                            runOnUiThread { Snackbar.make(rootView, "Action failed", Snackbar.LENGTH_LONG).show() }
                        }
                    })

                }
            }


        }
    }

    private fun init() {
        model = ViewModelProviders.of(this).get(BaseViewModel::class.java)

        model?.mAlbumDetail = intent.getSerializableExtra("album") as AlbumDetail?

        if (model?.mAlbumDetail != null) {

            title = model?.mAlbumDetail?.name

            textAlbum.text = model?.mAlbumDetail?.name

            Glide.with(this).load(model?.mAlbumDetail?.images!![3].url).into(imageAlbum)

            fatSaveBtn.setImageResource(android.R.drawable.ic_menu_save)

            fatSaveBtn.tag = "yet"

            showTracks()

        } else {
            model?.mAlbumStored = intent.getSerializableExtra("albumStored") as AlbumEntity?

            title = model?.mAlbumStored?.name

            textAlbum.text = model?.mAlbumStored?.name

            imageAlbum.setImageBitmap(model?.mAlbumStored?.image?.toBitmap())

            fatSaveBtn.setImageResource(android.R.drawable.ic_menu_delete)

            fatSaveBtn.tag = "saved"

            showStoredTracks()

        }

        recyclerViewTracks.layoutManager = LinearLayoutManager(this)
    }

    private fun showTracks() {
        val tracks = model?.mAlbumDetail?.tracks?.tracks
        if (tracks != null && tracks.isNotEmpty()) {
            val adapter = TrackAdapter(tracks)
            recyclerViewTracks.adapter = adapter
            adapter.notifyDataSetChanged()

            recyclerViewTracks.visibility = View.VISIBLE

            return
        }
    }

    private fun showStoredTracks(){
        val tracks = model?.mAlbumStored?.tracks
        if (tracks != null && tracks.isNotEmpty()) {
            val adapter = TrackStoredAdapter(tracks)
            recyclerViewTracks.adapter = adapter
            adapter.notifyDataSetChanged()

            recyclerViewTracks.visibility = View.VISIBLE

            return
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()

        return true
    }
}
