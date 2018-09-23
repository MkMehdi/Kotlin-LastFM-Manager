package ma.polyapps.lastfm.ui.albums


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_albums.*
import ma.polyapps.lastfm.R
import ma.polyapps.lastfm.ui.BaseViewModel
import ma.polyapps.lastfm.ui.adapter.AlbumStoredAdapter
import ma.polyapps.lastfm.ui.detail.AlbumDetailActivity
import ma.polyapps.lastfm.ui.listener.OnClickStoredAlbumListener


/**
 * A simple [Fragment] subclass.
 *
 */
class AlbumsFragment : Fragment(), OnClickStoredAlbumListener {

    private var model: BaseViewModel? = null

    fun newInstance(): AlbumsFragment {
        return AlbumsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(this).get(BaseViewModel::class.java)

        recyclerViewAlbums.layoutManager = GridLayoutManager(activity, 2)

        if (savedInstanceState == null)
            getAlbums()
        else
            showAlbums()
    }


    private fun getAlbums() {
        startProgress()
        model?.getStoredAlbums()?.observe(this, Observer {
            if (it != null && !it.isEmpty()) {
                model?.mStoredAlbumList = it

                showAlbums()

            } else {
                showEmptyView()
            }
            endProgress()
        })
    }

    private fun showAlbums() {
        val adapter = AlbumStoredAdapter(model?.mStoredAlbumList!!, this@AlbumsFragment)
        recyclerViewAlbums.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onClick(position: Int) {
        val i = Intent(activity, AlbumDetailActivity::class.java)
        i.putExtra("albumStored", model?.mStoredAlbumList!![position])
        startActivity(i)
    }


    private fun showError(desc: String) {
        Snackbar.make(rootView, desc, Snackbar.LENGTH_LONG).show()
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
}
