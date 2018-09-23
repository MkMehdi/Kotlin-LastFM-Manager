package ma.polyapps.lastfm.ui.search


import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import ma.polyapps.lastfm.R
import ma.polyapps.lastfm.isNetworkAvailable
import ma.polyapps.lastfm.ui.BaseViewModel
import ma.polyapps.lastfm.ui.adapter.ArtistAdapter


/**
 * A simple [Fragment] subclass.
 */
class ArtistFragment : Fragment() {

    private var model: BaseViewModel? = null
    private var lastQuery: String = ""

    fun newInstance(): ArtistFragment {
        return ArtistFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(this).get(BaseViewModel::class.java)

        recyclerViewArtists.layoutManager = GridLayoutManager(activity, 2)

        if (savedInstanceState == null)
            showEmptyView()
        else
            showArtists()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView
        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                lastQuery = query

                searchArtist(lastQuery)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })


    }

    private fun searchArtist(artistQuery: String) {
        if (activity!!.isNetworkAvailable()) {
            startProgress()
            model?.searchByArtist(artistQuery)?.observe(this, Observer {
                if (it != null) {
                    showArtists()
                } else {
                    showEmptyView()
                }

                endProgress()
            })
        }else{
            showError("Please check your network")
        }
    }

    private fun showArtists() {
        val listArtist = model?.mArtistList
        if (listArtist != null && listArtist.isNotEmpty()) {
            val adapter = ArtistAdapter(listArtist)
            recyclerViewArtists.adapter = adapter
            adapter.notifyDataSetChanged()

            recyclerViewArtists.visibility = View.VISIBLE

            return
        }

        showEmptyView()
    }


    private fun showError(desc: String) {
        Snackbar.make(rootView, desc, Snackbar.LENGTH_LONG).show()
    }

    private fun showEmptyView() {
        emptView.visibility = View.VISIBLE
        recyclerViewArtists.visibility = View.INVISIBLE
    }


    private fun startProgress() {
        emptView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun endProgress() {
        progressBar.visibility = View.INVISIBLE
    }
}
