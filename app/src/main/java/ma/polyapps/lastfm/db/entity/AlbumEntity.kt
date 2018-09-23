package ma.polyapps.lastfm.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import ma.polyapps.lastfm.db.converter.TracksConverters
import java.io.Serializable


/**
 * Created by Elmehdi Mellouk on 22/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */

@Entity(tableName = "album")
class AlbumEntity(val name: String,
                  @PrimaryKey
                  val mbid: String,
                  val url: String,
                  val artist: String,
                  val releaseDate: String,
                  @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
                  val image: ByteArray,
                  @TypeConverters(TracksConverters::class)
                  val tracks: List<TrackEntity>
): Serializable

