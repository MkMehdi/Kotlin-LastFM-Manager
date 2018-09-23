package ma.polyapps.lastfm.ui.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ma.polyapps.lastfm.R
import ma.polyapps.lastfm.api.model.Artist
import ma.polyapps.lastfm.ui.top.TopAlbumsActivity


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class ArtistAdapter(private val dataList: List<Artist>) :
        RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_view_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.textNameArtist.text = dataList[position].name
        Glide.with(mContext!!).load(dataList[position].images[2].url).into(holder.imageArtist)


        holder.itemView.setOnClickListener {
            val i = Intent(mContext,TopAlbumsActivity::class.java)
            i.putExtra("artist", dataList[position])
            mContext?.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textNameArtist: TextView
        var imageArtist: ImageView

        init {
            textNameArtist = itemView.findViewById(R.id.textArtist)
            imageArtist = itemView.findViewById(R.id.imageArtist)
        }
    }
}