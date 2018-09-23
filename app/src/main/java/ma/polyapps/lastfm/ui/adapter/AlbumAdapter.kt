package ma.polyapps.lastfm.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import ma.polyapps.lastfm.R
import ma.polyapps.lastfm.api.model.Album
import ma.polyapps.lastfm.ui.listener.OnClickAlbumListener


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class AlbumAdapter(private val dataList: List<Album>,
                   private val onClickListener: OnClickAlbumListener) :
        RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        mContext = parent.context

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_view_top_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.textAlbum.text = dataList[position].name
        Glide.with(mContext!!).load(dataList[position].images[2].url).into(holder.imageAlbum)

        holder.itemView.setOnClickListener {
            if (dataList[position].mbid != null)
                onClickListener.onClick(dataList[position].mbid!!)
            else
                Toast.makeText(mContext, "Cannot get Tracks, Sorry!", Toast.LENGTH_SHORT).show()

        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textAlbum: TextView
        var imageAlbum: ImageView

        init {
            textAlbum = itemView.findViewById(R.id.textAlbum)
            imageAlbum = itemView.findViewById(R.id.imageAlbum)
        }
    }
}