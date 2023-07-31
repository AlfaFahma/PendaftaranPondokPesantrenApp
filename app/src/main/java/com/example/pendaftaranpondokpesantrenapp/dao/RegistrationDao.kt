package com.example.pendaftaranpondokpesantrenapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pendaftaranpondokpesantrenapp.model.Registration
import kotlinx.coroutines.flow.Flow


@Dao
interface RegistrationDao {
    @Query("SELECT * FROM 'registration_table'ORDER BY name ASC")
    fun getAllRegistration():Flow<List<Registration>>

    @Insert
    suspend fun insertRegistration(registration: Registration)

    @Delete
    suspend fun deleteRegistration(registration: Registration)

    @Update fun updateRegistration(registration: Registration)
}