package ma.polyapps.lastfm.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */

@Entity(tableName = "artist")
class ArtistEntity(
        @PrimaryKey
        val name: String,
        val mbid: String,
        val url: String,
        val image: String,
        val streamable: String? = null
): Serializable