package com.example.pendaftaranpondokpesantrenapp.repository

import com.example.pendaftaranpondokpesantrenapp.dao.RegistrationDao
import com.example.pendaftaranpondokpesantrenapp.model.Registration
import kotlinx.coroutines.flow.Flow


class RegistrationRepository (private val registrationDao: RegistrationDao) {
    val allRegistrations: Flow<List<Registration>> = registrationDao.getAllRegistration()
    suspend fun insertRegistration(registration: Registration){
        registrationDao.insertRegistration(registration)
    }

    suspend fun deleteRegistration(registration: Registration) {
        registrationDao.deleteRegistration(registration)
    }

    suspend fun updateRegistration(registration: Registration) {
        registrationDao.updateRegistration(registration)
    }
}