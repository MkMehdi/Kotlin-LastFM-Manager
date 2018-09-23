package ma.polyapps.lastfm.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ma.polyapps.lastfm.R
import ma.polyapps.lastfm.db.entity.TrackEntity


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class TrackStoredAdapter(private val dataList: List<TrackEntity>) :
        RecyclerView.Adapter<TrackStoredAdapter.TrackViewHolder>() {

    var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackStoredAdapter.TrackViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_view_track, parent, false)
        return TrackStoredAdapter.TrackViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TrackStoredAdapter.TrackViewHolder, position: Int) {
        holder.textTitle.text = dataList[position].name
        holder.textDuration.text = "${dataList[position].duration / 60} minutes"
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

     class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textTitle: TextView
        var textDuration: TextView

        init {
            textTitle = itemView.findViewById(R.id.textTitle)
            textDuration = itemView.findViewById(R.id.textDuration)
        }
    }
}