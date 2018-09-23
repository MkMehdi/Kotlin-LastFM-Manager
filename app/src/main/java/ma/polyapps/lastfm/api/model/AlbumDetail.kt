package ma.polyapps.lastfm.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class AlbumDetail(
        val name: String,
        val mbid: String,
        val url: String,
        val artist: String,
        val releaseDate: String,
        @SerializedName("image") val images: List<Image>,
        val tracks: Tracklist
):Serializable
