package ma.polyapps.lastfm.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import ma.polyapps.lastfm.R
import ma.polyapps.lastfm.R.id.action_albums
import ma.polyapps.lastfm.R.id.action_artist
import ma.polyapps.lastfm.ui.albums.AlbumsFragment
import ma.polyapps.lastfm.ui.search.ArtistFragment


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()

        if(savedInstanceState == null) {
            setupFragment(AlbumsFragment::class.java)

            title = getString(R.string.text_albums)
        }

    }

    private fun init() {
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragmentClass: Class<*>?

        when (item.itemId) {
            action_albums -> {
                fragmentClass = AlbumsFragment::class.java
            }
            action_artist -> {
                fragmentClass = ArtistFragment::class.java
            }
            else -> {
                fragmentClass = AlbumsFragment::class.java
            }
        }

        setupFragment(fragmentClass)

        item.isChecked = true
        title = item.title

        return true
    }

    private fun setupFragment(fragmentClass: Class<*>?) {
        var fragment: Fragment? = null

        try {
            fragment = (fragmentClass?.newInstance() as Fragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        // Insert the fragment by replacing any existing fragment
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.contentFrame, fragment).commit()
    }

}
