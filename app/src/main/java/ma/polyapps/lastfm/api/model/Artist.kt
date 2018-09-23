package ma.polyapps.lastfm.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Elmehdi Mellouk on 21/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class Artist(
        val name: String,
        val mbid: String,
        val url: String,
        @SerializedName("image") val images: List<Image>,
        val streamable: String
    ): Serializable