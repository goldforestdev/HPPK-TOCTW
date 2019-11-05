package com.hppk.toctw.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hppk.toctw.data.model.ChildStampJoin
import com.hppk.toctw.data.model.Stamp
import com.hppk.toctw.data.source.ChildStampDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocalChildStampDao : ChildStampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun save(childStampJoin: ChildStampJoin): Completable

    @Query(
        """
           SELECT * FROM Stamp
           INNER JOIN child_stamp_join
           ON stamp.id=child_stamp_join.id
           WHERE child_stamp_join.name=:name
           """
    )
    override fun getStampsForChild(name: String): Single<List<Stamp>>
}