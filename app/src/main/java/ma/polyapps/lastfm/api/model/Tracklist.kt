package ma.polyapps.lastfm.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Elmehdi Mellouk on 23/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class Tracklist(
        @SerializedName("track") val tracks: List<Track>
): Serializable