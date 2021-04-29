package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decagon.facilitymanagementapp_group_two.model.data.RatingData
import com.decagon.facilitymanagementapp_group_two.model.data.coverter.Converters
import com.decagon.facilitymanagementapp_group_two.model.data.coverter.RatingConverter
import com.decagon.facilitymanagementapp_group_two.model.data.database.dao.*
import com.decagon.facilitymanagementapp_group_two.model.data.database.remotekeys.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.*

/**
 * This is our MainDatabase class. The database consists of three tables/entities
 * which are used for storing Comments, Requests and Users separately.
 */

@Database(
    entities = [
        Comments::class,
        Request::class,
        UserData::class,
        AuthResponse::class,
        Feeds::class,
        Complaints::class,
        ApartComplaints::class,
        ApplianceComplaints::class,
        OthersComplaints::class,
        RemoteKeys::class,
        ComplainRemoteKeys::class,
        ApartmentRemoteKeys::class,
        ApplianceRemoteKeys::class,
        OthersRemoteKeys::class,
        RatingData::class
               ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class, RatingConverter::class)
abstract class CentralDatabase : RoomDatabase() {

    abstract val commentDao: CommentsDao
    abstract val requestDao: RequestDao
    abstract val userDao: UserDao
    abstract val authResponseDao: AuthResponseDao
    abstract val complaintsDao: ComplaintsDao
    abstract val apartComplainsDao: ApartComplainsDao
    abstract val applianceCompDao: ApplianceCompDao
    abstract val othersComplainsDao: OthersComplainsDao
    abstract val feedDao : FeedDao
    abstract val remoteKeysDao: RemoteKeysDao
    abstract val complainRemoteKeysDao: ComplainRemoteKeysDao
    abstract val apartRemoteKeysDao: ApartRemoteKeysDao
    abstract val applianceRemoteKeyDao: ApplianceRemoteKeyDao
    abstract val othersRemoteKeysDao: OthersRemoteKeysDao
    abstract val ratingDao : RatingDao
}
