package ma.polyapps.lastfm.api.model

import java.io.Serializable


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class Track(
        val name: String,
        val duration: Int = 0,
        val mbid: String?,
        val url: String?,
        val artist: Artist
): Serializable